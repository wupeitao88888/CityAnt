package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;
import com.iloomo.bean.SMSBaseModel;

/**
 * Created by wupeitao on 16/8/14.
 */
public class LoginUserInfo extends BaseModel {
    private LoginUserInfoData data;

    public LoginUserInfoData getData() {
        return data;
    }

    public void setData(LoginUserInfoData data) {
        this.data = data;
    }

}
