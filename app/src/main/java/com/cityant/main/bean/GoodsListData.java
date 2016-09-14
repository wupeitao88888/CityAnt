package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 16/9/14.
 */
public class GoodsListData extends BaseData {
    private List<GoodsList> goods_list;

    public List<GoodsList> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsList> goods_list) {
        this.goods_list = goods_list;
    }
}
