package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 2016/10/15.
 */

public class TodayIncome implements Serializable {
    private String type;//类型(0:蚂蚁仓库,1:蚂蚁豆,3:工资,4:兼职,9:取消友情购)"
    private String price;//收益金额"

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
