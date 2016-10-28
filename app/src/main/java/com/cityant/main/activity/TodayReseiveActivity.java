package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cityant.main.R;
import com.cityant.main.adapter.ToadyReseiveAdapter;
import com.cityant.main.bean.TodayIncomeData;
import com.cityant.main.bean.TodayIncomeModel;
import com.cityant.main.bean.UpdateUserInfoModel;
import com.cityant.main.db.DBControlApp;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ToastUtil;
import com.iloomo.utils.UnicodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wupeitao on 16/8/13.
 */
public class TodayReseiveActivity extends ActivitySupport {

    private TextView income;
    private ListView reseiveListView;

    private ToadyReseiveAdapter toadyReseiveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todayreseive);
        setCtenterTitle(R.string.income);
        setRightTitle("晒一晒");
        income = (TextView) findViewById(R.id.income);
        setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        reseiveListView = (ListView) findViewById(R.id.reseiveListView);

        Map<String, String> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        DialogUtil.startDialogLoading(context);
        OkHttpUtils
                .post()
                .url(MYAppconfig.INCOME)
                .params(parameter)
                .tag(context)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showShort(context, e.getMessage());
                        DialogUtil.stopDialogLoading(context);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        response = UnicodeUtils.decodeUnicode(response);
                        DialogUtil.stopDialogLoading(context);
                        TodayIncomeModel model = JSON.parseObject(response, TodayIncomeModel.class);
                        TodayIncomeModel baseMadel = (TodayIncomeModel) model;
                        if (baseMadel.getCode().equals("200")) {
//                            ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                            StrUtil.setText(income, baseMadel.getData().getTotal_price());
                            toadyReseiveAdapter = new ToadyReseiveAdapter(context, baseMadel.getData().getIncome_list());
                            reseiveListView.setAdapter(toadyReseiveAdapter);
                        } else {
                            ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                        }
                    }
                });
    }


}
