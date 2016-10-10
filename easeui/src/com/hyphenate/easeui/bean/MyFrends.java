package com.hyphenate.easeui.bean;

import android.text.TextUtils;

import com.hyphenate.chat.EMMessage;

import java.io.Serializable;

/**
 * {"code":200,"data":{"friend_list":[{"friend_id":16,"user_name":"","user_avar":"http://123.56.182.208/mayi/Public/avar.png"}],"code_message":"请求数据成功"}}
 * Created by wupeitao on 16/8/19.
 */
public class MyFrends implements Serializable {
    private String friend_id;//环信id
    private String user_name;
    private String user_avar;
    private String PinYinName;
    private String group_id;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    private String isGroup;//1、普通2、聊天室

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getPinYinName() {
        return PinYinName;
    }

    public void setPinYinName(String pinYinName) {
        PinYinName = pinYinName;
    }

    public String getUser_name() {
        if (!TextUtils.isEmpty(user_name)) {
            return user_name;
        } else {
            return friend_id;
        }
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
}
