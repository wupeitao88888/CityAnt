package com.cityant.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cityant.main.R;
import com.cityant.main.bean.LoginUserInfo;
import com.hyphenate.easeui.db.DBControl;
import com.hyphenate.easeui.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;


import com.iloomo.net.ThreadCallBack;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import me.shaohui.shareutil.LoginUtil;
import me.shaohui.shareutil.login.LoginListener;
import me.shaohui.shareutil.login.LoginPlatform;
import me.shaohui.shareutil.login.LoginResult;
import me.shaohui.shareutil.login.result.QQToken;
import me.shaohui.shareutil.login.result.QQUser;

/**
 * Created by wupeitao on 16/8/8.
 */
public class LoginActivity extends ActivitySupport implements ThreadCallBack {
    private EditText phone_number;
    private EditText password_number;
    private Button login_button;
    private TextView negotiate;
    private CheckBox agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setCtenterTitle(mString(R.string.login_title));
        setRightTitle(mString(R.string.newuser));
        setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNewUserClick();
            }
        });
        initView();
    }

    private void initView() {
        phone_number = (EditText) findViewById(R.id.phone_number);
        password_number = (EditText) findViewById(R.id.password_number);
        login_button = (Button) findViewById(R.id.login_button);
        negotiate = (TextView) findViewById(R.id.negotiate);
        agree = (CheckBox) findViewById(R.id.agree);

        phone_number.addTextChangedListener(new EditChangedListener());
        password_number.addTextChangedListener(new EditChangedListener());
        negotiate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转协议内容
            }
        });
        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (agree.isChecked()) {
                    if (phone_number.getText().length() > 0) {
                        if (password_number.getText().length() > 0) {

                            if (phone_number.getText().length() == 11) {
                                login_button.setEnabled(true);
                            } else {
                                login_button.setEnabled(false);
                            }
                        } else {
                            login_button.setEnabled(false);
                        }
                    } else {
                        login_button.setEnabled(false);
                    }
                } else {
                    login_button.setEnabled(false);
                }
            }
        });
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
    final LoginListener listener = new LoginListener() {
        @Override
        public void loginSuccess(LoginResult result) {
            //登录成功， 如果你选择了获取用户信息，可以通过
            Toast.makeText(LoginActivity.this,
                    result.getUserInfo() != null ? result.getUserInfo().getNickname()
                            : "" + "登录成功", Toast.LENGTH_SHORT).show();

            // 处理result
            switch (result.getPlatform()) {
                case LoginPlatform.QQ:
                    QQUser user = (QQUser) result.getUserInfo();
                    QQToken token = (QQToken) result.getToken();
                    break;
                case LoginPlatform.WEIBO:
                    // 处理信息
                    break;
                case LoginPlatform.WX:

                    break;
            }
        }

        @Override
        public void loginFailure(Exception e) {
            Log.i("TAG", "登录失败");
        }

        @Override
        public void loginCancel() {
            Log.i("TAG", "登录取消");
        }
    };

    public void onQQLoginClick(View view) {
        LoginUtil.login(this, LoginPlatform.QQ, listener);
//        mIntent(context, BindingActivity.class);
    }

    public void onWeiXinLoginClick(View view) {

//        mIntent(context, BindingActivity.class);
        // LoginPlatform.WEIBO  微博登录
        // LoginPlatform.WX     微信登录
        // LoginPlatform.QQ     QQ登录

        LoginUtil.login(this, LoginPlatform.WX, listener);

    }

    public void onWeboLoginClick(View view) {
        mIntent(context, BindingActivity.class);
    }

    public void onForgetPasswordClick(View view) {
        mIntent(context, ForgetPasswordActivity.class);
    }

    public void onNewUserClick() {
        mIntent(context, RegisterActivity.class);
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
                        login_button.setEnabled(true);
                    } else {
                        login_button.setEnabled(false);
                    }
                } else {
                    login_button.setEnabled(false);
                }
            } else {
                login_button.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    public void startHttpRequst(String requestType, String url,
                                Map<String, Object> parameter, int resultCode) {

        AsyncHttpPost httpRequest;
        httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
                LoginUserInfo.class, context);


    }


    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {

        switch (resultCode) {
            case MYTaskID.USERLOGIN:
                hxLogin(modelClass);

                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        L.e("" + resultJson);
        DialogUtil.stopDialogLoading(context);
        LoginUserInfo loginUserInfo = (LoginUserInfo) modelClass;
        ToastUtil.showShort(context, loginUserInfo.getData().getCode_message());
    }

    private boolean isopen = false;

    private void hxLogin(Object modelClass) {
        LoginUserInfo loginUserInfo = (LoginUserInfo) modelClass;
        EMClient.getInstance().login(loginUserInfo.getData().getUser_id(), password_number.getText().toString(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                L.e("登录聊天服务器成功！");
                try {
                    EMClient.getInstance().contactManager().addContact("17", "没理由");
                } catch (HyphenateException e) {
                }

                MyThreadPool.getInstance().submit(new Runnable() {
                    @Override
                    public void run() {
                        DBControl.getInstance(context).insertLastUser(phone_number.getText().toString(), password_number.getText().toString());
                        LoginUserInfo loginUserInfo = (LoginUserInfo) modelClass;
                        DBControl.getInstance(context).insertLoginInfo(loginUserInfo.getData());
                        Message message = new Message();
                        message.what = LOGIN;
                        message.obj = modelClass;
                        handler.sendMessage(message);
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
                if (!isopen) {
                    isopen = true;
                    try {
                        EMClient.getInstance().createAccount(loginUserInfo.getData().getUser_id(), password_number.getText().toString());//同步方法
                        hxLogin(modelClass);
                    } catch (HyphenateException e) {
                    }
                } else {
                    Message mge = new Message();
                    mge.what = EORRO;
                    mge.obj = message;
                    handler.sendMessage(mge);
                }
            }
        });
    }

    private final int LOGIN = 100;
    private final int INIT = 101;
    private final int EORRO = 102;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN:
                    DialogUtil.stopDialogLoading(context);
                    LoginUserInfo LoginUserInfo = (LoginUserInfo) msg.obj;
                    MYAppconfig.loginUserInfoData = LoginUserInfo.getData();
                    mIntent(context, IndexFragment.class);
                    finish();
                    break;
                case INIT:
                    Bundle bundle = (Bundle) msg.obj;
                    String mobile = bundle.getString("mobile");
                    String password = bundle.getString("password");

                    if (!TextUtils.isEmpty(mobile)) {
                        phone_number.setText(mobile);
                    }
                    if (!TextUtils.isEmpty(password)) {
                        password_number.setText(password);
                        login_button.setEnabled(true);
                    } else {
                        login_button.setEnabled(false);
                    }
                    break;
                case EORRO:
                    DialogUtil.stopDialogLoading(context);
                    ToastUtil.showShort(context, msg.obj.toString());
                    break;
            }
        }
    };

    public boolean onKeyDown(int keyCode,KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_HOME:return true;
            case KeyEvent.KEYCODE_BACK:return true;
            case KeyEvent.KEYCODE_CALL:return true;
            case KeyEvent.KEYCODE_SYM: return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN: return true;
            case KeyEvent.KEYCODE_VOLUME_UP: return true;
            case KeyEvent.KEYCODE_STAR: return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

