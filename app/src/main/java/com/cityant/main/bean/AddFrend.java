package com.cityant.main.bean;

import com.iloomo.bean.BaseData;
import com.iloomo.bean.BaseModel;

import java.io.Serializable;

/**
 * Created by wupeitao on 2016/9/28.
 */

public class AddFrend extends BaseModel implements Serializable {

    private Msg data;

    public Msg getData() {
        return data;
    }

    public void setData(Msg data) {
        this.data = data;
    }

    public class Msg extends BaseData {

    }
}
