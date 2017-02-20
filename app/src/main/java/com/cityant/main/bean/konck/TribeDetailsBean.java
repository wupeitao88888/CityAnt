package com.cityant.main.bean.konck;

import com.iloomo.bean.BaseModel;
import java.util.List;

/**
* 部落详情
* @author Lvfl
* created at 2017/2/20 14:11
*/
public class TribeDetailsBean extends BaseModel {

    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String code_message;
        private String brand_id;
        private String brand_img;
        private String brand_name;
        private String groupid;
        private String active_count;
        private String is_focus;
        private String focus_number;
        private String brand_desc;
        public List<UserList> user_list;
        public List<BrandList> other_brand_list;
        public List<GoodsList> goods_list;

        public String getFocus_number() {
            return focus_number;
        }

        public void setFocus_number(String focus_number) {
            this.focus_number = focus_number;
        }

        public String getBrand_desc() {
            return brand_desc;
        }

        public void setBrand_desc(String brand_desc) {
            this.brand_desc = brand_desc;
        }

        public List<BrandList> getOther_brand_list() {
            return other_brand_list;
        }

        public void setOther_brand_list(List<BrandList> other_brand_list) {
            this.other_brand_list = other_brand_list;
        }

        public String getCode_message() {
            return code_message;
        }

        public void setCode_message(String code_message) {
            this.code_message = code_message;
        }

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

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getActive_count() {
            return active_count;
        }

        public void setActive_count(String active_count) {
            this.active_count = active_count;
        }

        public String getIs_focus() {
            return is_focus;
        }

        public void setIs_focus(String is_focus) {
            this.is_focus = is_focus;
        }

        public List<UserList> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<UserList> user_list) {
            this.user_list = user_list;
        }

        public List<GoodsList> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsList> goods_list) {
            this.goods_list = goods_list;
        }

        public class UserList{

            private String user_id;
            private String user_name;
            private String user_avar;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_avar() {
                return user_avar;
            }

            public void setUser_avar(String user_avar) {
                this.user_avar = user_avar;
            }
        }

        public class BrandList{
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
        public class GoodsList{
            private String rob_id;
            private String goods_title;
            private String goods_img;
            private String code;
            private String size;
            private String rob_price;
            private String need_man;
            private String poor_man;
            private String user_avar;
            private String user_name;

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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
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
