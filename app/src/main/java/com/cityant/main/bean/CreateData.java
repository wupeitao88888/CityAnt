package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 16/8/31.
 */
public class CreateData extends BaseData {
    private List<Created> need_list;

    public List<Created> getNeed_list() {
        return need_list;
    }

    public void setNeed_list(List<Created> need_list) {
        this.need_list = need_list;
    }
}
