package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 2017/2/10.
 */

public class BrandListData extends BaseData {
    private List<BrandList> brand_list;

    public List<BrandList> getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(List<BrandList> brand_list) {
        this.brand_list = brand_list;
    }
}
