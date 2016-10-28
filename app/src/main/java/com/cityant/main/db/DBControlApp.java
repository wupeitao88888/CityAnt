package com.cityant.main.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;

import com.cityant.main.bean.UserInfoData;
import com.hyphenate.easeui.bean.ChatMsgEntity;
import com.hyphenate.easeui.bean.LoginUserInfoData;
import com.hyphenate.easeui.bean.MessageList;
import com.hyphenate.easeui.bean.MyFrends;
import com.hyphenate.easeui.db.DBControl;
import com.hyphenate.easeui.db.DbHelper;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.db.DBbase;
import com.iloomo.db.DatabaseManager;
import com.iloomo.db.DbHelperBase;
import com.iloomo.utils.L;

import java.util.ArrayList;
import java.util.List;

//import com.iloomo.brush.bean.ExeTime;
//import com.iloomo.brush.bean.MpData;
//import com.iloomo.brush.bean.PackageData;
//import com.iloomo.brush.bean.VpnData;

/**
 * Created by wupeitao on 16/3/17.
 */
public class DBControlApp extends DBControl {

    public static String DB_VERSION = "14";

    public DBControlApp(Context context, DbHelperBase DbHelperBase) {
        super(context, DbHelperBase);
    }

    public static DBControlApp dbControl;

    public static DBControlApp getInstance(Context context) {
        if (dbControl == null) {
            DbHelper dbHelper = new DbHelper(context);
            dbControl = new DBControlApp(context, dbHelper);
        }
        return dbControl;
    }


    public void insertUserInfo(UserInfoData userInfoData) {
        try {
            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
                    .openDatabase();
            writableDatabase
                    .execSQL(
                            "insert into " + DbHelper.USERINFO +
                                    "(" +
                                    DbHelper.TOKEN + "," +
                                    DbHelper.QRCODE + "," +
                                    DbHelper.SEX + "," +
                                    DbHelper.BOTHDAY + "," +
                                    DbHelper.ARWA +
                                    ")values (?,?,?,?,?)",
                            new Object[]{
                                    enCode(userInfoData.getToken()),
                                    enCode(userInfoData.getQrcode()),
                                    enCode(userInfoData.getSex()),
                                    enCode(userInfoData.getBirthday()),
                                    enCode(userInfoData.getArea())});
            DatabaseManager.getInstance().closeDatabase();
            updateLoginInfo(userInfoData.getToken(), userInfoData.getUser_name(), userInfoData.getUser_avar());
        } catch (Exception e) {
            updateUserInfo(userInfoData);
        }
    }

    public void updateUserInfo(UserInfoData userInfoData) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.QRCODE, enCode(userInfoData.getQrcode()));
        values.put(DbHelper.SEX, enCode(userInfoData.getSex()));
        values.put(DbHelper.BOTHDAY, enCode(userInfoData.getBirthday()));
        values.put(DbHelper.ARWA, enCode(userInfoData.getArea()));
        db.update(DbHelper.USERINFO, values, "token=?",
                new String[]{enCode(userInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
        updateLoginInfo(userInfoData.getToken(), userInfoData.getUser_name(), userInfoData.getUser_avar());
    }

    public void updateUserInfoBothday(String BOTHDAY) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
//        values.put(DbHelper.QRCODE, enCode(userInfoData.getQrcode()));
//        values.put(DbHelper.SEX, enCode(userInfoData.getSex()));
        values.put(DbHelper.BOTHDAY, enCode(BOTHDAY));
//        values.put(DbHelper.ARWA, enCode(userInfoData.getArea()));
        db.update(DbHelper.USERINFO, values, "token=?",
                new String[]{enCode(MYAppconfig.loginUserInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateUserInfoSex(String sex) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.QRCODE, enCode(sex));
//        values.put(DbHelper.SEX, enCode(userInfoData.getSex()));
//        values.put(DbHelper.BOTHDAY, enCode(BOTHDAY));
//        values.put(DbHelper.ARWA, enCode(userInfoData.getArea()));
        db.update(DbHelper.USERINFO, values, "token=?",
                new String[]{enCode(MYAppconfig.loginUserInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateUserInfoArwa(String arwa) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
//        values.put(DbHelper.QRCODE, enCode(sex));
//        values.put(DbHelper.SEX, enCode(userInfoData.getSex()));
//        values.put(DbHelper.BOTHDAY, enCode(BOTHDAY));
        values.put(DbHelper.ARWA, enCode(arwa));
        db.update(DbHelper.USERINFO, values, "token=?",
                new String[]{enCode(MYAppconfig.loginUserInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
    }

    public UserInfoData selectUserinfo() {
        try {
            SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                    .readDatabase();
            Cursor cursor = readableDatabase.rawQuery(
                    "select * from " + DbHelper.USERINFO + " where token=?",
                    new String[]{MYAppconfig.loginUserInfoData.getToken()});
            UserInfoData userInfoData = new UserInfoData();
            while (cursor.moveToNext()) {
                userInfoData.setToken(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.TOKEN))));
                userInfoData.setBirthday(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.BOTHDAY))));
                userInfoData.setQrcode(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.QRCODE))));
                userInfoData.setArea(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.ARWA))));
                userInfoData.setSex(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.SEX))));
            }

            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return userInfoData;
        } catch (Exception e) {
            return new UserInfoData();
        }

    }


}
