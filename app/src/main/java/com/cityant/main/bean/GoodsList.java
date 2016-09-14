package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/9/14.
 */
public class GoodsList implements Serializable {
    private String goods_id;
    private String goods_title;
    private String rob_price;
    private String goods_img;
    private String need_man;
    private String state;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getRob_price() {
        return rob_price;
    }

    public void setRob_price(String rob_price) {
        this.rob_price = rob_price;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getNeed_man() {
        return need_man;
    }

    public void setNeed_man(String need_man) {
        this.need_man = need_man;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
