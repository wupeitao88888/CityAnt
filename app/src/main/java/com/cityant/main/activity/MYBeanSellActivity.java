package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.cityant.main.R;
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
public class MYBeanSellActivity extends ActivitySupport {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybeansell);
        setCtenterTitle(R.string.beansell);
    }

    public void onTopUpClick(View view) {

    }

    /**
     * 蚂蚁都卖出
     * @param count
     */
    public void setSex(String count) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("bean", count);
        DialogUtil.startDialogLoading(context);
        OkHttpUtils
                .post()
                .url(MYAppconfig.BEANSALE)
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
                        UpdateUserInfoModel model = JSON.parseObject(response, UpdateUserInfoModel.class);
                        UpdateUserInfoModel baseMadel = (UpdateUserInfoModel) model;

                        if (baseMadel.getCode().equals("200")) {
                            ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                        } else {
                            ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                        }
                    }
                });
    }

}
