package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/8/19.
 */
public class MyFrends implements Serializable {
    private String user_id;//环信id
    private String user_name;
    private String user_avar;
    private String PinYinName;
    private String isGroup;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
}
