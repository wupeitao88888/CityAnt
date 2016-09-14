package com.cityant.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wupeitao on 16/9/14.
 */
public class ClassList implements Serializable {
    private String class_id;
    private String class_name;
    private List<ChildList> child_list;

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public List<ChildList> getChild_list() {
        return child_list;
    }

    public void setChild_list(List<ChildList> child_list) {
        this.child_list = child_list;
    }
}
