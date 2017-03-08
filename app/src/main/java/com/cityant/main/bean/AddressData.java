package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 2017/3/3.
 */

public class AddressData extends BaseData {
    private List<Address> address_list;

    public List<Address> getAddress_list() {
        return address_list;
    }

    public void setAddress_list(List<Address> address_list) {
        this.address_list = address_list;
    }
}
