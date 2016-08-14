package com.iloomo.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/8/14.
 */
public class BaseDate implements Serializable {
    private String code_message;

    public String getCode_message() {
        return code_message;
    }

    public void setCode_message(String code_message) {
        this.code_message = code_message;
    }
}
