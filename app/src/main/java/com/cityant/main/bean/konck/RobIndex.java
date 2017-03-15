package com.cityant.main.bean.konck;

import com.iloomo.bean.BaseModel;

import java.util.List;

/**
* 抢首页
* @author Lvfl
* created at 2017/3/2 19:14
*/
public class RobIndex extends BaseModel {

    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String code_message;
        public List<robList> rob_list;
        public List<brandList> brand_list;

        public List<brandList> getBrand_list() {
            return brand_list;
        }

        public void setBrand_list(List<brandList> brand_list) {
            this.brand_list = brand_list;
        }

        public String getCode_message() {
            return code_message;
        }

        public void setCode_message(String code_message) {
            this.code_message = code_message;
        }

        public List<robList> getRob_list() {
            return rob_list;
        }

        public void setRob_list(List<robList> rob_list) {
            this.rob_list = rob_list;
        }

        public class brandList{
            private String brand_id;
            private String brand_img;
            private String brand_name;

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getBrand_img() {
                return brand_img;
            }

            public void setBrand_img(String brand_img) {
                this.brand_img = brand_img;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }
        }
        public class robList{
            private String rob_id;
            private String goods_title;
            private String goods_img;
            private String rob_price;
            private String need_man;
            private String poor_man;
            private String user_avar;
            private String user_name;
            private String is_friend;

            public String getIs_friend() {
                return is_friend;
            }

            public void setIs_friend(String is_friend) {
                this.is_friend = is_friend;
            }

            public String getRob_id() {
                return rob_id;
            }

            public void setRob_id(String rob_id) {
                this.rob_id = rob_id;
            }

            public String getGoods_title() {
                return goods_title;
            }

            public void setGoods_title(String goods_title) {
                this.goods_title = goods_title;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getRob_price() {
                return rob_price;
            }

            public void setRob_price(String rob_price) {
                this.rob_price = rob_price;
            }

            public String getNeed_man() {
                return need_man;
            }

            public void setNeed_man(String need_man) {
                this.need_man = need_man;
            }

            public String getPoor_man() {
                return poor_man;
            }

            public void setPoor_man(String poor_man) {
                this.poor_man = poor_man;
            }

            public String getUser_avar() {
                return user_avar;
            }

            public void setUser_avar(String user_avar) {
                this.user_avar = user_avar;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }
    }


}
