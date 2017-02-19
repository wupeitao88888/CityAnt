package com.iloomo.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.iloomo.alipay.util.OrderInfoUtil2_0;
import com.iloomo.model.OnAliPayCallBack;
import com.iloomo.net.ThreadCallBack;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by xht on 2016/5/6.
 */
public class AliPay {
    private Context context;

    public static AliPay aliPay;

    public static AliPay getAliPay(Context context) {
        if (aliPay == null) {
            aliPay = new AliPay(context);
        }
        return aliPay;
    }

    public AliPay(Context context) {
        this.context = context;
    }

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017021905765536";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088521239486185";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL3ILDrr0d6U4fv9" +
            "16R/s8SxEaIEtt9FDvBUf35z4cV3ZsimX27gFXe9tc8wKEDKf5dJCwvvNOA7w590" +
            "b3yMRRoeeMyYzAofvPvN4rHEhy1VjQMfS8otIfrrqGk2/qj5KaN3mHVBGLrxoYPx" +
            "I2Pclp30jPTjz5Jfe60DrkAigsP1AgMBAAECgYEAm/BQt5Fxfuziy0YG3Lm5otLD" +
            "5FbvVIHQBpXHtMXPK4wyvohGPjqOKTeOnlG5oZNCR7LTCtlgnyc8VFc+DB7N3370" +
            "5zoxVamFvmaUvaiBB2tEoXywh2Ue5dwKwxeb2Nmhz9jIt59KbmR66JXzIhTjlYIp" +
            "7hpdDyUrgnok7bdNJzkCQQDuctrpvBFESc1gA7CImGiwqAEhx0GOpXR4odqQQ2R8" +
            "moDDIi7jiboccEedZdaz1x4QXo3P+Q6eZuhWT7JSImBzAkEAy8BHZGw6VYFsr3EN" +
            "7kTaW9XvJh3IDrJ6UoFzaz7kgcG9MiJ4ajt8yGhULac1LEvL0CQrvzFzUtCi6pWA" +
            "MCU39wJBAMJkODuc6qa1VA0WdPCm75I1JNcYzkUwdcdyi/BYQK+kdZyfXYJ6YZOV" +
            "pYyX3XU3xnap3wrRjC1uz7Ktvj5fcCkCQH/pUyh5RVneqZOuftdhhE/5C2y7b5fC" +
            "dkaLnCDs19mZ/iOWPlA6zkimLoqGmTwYzXf67TfXXJXWAyEP/+y5wNsCQCgqVRN/" +
            "5/39MjE9wSPaPbJCx+URBo1BVH/Sd4Fy0vHtXSxEvu1wQIL4eWzNmOTe0YBdqCTb" +
            "iB9uX4tykxew7pM=";;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        if(onAliPayCallBack!=null){
                            onAliPayCallBack.onSUCCESS();
                        }
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        if(onAliPayCallBack!=null){
                            onAliPayCallBack.onFAILE();
                        }
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void payV2(AlipayBean alipayBean) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
//                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,alipayBean.getTotal_fee(),alipayBean.getSubject(),alipayBean.getBody(),alipayBean.getOut_trade_no());
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) context);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
//     * 支付宝账户授权业务
//     *
//     * @param v
//     */
//    public void authV2(View v) {
//        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
//                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
//                || TextUtils.isEmpty(TARGET_ID)) {
//            new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                        }
//                    }).show();
//            return;
//        }
//
//        /**
//         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//         *
//         * authInfo的获取必须来自服务端；
//         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
//        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
//        final String authInfo = info + "&" + sign;
//        Runnable authRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造AuthTask 对象
//                AuthTask authTask = new AuthTask((Activity) context);
//                // 调用授权接口，获取授权结果
//                Map<String, String> result = authTask.authV2(authInfo, true);
//
//                Message msg = new Message();
//                msg.what = SDK_AUTH_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread authThread = new Thread(authRunnable);
//        authThread.start();
//    }
//
//    /**
//     * get the sdk version. 获取SDK版本号
//     */
//    public void getSDKVersion() {
//        PayTask payTask = new PayTask((Activity) context);
//        String version = payTask.getVersion();
//        Toast.makeText(context, version, Toast.LENGTH_SHORT).show();
//    }

    public OnAliPayCallBack onAliPayCallBack;

    public void setOnAliPayCallBack(OnAliPayCallBack onAliPayCallBack) {
        this.onAliPayCallBack = onAliPayCallBack;
    }


}
