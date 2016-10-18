package com.iloomo.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.iloomo.net.ThreadCallBack;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by xht on 2016/5/6.
 */
public class AliPay implements ThreadCallBack {

    public static final int SUCCESS = 1;
    public static final int FAILE = 2;
    public static final int PAYING = 3;
    private final Map<String, String> parameter;
    private final String http_url;

    // 订单信息
    private String orderInfo;

    // 上下文
    private Context context;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AlipayBean alipayBean = (AlipayBean) msg.obj;
                    alipay(alipayBean);
                    break;
                case SUCCESS:
                    Toast.makeText(context, "支付宝支付成功", Toast.LENGTH_SHORT).show();
                    break;
                case PAYING:
                    Toast.makeText(context, "支付宝支付结果确认中", Toast.LENGTH_SHORT).show();
                    break;
                case FAILE:
                    Toast.makeText(context, "支付宝支付失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    /**
     * AliPay构造函数
     *
     */
    public AliPay(Context context, Map<String, String> parameter,String http_url) {
        this.context = context;
        this.parameter = parameter;
        this.http_url = http_url;
    }

    /**
     * 对外提供的支付方法
     */
    public void pay() {
        // TODO 网络请求
//        new AsyncHttpPost(AliPay.this, http_url, parameter, Http.RUTHPAY_PRODUCT_ORDER_ID,
//                AlipayBean.class, context);
    }

    /**
     * 具体的支付宝支付
     */
    private void alipay(final AlipayBean alipayBean) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                Message msg = new Message();

                // 创建订单信息
                orderInfo = getOrderInfo(alipayBean);
                // 签名
                String sign = sign(orderInfo,alipayBean.getPRIVATE());

                try {
                    // 仅需对sign 做URL编码
                    sign = URLEncoder.encode(sign, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                orderInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

                PayTask alipay = new PayTask((Activity) context);
                final String result = alipay.pay(orderInfo,true);

                PayResult payResult = new PayResult(result);
                /**
                 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                 * docType=1) 建议商户依赖异步通知
                 * 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                 */
                String resultInfo = payResult.getResult();

                String resultStatus = payResult.getResultStatus();

                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {

                    msg.what = SUCCESS;
                    handler.sendMessage(msg);
                } else {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        msg.what = PAYING;
                        handler.sendMessage(msg);
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        msg.what = FAILE;
                        handler.sendMessage(msg);
                    }
                }
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    // ===================以下是支付宝支付所需的工具方法=========================

    /**
     * 创建订单信息
     */
    private String getOrderInfo(AlipayBean alipayBean) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + alipayBean.getPartner() + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + alipayBean.getSeller_id() + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + alipayBean.getOut_trade_no() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + alipayBean.getBody() + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + alipayBean.getBody() + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + alipayBean.getTotal_fee() + "\"";

        // 服务器异步通知页面路径
        // 注意：下面的url为回调接口，请求支付宝后，支付宝会向服务器请求以下的url 所以项目不同，以下的url应不同
        orderInfo += "&notify_url=" + "\"" + alipayBean.getAli_notify_url() + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content,String PRIVATE) {
        return SignUtils.sign(content, PRIVATE);
    }

    /**
     * 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }


    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        AlipayBean alipayBean = (AlipayBean) modelClass;
        if("1".equals(alipayBean.getResultCode())){
            Message msg = new Message();
            msg.what = 0;
            msg.obj = alipayBean;
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
