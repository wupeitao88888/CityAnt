package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/8/19.
 */
public class MyFrends implements Serializable {
    private String token;
    private String mobile;
    private String user_name;
    private String user_avar;
    private String PinYinName;

    public String getPinYinName() {
        return PinYinName;
    }

    public void setPinYinName(String pinYinName) {
        PinYinName = pinYinName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
