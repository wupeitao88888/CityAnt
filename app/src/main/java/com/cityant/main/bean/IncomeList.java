package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 2016/10/23.
 */

public class IncomeList implements Serializable {
    private String type;
    private String price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
