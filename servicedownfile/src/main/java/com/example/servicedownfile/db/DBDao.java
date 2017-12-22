package com.example.servicedownfile.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.servicedownfile.bean.FileInfo;

/**
 * Created by Administrator on 2017/12/22.
 */

public class DBDao {

    private DBHelper dbHelper;

    public DBDao(Context context){
        dbHelper = DBHelper.getHelper(context);
    }
    public void insertInfo(FileInfo info){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(SQLInfo.TABLENAME,null,"where "+SQLInfo.FILEID+" = ? and "
        +SQLInfo.PATH + " = ? ",new String[]{info.getFileId(),info.getPath()},null,null,null);
    }
}
