package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 2016/10/23.
 */

public class IncomeListData extends BaseData {
    private String total_price;
    private List<IncomeList> income_list;

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public List<IncomeList> getIncome_list() {
        return income_list;
    }

    public void setIncome_list(List<IncomeList> income_list) {
        this.income_list = income_list;
    }
}
