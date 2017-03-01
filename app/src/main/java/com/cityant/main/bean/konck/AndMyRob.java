package com.cityant.main.bean.konck;

import com.iloomo.bean.BaseModel;

import java.util.List;

/**
* 与我相关
* @author Lvfl
* created at 2017/2/28 17:26
*/
public class AndMyRob extends BaseModel {

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

        public class robList{
            private String rob_id;
            private String time;
            private String goods_title;
            private String goods_img;
            private String rob_price;
            private String need_man;
            private String poor_man;
            private String user_avar;
            private String user_name;
            private String is_friend;

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
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
        }
    }
}
