package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/9/3.
 */
public class CreateSmallGreabType implements Serializable {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
