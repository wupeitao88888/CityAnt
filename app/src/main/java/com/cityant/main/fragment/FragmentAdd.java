package com.cityant.main.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.iloomo.base.FragmentSupport;


public class FragmentAdd extends FragmentSupport {


//    设置titleBar
    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    //设置View
    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_message, null);
        //设置标题
        setTitle("添加");

        return view;
    }


}
