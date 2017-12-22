package com.example.imagecache;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/11/29.
 */

public class HttpUtils {
    public static boolean isConnected(Context context){
        //创建连接对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info!=null){
            return info.isConnected();
        }
        return false;
    }
    //写一个请求网络数据的方法
    public byte[] getResult(Context context,String path){
        if (!isConnected(context)){
            //网络链接失败
            return null;
        }
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(false);
            //判断服务器是否请求成功
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = conn.getInputStream();
                int len = 0;
                byte[]by = new byte[1024];
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                while ((len=in.read(by))!=-1){
                    out.write(by,0,len);
                }
                byte[]bytes = out.toByteArray();
                out.close();
                in.close();
                return bytes;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
