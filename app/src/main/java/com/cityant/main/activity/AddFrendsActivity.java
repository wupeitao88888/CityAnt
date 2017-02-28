package com.cityant.main.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.cityant.main.R;
import com.cityant.main.bean.AddFrend;
import com.cityant.main.zxing.MipcaActivityCapture;
import com.hyphenate.easeui.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.PViewUtil;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 2016/9/28.
 */

public class AddFrendsActivity extends ActivitySupport implements ThreadCallBack {
    private EditText userid;
    private RelativeLayout ll_title_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addfrends);
        setRemoveTitle();
        initView();
    }

    private void initView() {
        userid = (EditText) findViewById(R.id.userid);
        ll_title_content = (RelativeLayout) findViewById(com.iloomo.paysdk.R.id.ll_title_content);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ll_title_content.setPadding(0, (int) PViewUtil.dip2px(context, 20.0f), 0, 0);
        } else {
            ll_title_content.setPadding(0, 0, 0, 0);
        }
    }

    public void onMyBack(View view) {
        finish();
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

    /***
     * 附近
     * @param view
     */
    public void onNearby(View view) {

    }

    /***
     * 扫一扫
     * @param view
     */
    public void onScan(View view) {
        Intent intent = new Intent(getContext(),MipcaActivityCapture.class);
        startActivity(intent);
    }

    /***
     * 通讯录
     * @param view
     */
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
                AddFrend addFrends = (AddFrend) modelClass;
                ToastUtil.showShort(context, addFrends.getData().getCode_message());
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
                AddFrend addFrends = (AddFrend) modelClass;
                ToastUtil.showShort(context, addFrends.getData().getCode_message());
                break;
        }
    }
}
