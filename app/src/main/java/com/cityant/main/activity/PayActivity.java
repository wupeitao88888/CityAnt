package com.cityant.main.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.cityant.main.R;
import com.cityant.main.bean.CreateSamllGrabPay;
import com.cityant.main.bean.CreatedModel;
import com.cityant.main.bean.CreatedSmallGrabModel;
import com.cityant.main.global.MYTaskID;
import com.cityant.main.wxapi.WXPayEntryActivity;
import com.cityant.main.wxapi.wxpay.UserOrder;
import com.cityant.main.wxapi.wxpay.WXPay;
import com.cityant.main.wxapi.wxpay.WXPayConfig;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.alipay.AliPay;
import com.iloomo.alipay.AlipayBean;
import com.iloomo.alipay.util.OrderInfoUtil2_0;
import com.iloomo.base.ActivitySupport;
import com.iloomo.bean.BaseModel;
import com.iloomo.model.OnAliPayCallBack;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ErrorUtil;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.ToastUtil;
import com.iloomo.utils.UnicodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 支付页面
 *
 * @author Lvfl
 *         created at 2016/11/1 17:49
 */
public class PayActivity extends ActivitySupport {

    private RelativeLayout prreace;
    private TextView price;
    private CreateSamllGrabPay createSamllGrabPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_layout);
        setCtenterTitle(R.string.pay_string);
        CreateSmallGrabTypeChooseActivity.aList.add(this);
        prreace = (RelativeLayout) findViewById(R.id.prreace);
        price = (TextView) findViewById(R.id.price);

        Intent intent = getIntent();
        createSamllGrabPay = (CreateSamllGrabPay) intent.getSerializableExtra("CreateSamllGrabPay");
        if (createSamllGrabPay != null) {
            prreace.setVisibility(View.GONE);
        }
    }

    public void onWXPay(View v) {
        onCreateSamll("1");
    }

    public void onALiPay(View v) {
        onCreateSamll("0");
    }

    public void onYuEPay(View v) {
        onCreateSamll("2");
    }


    public void onCreateSamll(String ptype) {
        if (createSamllGrabPay != null) {
            createSmallGrab(ptype);
            return;
        } else {

        }
    }

    private void createSmallGrab(String ptype) {
        DialogUtil.startDialogLoading(context);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("is_friend", createSamllGrabPay.getIs_friend());
        parameter.put("end_time", createSamllGrabPay.getEnd_time());
        parameter.put("goods_id", createSamllGrabPay.getGoods_id());
        parameter.put("pay_type", ptype);//支付方式(0:支付宝,1:微信,2:余额)
        parameter.put("type", "0");//发起类型(0:普通,1:聊天
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

                } else if ("2".equals(ptype)) {

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
        }, MYAppconfig.ROB_CREATE, parameter, MYTaskID.ROB_CREATE,
                CreatedSmallGrabModel.class, context);
    }


}
