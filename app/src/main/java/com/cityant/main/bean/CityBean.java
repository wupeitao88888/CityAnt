package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

import java.util.List;

/**
 * Created by lvfl on 2016/9/3.
 */
public class CityBean extends BaseModel {

    private CityData data;

    public CityData getData() {
        return data;
    }

    public void setData(CityData data) {
        this.data = data;
    }

    public class CityData{

        public String code_message;
        public List<CityList> city_list;

        public String getCode_message() {
            return code_message;
        }

        public void setCode_message(String code_message) {
            this.code_message = code_message;
        }

        public List<CityList> getCity_list() {
            return city_list;
        }

        public void setCity_list(List<CityList> city_list) {
            this.city_list = city_list;
        }

        public class CityList{

            public String city_id;
            public String city_name;

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }
        }
    }
}
