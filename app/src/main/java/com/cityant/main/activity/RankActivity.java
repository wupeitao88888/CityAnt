package com.cityant.main.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.adapter.RankAdapter;
import com.cityant.main.bean.EvaluateListModel;
import com.cityant.main.bean.TodayIncomeModel;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ToastUtil;
import com.iloomo.widget.CostomRatingBar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 16/8/13.
 */
public class RankActivity extends ActivitySupport implements ThreadCallBack {
    private TextView total_count;
    private TextView total_score;
    private CostomRatingBar overall_rating;
    private ListView evaluate_listListView;
    private RankAdapter rankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        setCtenterTitle("评价");
        initView();
    }

    private void initView() {
        total_count = (TextView) findViewById(R.id.total_count);
        total_score = (TextView) findViewById(R.id.total_score);
        overall_rating = (CostomRatingBar) findViewById(R.id.overall_rating);
        evaluate_listListView = (ListView) findViewById(R.id.evaluate_listListView);
        getRank();
    }

    public void getRank() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());

        AsyncHttpPost httpRequest;
        httpRequest = new AsyncHttpPost(this, MYAppconfig.EVALUATELIST, parameter, MYTaskID.EVALUATELIST,
                EvaluateListModel.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {

            case MYTaskID.EVALUATELIST:
                EvaluateListModel evaluateListModel = (EvaluateListModel) modelClass;

                rankAdapter = new RankAdapter(context, evaluateListModel.getData().getEvaluate_list());
                evaluate_listListView.setAdapter(rankAdapter);
                //评分
                overall_rating.setMark(Float.parseFloat(evaluateListModel.getData().getTotal_score()));
                StrUtil.setText(total_count, evaluateListModel.getData().getTotal_count());
                StrUtil.setText(total_score, evaluateListModel.getData().getTotal_score());
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.EVALUATELIST:
                EvaluateListModel evaluateListModel = (EvaluateListModel) modelClass;
                ToastUtil.showShort(context, evaluateListModel.getData().getCode_message());
                break;
        }
    }
}
