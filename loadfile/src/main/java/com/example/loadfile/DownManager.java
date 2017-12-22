package com.example.loadfile;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/12/21.
 */

public class DownManager {

    private String path;
    private final ThreadPoolExecutor pool;
    private String fileName;
    private File rootFile;
    //定义一个变量用来标识下载完成了多少
    private long downedSize = 0;
    //生命一个变量,用来标识下载中,暂停中
    private boolean isDowned = true;
    private RandomAccessFile raf;
    private int totalLength;
    private MyThread myThread;

    public DownManager(String path) {
        this.path = path;
        pool = new ThreadPoolExecutor(5, 5, 50, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000));
        fileName = path.substring(path.lastIndexOf("/") + 1);
        rootFile = FileUtils.saveFile();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            downLoadFile();
        }
    }

    //定义一个方法,用来下载数据
    private void downLoadFile() {

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            //conn.setRequestMethod("GET");
            File file = new File(rootFile, fileName);
            if (!file.exists()) {
                raf = new RandomAccessFile(file, "rad");
            }else{
                if (raf == null){
                    raf = new RandomAccessFile(file, "rad");
                    totalLength = conn.getContentLength();
                }else{
                    downedSize = file.length();
                    raf.seek(downedSize);
                    //设置网络数据下载位置
                    conn.setRequestProperty("Range","bytes="+downedSize+"-"+totalLength);
                    conn.connect();
                }
            }
            InputStream ins = conn.getInputStream();
            int len = 0;
            byte[]by = new byte[1024];
            long endTime = System.currentTimeMillis();
            while ((len=ins.read(by))!=-1 && isDowned){
                raf.write(by,0,len);
                downedSize+=len;
                if (System.currentTimeMillis()-endTime>500){
                    DecimalFormat format = new DecimalFormat("#0.00");
                    double value = downedSize/(totalLength*1.0);
                    String svalue = format.format(value*100)+"%";
                    Log.i("tag","=============="+svalue);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        if (myThread==null){
            this.isDowned = true;
            myThread = new MyThread();
            pool.execute(myThread);
        }
    }
    public void stop(){
        if (myThread!=null){
            this.isDowned = false;
            pool.remove(myThread);
            myThread = null;
        }
    }
}
