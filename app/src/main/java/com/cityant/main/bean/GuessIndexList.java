package com.cityant.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wupeitao on 2017/2/7.
 */

public class GuessIndexList implements Serializable {
    private int guess_id;//猜id
    private String question;//题干
    private String ok_bean;//答对奖励
    private String fail_bean;//答错扣分
    private int type;//类型(0:双选;1:三选;2:四选)",
    private String user_name;
    private String user_avar;
    private int answer;//用户答案",
    private int isAd;//是否是广告
    private List<String> keyList; //选项

    public int getGuess_id() {
        return guess_id;
    }

    public void setGuess_id(int guess_id) {
        this.guess_id = guess_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOk_bean() {
        return ok_bean;
    }

    public void setOk_bean(String ok_bean) {
        this.ok_bean = ok_bean;
    }

    public String getFail_bean() {
        return fail_bean;
    }

    public void setFail_bean(String fail_bean) {
        this.fail_bean = fail_bean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getIsAd() {
        return isAd;
    }

    public void setIsAd(int isAd) {
        this.isAd = isAd;
    }

    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }
}
