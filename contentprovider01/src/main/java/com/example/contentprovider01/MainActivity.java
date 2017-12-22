package com.example.contentprovider01;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    //定义路径，打开通话记录的路径
    private String callLogs = "content://call_log/calls";
    private String[] permissions = {Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG};
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        listView = (ListView) findViewById(R.id.listview1);
        //接收者对象
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(callLogs),null,null,null,null);
        //创建一般适配器
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.call_item,cursor,
                //查询的字段
                new String[]{CallLog.Calls.CACHED_NAME,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.CACHED_NUMBER_TYPE,
                        CallLog.Calls.DATE},
                //控件Id
                new int[]{R.id.name,R.id.number,R.id.numberType,R.id.date},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                );
        listView.setAdapter(adapter);
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 11);
                return;
            } else {
                //适配小米机型
                AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
                int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_READ_PHONE_STATE, /*Process.myUid()*/0, getPackageName());
                if (checkOp != AppOpsManager.MODE_ALLOWED) {
                    requestPermissions(permissions, 11);
                    return;
                }
            }         
        }
    }
}
