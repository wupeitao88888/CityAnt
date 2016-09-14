package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 16/9/14.
 */
public class ClassListData extends BaseData {
    private List<ClassList> class_list;

    public List<ClassList> getClass_list() {
        return class_list;
    }

    public void setClass_list(List<ClassList> class_list) {
        this.class_list = class_list;
    }
}
