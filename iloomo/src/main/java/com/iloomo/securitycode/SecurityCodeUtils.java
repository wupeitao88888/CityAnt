package com.iloomo.securitycode;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.iloomo.bean.SMSBaseModel;
import com.iloomo.bean.SMSRegister;
import com.iloomo.global.SMSAppConfig;
import com.iloomo.global.SMSTaskID;
import com.iloomo.net.AsyncHttpGet;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.BaseRequest;
import com.iloomo.net.DefaultThreadPool;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.L;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityCodeUtils implements ThreadCallBack {
    private static SecurityCodeUtils sh;
    public static final int PHONE_FILE = 0;
    public static final int PHONE_NULL = 2;
    public static final int NET_FILE = 3;

    public Timer timer;
    private Context context;
    private String phone;

    public SecurityCodeUtils(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public static synchronized SecurityCodeUtils instance(Context context) {
        if (sh == null) {
            sh = new SecurityCodeUtils(context);
        }
        return sh;
    }

    // 发送校验验证码验证

    /****
     * String phonenumber,String vcode vcode是验证码校验验证码请求参数
     *
     * @param phonenumber
     * @param vcode
     */
    public void sendCode(String phonenumber, String vcode, String vtype) {
        if (TextUtils.isEmpty(vtype)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("类型不能为空", PHONE_NULL);
            return;
        }
        if (TextUtils.isEmpty(vcode)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("验证码不能为空", PHONE_NULL);
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("手机号不能为空", PHONE_NULL);
            return;
        }
        if (!isMobileNO(phonenumber)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("手机号格式不正确", PHONE_FILE);
            return;
        }
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put(SMSAppConfig.mobilekey, phonenumber);
        parameter.put(SMSAppConfig.codeskey, vcode);
        parameter.put(SMSAppConfig.typekey, vtype);
        startHttpRequst("POST", SMSAppConfig.SEND_CODE, parameter,
                SMSTaskID.SEND_CODE);
    }

    // 发送校验验证码验证

    /****
     * String phonenumber,String vcode vcode是验证码校验验证码请求参数
     *
     * @param phonenumber
     * @param vcode
     */
    public void sendCodeRegister(String phonenumber, String vcode, String vtype, String password, String repassword) {
        if (TextUtils.isEmpty(vtype)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("类型不能为空", PHONE_NULL);
            return;
        }
        if (TextUtils.isEmpty(vcode)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("验证码不能为空", PHONE_NULL);
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("手机号不能为空", PHONE_NULL);
            return;
        }
        if (!isMobileNO(phonenumber)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("手机号格式不正确", PHONE_FILE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("密码不能为空", PHONE_FILE);
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("重复密码不能为空", PHONE_FILE);
            return;
        }
        if (!password.equals(repassword)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("密码不一致", PHONE_FILE);
            return;
        }
        if (password.length() < 6) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("密码长度太短", PHONE_FILE);
            return;
        }
        if (password.length() > 16) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("密码长度太长", PHONE_FILE);
            return;
        }
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put(SMSAppConfig.mobilekey, phonenumber);
        parameter.put(SMSAppConfig.codeskey, vcode);
        parameter.put(SMSAppConfig.typekey, vtype);
        parameter.put(SMSAppConfig.passwordkey, password);
        startHttpRequstRegister("POST", SMSAppConfig.SEND_CODE, parameter,
                SMSTaskID.SEND_CODE_REGISTER);
    }
    // 获取验证码

    /****
     * String phonenumber,String vtype//应用到什么地方的类型比如是注册还是找回密码等等
     *
     * @param phonenumber
     * @param vtype
     */
    public void getCode(String phonenumber, String vtype) {
        if (TextUtils.isEmpty(vtype)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("类型不能为空", PHONE_NULL);
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("手机号不能为空", PHONE_NULL);
            return;
        }
        if (!isMobileNO(phonenumber)) {
            if (securityCodeCallBack != null)
                securityCodeCallBack.onNetErrorCallBack("手机号格式不正确", PHONE_FILE);
            return;
        }
        phone = phonenumber;
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put(SMSAppConfig.mobilekey, phonenumber);
        parameter.put(SMSAppConfig.typekey, vtype);
        startHttpRequst("POST", SMSAppConfig.GET_CODE, parameter,
                SMSTaskID.GET_CODE);
    }

    public void startHttpRequst(String requestType, String url,
                                Map<String, Object> parameter, int resultCode) {

        BaseRequest httpRequest = null;
        if ("POST".equalsIgnoreCase(requestType)) {
            httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
                    SMSBaseModel.class);
        } else {
            httpRequest = new AsyncHttpGet(this, url, parameter, resultCode,
                    SMSBaseModel.class);
        }
        DefaultThreadPool.getInstance().execute(httpRequest);
        if (securityCodeCallBack != null)
            securityCodeCallBack.onStartNet();
    }

    public void startHttpRequstRegister(String requestType, String url,
                                        Map<String, Object> parameter, int resultCode) {

        BaseRequest httpRequest = null;
        if ("POST".equalsIgnoreCase(requestType)) {
            httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
                    SMSRegister.class);
        } else {
            httpRequest = new AsyncHttpGet(this, url, parameter, resultCode,
                    SMSRegister.class);
        }
        DefaultThreadPool.getInstance().execute(httpRequest);
        if (securityCodeCallBack != null)
            securityCodeCallBack.onStartNet();
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode,
                                     Object modelClass) {
        // TODO Auto-generated method stub
        switch (resultCode) {
            case SMSTaskID.GET_CODE:
                SMSBaseModel smsBaseModel = (SMSBaseModel) modelClass;
                if ("200".equals(smsBaseModel.getCode())) {
                    if (securityCodeCallBack != null)
                        securityCodeCallBack.onSendSecurityCodeCallBack();
                    mTime();
                } else {
                    if (securityCodeCallBack != null)
                        securityCodeCallBack
                                .onNetErrorCallBack("发送验证码失败！", NET_FILE);
                }
                break;
            case SMSTaskID.SEND_CODE:

                SMSBaseModel smsModel = (SMSBaseModel) modelClass;
                if ("200".equals(smsModel.getCode())) {
                    if (securityCodeCallBack != null)
                        securityCodeCallBack.onSecurityCodeCallBack(true);
                } else {
                    if (securityCodeCallBack != null)
                        if (securityCodeCallBack != null)
                            securityCodeCallBack.onSecurityCodeCallBack(false);
                }
                break;
            case SMSTaskID.SEND_CODE_REGISTER:

                SMSRegister smsRegister = (SMSRegister) modelClass;
                if ("200".equals(smsRegister.getCode())) {
                    if (securityCodeCallBack != null)
                        securityCodeCallBack.onSecurityCodeCallBack(true, smsRegister.getData());
                } else {
                    if (securityCodeCallBack != null)
                        if (securityCodeCallBack != null)
                            securityCodeCallBack.onSecurityCodeCallBack(false, smsRegister.getData());
                }
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode,
                                          Object modelClass) {
        SMSBaseModel baseModel = (SMSBaseModel) modelClass;
//        switch (resultCode) {
//            case SMSTaskID.GET_CODE:
//                if (securityCodeCallBack != null)
//                    securityCodeCallBack.onNetErrorCallBack(baseModel.getData().getCode_message(),
//                            PHONE_NULL);
////                break;
//            case SMSTaskID.SEND_CODE:
                if (securityCodeCallBack != null)
                    securityCodeCallBack.onNetErrorCallBack(baseModel.getData().getCode_message(),
                            PHONE_NULL);

//                break;
//        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    int s = (Integer) msg.obj;
                    if (s > 0) {
                        // lc_phone_getchecknumber.setText(s + "秒");
                        if (securityCodeCallBack != null)
                            securityCodeCallBack.onTitmerCallBack(s + "秒", phone);
                    } else {
                        // lc_phone_getchecknumber.setText("获取验证码");
                        if (securityCodeCallBack != null)
                            securityCodeCallBack.onTitmerOverCallBack();
                        ;
                        timer.cancel();
                        timer = null;
                    }
                    break;
            }
        }

    };

    // 开启计时器
    public void mTime() {
        TimerTask task = new TimerTask() {
            int i = 30;

            public void run() {
                i--;
                Message message = new Message();
                message.what = 1;
                message.obj = i;
                handler.sendMessage(message);
            }
        };
        timer = new Timer(true);
        timer.schedule(task, 1000, 1000); // 延时1000ms后执行，1000ms执行一次
    }

    private SecurityCodeCallBack securityCodeCallBack;

    public void setCodeCallBack(SecurityCodeCallBack securityCodeCallBack) {
        this.securityCodeCallBack = securityCodeCallBack;
    }

    public boolean isMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^((\\+8613[0-9])|(\\+8617[0-9])|(\\+8615[0-9])|(\\+8618[0-9])|(13[0-9])|(17[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
