package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 16/8/26.
 */
public class MeCrowdData extends BaseData {
    private List<MeCrows> group_list;

    public List<MeCrows> getGroup_list() {
        return group_list;
    }

    public void setGroup_list(List<MeCrows> group_list) {
        this.group_list = group_list;
    }
}
