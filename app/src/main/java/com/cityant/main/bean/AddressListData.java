package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 2017/3/6.
 */

public class AddressListData extends BaseData {
    private List<AddressList> area_list;

    public List<AddressList> getArea_list() {
        return area_list;
    }

    public void setArea_list(List<AddressList> area_list) {
        this.area_list = area_list;
    }
}
