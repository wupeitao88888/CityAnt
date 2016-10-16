package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

/**
 * Created by wupeitao on 2016/10/15.
 */

public class TodayIncomeModel extends BaseModel {
    private TodayIncomeData data;

    public TodayIncomeData getData() {
        return data;
    }

    public void setData(TodayIncomeData data) {
        this.data = data;
    }
}
