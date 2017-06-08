package com.example.jisung.myself;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jisung on 2017-06-08.
 */

public class todoDB extends SQLiteOpenHelper {
    public todoDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists toDo(" +
                "title text, " +
                "createDate text," +
                "time text," +
                "clear boolean," +
                "alarm boolean," +
                "id integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists toDo";
        db.execSQL(sql);
        onCreate(db);
    }
}
