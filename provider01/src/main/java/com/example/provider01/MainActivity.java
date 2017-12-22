package com.example.provider01;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String path = "content://us.mifeng/user";
    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取接收者对象
        resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(path),null,null,null,null,null);
    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.query:
                queryData();
                break;
            case R.id.insert:
                insertData();
                break;
            case R.id.update:
                updateData();
                break;
            case R.id.delete:
                deleteData();
                break;
        }
    }
    private void deleteData() {

        int delete = resolver.delete(Uri.parse(path+"/'小胖'"), SQLiteInfo.NAME + "= ?",
                new String[]{"小胖"});
        if (delete>0){
            queryData();
        }
    }

    private void updateData() {
        ContentValues values = new ContentValues();
        values.put(SQLiteInfo.NAME,"小胖");
        values.put(SQLiteInfo.AGE,21);
        int update = resolver.update(Uri.parse(path), values,
                SQLiteInfo._ID + " = ?", new String[]{6 + ""});
        if (update>0){
            queryData();
        }
    }


    private void insertData() {
        ContentValues values = new ContentValues();
        values.put(SQLiteInfo.NAME,"李四");
        values.put(SQLiteInfo.AGE,20);
        Uri insert = resolver.insert(Uri.parse(path), values);
        if (insert!=null){
            queryData();
        }
    }
    private void queryData() {
        Cursor cursot = resolver.query(Uri.parse(path), null, null, null, null, null);
        if (cursot!=null){
            while (cursot.moveToNext()){
                int id = cursot.getInt(cursot.getColumnIndex(SQLiteInfo._ID));
                String name = cursot.getString(cursot.getColumnIndex(SQLiteInfo.NAME));
                int age = cursot.getInt(cursot.getColumnIndex(SQLiteInfo.AGE));
                Log.i("tag","====id="+id+"  name="+name+"  age="+age);
                //将数据存放到List集合中，然后显示到ListView上
            }
        }

    }
}
