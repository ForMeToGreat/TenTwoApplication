package com.example.content01;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import db.DBHelper;
import db.SQLiteInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        //创建ContentValues对象
        ContentValues values = new ContentValues();
        for (int i=0;i<5;i++){
            values.put(SQLiteInfo.NAME,"小可爱"+i);
            values.put(SQLiteInfo.AGE,"2"+i);
            db.insert(SQLiteInfo.TABLE,null,values);
            values.clear();
        }
        db.close();
    }
}
