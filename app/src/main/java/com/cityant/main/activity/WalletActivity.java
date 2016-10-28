package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.EvaluateListModel;
import com.cityant.main.bean.TodayIncomeModel;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 16/8/13.
 */
public class WalletActivity extends ActivitySupport implements ThreadCallBack {
    private TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        setCtenterTitle(R.string.wallet);
        balance = (TextView) findViewById(R.id.balance);
//        getIncome();
    }

//    public void getIncome() {
//        Map<String, Object> parameter = new HashMap<>();
//        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
//        AsyncHttpPost httpRequest;
//        httpRequest = new AsyncHttpPost(this, MYAppconfig.TODAYINCOME, parameter, MYTaskID.TODAYINCOME,
//                TodayIncomeModel.class, context);
//    }

    /***
     * 绑定支付宝
     *
     * @param view
     */
    public void onBundleClick(View view) {

    }

    /****
     * 余额
     *
     * @param view
     */
    public void onBalanceClick(View view) {

    }

    /***
     * 充值
     */
    public void onTopUpClick(View view) {


    }

    /***
     * 提现
     *
     * @param view
     */
    public void onWithdrawcashClick(View view) {

    }


    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.TODAYINCOME:
                TodayIncomeModel todayIncomeModel = (TodayIncomeModel) modelClass;
                String total_price = todayIncomeModel.getData().getTotal_price();
                StrUtil.setText(balance, total_price);
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.TODAYINCOME:
                TodayIncomeModel todayIncomeModel = (TodayIncomeModel) modelClass;
                ToastUtil.showShort(context, todayIncomeModel.getData().getCode_message());
                break;
        }
    }
}
