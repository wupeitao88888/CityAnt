package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.TimePickerView;
import com.cityant.main.R;

import com.cityant.main.bean.TagList;
import com.cityant.main.global.MYAppconfig;
import com.cityant.main.global.MYApplication;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.bean.BaseModel;
import com.iloomo.net.AsyncHttpPost;

import com.iloomo.net.ThreadCallBack;

import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvfl on 2016/8/17.
 */
public class CreateDemandActivity extends ActivitySupport implements ThreadCallBack {

    private Button commit_btn;
    private TimePickerView birthPicker;
    private List<TagList.Data.Tag_List>  tag_lists = new ArrayList<>();

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
                DialogUtil.startDialogLoading(context);
                Map<String, Object> parameter = new HashMap<>();
                parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
                parameter.put("latitude", MYApplication.getInstance().latitude+"");
                parameter.put("longitude", MYApplication.getInstance().longitude+"");
                parameter.put("city_id", "10");
                parameter.put("pay_type", "0");
                parameter.put("need_way", "0");
                parameter.put("need_title", "测试需求");
                parameter.put("tag_id", tag_lists.get(0).getTag_id());
                parameter.put("need_content", "我要发布一款测试需求");
                parameter.put("need_sex", "0");
                parameter.put("address", "北京");
                parameter.put("need_time", "2016-09-01");
                parameter.put("end_time", "2");
                parameter.put("pay_price", "15");
                parameter.put("pay_unit", "元");
                parameter.put("need_man", "3");
                startHttpRequst(MYAppconfig.CREATE_NEED, parameter
                        , MYTaskID.CREATE_NEED);
            }
        });
        Map<String, Object> parameter = new HashMap<>();
        new AsyncHttpPost(this,MYAppconfig.TAG_LIST,parameter,MYTaskID.TAG_LIST,TagList.class,context);
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
                BaseModel.class, context);

    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {
        Log.e("-----chen gong -------",resultJson);
    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        if(MYTaskID.TAG_LIST == resultCode){
            TagList tagList = (TagList) modelClass;
            tag_lists.addAll(tagList.getData().getTag_list());
        } else if(MYTaskID.CREATE_NEED == resultCode){
            DialogUtil.stopDialogLoading(context);
        }
        Log.e("-----chen gong -------",resultJson);
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {
        Log.e("-----shi bai  -------",resultJson);
    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        Log.e("-----shi bai -------",resultJson);
    }
}
