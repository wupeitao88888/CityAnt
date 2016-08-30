package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/8/26.
 */
public class MeBrands implements Serializable {
    private String brand_id;
    private String brand_name;

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }
}
