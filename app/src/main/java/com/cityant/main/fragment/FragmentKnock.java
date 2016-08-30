package com.cityant.main.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.cityant.main.fragment.konck.TenFragment;
import com.iloomo.base.FragmentSupport;

import java.util.ArrayList;


public class FragmentKnock extends FragmentSupport {

    private ListView mlist;
    private FragmentHomeAdapter fragmentHomeAdapter = null;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_knock, null);
        setTitle("抢");
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        fragmentList = new ArrayList<Fragment>();
        TenFragment tenFragment = new TenFragment();
        fragmentList.add(tenFragment);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
        viewPager.setCurrentItem(0);//设置当前显示标签页为第一页
//        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        return view;
    }




    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;

        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }


    }
}