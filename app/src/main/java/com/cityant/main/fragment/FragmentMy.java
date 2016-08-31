package com.cityant.main.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.activity.CertifyCenterActivity;
import com.cityant.main.activity.CreateActivity;
import com.cityant.main.activity.FinishedActivity;
import com.cityant.main.activity.GuessActivity;
import com.cityant.main.activity.IdeaActivity;
import com.cityant.main.activity.JudgeActivity;
import com.cityant.main.activity.MYBeanActivity;
import com.cityant.main.activity.RankActivity;
import com.cityant.main.activity.SettingActivity;
import com.cityant.main.activity.ShoppingActivity;
import com.cityant.main.activity.SingupActivity;
import com.cityant.main.activity.SportsActivity;
import com.cityant.main.activity.StorehouseActivity;
import com.cityant.main.activity.TodayReseiveActivity;
import com.cityant.main.activity.UserinfoActivity;
import com.cityant.main.activity.WalletActivity;
import com.iloomo.base.FragmentSupport;
public class FragmentMy extends FragmentSupport implements View.OnClickListener {

    private ImageView userhead;//头像
    private TextView income;//收益
    private RelativeLayout reseive_re;
    private RelativeLayout wallet_re;
    private RelativeLayout Warehouse;
    private RelativeLayout bean;
    private RelativeLayout created;
    private RelativeLayout signup;
    private RelativeLayout finished;
    private RelativeLayout judge;
    private RelativeLayout me_guess_re;
    private RelativeLayout shopping;
    private RelativeLayout shports;
    private RelativeLayout center;
    private RelativeLayout rank;
    private RelativeLayout idea;
    private RelativeLayout service;
    private RelativeLayout setting;

    @Override
    public View initView() {
        setTitle("我的");
        View inflate = LayoutInflater.from(context).inflate(R.layout.fragment_my, null);
        userhead = (ImageView) findViewById(inflate, R.id.userhead);
        income = (TextView) findViewById(inflate, R.id.income);
        reseive_re = (RelativeLayout) findViewById(inflate, R.id.reseive_re);
        wallet_re = (RelativeLayout) findViewById(inflate, R.id.wallet_re);
        Warehouse = (RelativeLayout) findViewById(inflate, R.id.Warehouse);
        bean = (RelativeLayout) findViewById(inflate, R.id.bean);
        created = (RelativeLayout) findViewById(inflate, R.id.created);
        signup = (RelativeLayout) findViewById(inflate, R.id.signup);
        finished = (RelativeLayout) findViewById(inflate, R.id.finished);
        judge = (RelativeLayout) findViewById(inflate, R.id.judge);
        me_guess_re = (RelativeLayout) findViewById(inflate, R.id.me_guess_re);
        shopping = (RelativeLayout) findViewById(inflate, R.id.shopping);
        shports = (RelativeLayout) findViewById(inflate, R.id.shports);
        center = (RelativeLayout) findViewById(inflate, R.id.center);
        rank = (RelativeLayout) findViewById(inflate, R.id.rank);
        idea = (RelativeLayout) findViewById(inflate, R.id.idea);
        service = (RelativeLayout) findViewById(inflate, R.id.service);
        setting= (RelativeLayout) findViewById(inflate, R.id.setting);

        userhead.setOnClickListener(this);
        reseive_re.setOnClickListener(this);
        wallet_re.setOnClickListener(this);
        Warehouse.setOnClickListener(this);
        bean.setOnClickListener(this);
        created.setOnClickListener(this);
        signup.setOnClickListener(this);
        finished.setOnClickListener(this);
        judge.setOnClickListener(this);
        me_guess_re.setOnClickListener(this);
        shopping.setOnClickListener(this);
        shports.setOnClickListener(this);
        center.setOnClickListener(this);
        rank.setOnClickListener(this);
        idea.setOnClickListener(this);
        service.setOnClickListener(this);
        setting.setOnClickListener(this);
        return inflate;
    }

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        titleBar.setFristMenuimgIsVisbility(View.VISIBLE);
        titleBar.setSecondMenuimgIsVisbility(View.VISIBLE);
        titleBar.setRightFristMenuimgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        titleBar.setRightSecondMenuimgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return super.setTitleBar(view);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userhead://头像
                context.startActivity(new Intent(context, UserinfoActivity.class));
                break;
            case R.id.reseive_re://收益详情
                context.startActivity(new Intent(context, TodayReseiveActivity.class));
                break;
            case R.id.wallet_re://钱包
                context.startActivity(new Intent(context, WalletActivity.class));
                break;
            case R.id.Warehouse://蚂蚁仓库
                context.startActivity(new Intent(context, StorehouseActivity.class));
                break;
            case R.id.bean://蚂蚁豆
                context.startActivity(new Intent(context, MYBeanActivity.class));
                break;
            case R.id.created://已创建
                context.startActivity(new Intent(context, CreateActivity.class));
                break;
            case R.id.signup://报名等待
                context.startActivity(new Intent(context, SingupActivity.class));
                break;
            case R.id.finished://确认完结
                context.startActivity(new Intent(context, FinishedActivity.class));
                break;
            case R.id.judge://待评价
                context.startActivity(new Intent(context, JudgeActivity.class));
                break;
            case R.id.me_guess_re://蚂蚁猜
                context.startActivity(new Intent(context, GuessActivity.class));
                break;
            case R.id.shopping://有请购
                context.startActivity(new Intent(context, ShoppingActivity.class));
                break;
            case R.id.shports://活动&竞技
                context.startActivity(new Intent(context, SportsActivity.class));
                break;
            case R.id.center://认证中心
                context.startActivity(new Intent(context, CertifyCenterActivity.class));
                break;
            case R.id.rank://累计评级
                context.startActivity(new Intent(context, RankActivity.class));
                break;
            case R.id.idea://意见反馈
                context.startActivity(new Intent(context, IdeaActivity.class));
                break;
            case R.id.service://联系客服
                break;
            case R.id.setting://设置
                context.startActivity(new Intent(context, SettingActivity.class));
                break;
//
        }
    }


}
