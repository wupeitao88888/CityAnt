package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 2016/10/22.
 */

public class MYBeanSell implements Serializable {
    private String bean_id;
    private String price;
    private String par;

    public String getBean_id() {
        return bean_id;
    }

    public void setBean_id(String bean_id) {
        this.bean_id = bean_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }
}
