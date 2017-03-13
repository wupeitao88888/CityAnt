package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 2017/3/6.
 */

public class AddressList implements Serializable {
    private String code;//地区编码"
    private String name;//地区名称"

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
