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

    public static final String LOGININFO = "create table logininfo(token varchar(50),mobile varchar(30) primary key,user_name varchar(30),user_avar varchar(50))";
    public static final String LASTUSER = "create table lastuser(mobile varchar(30) primary key,password varchar(30))";

    public static final String DELETE_LOGININFO = "drop table if exists logininfo";
    public static final String DELETE_LASTUSER = "drop table if exists lastuser";

    public static final String MYFREDNS_NAME="myfends";
    public static final String FREND_ID="friend_id";
    public static final String USER_NAME="user_name";
    public static final String USER_AVAR="user_avar";
    public static final String MYFRENDS = "create table "+DbHelper.MYFREDNS_NAME+"("+FREND_ID+" varchar(30) primary key,"+USER_NAME+" varchar(30),"+USER_AVAR+" varchar(150))";
    public static final String DELETE_MYFRENDS = "drop table if exists "+DbHelper.MYFREDNS_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        db.execSQL(LOGININFO);
        db.execSQL(LASTUSER);
        db.execSQL(MYFRENDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        db.execSQL(DELETE_LOGININFO);
        db.execSQL(DELETE_LASTUSER);
        db.execSQL(DELETE_MYFRENDS);
        onCreate(db);
    }
}
