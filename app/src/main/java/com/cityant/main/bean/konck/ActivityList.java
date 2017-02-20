package com.cityant.main.bean.konck;

import com.iloomo.bean.BaseModel;

import java.util.List;

/**
* 部落所有关注
* @author Lvfl
* created at 2017/2/20 16:09
*/
public class ActivityList extends BaseModel {

    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String code_message;
        private String active_count;
        public List<UserList> user_list;

        public String getCode_message() {
            return code_message;
        }

        public void setCode_message(String code_message) {
            this.code_message = code_message;
        }

        public String getActive_count() {
            return active_count;
        }

        public void setActive_count(String active_count) {
            this.active_count = active_count;
        }

        public List<UserList> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<UserList> user_list) {
            this.user_list = user_list;
        }

        public class UserList{
            private String user_id;
            private String user_name;
            private String user_avar;
            private String active_number;

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

            public String getActive_number() {
                return active_number;
            }

            public void setActive_number(String active_number) {
                this.active_number = active_number;
            }
        }
    }
}
