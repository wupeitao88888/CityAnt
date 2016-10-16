package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

/**
 * Created by wupeitao on 2016/10/16.
 */

public class FeedBackModel extends BaseModel {
    private FeedBackData data;

    public FeedBackData getData() {
        return data;
    }

    public void setData(FeedBackData data) {
        this.data = data;
    }
}
