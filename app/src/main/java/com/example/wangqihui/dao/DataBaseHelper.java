package com.example.wangqihui.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by wangqihui on 2016/1/11.
 */
public class DataBaseHelper extends SQLiteOpenHelper {


    private static final  String TAG ="UserHelper";
    private static final  int VERSION=1;


    public  DataBaseHelper(Context context,String name ,SQLiteDatabase.CursorFactory factory ,int version){
        super(context, name, factory, VERSION);
    }

    public DataBaseHelper(Context context ,String name){
        this(context, name, VERSION);
    }

    public DataBaseHelper(Context context,String name,int version){
        this(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "create user_table");
        db.execSQL(" create table user_table( user_id integer PRIMARY KEY autoincrement ,userName varchar(20), password varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("drop table if exists user_table ");
         onCreate(db);
    }

}
