package com.cityant.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cityant.main.R;
import com.hyphenate.easeui.bean.LoginUserInfoData;
import com.hyphenate.easeui.db.DBControl;
import com.iloomo.base.ActivitySupport;
import com.iloomo.bean.BaseData;
import com.iloomo.global.AppConfig;
import com.iloomo.global.SMSAppConfig;
import com.iloomo.securitycode.SecurityCodeCallBack;
import com.iloomo.securitycode.SecurityCodeUtils;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.ToastUtil;

/**
 * Created by wupeitao on 16/8/8.
 */
public class ForgetPasswordActivity extends ActivitySupport implements SecurityCodeCallBack {
    private EditText phone_number;
    private EditText code_number;
    private Button sendcode;
    private Button login_button;
    private EditText pwnumber;
    private EditText re_pwnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        setCtenterTitle(mString(R.string.forgetpw));
        SMSAppConfig.GET_CODE = AppConfig.GET_CODE;
        SMSAppConfig.SEND_CODE = AppConfig.FORGETPASSWOD;
        SMSAppConfig.mobilekey = "mobile";
        SMSAppConfig.codeskey = "code";
        SMSAppConfig.passwordkey = "password";
        SMSAppConfig.typekey = "type";
        initView();
    }

    private void initView() {
        phone_number = (EditText) findViewById(R.id.phone_number);
        code_number = (EditText) findViewById(R.id.code_number);
        pwnumber = (EditText) findViewById(R.id.pwnumber);
        re_pwnumber = (EditText) findViewById(R.id.re_pwnumber);

        sendcode = (Button) findViewById(R.id.sendcode);
        login_button = (Button) findViewById(R.id.login_button);
        phone_number.addTextChangedListener(new EditChangedListener());
        code_number.addTextChangedListener(new EditChangedListener());
        pwnumber.addTextChangedListener(new EditChangedListener());
        re_pwnumber.addTextChangedListener(new EditChangedListener());
        sendcode.setEnabled(false);
        login_button.setEnabled(false);

        SecurityCodeUtils.instance(context).setCodeCallBack(this);
    }

    public void onLoginClick(View view) {
//        mIntent(context, IndexFragment.class);
        SecurityCodeUtils.instance(context).sendCodeRegister(phone_number.getText().toString(), code_number.getText().toString(), 1 + "", pwnumber.getText().toString(), re_pwnumber.getText().toString());
    }

    public void onSendCode(View view) {
        SecurityCodeUtils.instance(context).getCode(phone_number.getText().toString(), 1 + "");
    }

    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (phone_number.getText().length() > 0) {
                if (phone_number.getText().length() == 11) {

                    sendcode.setEnabled(true);
                    if (pwnumber.getText().length() > 0) {
                        if (re_pwnumber.getText().length() > 0) {
                            login_button.setEnabled(true);
                        } else {
                            login_button.setEnabled(false);
                        }
                    } else {
                        login_button.setEnabled(false);
                    }
                } else {
                    sendcode.setEnabled(false);
                    login_button.setEnabled(false);
                }
            } else

            {
                sendcode.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @Override
    public void onTitmerCallBack(String str, String phonenumber) {
        sendcode.setText(str);
        sendcode.setEnabled(false);
        phone_number.setText(phonenumber);
    }

    @Override
    public void onTitmerOverCallBack() {
        sendcode.setText("再次发送");
        sendcode.setEnabled(true);
    }

    @Override
    public void onNetErrorCallBack(String str, int po) {
        DialogUtil.stopDialogLoading(context);
        switch (po) {
            case SecurityCodeUtils.PHONE_FILE:
                ToastUtil.showShort(context, str);
                break;
            case SecurityCodeUtils.PHONE_NULL:
                ToastUtil.showShort(context, str);
                break;
            case SecurityCodeUtils.NET_FILE:
                ToastUtil.showShort(context, str);
                break;
        }
    }

    @Override
    public void onSecurityCodeCallBack(boolean blean) {
        DialogUtil.stopDialogLoading(context);
        if (blean) {
            // 验证成功
//            ToastUtil.showShort(context, "验证成功");
//            mIntent(context, IndexFragment.class);
        } else {
            // 验证失败
//            ToastUtil.showShort(context, "验证失败");
        }
    }

    @Override
    public void onSecurityCodeCallBack(boolean blean, Object userRegister) {

        if (blean) {
            // 验证成功
//            ToastUtil.showShort(context, "验证成功:" + ((SMSRegister.UserRegister) userRegister).getToken());
            MyThreadPool.getInstance().submit(new Runnable() {
                @Override
                public void run() {
                    LoginUserInfoData loginUserInfoData = new LoginUserInfoData();
                    loginUserInfoData.setToken(((BaseData) userRegister).getToken());
                    loginUserInfoData.setMobile(phone_number.getText().toString());
                    DBControl.getInstance(context).insertLoginInfo(loginUserInfoData);
                    DBControl.getInstance(context).insertLastUser(phone_number.getText().toString(), pwnumber.getText().toString());
                    Message message = new Message();
                    message.what = REGISTER;
                    message.obj = "";
                    handler.sendMessage(message);
                }
            });


        } else {
            // 验证失败
//            ToastUtil.showShort(context, "验证失败");
        }
    }

    @Override
    public void onSendSecurityCodeCallBack() {
//获取验证码成功
        DialogUtil.stopDialogLoading(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SecurityCodeUtils.instance(context).cancel();
    }

    @Override
    public void onStartNet() {
// TODO Auto-generated method stub
        DialogUtil.startDialogLoading(context);
    }

    private final int REGISTER = 100;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REGISTER:
                    DialogUtil.stopDialogLoading(context);
                    mIntent(context, IndexFragment.class);
                    finish();
                    break;
            }
        }
    };
}
