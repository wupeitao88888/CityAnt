package com.cityant.main.wxapi.wxpay;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/13.
 */
public class BasicNameValuePair implements NameValuePair,Serializable {

    private String name;
    private String value;

    public BasicNameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new RuntimeException("Stub!");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
