package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * 设置页面
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
     *
     * @param view
     */
    public void onMessageClick(View view) {
        mIntent(context, MessageNotifyActivity.class);
    }

    /***
     * 黑名单
     *
     * @param view
     */
    public void onBlacklistClick(View view) {

    }

    /***
     * 清除缓存
     *
     * @param view
     */
    public void onClearcCecheClick(View view) {

    }

    /***
     * 检查更新
     *
     * @param view
     */
    public void onCheckUpdateClick(View view) {

    }

    /***
     * 给蚂蚁点个赞
     *
     * @param view
     */
    public void onZanClick(View view) {

    }

    /***
     * 账号安全
     *
     * @param view
     */
    public void onAccountSecurityClick(View view) {

    }

    /****
     * 关于我们
     *
     * @param view
     */
    public void onAboutMeClick(View view) {

    }
}
