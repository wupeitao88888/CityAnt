package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.cityant.main.R;
import com.cityant.main.adapter.MyBeanTopUpAdapter;
import com.cityant.main.bean.MYBeanSell;
import com.cityant.main.bean.MYBeanSellModel;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wupeitao on 16/8/13.
 */
public class MYBeanTopUpActivity extends ActivitySupport {
    private GridView bean_sell;
    private MyBeanTopUpAdapter myBeanTopUpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybeantopup);
        setCtenterTitle(R.string.beantopup);
        bean_sell = (GridView) findViewById(R.id.bean_sell);

        getBeanList();
    }

    public void getBeanList() {
        Map<String, String> parameter = new HashMap<>();
//        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
//        parameter.put("sex", sexStr);
        DialogUtil.startDialogLoading(context);
        OkHttpUtils
                .post()
                .url(MYAppconfig.beanList)
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
                        MYBeanSellModel model = JSON.parseObject(response, MYBeanSellModel.class);
                        MYBeanSellModel baseMadel = (MYBeanSellModel) model;
                        if (baseMadel.getCode().equals("200")) {
                            myBeanTopUpAdapter = new MyBeanTopUpAdapter(context, baseMadel.getData().getBean_list());
                            bean_sell.setAdapter(myBeanTopUpAdapter);
                            //跳转到支付页面
                            bean_sell.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                }
                            });
                        } else {
                            ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                        }
                    }
                });
    }
}
