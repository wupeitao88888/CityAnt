package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

import java.util.List;

/**
 * Created by lvfl on 2016/8/25.
 */
public class HomeBean extends BaseModel {

    public HomeData data;

    public HomeData getData() {
        return data;
    }

    public void setData(HomeData data) {
        this.data = data;
    }

    public class HomeData{
        public String code_message;
        public List<BannerList> banner_list;
        public List<ADList> ad_list;
        public List<NeedList> need_list;

        public String getCode_message() {
            return code_message;
        }

        public void setCode_message(String code_message) {
            this.code_message = code_message;
        }

        public List<BannerList> getBanner_list() {
            return banner_list;
        }

        public void setBanner_list(List<BannerList> banner_list) {
            this.banner_list = banner_list;
        }

        public List<ADList> getAd_list() {
            return ad_list;
        }

        public void setAd_list(List<ADList> ad_list) {
            this.ad_list = ad_list;
        }

        public List<NeedList> getNeed_list() {
            return need_list;
        }

        public void setNeed_list(List<NeedList> need_list) {
            this.need_list = need_list;
        }

        public class BannerList{
            public String ad_title;
            public String ad_url;
            public String ad_image;

            public String getAd_title() {
                return ad_title;
            }

            public void setAd_title(String ad_title) {
                this.ad_title = ad_title;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }

            public String getAd_image() {
                return ad_image;
            }

            public void setAd_image(String ad_image) {
                this.ad_image = ad_image;
            }
        }
        public class ADList{
            public String ad_title;
            public String ad_url;
            public String ad_image;

            public String getAd_title() {
                return ad_title;
            }

            public void setAd_title(String ad_title) {
                this.ad_title = ad_title;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }

            public String getAd_image() {
                return ad_image;
            }

            public void setAd_image(String ad_image) {
                this.ad_image = ad_image;
            }
        }
        public class NeedList{
            public String need_id;
            public String need_title;
            public String need_content;
            public String pay_price;
            public String pay_unit;
            public String tag_name;
            public String need_man;
            public String city_id;
            public String need_way;
            public String add_time;
            public String user_name;
            public String user_avar;
            public String sex;
            public String is_true;
            public String age;
            public String address;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getNeed_id() {
                return need_id;
            }

            public void setNeed_id(String need_id) {
                this.need_id = need_id;
            }

            public String getNeed_title() {
                return need_title;
            }

            public void setNeed_title(String need_title) {
                this.need_title = need_title;
            }

            public String getNeed_content() {
                return need_content;
            }

            public void setNeed_content(String need_content) {
                this.need_content = need_content;
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

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }

            public String getNeed_man() {
                return need_man;
            }

            public void setNeed_man(String need_man) {
                this.need_man = need_man;
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

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
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

            public String getIs_true() {
                return is_true;
            }

            public void setIs_true(String is_true) {
                this.is_true = is_true;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }
        }
    }

}
