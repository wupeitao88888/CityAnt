package com.cityant.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.cityant.main.R;
import com.cityant.main.activity.MyFrendActivity;
import com.cityant.main.utlis.ColorRandomizer;
import com.iloomo.base.FragmentSupport;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FragmentMessage extends FragmentSupport {

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        titleBar.setFristMenuimgIsVisbility(View.VISIBLE);
        titleBar.setSecondMenuimgIsVisbility(View.VISIBLE);
        titleBar.setRightFristMenuimgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MyFrendActivity.class));
            }
        });
        titleBar.setRightSecondMenuimgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShort(context, "右边第一个");

            }
        });
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_message, null);
        setTitle("消息");


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}