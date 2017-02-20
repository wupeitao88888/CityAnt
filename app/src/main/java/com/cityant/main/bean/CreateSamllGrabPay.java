package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 2017/2/19.
 */

public class CreateSamllGrabPay implements Serializable {
    private String is_friend;
    private String end_time;
    private String goods_id;
    private String type;

    public String getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(String is_friend) {
        this.is_friend = is_friend;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
