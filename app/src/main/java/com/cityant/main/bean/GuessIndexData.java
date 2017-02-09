package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 2017/2/7.
 */

public class GuessIndexData extends BaseData {
    private String bean;
    private String is_message;
    private List<GuessIndexList> guess_list;

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public String getIs_message() {
        return is_message;
    }

    public void setIs_message(String is_message) {
        this.is_message = is_message;
    }

    public List<GuessIndexList> getGuess_list() {
        return guess_list;
    }

    public void setGuess_list(List<GuessIndexList> guess_list) {
        this.guess_list = guess_list;
    }
}
