package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

import java.util.List;

/**
* 需求详情实体类
* @author lvfl
* @time 2016/11/19 16:07
*/
public class NeedDetails extends BaseModel {

    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String code_message;
        private String need_id;
        private String city_id;
        private String need_way;
        private String need_title;
        private String tag_name;
        private String tag_img;
        private String need_content;
        private String address;
        private String pay_price;
        private String pay_unit;
        private String need_man;
        private String need_time;
        private String end_time;
        private String need_sex;
        private String user_name;
        private String user_avar;
        private String sex;
        private String area;
        private String age;
        private String is_report;
        public List<Reply_list> reply_list;

        public String getCode_message() {
            return code_message;
        }

        public void setCode_message(String code_message) {
            this.code_message = code_message;
        }

        public String getNeed_id() {
            return need_id;
        }

        public void setNeed_id(String need_id) {
            this.need_id = need_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getNeed_way() {
            return need_way;
        }

        public void setNeed_way(String need_way) {
            this.need_way = need_way;
        }

        public String getNeed_title() {
            return need_title;
        }

        public void setNeed_title(String need_title) {
            this.need_title = need_title;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public String getTag_img() {
            return tag_img;
        }

        public void setTag_img(String tag_img) {
            this.tag_img = tag_img;
        }

        public String getNeed_content() {
            return need_content;
        }

        public void setNeed_content(String need_content) {
            this.need_content = need_content;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPay_price() {
            return pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public String getPay_unit() {
            return pay_unit;
        }

        public void setPay_unit(String pay_unit) {
            this.pay_unit = pay_unit;
        }

        public String getNeed_man() {
            return need_man;
        }

        public void setNeed_man(String need_man) {
            this.need_man = need_man;
        }

        public String getNeed_time() {
            return need_time;
        }

        public void setNeed_time(String need_time) {
            this.need_time = need_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getNeed_sex() {
            return need_sex;
        }

        public void setNeed_sex(String need_sex) {
            this.need_sex = need_sex;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getIs_report() {
            return is_report;
        }

        public void setIs_report(String is_report) {
            this.is_report = is_report;
        }

        public List<Reply_list> getReply_list() {
            return reply_list;
        }

        public void setReply_list(List<Reply_list> reply_list) {
            this.reply_list = reply_list;
        }

        public class Reply_list{
            private String content;
            private String user_name;
            private String user_avar;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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
    }
}
