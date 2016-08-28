package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/8/26.
 */
public class NewFrends implements Serializable {
    private String friend_id;
    private String state;
    private String user_name;
    private String user_avar;

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
