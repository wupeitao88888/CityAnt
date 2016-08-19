package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.TimePickerView;
import com.cityant.main.R;

import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;

import com.iloomo.net.ThreadCallBack;

import com.iloomo.utils.ToastUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by lvfl on 2016/8/17.
 */
public class CreateDemandActivity extends ActivitySupport implements ThreadCallBack {

    private Button commit_btn;
    private TimePickerView birthPicker;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CreateDemandActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_demand_layout);
        setCtenterTitle("创建需求");
        initView();
    }

    private void initView() {
        initBirthPicker();
        commit_btn = (Button) findViewById(R.id.commit_btn);
        birthPicker.setOnTimeSelectListener(data -> {
            ToastUtil.show(context, data.toString(), ToastUtil.SHOW_TOAST);
        });
        commit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 网络请求
//                string   token   用户令牌
//                int   latitude   当前用户纬度
//                int   longitude   当前用户经度
//                int   city_id   城市id
//                int   pay_type   支付方式(0:支付宝,1:微信)
//                int   need_way   形式(0:线上,1:线下)
//                string   need_title   标题
//                int   tag_id   标签id
//                string   need_content   详情
//                int   need_sex   性别要求(0:男，1：女，2：不限)
//                string   address   地点
//                string   need_time   时间格式(Y-m-d H:i:s)
//                string   end_time   报名截止日期格式(Y-m-d H:i:s)
//                string   pay_price   单价
//                string   pay_unit   单位
//                int   need_man   所需名额
                birthPicker.show();
//                DialogUtil.startDialogLoading(context);
//                Map<String, Object> parameter = new HashMap<>();
//                parameter.put("token", "");
//                parameter.put("latitude", "");
//                parameter.put("longitude", "");
//                parameter.put("city_id", "");
//                parameter.put("pay_type", "");
//                parameter.put("need_way", "");
//                parameter.put("need_title", "");
//                parameter.put("tag_id", "");
//                parameter.put("need_content", "");
//                parameter.put("need_sex", "");
//                parameter.put("address", "");
//                parameter.put("need_time", "");
//                parameter.put("end_time", "");
//                parameter.put("pay_price", "15");
//                parameter.put("pay_unit", "元");
//                parameter.put("need_man", "3");
//                startHttpRequst(MYAppconfig.USERLOGIN, parameter
//                        , MYTaskID.USERLOGIN);
            }
        });
    }

    private void initBirthPicker() {
        birthPicker = new TimePickerView(this, TimePickerView.Type.ALL);
        Calendar calendar = Calendar.getInstance();
        birthPicker.setRange(calendar.get(Calendar.YEAR) - 99, calendar.get(Calendar.YEAR));
        birthPicker.setTime(new Date());
        birthPicker.setCyclic(false);
        birthPicker.setCancelable(true);
    }

    public void startHttpRequst(String url,
                                Map<String, Object> parameter, int resultCode) {


        AsyncHttpPost httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
                CreateDemandActivity.class, context);

    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {

    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

    }
}
