package com.cityant.main.bean;

import com.iloomo.bean.BaseData;
import com.iloomo.bean.BaseModel;

/**
 * Created by wupeitao on 16/8/28.
 */
public class HandleModel extends BaseModel {
    private HandleData data;

    public HandleData getData() {
        return data;
    }

    public void setData(HandleData data) {
        this.data = data;
    }
}
