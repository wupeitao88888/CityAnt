package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/13.
 */
public class WalletActivity extends ActivitySupport {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        setCtenterTitle(R.string.wallet);
    }

    /***
     * 绑定银行卡
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


}
