package com.iloomo.bean;

import java.io.Serializable;

public class ErrorBaseModel implements Serializable {

    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String code_message;

        public String getCode_message() {
            return code_message;
        }

        public void setCode_message(String code_message) {
            this.code_message = code_message;
        }
    }


}
