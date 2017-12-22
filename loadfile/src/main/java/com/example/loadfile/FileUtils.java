package com.example.loadfile;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/12/21.
 */

public class FileUtils {
    public static boolean isSD(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
    public static File saveFile(){
        File saveRoot = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File rootFile = new File(saveRoot,"mkt");
        if (!rootFile.exists()){
            rootFile.mkdirs();
        }
        return rootFile;
    }
}
