package com.cityant.main.activity;

import android.os.Bundle;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
* 支付页面
* @author Lvfl
* created at 2016/11/1 17:49
*/
public class PayActivity extends ActivitySupport {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_layout);
        setCtenterTitle(R.string.pay_string);
    }
}
