package com.cityant.main.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.iloomo.base.FragmentSupport;


public class FragmentKnock extends FragmentSupport {

    private ListView mlist;
    private FragmentHomeAdapter fragmentHomeAdapter = null;

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_knock, null);
        setTitle("抢");

        return view;
    }


}
