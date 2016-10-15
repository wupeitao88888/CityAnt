package com.cityant.main.bean;

import com.iloomo.bean.BaseData;

import java.util.List;

/**
 * Created by wupeitao on 2016/10/15.
 */

public class EvaluateListData extends BaseData {
    private String overall_rating;//综合评级
    private String total_count;//累计次数
    private String total_score;//累计评分"
    private List<EvaluateList> evaluate_list;

    public String getOverall_rating() {
        return overall_rating;
    }

    public void setOverall_rating(String overall_rating) {
        this.overall_rating = overall_rating;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getTotal_score() {
        return total_score;
    }

    public void setTotal_score(String total_score) {
        this.total_score = total_score;
    }

    public List<EvaluateList> getEvaluate_list() {
        return evaluate_list;
    }

    public void setEvaluate_list(List<EvaluateList> evaluate_list) {
        this.evaluate_list = evaluate_list;
    }
}
