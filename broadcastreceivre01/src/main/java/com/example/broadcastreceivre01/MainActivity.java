package com.example.broadcastreceivre01;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import broad.MyBroadCast;

public class MainActivity extends AppCompatActivity {

    private MyBroadCast broad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //动态注册广播
        broad = new MyBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("us.mifeng.buba");
        registerReceiver(broad,filter);
    }

    public void send(View view) {
        Intent intent = new Intent();
        intent.setAction("us.mifeng.buba");
        //静态广播注册
        sendBroadcast(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (broad!=null){
            unregisterReceiver(broad);
        }
    }
}
