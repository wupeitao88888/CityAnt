package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 16/9/3.
 */
public class MGoodsData extends BaseData {
    private List<MGoods> goods_list;

    public List<MGoods> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<MGoods> goods_list) {
        this.goods_list = goods_list;
    }
}
