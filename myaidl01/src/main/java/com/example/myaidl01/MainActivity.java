package com.example.myaidl01;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String TAG = "tag";
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.UserBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private MyService.UserBinder binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void binder(View view) {
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }

    public void chuli(View view) throws RemoteException {
        String str = binder.getStr();
        int add = binder.add(111,222);
        Log.d(TAG,"chuli: ======add:"+add+" ======str:"+str);
        binder.basicTypes(12,12,true,12,12,"12");
    }
}
