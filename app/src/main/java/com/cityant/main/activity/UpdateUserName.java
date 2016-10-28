package com.cityant.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.cityant.main.R;
import com.cityant.main.bean.UpdateUserInfoModel;
import com.cityant.main.bean.UserInfoModel;
import com.cityant.main.db.DBControlApp;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;

import com.iloomo.bean.BaseModel;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ErrorUtil;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ToastUtil;
import com.iloomo.utils.UnicodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wupeitao on 2016/10/20.
 */
public class UpdateUserName extends ActivitySupport implements ThreadCallBack {
    private EditText username;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateusername);
        setCtenterTitle("修改姓名");
        setRightTitle("保存");
        setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString())) {
                    ToastUtil.showShort(context, "修改姓名不能为空！");
                    return;
                }
                Map<String, String> parameter = new HashMap<>();

//                new AsyncHttpPost(this, MYAppconfig.USERUPDATE, parameter, MYTaskID.UPDATEUSERNAME, UpdateUserInfoModel.class, context);
                parameter.put("user_name", username.getText().toString());
                parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
                DialogUtil.startDialogLoading(context);
                OkHttpUtils
                        .post()
                        .url(MYAppconfig.USERUPDATE)
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
                                    MyThreadPool.getInstance().submit(new Runnable() {
                                        @Override
                                        public void run() {
                                            DBControlApp.getInstance(context).updateLoginInfo(MYAppconfig.loginUserInfoData.getToken(),
                                                    username.getText().toString(),
                                                    MYAppconfig.loginUserInfoData.getUser_avar());
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    MYAppconfig.loginUserInfoData.setUser_name(username.getText().toString());
                                                    finish();
                                                }
                                            });
                                        }
                                    });


                                } else {
                                    ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                                }
                            }
                        });

            }
        });
        username = (EditText) findViewById(R.id.username);
        StrUtil.setText(username, MYAppconfig.loginUserInfoData.getUser_name());
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
