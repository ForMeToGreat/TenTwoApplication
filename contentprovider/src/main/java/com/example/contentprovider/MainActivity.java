package com.example.contentprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //首先先拿到短信暴露的路径
    private String path = "content://sms/";
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPremission();
        listview = (ListView) findViewById(R.id.listview);
        //学习游标适配器
        //获取游标对象
        //首先获取广播接受者对象
        ContentResolver resolver = getContentResolver();

        //根据广播的接受者对象去获取游标
        Cursor cursor = resolver.query(Uri.parse(path),null,null,null,null,null);
        //添加游标适配器
        MyCursorAdapter adapter = new MyCursorAdapter(this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listview.setAdapter(adapter);


    }

    /**
     * 是否需要70
     */
    private boolean isNeedAdapter(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
    //添加权限
    private void addPremission(){
        String[]permission = {Manifest.permission.READ_SMS};
         requestPermissions(permission,RESULT_OK);
    }

    class MyCursorAdapter extends CursorAdapter{

        public MyCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        //创建视图,并返回
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view= LayoutInflater.from(context).inflate(R.layout.sms_item,null);
            ViewHolder vh = new ViewHolder(view);
            view.setTag(vh);
            return view;
        }

        //将数据绑定到视图上
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder vh = (ViewHolder) view.getTag();
            String address = cursor.getString(cursor.getColumnIndex("address"));
            vh.address.setText(address);

            int date = cursor.getInt(cursor.getColumnIndex("date"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dd = new Date(date);
            String dateValue = format.format(dd);
            vh.date.setText(dateValue);

            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String typeValue = "";
            if (type==1){
                typeValue = "接收";
            }else{
                typeValue = "发送";
            }
            vh.send.setText(typeValue);

            String body = cursor.getString(cursor.getColumnIndex("body"));
            vh.body.setText(body);

        }

        class ViewHolder{
            TextView address,date,send,body;
            public ViewHolder(View view) {
                address = (TextView) view.findViewById(R.id.address);
                date = (TextView) view.findViewById(R.id.date);
                send = (TextView) view.findViewById(R.id.send);
                body = (TextView) view.findViewById(R.id.body);
            }
        }
    }
}
