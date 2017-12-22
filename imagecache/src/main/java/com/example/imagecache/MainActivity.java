package com.example.imagecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    private String path = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3965705221,2010595691&fm=27&gp=0.jpg";
    private ImageView image;
    private String imgName = "img01.jpg";
    // LinkedHashMap  有序的存放，插入和删除较快
    private LinkedHashMap<String, SoftReference<Bitmap>> memory = new LinkedHashMap<>();
    private MyLruCache lruCache;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            image.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.img);
        //获取手机的运行内存
        int memorySize = (int) (Runtime.getRuntime().maxMemory() / 8);
        lruCache = new MyLruCache(memorySize);
    }

    public void click(View view) {
        //首先从引用中加载图片
        Bitmap bitmap = getBitmapCache();
        if (bitmap == null) {
            MyThread thread = new MyThread();
            thread.start();
        }
    }

    //创建手机的高速缓冲区
    class MyLruCache extends LruCache<String, Bitmap> {

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         *                因为缓存不覆盖{ @link # sizeOf },这是缓存中的条目的最大数量。
         *                对于所有其他缓存,这是最大的和这个cachefor缓存条目的大小不覆盖
         *                { @link # sizeOf },
         *                这是缓存中的条目的最大数量。
         *                对于所有其他缓存,这是最大的和在这个缓存条目的大小
         */
        public MyLruCache(int maxSize) {
            super(maxSize);
        }

        //计算图片大小
        @Override
        protected int sizeOf(String key, Bitmap value) {
            //value.getByteCount();
            return value.getRowBytes() * value.getHeight();
        }

        /**
         * @param evicted  驱逐，被驱赶的意思，也就说当内存不足,要释放一些空间的时候为true,否则为false
         * @param key      图片的名字或者地址
         * @param oldValue 要被移除的图片
         * @param newValue 新添加进去的图片
         */
        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            if (evicted) {
                SoftReference<Bitmap> reference = new SoftReference<Bitmap>(oldValue);
                memory.put(key, reference);
            }
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            HttpUtils utils = new HttpUtils();
            byte[] by = utils.getResult(MainActivity.this, path);
            Bitmap bitmap = BitmapFactory.decodeByteArray(by, 0, by.length);
            //向引用中添加一份?
            if (bitmap != null) {
                lruCache.put(path, bitmap);
                Message msg = handler.obtainMessage();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        }
    }

    private Bitmap getBitmapCache() {
        //首先从强引用去
        Bitmap bitmap = lruCache.get(path);
        if (bitmap == null) {
            //从软引用去
            SoftReference<Bitmap> soft = memory.get(path);
            if (soft != null) {
                bitmap = soft.get();
                lruCache.put(path, bitmap);
                memory.remove(path);
            } else {
                //从本地中取
                byte[] by = ExternalUtils.readData(imgName);
                if (by != null) {
                    bitmap = BitmapFactory.decodeByteArray(by, 0, by.length);
                    if (bitmap != null) {
                        lruCache.put(path, bitmap);
                    }
                }
            }
        }
        return bitmap;
    }
}
