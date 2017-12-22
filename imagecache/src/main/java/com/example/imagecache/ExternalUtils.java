package com.example.imagecache;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/11/29.
 */

public class ExternalUtils {
    private static String fileName = "images";
    public static boolean isSdUsed(){
        //获取外部存储变量的状态
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }
    private static File getRootFile(){
        File sdFile = Environment.getExternalStorageDirectory();
        File fileDir = new File(sdFile,fileName);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        return fileDir;
    }
    public static void wrateData(String imgName,byte[]b){
        if (!isSdUsed()){
            return;
        }
        File fileDir = getRootFile();
        File file = new File(fileDir,imgName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(b,0,b.length);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static byte[] readData(String imgName){
        if (isSdUsed()){
            return null;
        }
        File fileDir = getRootFile();
        File file = new File(fileDir,imgName);
        try {
            FileInputStream fos = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;
            byte[]by = new byte[1024];
            while ((len=fos.read(by))!=-1){
                bos.write(by,0,len);
            }
            byte[] b = bos.toByteArray();
            bos.flush();
            bos.close();
            fos.close();
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
