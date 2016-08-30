package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 16/8/26.
 */
public class MeBrandsData extends BaseData {
    private List<MeBrands> brand_list;

    public List<MeBrands> getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(List<MeBrands> brand_list) {
        this.brand_list = brand_list;
    }
}
