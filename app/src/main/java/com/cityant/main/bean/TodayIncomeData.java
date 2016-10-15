package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 2016/10/15.
 */

public class TodayIncomeData extends BaseData {
    private String total_price;
    private List<TodayIncome> income_list;//今日总收益",

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public List<TodayIncome> getIncome_list() {
        return income_list;
    }

    public void setIncome_list(List<TodayIncome> income_list) {
        this.income_list = income_list;
    }
}
