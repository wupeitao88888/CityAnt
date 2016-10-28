package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;

/**
 * Created by lvfl on 2016/8/18.
 */
public class CreateSmallGrabActivity extends ActivitySupport implements View.OnClickListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CreateSmallGrabActivity.class);
        context.startActivity(intent);
    }

    private Button confirm;
    private ImageView userhead;
    private TextView username;
    private RelativeLayout choose_create_type;
    private ImageView type_img;
    private TextView goods_info;
    private TextView goods_price;
    private TextView goods_specifications;
    private TextView goods_all_pople;
    private Button permissions_all;
    private Button permissions_frends;
    private Button one_day;
    private Button five_day;
    private Button ten_day;
    private Button unlimited_day;

    private int is_friend = 0;//默认选中所有，限好友是1
    private int end_time = 1;//限时(0:不限;1:1天,5:5天,10:10天)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_small_grab_layout);
        setCtenterTitle(mString(R.string.mecreatek));
        initView();
    }

    private void initView() {
        confirm = (Button) findViewById(R.id.confirm);
        userhead = (ImageView) findViewById(R.id.userhead);
        username = (TextView) findViewById(R.id.username);
        choose_create_type = (RelativeLayout) findViewById(R.id.choose_create_type);
        type_img = (ImageView) findViewById(R.id.type_img);
        goods_info = (TextView) findViewById(R.id.goods_info);
        goods_price = (TextView) findViewById(R.id.goods_price);
        goods_specifications = (TextView) findViewById(R.id.goods_specifications);
        goods_all_pople = (TextView) findViewById(R.id.goods_all_pople);

        permissions_all = (Button) findViewById(R.id.permissions_all);
        permissions_frends = (Button) findViewById(R.id.permissions_frends);

        one_day = (Button) findViewById(R.id.one_day);
        five_day = (Button) findViewById(R.id.five_day);
        ten_day = (Button) findViewById(R.id.ten_day);
        unlimited_day = (Button) findViewById(R.id.unlimited_day);
        confirm.setEnabled(false);
        permissions_all.setOnClickListener(this);
        permissions_frends.setOnClickListener(this);

        one_day.setOnClickListener(this);
        five_day.setOnClickListener(this);
        ten_day.setOnClickListener(this);
        unlimited_day.setOnClickListener(this);

        permissions_all.setEnabled(false);
        permissions_frends.setEnabled(true);

        one_day.setEnabled(false);
        five_day.setEnabled(true);
        ten_day.setEnabled(true);
        unlimited_day.setEnabled(true);

        StrUtil.setText(username, MYAppconfig.loginUserInfoData.getUser_name());
        PImageLoaderUtils.getInstance().displayuserHand(MYAppconfig.loginUserInfoData.getUser_avar(), userhead, context);

        choose_create_type.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.permissions_all:
                permissions_all.setEnabled(false);
                permissions_frends.setEnabled(true);
                is_friend = 0;
                break;
            case R.id.permissions_frends:
                permissions_all.setEnabled(true);
                permissions_frends.setEnabled(false);
                is_friend = 1;
                break;
            case R.id.one_day:
                one_day.setEnabled(false);
                five_day.setEnabled(true);
                ten_day.setEnabled(true);
                unlimited_day.setEnabled(true);
                end_time = 1;
                break;
            case R.id.five_day:
                one_day.setEnabled(true);
                five_day.setEnabled(false);
                ten_day.setEnabled(true);
                unlimited_day.setEnabled(true);
                end_time = 5;
                break;
            case R.id.ten_day:
                one_day.setEnabled(true);
                five_day.setEnabled(true);
                ten_day.setEnabled(false);
                unlimited_day.setEnabled(true);
                end_time = 10;
                break;
            case R.id.unlimited_day:
                one_day.setEnabled(true);
                five_day.setEnabled(true);
                ten_day.setEnabled(true);
                unlimited_day.setEnabled(false);
                end_time = 0;
                break;
            case R.id.choose_create_type:
                mIntent(context, CreateSmallGrabTypeActivity.class);
                break;
        }
    }
}
