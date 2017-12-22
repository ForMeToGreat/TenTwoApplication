package com.example.servicedownfile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017/12/22.
 */

public class DBHelper extends SQLiteOpenHelper{

    private String TAG = "tag";
    private static DBHelper dbHelper;

    public DBHelper(Context context) {
        super(context,SQLInfo.DBNAME,null,SQLInfo.VERSION);
    }
    //定义一个方法,用来直接获取DBHelper的对象
    public static DBHelper getHelper(Context context){
        if (dbHelper==null){
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists" +
                SQLInfo.TABLENAME+"("+ SQLInfo.FILEID+"integer primary key, "+
                SQLInfo.NAME+"text not null ,"+
                SQLInfo.PATH+"text not null ,"+
                SQLInfo.FILEPATH+"text not null ,"+
                SQLInfo.TOTALSIZE+"text not null ,"+
                SQLInfo.DOWNSIZE+"text not null )";
        Log.i(TAG, "onCreate: =========="+sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
