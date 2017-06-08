package com.example.jisung.myself;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jisung on 2017-06-08.
 */

public class manageDB {
    private static todoDB database = null;
    private static SQLiteDatabase myDB2 = null;
    private static manageDB mInstance = null;

    public final static manageDB getInstance(Context context) {
        if (mInstance == null) mInstance = new manageDB(context);
        return mInstance;
    }

    manageDB(Context context) {
        database = new todoDB(context, "myDB2", null, 1);
        myDB2 = database.getWritableDatabase();
    }

    public Boolean inserttoDo(toDo data){
        String sql = "insert into toDo values('";
        sql+=data.getTitle()+"','";
        sql+=data.getCreateDate()+"','";
        sql+=data.getTime()+"',";
        if(data.getClear())
            sql+=1+",";
        else
            sql+=0+",";
        if(data.getAlram())
            sql+=1+",";
        else
            sql+=0+",";
        sql+=data.id+")";
        Log.d("sql test",""+sql);
        myDB2.execSQL(sql);
        return true;
    }
    public ArrayList<toDo> selecttoDo_M() {
        ArrayList<toDo> data = new ArrayList<>();
        String sql ="select * from toDo order by  createDate asc, time asc";
        Cursor cursor = myDB2.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                String title = "", cretime = "", time = "";
                boolean clear, alarm;
                int id;
                title = cursor.getString(0);
                cretime = cursor.getString(1);
                time = cursor.getString(2);
                if (cursor.getInt(3) == 1)
                    clear = true;
                else
                    clear = false;
                if (cursor.getInt(4) == 1)
                    alarm = true;
                else
                    alarm = false;
                id = cursor.getInt(5);
                data.add(new toDo(title, cretime, time, clear, alarm, id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }
    public ArrayList<toDo> selecttoDo_A(){
        ArrayList<toDo> data = new ArrayList<>();
        String sql2 ="Select * from toDo where alarm=1 order by createDate asc,time asc";
        Cursor cursor = myDB2.rawQuery(sql2, null);

        if(cursor.moveToFirst()) {
            do {
                String title = "", cretime = "", time = "";
                boolean clear, alarm;
                int id;
                title = cursor.getString(0);
                cretime = cursor.getString(1);
                time = cursor.getString(2);
                if (cursor.getInt(3) == 1)
                    clear = true;
                else
                    clear = false;
                if (cursor.getInt(4) == 1)
                    alarm = true;
                else
                    alarm = false;
                id = cursor.getInt(5);
                data.add(new toDo(title, cretime, time, clear, alarm, id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }
    public void deleteAll(){
        String sql ="delete from toDo";
        myDB2.execSQL(sql);
    }
    public void delete(int id){
        Log.d("id find",id+"");
        String sql ="delete from toDo where id="+id;
        myDB2.execSQL(sql);
    }
}
