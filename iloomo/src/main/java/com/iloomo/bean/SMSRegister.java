package com.iloomo.bean;

/**
 * Created by wupeitao on 16/8/14.
 */
public class SMSRegister extends BaseModel {
    public UserRegister data;

    public UserRegister getData() {
        return data;
    }

    public void setData(UserRegister data) {
        this.data = data;
    }

    public class UserRegister extends BaseDate {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
