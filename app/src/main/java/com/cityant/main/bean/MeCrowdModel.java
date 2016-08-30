package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

/**
 * Created by wupeitao on 16/8/26.
 */
public class MeCrowdModel extends BaseModel {
    private MeCrowdData data;

    public MeCrowdData getData() {
        return data;
    }

    public void setData(MeCrowdData data) {
        this.data = data;
    }
}
