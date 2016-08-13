package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/13.
 */
public class UserinfoActivity extends ActivitySupport {

    private TextView nickname;
    private ImageView userhead_img;
    private ImageView sex;
    private TextView adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        setCtenterTitle(R.string.userinfo);
        initView();
    }

    private void initView() {
        nickname = (TextView) findViewById(R.id.nickname);
        userhead_img = (ImageView) findViewById(R.id.userhead_img);
        sex = (ImageView) findViewById(R.id.sex);
        adress = (TextView) findViewById(R.id.adress);
    }

    /***
     * 点击头像查看
     *
     * @param view
     */
    public void onUserheadClick(View view) {

    }

    /***
     * 点击更换头像
     *
     * @param view
     */
    public void onUserheadChangeClick(View view) {

    }

    /***
     * 点击修改昵称
     *
     * @param view
     */
    public void onNickNameChangeClick(View view) {

    }

    /***
     * 查看我的二维码
     *
     * @param view
     */
    public void onMeTwoCodeClick(View view) {
    }

    /****
     * 收货地址查看与更改
     *
     * @param view
     */
    public void onReseiveAdressClick(View view) {

    }

    /***
     * 修改出生年月
     */
    public void onBothdayClick(View view) {
    }

    /***
     * 修改地址
     *
     * @param view
     */
    public void onAdressClick(View view) {

    }

}
