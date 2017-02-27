package com.cityant.main.wxapi;
//

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
//
import com.cityant.main.R;
import com.cityant.main.activity.CreateSmallGrabActivity;
import com.cityant.main.activity.CreateSmallGrabTypeChooseActivity;
import com.cityant.main.wxapi.wxpay.WXPayConfig;
import com.iloomo.utils.ToastUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

//
///**
//* 微信支付回调页面
//* @author Lvfl
//* created at 2016/9/7 11:03
//*/
public class WXPayEntryActivity extends Activity
        implements IWXAPIEventHandler, View.OnClickListener {

    // 标题栏返回箭头
    private ImageView iv_wx_pay_callback_back;

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_pay_finish);

        iv_wx_pay_callback_back = (ImageView) findViewById(R.id.iv_wx_pay_callback_back);
        iv_wx_pay_callback_back.setOnClickListener(this);

        api = WXAPIFactory.createWXAPI(this, WXPayConfig.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("TAG", "onPayFinish, errCode = " + resp.errCode);
        /**
         * 0表示支付成功，-1表示支付终端，-2表示支付失败
         */
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//            builder.setTitle("提示");
//            builder.setMessage("微信支付结果：" + String.valueOf(resp.errCode));
//            builder.show();
            if ("-1".equals(String.valueOf(resp.errCode))) {
                ToastUtil.showShort(WXPayEntryActivity.this, "支付终端");
            } else if ("0".equals(String.valueOf(resp.errCode))) {
                for (int i = 0; i < CreateSmallGrabTypeChooseActivity.aList.size(); i++) {
                    CreateSmallGrabTypeChooseActivity.aList.get(i).finish();
                }
                this.startActivity(new Intent(this, CreateSmallGrabActivity.class));
                ToastUtil.showShort(WXPayEntryActivity.this, "支付成功");

            } else if ("-2".equals(String.valueOf(resp.errCode))) {
                ToastUtil.showShort(WXPayEntryActivity.this, "支付失败");
            }
            finish();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_wx_pay_callback_back:
                finish();
                break;
        }
    }
}
