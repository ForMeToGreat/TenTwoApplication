package com.example.myaidl02;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myaidl01.UserAidl;

public class MainActivity extends AppCompatActivity {
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            userAidl = UserAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private UserAidl userAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onbind(View view) {
        Intent intent = new Intent("aidl.us");
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }

    public void chuli(View view) throws RemoteException {
        userAidl.basicTypes(12,21,true,12,21,"asd");
        int add = userAidl.add(11, 12);
        String str = userAidl.getStr();
        Log.d("tag",str+"========"+add);
    }
}
