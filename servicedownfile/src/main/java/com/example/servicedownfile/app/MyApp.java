package com.example.servicedownfile.app;

import android.app.Application;
import android.content.Intent;

import com.example.servicedownfile.service.DownService;

/**
 * Created by Administrator on 2017/12/22.
 */

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this,DownService.class);
        startService(intent);
    }
}
