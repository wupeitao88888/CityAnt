package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.cityant.main.R;
import com.cityant.main.adapter.MyFragmentPagerAdapter;
import com.cityant.main.fragment.konck.RelatedMeFragment;
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

    private TabLayout pagerStrip;
    private ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_me_layout);
        setCtenterTitle("与我相关");
        initView();
    }

    private void initView() {
        pagerStrip = (TabLayout) findViewById(R.id.pagerStrip);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        initFragmentPager(viewpager, pagerStrip);
    }

    public void initFragmentPager(ViewPager viewPager, TabLayout pagerSlidingTabStrip) {
        final ArrayList<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("未中奖");
        titleList.add("已中奖");
        titleList.add("已退款");
        fragmentList.add(RelatedMeFragment.newInstance(0));
        fragmentList.add(RelatedMeFragment.newInstance(1));
        fragmentList.add(RelatedMeFragment.newInstance(2));
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        pagerSlidingTabStrip.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }


}
