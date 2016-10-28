package com.cityant.main.activity;

import android.os.Bundle;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 2016/10/20.
 */

public class UserCodeInfo extends ActivitySupport {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercodeinfo);
        setCtenterTitle("我的二维码");
    }
}
