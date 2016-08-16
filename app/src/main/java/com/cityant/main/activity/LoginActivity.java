package com.cityant.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cityant.main.R;
import com.cityant.main.bean.LoginUserInfo;
import com.cityant.main.db.DBControl;
import com.cityant.main.glabal.MYAppconfig;
import com.cityant.main.glabal.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.bean.SMSBaseModel;
import com.iloomo.net.AsyncHttpGet;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.BaseRequest;
import com.iloomo.net.DefaultThreadPool;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 16/8/8.
 */
public class LoginActivity extends ActivitySupport implements ThreadCallBack {
    private EditText phone_number;
    private EditText password_number;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRemoveTitle();
        initView();
    }

    private void initView() {
        phone_number = (EditText) findViewById(R.id.phone_number);
        password_number = (EditText) findViewById(R.id.password_number);
        login_button = (Button) findViewById(R.id.login_button);
        phone_number.addTextChangedListener(new EditChangedListener());
        password_number.addTextChangedListener(new EditChangedListener());

    }


    @Override
    protected void onStart() {
        super.onStart();
        MyThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = DBControl.getInstance(context).selectLastUser();
                Message message = new Message();
                message.what = INIT;
                message.obj = bundle;
                handler.sendMessage(message);
            }
        });
    }

    public void onLoginClick(View view) {
        DialogUtil.startDialogLoading(context);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("mobile", phone_number.getText().toString());
        parameter.put("password", password_number.getText().toString());
        startHttpRequst("POST", MYAppconfig.USERLOGIN, parameter
                , MYTaskID.USERLOGIN);

    }

    public void onQQLoginClick(View view) {
        mIntent(context, BindingActivity.class);
    }

    public void onWeiXinLoginClick(View view) {
        mIntent(context, BindingActivity.class);
    }

    public void onWeboLoginClick(View view) {
        mIntent(context, BindingActivity.class);
    }

    public void onForgetPasswordClick(View view) {
        mIntent(context, ForgetPasswordActivity.class);
    }

    public void onNewUserClick(View view) {
        mIntent(context, NewUserActivity.class);
    }

    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (phone_number.getText().length() > 0) {
                if (password_number.getText().length() > 0) {

                    if (phone_number.getText().length() == 11) {
                        login_button.setPressed(false);
                        login_button.setClickable(true);
                    } else {
                        login_button.setPressed(true);
                        login_button.setClickable(false);
                    }
                } else {
                    login_button.setPressed(true);
                    login_button.setClickable(false);
                }
            } else {
                login_button.setPressed(true);
                login_button.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    public void startHttpRequst(String requestType, String url,
                                Map<String, Object> parameter, int resultCode) {

        BaseRequest httpRequest = null;
        if ("POST".equalsIgnoreCase(requestType)) {
            httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
                    LoginUserInfo.class);
        } else {
            httpRequest = new AsyncHttpGet(this, url, parameter, resultCode,
                    LoginUserInfo.class);
        }
        DefaultThreadPool.getInstance().execute(httpRequest);
    }


    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {

        switch (resultCode) {
            case MYTaskID.USERLOGIN:
                MyThreadPool.getInstance().submit(new Runnable() {
                    @Override
                    public void run() {
                        DBControl.getInstance(context).insertLastUser(phone_number.getText().toString(), password_number.getText().toString());
                        LoginUserInfo loginUserInfo = (LoginUserInfo) modelClass;
                        DBControl.getInstance(context).insertLoginInfo(loginUserInfo.getData());
                        Message message = new Message();
                        message.what = LOGIN;
                        message.obj = "";
                        handler.sendMessage(message);
                    }
                });
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        L.e(""+resultJson);
        DialogUtil.stopDialogLoading(context);
        LoginUserInfo loginUserInfo = (LoginUserInfo) modelClass;
        ToastUtil.showShort(context,loginUserInfo.getData().getCode_message());
    }

    private final int LOGIN = 100;
    private final int INIT = 101;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN:
                    DialogUtil.stopDialogLoading(context);
                    mIntent(context, IndexFragment.class);
                    finish();
                    break;
                case INIT:
                    Bundle bundle=(Bundle)msg.obj;
                    String mobile = bundle.getString("mobile");
                    String password = bundle.getString("password");

                    if (!TextUtils.isEmpty(mobile)) {
                        phone_number.setText(mobile);
                    }
                    if (!TextUtils.isEmpty(password)) {
                        password_number.setText(password);
                        login_button.setPressed(false);
                        login_button.setClickable(true);
                    }else{
                        login_button.setPressed(true);
                        login_button.setClickable(false);
                    }
                    break;
            }
        }
    };
}

