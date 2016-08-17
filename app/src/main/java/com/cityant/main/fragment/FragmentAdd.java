package com.cityant.main.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.activity.CreateDemandActivity;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.iloomo.base.FragmentSupport;
import com.iloomo.utils.ToastUtil;


public class FragmentAdd extends FragmentSupport {


    private LinearLayout friendship_ll;
    private LinearLayout create_ant_ll;
    private LinearLayout small_amount_ll;
    private LinearLayout part_time_job_ll;

    //    设置titleBar
    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    //设置View
    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_add_layout, null);
        //设置标题
        setTitle("添加");
        friendship_ll = (LinearLayout) view.findViewById(R.id.friendship_ll);
        create_ant_ll = (LinearLayout) view.findViewById(R.id.create_ant_ll);
        small_amount_ll = (LinearLayout) view.findViewById(R.id.small_amount_ll);
        part_time_job_ll = (LinearLayout) view.findViewById(R.id.part_time_job_ll);
        friendship_ll.setOnClickListener(v -> CreateDemandActivity.startActivity(context));
        return view;
    }


}
