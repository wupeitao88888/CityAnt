package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

/**
 * Created by wupeitao on 16/8/31.
 */
public class CreatedModel extends BaseModel {
    private CreateData data;

    public CreateData getCreateData() {
        return data;
    }

    public void setCreateData(CreateData createData) {
        this.data = createData;
    }
}