package com.cityant.main.bean;

import java.io.Serializable;

/**
 *                "address_id": "<int>地址id"
 "name": "<string>姓名"
 "mobile": "<string>手机号"
 "area": "<string>地区"
 "address": "<string>地址"
 "is_default": "<int>默认地址(1:是,0:否)"
 * Created by wupeitao on 2017/3/3.
 */

public class Address implements Serializable {
    private String address_id;
    private String name;
    private String mobile;
    private String area;
    private String address;
    private String is_default;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }
}
