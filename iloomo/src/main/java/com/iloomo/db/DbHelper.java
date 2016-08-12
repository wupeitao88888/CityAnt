package com.iloomo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper extends
		android.database.sqlite.SQLiteOpenHelper {

	public DbHelper(Context context) {
		super(context, "Ntalker_Contrasted", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table top(id Integer  primary key autoincrement,kfuserid varchar(30),sex varchar(20),province varchar(20),city varchar(20),status varchar(20),updaterecenttime varchar(50),isreport  varchar(50),userid varchar(80),param6 varchar(20),usertype varchar(20),iscrm varchar,allocation varchar(50),starcount varchar(50),useridcrm varchar(30),staytimelong varchar,returncount varchar,coupon varchar,sessionstate varchar,stat_buycount varchar,stat_count varchar,stat_lasttime varchar,stat_lastkfname varchar(30),username varchar(30),saleindex varchar,level varchar,logintime varchar,relavancy varchar,count varchar,time varchar,lastkfname varchar(20),age varchar,credit varchar,video varchar,source varchar,isdelete varchar)");
		db.execSQL("create table chatinfo(id integer primary key autoincrement,customerid varchar(200),customername varchar(200),messageid varchar(200),customericon varchar(200),type varchar(20),content varchar(1000),time varchar(50),isComMeg varchar(20),kfuserid varchar(200),length varchar(200),size varchar(20),title varchar(500),chatid varchar(200),url varchar(500),remark varchar(200),sendstatus varchar(20),location varchar(100))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
