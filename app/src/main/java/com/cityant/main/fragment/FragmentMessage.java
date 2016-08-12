package com.cityant.main.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.iloomo.base.FragmentSupport;


import java.util.ArrayList;
import java.util.List;


public class FragmentMessage extends FragmentSupport {

    private ListView mlist;
    private FragmentHomeAdapter fragmentHomeAdapter = null;

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
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_message, null);
        setTitle("消息");
        return view;
    }


}
