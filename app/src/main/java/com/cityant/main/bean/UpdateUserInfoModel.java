package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

/**
 * Created by wupeitao on 2016/10/20.
 */

public class UpdateUserInfoModel extends BaseModel {
    private UpdateUserInfoData data;

    public UpdateUserInfoData getData() {
        return data;
    }

    public void setData(UpdateUserInfoData data) {
        this.data = data;
    }
}
