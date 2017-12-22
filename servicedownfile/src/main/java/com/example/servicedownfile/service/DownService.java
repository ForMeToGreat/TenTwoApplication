package com.example.servicedownfile.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownService extends Service {

    public DownService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
