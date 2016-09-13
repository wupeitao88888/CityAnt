package com.cityant.main.bean;

import com.iloomo.bean.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class TagList extends BaseModel {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<Tag_List> tag_list;

        public List<Tag_List> getTag_list() {
            return tag_list;
        }

        public void setTag_list(List<Tag_List> tag_list) {
            this.tag_list = tag_list;
        }

        public class Tag_List {
            private String tag_id;
            private String tag_name;

            public String getTag_id() {
                return tag_id;
            }

            public void setTag_id(String tag_id) {
                this.tag_id = tag_id;
            }

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }
        }
    }
}
