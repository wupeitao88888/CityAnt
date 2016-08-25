package com.iloomo.bean;

import java.io.Serializable;

public class SMSBaseModel extends BaseModel implements Serializable {

    private BaseData data;
    public BaseData getData() {
        return data;
    }

    public void setData(BaseData data) {
        this.data = data;
    }
}
