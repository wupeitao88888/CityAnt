package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

/**
 * Created by wupeitao on 2017/3/6.
 */

public class AddressListModel extends BaseModel {
    private AddressListData data;

    public AddressListData getData() {
        return data;
    }

    public void setData(AddressListData data) {
        this.data = data;
    }
}
