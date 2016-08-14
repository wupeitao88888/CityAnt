package com.iloomo.bean;

import java.io.Serializable;

public class SMSBaseModel extends BaseModel implements Serializable {

    private BaseDate data;
    public BaseDate getData() {
        return data;
    }

    public void setData(BaseDate data) {
        this.data = data;
    }
}
