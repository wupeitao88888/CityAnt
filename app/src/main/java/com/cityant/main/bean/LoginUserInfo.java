package com.cityant.main.bean;

import com.hyphenate.easeui.bean.LoginUserInfoData;
import com.iloomo.bean.BaseModel;

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
