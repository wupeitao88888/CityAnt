package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 16/8/26.
 */
public class MyFrendsData extends BaseData {
    private List<MyFrends> friend_list;

    public List<MyFrends> getFriend_list() {
        return friend_list;
    }

    public void setFriend_list(List<MyFrends> friend_list) {
        this.friend_list = friend_list;
    }
}
