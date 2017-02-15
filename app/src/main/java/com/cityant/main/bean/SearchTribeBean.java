package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

import java.util.List;

/**
* 搜索品牌部落
* @author Lvfl
* created at 2017/2/15 17:00
*/
public class SearchTribeBean extends BaseModel {

    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String code_message;
        public List<BrandList> brand_list;

        public String getCode_message() {
            return code_message;
        }

        public void setCode_message(String code_message) {
            this.code_message = code_message;
        }

        public List<BrandList> getBrand_list() {
            return brand_list;
        }

        public void setBrand_list(List<BrandList> brand_list) {
            this.brand_list = brand_list;
        }
    }
}
