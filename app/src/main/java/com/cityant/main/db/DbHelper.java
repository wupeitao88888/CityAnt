package com.cityant.main.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.iloomo.db.DbHelperBase;

/**
 * Created by wupeitao on 16/3/17.
 */
public class DbHelper extends DbHelperBase {
    public DbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        db.execSQL("create table logininfo(token varchar(50),mobile varchar(30) primary key,user_name varchar(30),user_avar varchar(50))");
        db.execSQL("create table lastuser(mobile varchar(30) primary key,password varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        db.execSQL("drop table if exists logininfo");
        db.execSQL("drop table if exists lastuser");
        onCreate(db);
    }
}
