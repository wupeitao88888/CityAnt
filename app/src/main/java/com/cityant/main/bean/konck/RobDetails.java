package com.cityant.main.bean.konck;

import com.iloomo.bean.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
* 抢详情
* @author Lvfl
* created at 2017/3/1 19:04
*/
public class RobDetails extends BaseModel {

    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable{
        private String code_message;
        private String rob_id;
        private String state;
        private String goods_title;
        private String goods_img;
        private List<String> goods_imgs;
        private String rob_price;
        private String need_man;
        private String poor_man;
        private String size;
        private String is_friend;
        private String end_time;
        public List<userList> user_list;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getIs_friend() {
            return is_friend;
        }

        public void setIs_friend(String is_friend) {
            this.is_friend = is_friend;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCode_message() {
            return code_message;
        }

        public void setCode_message(String code_message) {
            this.code_message = code_message;
        }

        public String getRob_id() {
            return rob_id;
        }

        public void setRob_id(String rob_id) {
            this.rob_id = rob_id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

        public List<String> getGoods_imgs() {
            return goods_imgs;
        }

        public void setGoods_imgs(List<String> goods_imgs) {
            this.goods_imgs = goods_imgs;
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

        public List<userList> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<userList> user_list) {
            this.user_list = user_list;
        }

        public class userList implements Serializable{
            private String user_name;
            private String user_avar;
            private String is_own;
            private String is_win;

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

            public String getIs_own() {
                return is_own;
            }

            public void setIs_own(String is_own) {
                this.is_own = is_own;
            }

            public String getIs_win() {
                return is_win;
            }

            public void setIs_win(String is_win) {
                this.is_win = is_win;
            }
        }
    }
}
