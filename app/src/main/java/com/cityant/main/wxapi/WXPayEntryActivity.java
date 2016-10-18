package com.cityant.main.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cityant.main.R;
import com.cityant.main.wxapi.wxpay.WXPayConfig;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
* 微信支付回调页面
* @author Lvfl
* created at 2016/9/7 11:03
*/
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler, View.OnClickListener {

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
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("微信支付结果：" + String.valueOf(resp.errCode));
            builder.show();
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
