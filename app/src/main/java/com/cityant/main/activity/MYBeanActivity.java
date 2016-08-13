package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/13.
 */
public class MYBeanActivity extends ActivitySupport {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybean);
        setCtenterTitle(R.string.bean);
    }

    /***
     * 充值
     *
     * @param view
     */
    public void onTopUpClick(View view) {
        mIntent(this, MYBeanTopUpActivity.class);
    }

    /***
     * 卖出
     *
     * @param view
     */
    public void onSellBeanClick(View view) {
        mIntent(this, MYBeanSellActivity.class);
    }
}
