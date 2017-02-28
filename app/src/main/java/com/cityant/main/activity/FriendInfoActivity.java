package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;
import com.iloomo.widget.CostomRatingBar;

/**
 * Created by wupeitao on 2017/2/28.
 */

public class FriendInfoActivity extends ActivitySupport {

    private ImageView frend_head;
    private TextView frend_username;
    private CostomRatingBar overall_rating;
    private TextView frends_sex;
    private TextView frends_address;
    private TextView frend_age;
    private RelativeLayout frend_ren;
    private RelativeLayout frend_create;
    private ImageView type_img;
    private TextView goods_info;
    private TextView goods_specifications;
    private TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frendinfo);
        initView();
    }

    private void initView() {
        frend_head = (ImageView) findViewById(R.id.frend_head);
        frend_username = (TextView) findViewById(R.id.frend_username);
        overall_rating = (CostomRatingBar) findViewById(R.id.overall_rating);
        frends_sex = (TextView) findViewById(R.id.frends_sex);
        frends_address = (TextView) findViewById(R.id.frends_address);
        frend_age = (TextView) findViewById(R.id.frend_age);
        frend_ren = (RelativeLayout) findViewById(R.id.frend_ren);
        frend_create = (RelativeLayout) findViewById(R.id.frend_create);
        type_img = (ImageView) findViewById(R.id.type_img);
        goods_info = (TextView) findViewById(R.id.goods_info);
        goods_specifications = (TextView) findViewById(R.id.goods_specifications);
        count = (TextView) findViewById(R.id.count);
    }

    /***
     * 发起视频聊天
     *
     * @param view
     */
    public void onSendVoide(View view) {

    }

    /****
     * 发消息
     *
     * @param view
     */
    public void onSendMsg(View view) {

    }

    /**
     * 更多
     *
     * @param view
     */
    public void onMore(View view) {

    }

    /***
     * 蚂蚁猜
     *
     * @param view
     */
    public void onGuess(View view) {

    }

    /***
     * 立即抢
     *
     * @param view
     */
    public void onGrab(View view) {

    }

    /***
     * 个人发起查看更多
     *
     * @param view
     */
    public void onCreateMore(View view) {

    }

    /***
     * 设置昵称
     *
     * @param view
     */
    public void onSetNickName(View view) {

    }


}
