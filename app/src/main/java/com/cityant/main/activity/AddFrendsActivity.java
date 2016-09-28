package com.cityant.main.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cityant.main.R;
import com.cityant.main.bean.AddFrend;
import com.cityant.main.bean.MyFrendsModel;
import com.cityant.main.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 2016/9/28.
 */

public class AddFrendsActivity extends ActivitySupport implements ThreadCallBack {
    private EditText userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfrends);
        initView();
    }

    private void initView() {
        userid = (EditText) findViewById(R.id.userid);

    }


    public void onAddFrends(View view) {
        String user_id = userid.getText().toString();
        if (TextUtils.isEmpty(user_id)) {
            ToastUtil.showShort(context, "用户id不能为空");
            return;
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("friend_id", user_id);
        startHttpRequst(MYAppconfig.ADDFRENDS, parameter, MYTaskID.ADDFRENDS);

    }

    public void onNearby(View view) {

    }

    public void onScan(View view) {

    }

    public void onAddressBookContact(View view) {

    }


    public void startHttpRequst(String url,
                                Map<String, Object> parameter, int resultCode) {

        new AsyncHttpPost(this, url, parameter, resultCode,
                AddFrend.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.ADDFRENDS:
                AddFrend addFrends=(AddFrend)modelClass;
                ToastUtil.showShort(context,addFrends.getData().getCode_message());
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.ADDFRENDS:
                AddFrend addFrends=(AddFrend)modelClass;
                ToastUtil.showShort(context,addFrends.getData().getCode_message());
                break;
        }
    }
}
