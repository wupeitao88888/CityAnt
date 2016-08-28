package com.cityant.main.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/8/26.
 */
public class MeCrows implements Serializable {
    private String groupid;
    private String group_name;
    private String group_desc;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_desc() {
        return group_desc;
    }

    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }
}
