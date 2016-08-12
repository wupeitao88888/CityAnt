package com.cityant.main.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.cityant.main.R;
import com.iloomo.base.FragmentSupport;


public class FragmentMy extends FragmentSupport  {


    @Override
    public View initView() {
        setTitle("我的");
        View inflate = LayoutInflater.from(context).inflate(R.layout.fragment_my, null);

        return inflate;
    }

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }


}
