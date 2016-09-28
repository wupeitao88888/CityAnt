package com.cityant.main.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;

//import com.iloomo.brush.bean.ExeTime;
//import com.iloomo.brush.bean.MpData;
//import com.iloomo.brush.bean.PackageData;
//import com.iloomo.brush.bean.VpnData;
import com.cityant.main.bean.ChatMsgEntity;
import com.cityant.main.bean.LoginUserInfoData;
import com.cityant.main.bean.MessageList;
import com.cityant.main.bean.MyFrends;
import com.cityant.main.global.MYAppconfig;
import com.iloomo.db.DBbase;
import com.iloomo.db.DatabaseManager;
import com.iloomo.db.DbHelperBase;
import com.iloomo.utils.DesUtils;
import com.iloomo.utils.L;

import java.util.ArrayList;
import java.util.List;

import static com.cityant.main.db.DbHelper.CREATE_CONVERSATIONLIST;

/**
 * Created by wupeitao on 16/3/17.
 */
public class DBControl extends DBbase {

    public static String DB_VERSION = "14";

    public DBControl(Context context, DbHelperBase DbHelperBase) {
        super(context, DbHelperBase);
    }

    public static DBControl dbControl;

    public static DBControl getInstance(Context context) {
        if (dbControl == null) {
            DbHelper dbHelper = new DbHelper(context);
            dbControl = new DBControl(context, dbHelper);
        }
        return dbControl;
    }


    private String enCode(String str) {
//        String string = "";
//        try {
//            string = DesUtils.getInstance().encrypt(str);
//        } catch (Exception e) {
//        }
//        return string;
        L.e("插入数据：" + str);
        return str;
    }

    private String deCode(String str) {
//        String string = "";
//        try {
//            string = DesUtils.getInstance().decrypt(str);
//        } catch (Exception e) {
//        }
//        return string;
        L.e("得到数据：" + str);
        return str;
    }

    /***
     * logininfo++++++++++++++start+++++++++++++++++++++
     *
     * @param loginUserInfoData
     */
    public synchronized void insertLoginInfo(LoginUserInfoData loginUserInfoData) {
        try {
            SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                    .readDatabase();
            Cursor cursor = readableDatabase.rawQuery("select * from logininfo", new String[]{});
//        tb
            String token = null;
            while (cursor.moveToNext()) {
                token = deCode(cursor.getString(cursor.getColumnIndex("token")));
            }
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            if (!TextUtils.isEmpty(token)) {
                SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
                        .openDatabase();
                writableDatabase.execSQL("delete from logininfo");
                DatabaseManager.getInstance().closeDatabase();
            }
        } catch (Exception e) {

        }

        try {
            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
                    .openDatabase();
            writableDatabase
                    .execSQL(
                            "insert into logininfo(" + DbHelper.USER_ID + ",token,mobile,user_name,user_avar) values (?,?,?,?,?)",
                            new Object[]{enCode(loginUserInfoData.getUser_id()), enCode(loginUserInfoData.getToken()),
                                    enCode(loginUserInfoData.getMobile()),
                                    enCode(loginUserInfoData.getUser_name()),
                                    enCode(loginUserInfoData.getUser_avar())});
            DatabaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            updateLoginInfo(loginUserInfoData);
        }
    }

    public synchronized void updateLoginInfo(LoginUserInfoData loginUserInfoData) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.USER_ID, enCode(loginUserInfoData.getUser_id()));
        values.put("mobile", enCode(loginUserInfoData.getMobile()));
        values.put("user_name", enCode(loginUserInfoData.getUser_name()));
        values.put("user_avar", enCode(loginUserInfoData.getUser_avar()));
        db.update("logininfo", values, "token=?",
                new String[]{enCode(loginUserInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
    }


    public synchronized LoginUserInfoData selectLoginInfo(String token) {
        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                .readDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from logininfo where token=?",
                new String[]{enCode(token)});
        LoginUserInfoData loginUserInfoData = new LoginUserInfoData();
        while (cursor.moveToNext()) {
            loginUserInfoData.setToken(deCode(cursor.getString(cursor.getColumnIndex("token"))));
            loginUserInfoData.setMobile(deCode(cursor.getString(cursor.getColumnIndex("mobile"))));
            loginUserInfoData.setUser_name(deCode(cursor.getString(cursor.getColumnIndex("user_name"))));
            loginUserInfoData.setUser_avar(deCode(cursor.getString(cursor.getColumnIndex("user_avar"))));
            loginUserInfoData.setUser_id(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_ID))));
        }


        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return loginUserInfoData;
    }

    public synchronized LoginUserInfoData selectLoginInfo() {
        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                .readDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from logininfo",
                new String[]{});
        LoginUserInfoData loginUserInfoData = new LoginUserInfoData();
        while (cursor.moveToNext()) {
            loginUserInfoData.setToken(deCode(cursor.getString(cursor.getColumnIndex("token"))));
            loginUserInfoData.setMobile(deCode(cursor.getString(cursor.getColumnIndex("mobile"))));
            loginUserInfoData.setUser_name(deCode(cursor.getString(cursor.getColumnIndex("user_name"))));
            loginUserInfoData.setUser_avar(deCode(cursor.getString(cursor.getColumnIndex("user_avar"))));
            loginUserInfoData.setUser_id(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_ID))));
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        if (TextUtils.isEmpty(loginUserInfoData.getToken())) {
            loginUserInfoData = null;
        }
        return loginUserInfoData;
    }

    public synchronized void deleteLoginInfo(String token) {
        SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
                .openDatabase();
        //调用delete方法，删除数据
        writableDatabase.delete("logininfo", "token=?", new String[]{enCode(token)});
        DatabaseManager.getInstance().closeDatabase();
    }

    /**************************
     * end
     ******************************/

    /***
     * *******************start******************
     *
     * @param mobile
     * @param password
     */
    public synchronized void insertLastUser(String mobile, String password) {
        try {
            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
                    .openDatabase();
            writableDatabase
                    .execSQL(
                            "insert into lastuser(mobile,password) values (?,?)",
                            new Object[]{enCode(mobile),
                                    enCode(password)});
            DatabaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            updateLastUser(mobile, password);
        }
    }

    public synchronized void updateLastUser(String mobile, String password) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put("password", enCode(password));
        db.update("lastuser", values, "mobile=?",
                new String[]{enCode(mobile)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public synchronized void updateLastUser(String oldmobile, String mobile, String password) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put("password", enCode(password));
        values.put("mobile", enCode(mobile));
        db.update("lastuser", values, "mobile=?",
                new String[]{enCode(oldmobile)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public synchronized void deleteLastUser(String mobile) {
        SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
                .openDatabase();
        //调用delete方法，删除数据
        writableDatabase.delete("lastuser", "mobile=?", new String[]{enCode(mobile)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public synchronized Bundle selectLastUser() {
        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                .readDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from lastuser", new String[]{});
        Bundle bundle = new Bundle();
        while (cursor.moveToNext()) {
            bundle.putString("mobile", deCode(cursor.getString(cursor.getColumnIndex("mobile"))));
            bundle.putString("password", deCode(cursor.getString(cursor.getColumnIndex("password"))));
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return bundle;
    }

    /************
     * +++++++++++++++++end+++++++++++++++++++++++
     */
    /***
     * *******************我的好友start******************
     */
    /***
     * @param myFrends 会话列表
     */
    public synchronized void insertFrends(MyFrends myFrends) {

        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                .readDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + DbHelper.MYFREDNS_NAME + " where " + DbHelper.M_USER_TOKEN + "=? and " + DbHelper.USER_ID + "=?",
                new String[]{enCode(MYAppconfig.loginUserInfoData.getToken()), enCode(myFrends.getUser_id())});
        String token = "";
        while (cursor.moveToNext()) {
            token = deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_ID)));
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        if (TextUtils.isEmpty(token)) {
            try {
                SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
                        .openDatabase();
                writableDatabase
                        .execSQL(
                                "insert into " + DbHelper.MYFREDNS_NAME +
                                        "(" +
                                        DbHelper.USER_ID + "," +
                                        DbHelper.USER_NAME + "," +
                                        DbHelper.USER_AVAR + "," +
                                        DbHelper.M_USER_TOKEN +
                                        ")values (?,?,?,?)",
                                new Object[]{
                                        enCode(myFrends.getUser_id()),
                                        enCode(myFrends.getUser_name()),
                                        enCode(myFrends.getUser_avar()),
                                        enCode(MYAppconfig.loginUserInfoData.getToken())});
                DatabaseManager.getInstance().closeDatabase();
            } catch (Exception e) {
            }
        } else {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            ContentValues values = new ContentValues();
            values.put(DbHelper.USER_NAME, enCode(myFrends.getUser_name()));
            values.put(DbHelper.USER_AVAR, enCode(myFrends.getUser_avar()));
            db.update(DbHelper.MYFREDNS_NAME, values, DbHelper.USER_ID + "=? and " + DbHelper.M_USER_TOKEN + "=?",
                    new String[]{enCode(myFrends.getUser_id()), enCode(MYAppconfig.loginUserInfoData.getToken())});
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public synchronized MyFrends selectMyFrends(String userid) {
        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                .readDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + DbHelper.MYFREDNS_NAME + " where " + DbHelper.M_USER_TOKEN + "=? and " + DbHelper.USER_ID + "=?",
                new String[]{enCode(MYAppconfig.loginUserInfoData.getToken()), enCode(userid)});
        MyFrends messageLists = new MyFrends();
        while (cursor.moveToNext()) {
            MessageList messageList = new MessageList();
            messageList.setUser_id(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_ID))));
            messageList.setUser_name(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_NAME))));
            messageList.setUser_avar(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_AVAR))));
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return messageLists;
    }

/************
 * +++++++++++++++++end+++++++++++++++++++++++
 */
    /***
     * *******************我的会话start******************
     *
     * @param messageList 会话列表
     */
    public synchronized void insertConversationlist(MessageList messageList) {
        try {
            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
                    .openDatabase();
            writableDatabase
                    .execSQL(
                            "insert into " + DbHelper.CONVERSATIONLIST_NAME +
                                    "(" +
                                    DbHelper.USER_ID + "," +
                                    DbHelper.USER_NAME + "," +
                                    DbHelper.USER_AVAR + "," +
                                    DbHelper.LAST_MSG + "," +
                                    DbHelper.LAST_MSG_TIME + "," +
                                    DbHelper.UNREAD_MSG_COUNT + "," +
                                    DbHelper.CHAT_TYPE + "," +
                                    DbHelper.M_USER_TOKEN + "," +
                                    DbHelper.LAST_MSG_ID +
                                    ")values (?,?,?,?,?,?,?,?,?)",
                            new Object[]{
                                    enCode(messageList.getUser_id()),
                                    enCode(messageList.getUser_name()),
                                    enCode(messageList.getUser_avar()),
                                    enCode(messageList.getLastmsg()),
                                    enCode(messageList.getTime()),
                                    enCode(messageList.getCount()),
                                    enCode(messageList.getChat_type()),
                                    enCode(MYAppconfig.loginUserInfoData.getToken()),
                                    enCode(messageList.getMsgid())});
            DatabaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            updateConversationlist(messageList);
        }
    }

    /*****
     * @param messageList
     */
    public synchronized void updateConversationlist(MessageList messageList) {

//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
//                .readDatabase();
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from " + DbHelper.CONVERSATIONLIST_NAME + " where " + DbHelper.M_USER_TOKEN + "=? and " + DbHelper.FREND_ID + "=?",
//                new String[]{enCode(MYAppconfig.loginUserInfoData.getToken()), enCode(messageList.getFriend_id())});
//        int count = 0;
//        while (cursor.moveToNext()) {
//
//            try {
//                count = Integer.parseInt(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.UNREAD_MSG_COUNT))));
//            } catch (Exception e) {
//                count = 0;
//            }
//
//        }
//
//        cursor.close();
//        DatabaseManager.getInstance().closeDatabase();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.USER_NAME, enCode(messageList.getUser_name()));
        values.put(DbHelper.USER_ID, enCode(messageList.getUser_id()));
        values.put(DbHelper.USER_AVAR, enCode(messageList.getUser_avar()));
        values.put(DbHelper.LAST_MSG, enCode(messageList.getLastmsg()));
        values.put(DbHelper.LAST_MSG_TIME, enCode(messageList.getTime()));
        values.put(DbHelper.UNREAD_MSG_COUNT, enCode(messageList.getCount() + ""));
        values.put(DbHelper.CHAT_TYPE, enCode(messageList.getChat_type()));
        values.put(DbHelper.LAST_MSG_ID, enCode(messageList.getMsgid()));
        db.update(DbHelper.CONVERSATIONLIST_NAME, values, DbHelper.USER_ID + "=? and " + DbHelper.M_USER_TOKEN + "=?",
                new String[]{enCode(messageList.getUser_id()), enCode(MYAppconfig.loginUserInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
    }

    /****
     * 获取会话列表
     *
     * @return
     */
    public synchronized List<MessageList> selectConversationlist() {
        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                .readDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + DbHelper.CONVERSATIONLIST_NAME + " where " + DbHelper.M_USER_TOKEN + "=? Order By " + DbHelper.LAST_MSG_TIME + " Desc",
                new String[]{enCode(MYAppconfig.loginUserInfoData.getToken())});
        List<MessageList> messageLists = new ArrayList<>();
        while (cursor.moveToNext()) {
            MessageList messageList = new MessageList();
            messageList.setUser_id(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_ID))));
            messageList.setUser_name(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_NAME))));
            messageList.setUser_avar(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_AVAR))));
            messageList.setLastmsg(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.LAST_MSG))));
            messageList.setTime(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.LAST_MSG_TIME))));
            messageList.setCount(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.UNREAD_MSG_COUNT))));
            messageList.setChat_type(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.CHAT_TYPE))));
            messageList.setMsgid(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.LAST_MSG_ID))));
            messageLists.add(messageList);
        }


        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return messageLists;
    }

    /****
     * 归零
     *
     * @param messageList
     */
    public synchronized void updateMessageCountRset(MessageList messageList) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.UNREAD_MSG_COUNT, enCode("0"));

        db.update(DbHelper.CONVERSATIONLIST_NAME, values, DbHelper.USER_ID + "=? and " + DbHelper.M_USER_TOKEN + "=?",
                new String[]{enCode(messageList.getUser_id()), enCode(MYAppconfig.loginUserInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
    }


    public synchronized int getALLCount() {
        int count = 0;
        try {

            SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                    .readDatabase();
            Cursor cursor = readableDatabase.rawQuery(
                    "select * from " + DbHelper.CONVERSATIONLIST_NAME + " where " + DbHelper.M_USER_TOKEN + "=?",
                    new String[]{enCode(MYAppconfig.loginUserInfoData.getToken())});

            while (cursor.moveToNext()) {
                try {
                    count = count + Integer.parseInt(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.UNREAD_MSG_COUNT))));
                } catch (Exception e) {

                }
            }


            cursor.close();
            DatabaseManager.getInstance().closeDatabase();

        } catch (Exception e) {

        }
        return count;
    }
    /************
     * +++++++++++++++++end+++++++++++++++++++++++
     */
    /************
     * ++++++++++++聊天信息+++++start+++++++++++++++++++++++
     */
    public synchronized void insertChat(ChatMsgEntity chatMsgEntity) {
        try {
            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
                    .openDatabase();
            writableDatabase
                    .execSQL(
                            "insert into " + DbHelper.CHAT_NAME +
                                    "(" +
                                    DbHelper.USER_ID + "," +
                                    DbHelper.USER_NAME + "," +
                                    DbHelper.USER_AVAR + "," +
                                    DbHelper.MOBILE + "," +
                                    DbHelper.MESSAGE + "," +
                                    DbHelper.MESSAGEID + "," +
                                    DbHelper.TYPE + "," +
                                    DbHelper.TIME + "," +
                                    DbHelper.STREET + "," +
                                    DbHelper.LONGITUDE + "," +
                                    DbHelper.LATITUDE + "," +
                                    DbHelper.DURATION + "," +
                                    DbHelper.IMGURL + "," +
                                    DbHelper.STATUS + "," +
                                    DbHelper.VOICESTATUS + "," +
                                    DbHelper.VOICEURL + "," +
                                    DbHelper.VOICELENTH + "," +
                                    DbHelper.VOICEPLAY + "," +
                                    DbHelper.M_USER_TOKEN + "," +
                                    DbHelper.GROUPID + "," +
                                    DbHelper.CHAT_TYPE + "," +
                                    ")values (?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?," +
                                    "?)",
                            new Object[]{
                                    enCode(chatMsgEntity.getUser_id()),
                                    enCode(chatMsgEntity.getUser_name()),
                                    enCode(chatMsgEntity.getUser_avar()),
                                    enCode(chatMsgEntity.getMobile()),
                                    enCode(chatMsgEntity.getMessage()),
                                    enCode(chatMsgEntity.getMessageid()),
                                    enCode(chatMsgEntity.getType() + ""),
                                    enCode(chatMsgEntity.getTime()),
                                    enCode(chatMsgEntity.getStreet()),
                                    enCode(chatMsgEntity.getLongitude()),
                                    enCode(chatMsgEntity.getLatitude()),
                                    enCode(chatMsgEntity.getDuration()),
                                    enCode(chatMsgEntity.getImgurl()),
                                    enCode(chatMsgEntity.getStatus() + ""),
                                    enCode(chatMsgEntity.getVoicestatus() + ""),
                                    enCode(chatMsgEntity.getVoiceurl()),
                                    enCode(chatMsgEntity.getVoicelenth()),
                                    enCode(chatMsgEntity.isVoiceplay() + ""),
                                    enCode(MYAppconfig.loginUserInfoData.getToken()),
                                    enCode(chatMsgEntity.getGroupid()),
                                    enCode(chatMsgEntity.getChat_type())
                            });
            DatabaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
        }
    }

    /****
     * 获取聊天内容
     *
     * @param userid
     * @return
     */
    public synchronized List<ChatMsgEntity> getChat(String userid, int pager) {
        pager = pager * 20;
        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                .readDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + DbHelper.CHAT_NAME + " where " + DbHelper.M_USER_TOKEN + "=? and " + DbHelper.USER_ID + "=?  Order By " + DbHelper.TIME + " ASC limit 20 offset ?",
                new String[]{enCode(MYAppconfig.loginUserInfoData.getToken()), enCode(userid), pager + ""});
        List<ChatMsgEntity> chatMsgEntities = new ArrayList<>();
        while (cursor.moveToNext()) {
            ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
            chatMsgEntity.setUser_id(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_ID))));
            chatMsgEntity.setUser_name(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_NAME))));
            chatMsgEntity.setUser_avar(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_AVAR))));
            chatMsgEntity.setMobile(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.MOBILE))));
            chatMsgEntity.setMessage(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.MESSAGE))));
            chatMsgEntity.setMessageid(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.MESSAGEID))));
            try {
                chatMsgEntity.setType(Integer.parseInt(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.TYPE)))));
            } catch (Exception e) {
            }
            chatMsgEntity.setTime(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.TIME))));
            chatMsgEntity.setStreet(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.STREET))));
            chatMsgEntity.setLongitude(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.LONGITUDE))));
            chatMsgEntity.setLatitude(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.LASTUSER))));
            chatMsgEntity.setDuration(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.DURATION))));
            chatMsgEntity.setImgurl(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.IMGURL))));
            try {
                chatMsgEntity.setStatus(Integer.parseInt(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.STATUS)))));
            } catch (Exception e) {
            }
            chatMsgEntity.setVoicestatus(Integer.parseInt(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.VOICESTATUS)))));
            chatMsgEntity.setVoiceurl(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.VOICEURL))));
            chatMsgEntity.setVoicelenth(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.VOICELENTH))));
            try {
                chatMsgEntity.setVoiceplay(Boolean.parseBoolean(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.VOICEPLAY)))));
            } catch (Exception e) {
            }
            chatMsgEntity.setGroupid(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.GROUPID))));
            chatMsgEntity.setChat_type(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.CHAT_TYPE))));
            chatMsgEntities.add(chatMsgEntity);
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return chatMsgEntities;
    }

    /****
     * 获取聊天组内容
     *
     * @param groupid
     * @return
     */
    public synchronized List<ChatMsgEntity> getChatGroup(String groupid, int pager) {
        pager = pager * 20;
        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
                .readDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + DbHelper.CHAT_NAME + " where " + DbHelper.M_USER_TOKEN + "=? and " + DbHelper.GROUPID + "=?  Order By " + DbHelper.TIME + " ASC limit 20 offset ?",
                new String[]{enCode(MYAppconfig.loginUserInfoData.getToken()), enCode(groupid), pager + ""});
        List<ChatMsgEntity> chatMsgEntities = new ArrayList<>();
        while (cursor.moveToNext()) {
            ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
            chatMsgEntity.setUser_id(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_ID))));
            chatMsgEntity.setUser_name(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_NAME))));
            chatMsgEntity.setUser_avar(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.USER_AVAR))));
            chatMsgEntity.setMobile(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.MOBILE))));
            chatMsgEntity.setMessage(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.MESSAGE))));
            chatMsgEntity.setMessageid(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.MESSAGEID))));
            try {
                chatMsgEntity.setType(Integer.parseInt(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.TYPE)))));
            } catch (Exception e) {
            }
            chatMsgEntity.setTime(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.TIME))));
            chatMsgEntity.setStreet(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.STREET))));
            chatMsgEntity.setLongitude(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.LONGITUDE))));
            chatMsgEntity.setLatitude(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.LASTUSER))));
            chatMsgEntity.setDuration(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.DURATION))));
            chatMsgEntity.setImgurl(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.IMGURL))));
            try {
                chatMsgEntity.setStatus(Integer.parseInt(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.STATUS)))));
            } catch (Exception e) {
            }
            chatMsgEntity.setVoicestatus(Integer.parseInt(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.VOICESTATUS)))));
            chatMsgEntity.setVoiceurl(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.VOICEURL))));
            chatMsgEntity.setVoicelenth(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.VOICELENTH))));
            try {
                chatMsgEntity.setVoiceplay(Boolean.parseBoolean(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.VOICEPLAY)))));
            } catch (Exception e) {
            }
            chatMsgEntity.setGroupid(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.GROUPID))));
            chatMsgEntity.setChat_type(deCode(cursor.getString(cursor.getColumnIndex(DbHelper.CHAT_TYPE))));
            chatMsgEntities.add(chatMsgEntity);
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return chatMsgEntities;
    }

    /****
     * 修改消息状态
     *
     * @param message_id
     * @param status
     */
    public synchronized void updateChatMessageStatus(String message_id, String status) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.STATUS, enCode(status));

        db.update(DbHelper.CHAT_NAME, values, DbHelper.MESSAGEID + "=? and " + DbHelper.M_USER_TOKEN + "=?",
                new String[]{enCode(message_id), enCode(MYAppconfig.loginUserInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
    }

    /****
     * 修改消息状态
     *
     * @param message_id
     * @param voiceplay
     */
    public synchronized void updateChatMessageVoicePlay(String message_id, String voiceplay) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.VOICEPLAY, enCode(voiceplay));

        db.update(DbHelper.CHAT_NAME, values, DbHelper.MESSAGEID + "=? and " + DbHelper.M_USER_TOKEN + "=?",
                new String[]{enCode(message_id), enCode(MYAppconfig.loginUserInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
    }

    /****
     * 修改消息状态
     *
     * @param message_id
     * @param voicestatus
     */
    public synchronized void updateChatMessageVoiceStatus(String message_id, String voicestatus) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.VOICESTATUS, enCode(voicestatus));

        db.update(DbHelper.CHAT_NAME, values, DbHelper.MESSAGEID + "=? and " + DbHelper.M_USER_TOKEN + "=?",
                new String[]{enCode(message_id), enCode(MYAppconfig.loginUserInfoData.getToken())});
        DatabaseManager.getInstance().closeDatabase();
    }


//        public synchronized void insertExec(ExeTime exeTime) {
//        try {
//            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
//                    .openDatabase();
//            writableDatabase
//                    .execSQL(
//                            "insert into exec(exec_id,device_id,exec_date,exec_time,pnumber,networktype,type" +
//                                    ")values(?,?,?,?,?,?,?)",
//                            new Object[]{exeTime.getExec_id(), exeTime.getDevice_id(), exeTime.getExec_date(),
//                                    exeTime.getExec_time(), exeTime.getPnumber(), exeTime.getNetworktype(),
//                                    "0"});
//            DatabaseManager.getInstance().closeDatabase();
//        } catch (Exception e) {
//            L.e("exec插入数据失败（原因：已存在）！");
//        }
//    }

//    public synchronized boolean isOverTask(String time) {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance().openDatabase();
//
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from exec where type=? and exec_date=?",
//                new String[]{"0", time});
//
//        String device_id = "";
//        while (cursor.moveToNext()) {
//            device_id = cursor.getString(cursor.getColumnIndex("device_id"));
//        }
//
//        Cursor cursorPriority = readableDatabase.rawQuery(
//                "select * from exec where type=? and exec_date=?",
//                new String[]{"1", time});
//        String device_idPriority = "";
//        while (cursorPriority.moveToNext()) {
//            device_idPriority = cursorPriority.getString(cursorPriority.getColumnIndex("device_id"));
//        }
//
//        cursorPriority.close();
//        cursor.close();
//        DatabaseManager.getInstance().closeDatabase();
//        if (!TextUtils.isEmpty(device_idPriority)) {
//            return false;
//        }
//        if (TextUtils.isEmpty(device_id)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//

//
//

    //
//    public synchronized boolean isTodayTask(String time) {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
//                .readDatabase();
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from exec where type=? and exec_date=?",
//                new String[]{"0", time});
//
//        String device_id = "";
//        while (cursor.moveToNext()) {
//            device_id = cursor.getString(cursor.getColumnIndex("device_id"));
//        }
//
//        Cursor cursorPriority = readableDatabase.rawQuery(
//                "select * from exec where type=? and exec_date=?",
//                new String[]{"1", time});
//        String device_idPriority = "";
//        while (cursorPriority.moveToNext()) {
//            device_idPriority = cursorPriority.getString(cursorPriority.getColumnIndex("device_id"));
//        }
//
//        cursorPriority.close();
//        cursor.close();
//        DatabaseManager.getInstance().closeDatabase();
//        if (!TextUtils.isEmpty(device_idPriority)) {
//            return false;
//        }
//        if (TextUtils.isEmpty(device_id)) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }
//
//    public synchronized int isTaskSelect(String time) {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance().openDatabase();
//        int i = 0;
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from exec where type=? and exec_date=?",
//                new String[]{"0", time});
//
//        String device_id = "";
//        while (cursor.moveToNext()) {
//            device_id = cursor.getString(cursor.getColumnIndex("device_id"));
//            i++;
//        }
//
//        Cursor cursorPriority = readableDatabase.rawQuery(
//                "select * from exec where type=? and exec_date=?",
//                new String[]{"1", time});
//        String device_idPriority = "";
//        while (cursorPriority.moveToNext()) {
//            device_idPriority = cursorPriority.getString(cursorPriority.getColumnIndex("device_id"));
//            i++;
//        }
//
//        cursorPriority.close();
//        cursor.close();
//        DatabaseManager.getInstance().closeDatabase();
//        return i;
//    }
//
//
//    public synchronized List<ExeTime> selectExec(String types, String exec_dates) {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
//                .readDatabase();
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from exec where type=? and exec_date=?",
//                new String[]{types, exec_dates});
//
//        List<ExeTime> exeTimes = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            String device_id = "";
//            String exec_date = "";
//            String exec_time = "";
//            String pnumber = "";
//            String networktype = "";
//            String type = "";
//            device_id = cursor.getString(cursor.getColumnIndex("device_id"));
//            exec_date = cursor.getString(cursor.getColumnIndex("exec_date"));
//            exec_time = cursor.getString(cursor.getColumnIndex("exec_time"));
//            pnumber = cursor.getString(cursor.getColumnIndex("pnumber"));
//            networktype = cursor.getString(cursor.getColumnIndex("networktype"));
//            type = cursor.getString(cursor.getColumnIndex("type"));
//            String exec_id = cursor.getString(cursor.getColumnIndex("exec_id"));
//            ExeTime exeTime = new ExeTime();
//            exeTime.setExec_id(exec_id);
//            exeTime.setDevice_id(device_id);
//            exeTime.setExec_date(exec_date);
//            exeTime.setExec_time(exec_time);
//            exeTime.setPnumber(pnumber);
//            exeTime.setNetworktype(networktype);
//            exeTime.setType(type);
//            exeTimes.add(exeTime);
//        }
//
//        DatabaseManager.getInstance().closeDatabase();
//        cursor.close();
//        return exeTimes;
//    }
//
//
//    public synchronized void insertMpData(MpData mpData, String exec_id) {
//        try {
//            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
//                    .openDatabase();
//            writableDatabase
//                    .execSQL(
//                            "insert into mpdata(mp_id,exec_id,m_products,screen_width,screen_hight,m_apparatus,rom_version" +
//                                    ",mac_address,type,simoperatorname,simoperator)values(?,?,?,?,?,?,?,?,?,?,?)",
//                            new Object[]{mpData.getMp_id() + "_" + exec_id, exec_id, mpData.getM_products(), mpData.getScreen_width(), mpData.getScreen_hight(), mpData.getM_apparatus(), mpData.getRom_version(), mpData.getMac_address(), "0",mpData.getSimoperatorname(),mpData.getSimoperator()});
//            DatabaseManager.getInstance().closeDatabase();
//        } catch (Exception e) {
//            L.e("mpdata插入数据失败（原因：已存在）！");
//        }
//    }
//
//
//    public synchronized List<MpData> selectMpData(String exec_id) {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
//                .readDatabase();
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from mpdata where exec_id=?",
//                new String[]{exec_id});
//
//        List<MpData> mpDatas = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            String mp_id = cursor.getString(cursor.getColumnIndex("mp_id"));
//            String m_products = cursor.getString(cursor.getColumnIndex("m_products"));
//            String screen_width = cursor.getString(cursor.getColumnIndex("screen_width"));
//            String screen_hight = cursor.getString(cursor.getColumnIndex("screen_hight"));
//            String m_apparatus = cursor.getString(cursor.getColumnIndex("m_apparatus"));
//            String rom_version = cursor.getString(cursor.getColumnIndex("rom_version"));
//            String mac_address = cursor.getString(cursor.getColumnIndex("mac_address"));
//            String simoperatorname = cursor.getString(cursor.getColumnIndex("simoperatorname"));
//            String simoperator = cursor.getString(cursor.getColumnIndex("simoperator"));
//            MpData mpData = new MpData();
//            mpData.setMp_id(mp_id);
//            mpData.setM_products(m_products);
//            mpData.setScreen_width(screen_width);
//            mpData.setScreen_hight(screen_hight);
//            mpData.setM_apparatus(m_apparatus);
//            mpData.setRom_version(rom_version);
//            mpData.setMac_address(mac_address);
//            mpData.setSimoperator(simoperator);
//            mpData.setSimoperatorname(simoperatorname);
//            mpDatas.add(mpData);
//        }
//
//        DatabaseManager.getInstance().closeDatabase();
//        cursor.close();
//        return mpDatas;
//    }
//
//
//    public synchronized void insertPackagedata(PackageData packageData, String exec_id) {
//        try {
//            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
//                    .openDatabase();
//            writableDatabase
//                    .execSQL(
//                            "insert into packageData(task_id,exec_id,timeonpage,package_name,countlist,type,down_url)values(?,?,?,?,?,?,?)",
//                            new Object[]{packageData.getTask_id() + "_" + exec_id, exec_id, packageData.getTimeonpage(), packageData.getPackage_name(), packageData.getCountlist(), "0", packageData.getDown_url()});
//            DatabaseManager.getInstance().closeDatabase();
//        } catch (Exception e) {
//            L.e("packageData插入数据失败（原因：已存在）！");
//        }
//    }
//
//    public synchronized List<PackageData> selectPackagedata(String types, String exec_id) {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
//                .readDatabase();
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from packageData where exec_id=? and type=?",
//                new String[]{exec_id, types});
//
//        List<PackageData> packageDatas = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            String task_id = cursor.getString(cursor.getColumnIndex("task_id"));
//            String timeonpage = cursor.getString(cursor.getColumnIndex("timeonpage"));
//            String package_name = cursor.getString(cursor.getColumnIndex("package_name"));
//            String countlist = cursor.getString(cursor.getColumnIndex("countlist"));
//            String type = cursor.getString(cursor.getColumnIndex("type"));
//            PackageData packageData = new PackageData();
//            packageData.setTask_id(task_id);
//            packageData.setType(type);
//            packageData.setCountlist(countlist);
//            packageData.setPackage_name(package_name);
//            packageData.setTimeonpage(timeonpage);
//            packageData.setDown_url(cursor.getString(cursor.getColumnIndex("down_url")));
//            packageDatas.add(packageData);
//        }
//        cursor.close();
//        DatabaseManager.getInstance().closeDatabase();
//        return packageDatas;
//    }
//
//    public synchronized int selectPackagedataAll() {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
//                .readDatabase();
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from packageData where type=?",
//                new String[]{"0"});
//
//        List<PackageData> packageDatas = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            String task_id = cursor.getString(cursor.getColumnIndex("task_id"));
//            String timeonpage = cursor.getString(cursor.getColumnIndex("timeonpage"));
//            String package_name = cursor.getString(cursor.getColumnIndex("package_name"));
//            String countlist = cursor.getString(cursor.getColumnIndex("countlist"));
//            String type = cursor.getString(cursor.getColumnIndex("type"));
//            PackageData packageData = new PackageData();
//            packageData.setTask_id(task_id);
//            packageData.setType(type);
//            packageData.setCountlist(countlist);
//            packageData.setPackage_name(package_name);
//            packageData.setTimeonpage(timeonpage);
//            packageData.setDown_url(cursor.getString(cursor.getColumnIndex("down_url")));
//            packageDatas.add(packageData);
//        }
//        cursor.close();
//        DatabaseManager.getInstance().closeDatabase();
//        return packageDatas.size();
//    }
//
//
//    public synchronized void updatePackagedata(String exec_id, String task_id) {
//        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//        ContentValues values = new ContentValues();
//        values.put("type", "1");
//        db.update("packageData", values, "exec_id=? and task_id=?", new String[]{exec_id, task_id});
//        DatabaseManager.getInstance().closeDatabase();
//    }
//
//    public synchronized void insertVpndata(VpnData vpnData, String exec_id) {
//        try {
//            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
//                    .openDatabase();
//            writableDatabase
//                    .execSQL(
//                            "insert into vpndata(vpnid,conip,exec_id,vpnname,vpnpwd)values(?,?,?,?,?)",
//                            new Object[]{vpnData.getConip() + "_" + exec_id, vpnData.getConip(), exec_id, vpnData.getVpnname(), vpnData.getVpnpwd()});
//            DatabaseManager.getInstance().closeDatabase();
//        } catch (Exception e) {
//            L.e("vpndata插入数据失败（原因：已存在）！");
//        }
//    }
//
//    public synchronized List<VpnData> selectVpnData(String exec_id) {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
//                .readDatabase();
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from vpndata where exec_id=?",
//                new String[]{exec_id});
//
//        List<VpnData> vpnDatas = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            String conip = cursor.getString(cursor.getColumnIndex("conip"));
//            String vpnname = cursor.getString(cursor.getColumnIndex("vpnname"));
//            String vpnpwd = cursor.getString(cursor.getColumnIndex("vpnpwd"));
//
//            VpnData vpnData = new VpnData();
//            vpnData.setConip(conip);
//            vpnData.setVpnname(vpnname);
//            vpnData.setVpnpwd(vpnpwd);
//            vpnDatas.add(vpnData);
//        }
//        cursor.close();
//        DatabaseManager.getInstance().closeDatabase();
//        return vpnDatas;
//    }
//
//
//    public synchronized void insertServar(String servertime) {
//        try {
//            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
//                    .openDatabase();
//            writableDatabase
//                    .execSQL(
//                            "insert into checktime(servertime)values(?)",
//                            new Object[]{servertime});
//            DatabaseManager.getInstance().closeDatabase();
//        } catch (Exception e) {
//            L.e("checktime插入数据失败（原因：已存在）！" + e);
//        }
//    }
//
//    public synchronized String selectServar() {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
//                .readDatabase();
//        Cursor cursor = readableDatabase.rawQuery(
//                "select * from checktime",
//                new String[]{});
//        String servertime = "";
//        while (cursor.moveToNext()) {
//            servertime = cursor.getString(cursor.getColumnIndex("servertime"));
//        }
//        cursor.close();
//        DatabaseManager.getInstance().closeDatabase();
//        return servertime;
//    }
//
//
//    public synchronized void deleteAllTab() {
//        SQLiteDatabase db = DatabaseManager.getInstance()
//                .openDatabase();
//        db.execSQL("drop table if exists exec");
//        db.execSQL("drop table if exists mpdata");
//        db.execSQL("drop table if exists packagedata");
//        db.execSQL("drop table if exists vpndata");
//        db.execSQL("drop table if exists checktime");
//        db.execSQL("drop table if exists info");
////        db.execSQL("drop table if exists phoneinfo");
//        DatabaseManager.getInstance().closeDatabase();
//        createAllTab();
//    }
//
//    //    创建表
//    public synchronized void createAllTab() {
//        SQLiteDatabase db = DatabaseManager.getInstance()
//                .openDatabase();
//        db.execSQL("create table exec(exec_id varchar(50) primary key,device_id varchar(50),exec_date varchar(50),exec_time varchar(50),pnumber varchar(20),networktype varchar(10),type varchar(20))");
//        db.execSQL("create table mpdata(mp_id varchar(50),exec_id varchar(50),m_products varchar(50),screen_width varchar(50),screen_hight varchar(50),m_apparatus varchar(20),rom_version varchar(20),mac_address varchar(20) primary key,type varchar(20),simoperatorname varchar(100),simoperator varchar(100))");
//        db.execSQL("create table packagedata(task_id varchar(50) primary key,exec_id varchar(50),timeonpage varchar(50),package_name varchar(50),countlist varchar(50),type varchar(20),down_url varchar(20))");
//        db.execSQL("create table vpndata(vpnid varchar(50) primary key,conip varchar(50),exec_id varchar(50),vpnname varchar(50),vpnpwd varchar(50))");
//        db.execSQL("create table checktime(servertime varchar(50) primary key)");
//        db.execSQL("CREATE TABLE info(name VARCHAR(50),filename VARCHAR(50),path VARCHAR(1024), thid VARCHAR(54), done VARCHAR(54), PRIMARY KEY(path, thid))");
//        db.execSQL("create table phoneinfo(imei varchar(50),name varchar(30) primary key)");
//        DatabaseManager.getInstance().closeDatabase();
//    }
//
//
//    /****
//     * 插入手机的IMEI  ******************start***********************
//     */
//    public synchronized void insertPhoneTab(String imei) {
//        try {
//
//            SQLiteDatabase writableDatabase = DatabaseManager.getInstance()
//                    .openDatabase();
//            writableDatabase
//                    .execSQL(
//                            "insert into phoneinfo(imei,name)values(?,?)",
//                            new Object[]{imei, "imei"});
//            DatabaseManager.getInstance().closeDatabase();
//        } catch (Exception e) {
//            L.e("imei插入数据失败（原因：已存在）！");
//        }
//    }
//
//    public synchronized String selectPhoneTab() {
//        SQLiteDatabase readableDatabase = DatabaseManager.getInstance()
//                .readDatabase();
//        Cursor cursor = readableDatabase.rawQuery(
//                "select imei from phoneinfo",
//                new String[]{});
//
//        String mp_id = "";
//        while (cursor.moveToNext()) {
//            mp_id = cursor.getString(cursor.getColumnIndex("imei"));
//        }
//
//        DatabaseManager.getInstance().closeDatabase();
//        cursor.close();
//        return mp_id;
//    }
//    /*******
//     * **************************end***********************************
//     */
    public synchronized void deleteAllTab() {
        SQLiteDatabase db = DatabaseManager.getInstance()
                .openDatabase();
        db.execSQL(DbHelper.DELETE_LOGININFO);
        db.execSQL(DbHelper.DELETE_LASTUSER);
        db.execSQL(DbHelper.DELETE_MYFRENDS);
        db.execSQL(DbHelper.DELETE_CONVERSATIONLIST);

        DatabaseManager.getInstance().closeDatabase();
        createAllTab();
    }

    //    创建表
    public synchronized void createAllTab() {
        SQLiteDatabase db = DatabaseManager.getInstance()
                .openDatabase();
        db.execSQL(DbHelper.LOGININFO);
        db.execSQL(DbHelper.LASTUSER);
        db.execSQL(DbHelper.MYFRENDS);
        db.execSQL(DbHelper.CREATE_CONVERSATIONLIST);

        DatabaseManager.getInstance().closeDatabase();
    }
}
