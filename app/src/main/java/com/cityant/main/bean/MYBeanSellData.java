package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 2016/10/22.
 */

public class MYBeanSellData extends BaseData {
    private List<MYBeanSell> bean_list;

    public List<MYBeanSell> getBean_list() {
        return bean_list;
    }

    public void setBean_list(List<MYBeanSell> bean_list) {
        this.bean_list = bean_list;
    }
}
