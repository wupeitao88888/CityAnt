package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 16/8/26.
 */
public class NewFrendsData extends BaseData {
    private List<NewFrends> friend_list;

    public List<NewFrends> getFriend_list() {
        return friend_list;
    }

    public void setFriend_list(List<NewFrends> friend_list) {
        this.friend_list = friend_list;
    }
}
