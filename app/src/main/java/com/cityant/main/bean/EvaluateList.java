package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 2016/10/15.
 */

public class EvaluateList implements Serializable {
    private String rating;//星级
    private String user_name;//评论者
    private String user_avar;//评论者头像",
    private String add_time;//评论时间

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avar() {
        return user_avar;
    }

    public void setUser_avar(String user_avar) {
        this.user_avar = user_avar;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
