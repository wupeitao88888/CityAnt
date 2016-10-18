package com.cityant.main.wxapi.wxpay;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.iloomo.net.ThreadCallBack;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
* 微信支付类
* @author Lvfl
* created at 2016/9/7 11:12
*/
public class WXPay implements ThreadCallBack {

    private String http_url;
    private Map<String, String> parameter;
    //支付请求类
    private PayReq req;
    //IWXAPI对象
    private IWXAPI msgApi;

    // 上下文
    private Context context;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                UserOrder userOrder = (UserOrder) msg.obj;
                genPayReq(userOrder);
            }
        }
    };


    public WXPay(Context context, Map<String, String> parameter,String http_url) {
        this.context = context;
        this.parameter = parameter;
        this.http_url = http_url;
        //创建PayReq对象
        req = new PayReq();
        //创建IWXAPI对象
        msgApi = WXAPIFactory.createWXAPI(context, null);
        //使用app_id注册app
        msgApi.registerApp(WXPayConfig.APP_ID);

        getUserOrderFromServer();
    }

    /**
     * 向自己服务器请求
     * 获得UserOrder对象
     * 该对象中封装了prepayId等信息
     */
    private void getUserOrderFromServer() {
        // TODO 添加网络请求
//        new AsyncHttpPost(WXPay.this, http_url, parameter, HttpConfig.WECHATPAY_PRODUCT_ORDER_ID,
//                UserOrder.class, context);
    }

    /**
     * 先做签名
     *
     * @param
     */
    private void genPayReq(UserOrder order) {

        req.appId = WXPayConfig.APP_ID;
        req.partnerId = order.getMch_id();
        req.prepayId = order.getPrepayId();
        req.packageValue = "Sign=WXPay";
        req.nonceStr= order.getNonce_str();
        req.timeStamp= order.getTimeStamp();
//        req.sign= order.getSign();

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams,order.getKey());
        Log.e("orion", genAppSign(signParams,order.getKey()));
        msgApi.sendReq(req);
    }
    /**
     * 在app端生成签名
     * @param params
     * @return
     */
    private String genAppSign(List<NameValuePair> params,String api_key) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            str.append(params.get(i).getName());
            str.append('=');
            str.append(params.get(i).getValue());
            str.append('&');
        }
        Log.e("sing_str",str.toString());
        str.append("key=");
        str.append(api_key);
        Log.e("sing",str.toString());

        String appSign = MD5.getMessageDigest(str.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    // 生成当前时间
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        UserOrder userOrder = (UserOrder) modelClass;
        if("1".equals(userOrder.getResultCode())){
            Message msg = new Message();
            msg.what = 1;
            msg.obj = userOrder;
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

    }
}
