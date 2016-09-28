package com.cityant.main.bean;

import com.hyphenate.chat.EMMessageBody;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/9/16.
 */
public class MessageList implements Serializable {
    private String user_id;//userid
    private String user_name;
    private String user_avar;
    private String count;
    private String lastmsg;
    private String time;
    private String msgid;
    private String chat_type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getChat_type() {
        return chat_type;
    }

    public void setChat_type(String chat_type) {
        this.chat_type = chat_type;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avar() {
        return user_avar;
    }

    public void setUser_avar(String user_avar) {
        this.user_avar = user_avar;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

}
