package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/13.
 */
public class SettingActivity extends ActivitySupport {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setCtenterTitle(R.string.setting);
    }

    /***
     * 消息通知
     * @param view
     */
    public void onMessageClick(View view){

    }

    /***
     * 黑名单
     * @param view
     */
    public void onBlacklistClick(View view){

    }


}
