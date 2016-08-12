package com.cityant.main.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.iloomo.base.FragmentSupport;


public class FragmentMy extends FragmentSupport {

    private ImageView userhead;//头像
    private TextView income;//收益

    @Override
    public View initView() {
        setTitle("我的");
        View inflate = LayoutInflater.from(context).inflate(R.layout.fragment_my, null);
        userhead = (ImageView) findViewById(inflate, R.id.userhead);

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

    /***
     * 点击头像
     *
     * @param view
     */
    public void onUserheadClick(View view) {

    }


}
