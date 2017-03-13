package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

/**
 * Created by wupeitao on 2017/3/3.
 */

public class AddressModel extends BaseModel {
    private AddressData data;

    public AddressData getData() {
        return data;
    }

    public void setData(AddressData data) {
        this.data = data;
    }
}
