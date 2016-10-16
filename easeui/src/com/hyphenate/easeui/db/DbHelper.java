package com.hyphenate.easeui.db;

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

    public static final String LOGININFO = "CREATE TABLE IF NOT EXISTS logininfo(user_id varchar(50),token varchar(50),mobile varchar(30) primary key,user_name varchar(30),user_avar varchar(50))";
    public static final String LASTUSER = "CREATE TABLE IF NOT EXISTS lastuser(mobile varchar(30) primary key,password varchar(30))";

    public static final String DELETE_LOGININFO = "drop table if exists logininfo";
    public static final String DELETE_LASTUSER = "drop table if exists lastuser";

    public static final String MYFREDNS_NAME = "myfends";
    public static final String TOKEN = "token";//token
    public static final String USER_NAME = "user_name";
    public static final String USER_AVAR = "user_avar";
    public static final String USER_ID = "user_id";//环信id
    public static final String M_USER_TOKEN = "m_user_token";
    public static final String MYFRENDS = "CREATE TABLE IF NOT EXISTS " + DbHelper.MYFREDNS_NAME + "(" +
            "id INTEGER primary key AUTOINCREMENT," +
            USER_ID + " varchar(30)," +
            USER_NAME + " varchar(30)," +
            USER_AVAR + " varchar(150)," +
            M_USER_TOKEN + " varchar(80))";
    public static final String DELETE_MYFRENDS = "drop table if exists " + DbHelper.MYFREDNS_NAME;

    public static final String CONVERSATIONLIST_NAME = "conversationlist";
    public static final String LAST_MSG = "last_msg";
    public static final String LAST_MSG_TIME = "last_msg_time";
    public static final String UNREAD_MSG_COUNT = "last_msg_count";
    public static final String LAST_MSG_ID = "last_msg_id";
    public static final String CHAT_TYPE = "chat_type";
    public static final String CREATE_CONVERSATIONLIST = "CREATE TABLE IF NOT EXISTS " + CONVERSATIONLIST_NAME + "(" +
            USER_ID + " varchar(30) primary key," +
            USER_NAME + " varchar(30)," +
            USER_AVAR + " varchar(150)," +
            LAST_MSG + " varchar(120)," +
            LAST_MSG_TIME + " varchar(50)," +
            UNREAD_MSG_COUNT + " varchar(50)," +
            CHAT_TYPE + " varchar(50)," +
            M_USER_TOKEN + " varchar(80)," +
            LAST_MSG_ID + " varchar(80))";

    public static final String DELETE_CONVERSATIONLIST = "drop table if exists " + DbHelper.CONVERSATIONLIST_NAME;

    public static final String CHAT_NAME = "chat_data";
    public static final String TYPE = "type";
    public static final String MOBILE = "mobile";
    public static final String MESSAGE = "message";
    public static final String MESSAGEID = "messageid";
    public static final String TIME = "time";
    public static final String STREET = "street";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String DURATION = "duration";
    public static final String IMGURL = "imgurl";
    public static final String STATUS = "status";
    public static final String VOICESTATUS = "voicestatus";
    public static final String VOICEURL = "voiceurl";
    public static final String VOICELENTH = "voicelenth";
    public static final String VOICEPLAY = "voiceplay";
    public static final String GROUPID = "groupid";

    public static final String CREATE_CHAT = "CREATE TABLE IF NOT EXISTS " + CHAT_NAME + "(" +
            USER_ID + " varchar(30)," +
            USER_NAME + " varchar(50)," +
            USER_AVAR + " varchar(50)," +
            MOBILE + " varchar(10)," +
            MESSAGE + " varchar(300)," +
            MESSAGEID + " varchar(50) primary key," +
            TYPE + " varchar(50)," +
            TIME + " varchar(50)," +
            STREET + " varchar(50)," +
            LONGITUDE + " varchar(50)," +
            LATITUDE + " varchar(50)," +
            DURATION + " varchar(50)," +
            IMGURL + " varchar(50)," +
            STATUS + " varchar(50)," +
            VOICESTATUS + " varchar(50)," +
            VOICEURL + " varchar(50)," +
            VOICELENTH + " varchar(50)," +
            VOICEPLAY + " varchar(50)," +
            M_USER_TOKEN + " varchar(80)," +
            GROUPID + " varchar(80)," +
            CHAT_TYPE + " varchar(50))";
    public static final String DELETE_CHAT = "drop table if exists " + DbHelper.CHAT_NAME;
    private static final String USERINFO = "userinfo";
    private static final String QRCODE = "qrcode";
    private static final String SEX = "sex";
    private static final String BOTHDAY = "birthday";
    private static final String ARWA = "area";
    public static final String CREATE_USERINFO = "CREATE TABLE IF NOT EXISTS " + DbHelper.USERINFO + "(" +
            DbHelper.TOKEN + " varchar(50) primary key," +
            DbHelper.QRCODE + " varchar(50)," +
            DbHelper.SEX + " varchar(30)," +
            DbHelper.BOTHDAY + " varchar(30)," +
            DbHelper.ARWA+" varchar(50))";
    public static final String DELETE_USERINFO  = "drop table if exists " + DbHelper.USERINFO;
    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        db.execSQL(LOGININFO);
        db.execSQL(LASTUSER);
        db.execSQL(MYFRENDS);
        db.execSQL(CREATE_CONVERSATIONLIST);
        db.execSQL(CREATE_CHAT);
        db.execSQL(CREATE_USERINFO);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        db.execSQL(DELETE_LOGININFO);
        db.execSQL(DELETE_LASTUSER);
        db.execSQL(DELETE_MYFRENDS);
        db.execSQL(DELETE_CONVERSATIONLIST);
        db.execSQL(DELETE_CHAT);
        db.execSQL(DELETE_USERINFO);
        onCreate(db);
    }
}
