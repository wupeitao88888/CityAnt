package com.cityant.main.bean;

/**
 * Created by wupeitao on 2016/9/22.
 */

public class BusEventFragmentMessage {
    public int isList;//是否刷新列表 1,刷新，2，是连接不到聊天服务器，3，当前网络不可用，请检查网络设置

    public BusEventFragmentMessage(int isList) {
        this.isList = isList;
    }

    public int getContent() {
        return isList;
    }

    public void setContent(int isList) {
        this.isList = isList;
    }
}
