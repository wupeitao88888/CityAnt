package com.cityant.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cityant.main.R;
import com.cityant.main.bean.CreatedSmallGrabModel;
import com.cityant.main.global.MYTaskID;
import com.cityant.main.wxapi.wxpay.UserOrder;
import com.cityant.main.wxapi.wxpay.WXPay;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.alipay.AliPay;
import com.iloomo.alipay.AlipayBean;
import com.iloomo.base.ActivitySupport;
import com.iloomo.model.OnAliPayCallBack;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 充值
 * Created by wupeitao on 2017/3/8.
 */

public class RechargeActivity extends ActivitySupport {
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        setRightTitle("充值");
        price = (EditText) findViewById(R.id.price);
    }

    public void onALiPay(View view) {
        paytype("0");
    }

    public void onWXPay(View view) {
        paytype("1");
    }

    private void paytype(String ptype){
        DialogUtil.startDialogLoading(context);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("pay_type", ptype);//支付方式(0:支付宝,1:微信,)
        parameter.put("price", price.getText().toString());//充值钱数
        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                DialogUtil.stopDialogLoading(context);
                CreatedSmallGrabModel createdSmallGrabModel = (CreatedSmallGrabModel) modelClass;
                if ("0".equals(ptype)) {
                    AlipayBean alipayBean = new AlipayBean();
                    alipayBean.setAli_notify_url(createdSmallGrabModel.getData().getNotify_url());
                    alipayBean.setBody(createdSmallGrabModel.getData().getBody());
                    alipayBean.setOut_trade_no(createdSmallGrabModel.getData().getOut_trade_no());
                    alipayBean.setTotal_fee(createdSmallGrabModel.getData().getTotal_fee());
                    alipayBean.setSubject(createdSmallGrabModel.getData().getSubject());

                    AliPay.getAliPay(context).setOnAliPayCallBack(new OnAliPayCallBack() {
                        @Override
                        public void onSUCCESS() {
                            for (int i = 0; i < CreateSmallGrabTypeChooseActivity.aList.size(); i++) {
                                CreateSmallGrabTypeChooseActivity.aList.get(i).finish();
                            }
                            context.startActivity(new Intent(context, CreateSmallGrabActivity.class));
                            ToastUtil.showShort(context, "支付成功");
                        }

                        @Override
                        public void onFAILE() {
                            ToastUtil.showShort(context, "支付失败");
                        }
                    });
                    AliPay.getAliPay(context).payV2(alipayBean);
                } else if ("1".equals(ptype)) {
                    UserOrder userOrder = new UserOrder();
                    userOrder.setAppid(createdSmallGrabModel.getData().getAppid());
                    userOrder.setMch_id(createdSmallGrabModel.getData().getPartnerid());
                    userOrder.setPrepayId(createdSmallGrabModel.getData().getPrepayid());
                    userOrder.setNonce_str(createdSmallGrabModel.getData().getNoncestr());
                    userOrder.setTimeStamp(createdSmallGrabModel.getData().getTimestamp());
                    userOrder.setSign(createdSmallGrabModel.getData().getSign());
                    WXPay.getWxPay(context).genPayReq(userOrder);

                }
            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                DialogUtil.stopDialogLoading(context);
                CreatedSmallGrabModel createdSmallGrabModel = (CreatedSmallGrabModel) modelClass;
                ToastUtil.showShort(context, createdSmallGrabModel.getData().getCode_message());
            }
        }, MYAppconfig.USERPRICE_PAYPRICE, parameter, MYTaskID.USERPRICE_PAYPRICE,
                CreatedSmallGrabModel.class, context);
    }




}
