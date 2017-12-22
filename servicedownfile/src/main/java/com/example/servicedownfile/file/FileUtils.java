package com.example.servicedownfile.file;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/12/22.
 */

public class FileUtils {
    public static boolean isSdCard(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
    public static File getRootFile(){
        if (!isSdCard()){
            return null;
        }
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File rootFile = new File(root,"mkt");
        if (!rootFile.exists()){
            rootFile.mkdirs();
        }
        return rootFile;
    }
}
