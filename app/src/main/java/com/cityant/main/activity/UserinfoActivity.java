package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.EvaluateListModel;
import com.cityant.main.bean.UserInfoModel;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 16/8/13.
 */
public class UserinfoActivity extends ActivitySupport implements ThreadCallBack {

    private TextView nickname;
    private ImageView userhead_img;
    private TextView sex;
    private TextView adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        setCtenterTitle(R.string.userinfo);
        initView();
    }

    private void initView() {
        nickname = (TextView) findViewById(R.id.nickname);
        userhead_img = (ImageView) findViewById(R.id.userhead_img);
        sex = (TextView) findViewById(R.id.sex);
        adress = (TextView) findViewById(R.id.adress);
        getUserInfo();
    }

    public void getUserInfo() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());

        new AsyncHttpPost(this, MYAppconfig.USERINFO, parameter, MYTaskID.USERINFO,
                UserInfoModel.class, context);
    }

    /***
     * 点击头像查看
     *
     * @param view
     */
    public void onUserheadClick(View view) {

    }

    /***
     * 点击更换头像
     *
     * @param view
     */
    public void onUserheadChangeClick(View view) {

    }

    /***
     * 点击修改昵称
     *
     * @param view
     */
    public void onNickNameChangeClick(View view) {

    }

    /***
     * 查看我的二维码
     *
     * @param view
     */
    public void onMeTwoCodeClick(View view) {
    }

    /****
     * 收货地址查看与更改
     *
     * @param view
     */
    public void onReseiveAdressClick(View view) {

    }

    /***
     * 修改出生年月
     */
    public void onBothdayClick(View view) {
    }

    /***
     * 修改地址
     *
     * @param view
     */
    public void onAdressClick(View view) {

    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.USERINFO:
                UserInfoModel userInfoModel = (UserInfoModel) modelClass;

                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.USERINFO:
                UserInfoModel userInfoModel = (UserInfoModel) modelClass;
                ToastUtil.showShort(context, userInfoModel.getData().getCode_message());
                break;
        }
    }
}
