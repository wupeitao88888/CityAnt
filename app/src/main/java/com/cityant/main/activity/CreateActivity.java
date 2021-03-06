package com.cityant.main.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.CreateAdapter;
import com.cityant.main.bean.CreatedModel;
import com.hyphenate.easeui.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 16/8/13.
 */
public class CreateActivity extends ActivitySupport implements ThreadCallBack {
    private ListView createds;
    private CreateAdapter createAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setCtenterTitle(R.string.created);
        createds = (ListView) findViewById(R.id.createds);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("page", "1");
        parameter.put("page_size", "1000");

        startHttpRequst(MYAppconfig.NEEDLIST, parameter, MYTaskID.NEEDLIST);
    }

    public void startHttpRequst(String url,
                                Map<String, Object> parameter, int resultCode) {

        new AsyncHttpPost(this, url, parameter, resultCode,
                CreatedModel.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.NEEDLIST:
                CreatedModel createdModel = (CreatedModel) modelClass;
                createAdapter = new CreateAdapter(context, createdModel.getCreateData().getNeed_list());
                createds.setAdapter(createAdapter);
                break;
        }

    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.NEEDLIST:
                CreatedModel createdModel = (CreatedModel) modelClass;
                ToastUtil.showShort(context, createdModel.getCreateData().getCode_message());
                break;
        }
    }
}
