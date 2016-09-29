package com.example.dllo.thirtysixkr.tools.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dllo on 16/8/24.
 */
public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // BLOB : 数据库存储二进制文件的格式
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DBValues.TABLE_NAME
                +" (id integer primary key autoincrement,"
                +DBValues.TABLE_HISTORY_CONTENT +" text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
