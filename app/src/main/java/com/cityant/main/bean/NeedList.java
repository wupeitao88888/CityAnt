package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/8/28.
 */
public class NeedList implements Serializable {
    private String need_id;
    private String need_title;
    private String need_content;
    private String pay_price;
    private String pay_unit;
    private String tag_name;
    private String need_man;
    private String apply_man;
    private String ok_man;
    private String wait_man;

    public String getNeed_id() {
        return need_id;
    }

    public void setNeed_id(String need_id) {
        this.need_id = need_id;
    }

    public String getNeed_title() {
        return need_title;
    }

    public void setNeed_title(String need_title) {
        this.need_title = need_title;
    }

    public String getNeed_content() {
        return need_content;
    }

    public void setNeed_content(String need_content) {
        this.need_content = need_content;
    }

    public String getPay_price() {
        return pay_price;
    }

    public void setPay_price(String pay_price) {
        this.pay_price = pay_price;
    }

    public String getPay_unit() {
        return pay_unit;
    }

    public void setPay_unit(String pay_unit) {
        this.pay_unit = pay_unit;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getNeed_man() {
        return need_man;
    }

    public void setNeed_man(String need_man) {
        this.need_man = need_man;
    }

    public String getApply_man() {
        return apply_man;
    }

    public void setApply_man(String apply_man) {
        this.apply_man = apply_man;
    }

    public String getOk_man() {
        return ok_man;
    }

    public void setOk_man(String ok_man) {
        this.ok_man = ok_man;
    }

    public String getWait_man() {
        return wait_man;
    }

    public void setWait_man(String wait_man) {
        this.wait_man = wait_man;
    }
}
