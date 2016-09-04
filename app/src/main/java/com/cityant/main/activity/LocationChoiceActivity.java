package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.cityant.main.R;
import com.cityant.main.adapter.LocationChoiceAdapter;
import com.cityant.main.bean.CityBean;
import com.cityant.main.bean.HomeBean;
import com.cityant.main.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.bean.BaseModel;
import com.iloomo.global.MApplication;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.ToastUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvfl on 2016/8/10.
 */
public class LocationChoiceActivity extends ActivitySupport implements ThreadCallBack {

    private ListView listView;
    private List<CityBean.CityData.CityList> city_list = new ArrayList<>();
    private LocationChoiceAdapter adapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LocationChoiceActivity.class);
        context.startActivity(intent);
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_choice_layout);
        setCtenterTitle("位置");
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        adapter = new LocationChoiceAdapter(city_list, context);
        listView.setAdapter(adapter);
        getCity();
    }

    private void getCity() {
        DialogUtil.startDialogLoading(context);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("is_hot", "1"); // 1 热门  0 全部

        startHttpRequst("POST", MYAppconfig.CITY_LIST, parameter
                , MYTaskID.CITY_LIST);
    }


    public void startHttpRequst(String requestType, String url,
                                Map<String, Object> parameter, int resultCode) {

        AsyncHttpPost httpRequest;
        httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
                CityBean.class, context);


    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        DialogUtil.stopDialogLoading(context);
        if(resultCode == MYTaskID.CITY_LIST){
            CityBean cityBean = (CityBean) modelClass;
            city_list.addAll(cityBean.getData().city_list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        DialogUtil.stopDialogLoading(context);
        BaseModel baseModel = (BaseModel) modelClass;
        ToastUtil.show(context,baseModel.getResultMsg(), Toast.LENGTH_SHORT);
    }
}
