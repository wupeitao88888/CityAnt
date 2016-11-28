package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.cityant.main.R;
import com.cityant.main.adapter.MyFragmentPagerAdapter;
import com.cityant.main.fragment.FragmentKnock;
import com.cityant.main.fragment.konck.RelatedMeFragment;
import com.cityant.main.widget.TabsLayout;
import com.iloomo.base.ActivitySupport;

import java.util.ArrayList;
import java.util.List;

/**
* 与我相关
* @author Lvfl
* created at 2016/11/28 18:23
*/
public class RelatedMeActivity extends ActivitySupport {

    public static void startActivity(Context context){
        Intent intent = new Intent(context,RelatedMeActivity.class);
        context.startActivity(intent);
    }

    private TabsLayout pagerStrip;
    private ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_me_layout);
        setCtenterTitle("与我相关");
        initView();
    }

    private void initView() {
        pagerStrip = (TabsLayout) findViewById(R.id.pagerStrip);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        initFragmentPager(viewpager, pagerStrip);
    }

    public void initFragmentPager(ViewPager viewPager, TabsLayout pagerSlidingTabStrip) {
        final ArrayList<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("等开奖");
        titleList.add("已中奖");
        titleList.add("未中奖");
        titleList.add("待退款");
        titleList.add("已退款");
        for (int i = 0; i < titleList.size(); i++) {
            fragmentList.add(RelatedMeFragment.newInstance(R.color.none_color));
        }
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        pagerSlidingTabStrip.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }


}
