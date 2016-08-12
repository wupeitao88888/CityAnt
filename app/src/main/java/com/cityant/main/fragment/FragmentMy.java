package com.cityant.main.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.cityant.main.R;
import com.iloomo.base.FragmentSupport;


public class FragmentMy extends FragmentSupport implements View.OnClickListener {


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.onDialog:
//                startActivity(new Intent(context, AlertViewActivity.class));
//                break;
//            case R.id.onloading:
//                startActivity(new Intent(context, SVProgressActivity.class));
//                break;
//            case R.id.camera:
//                startActivity(new Intent(context, Photo.class));
//                break;
        }
    }
}
