package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/12/1.
 */

public class DBHelper extends SQLiteOpenHelper{
    public static DBHelper instance;

    public DBHelper(Context context) {
        super(context,SQLiteInfo.SQLTABLE,null,SQLiteInfo.VERSION);
    }
    //使用单利模式
    public static DBHelper getInstance(Context context){
        if (instance==null){
            synchronized (DBHelper.class){
                if (instance==null){
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists" +
                SQLiteInfo.TABLE+"("+SQLiteInfo._ID+""+
                "integer primary key autoincrement," +
                SQLiteInfo.NAME+"text not null, "+SQLiteInfo.AGE+" integer )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
