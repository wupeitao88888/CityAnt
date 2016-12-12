package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.cityant.main.R;
import com.cityant.main.adapter.MyFragmentPagerAdapter;
import com.cityant.main.base.BaseToolbarActivity;
import com.cityant.main.fragment.konck.GoodsDetailsFragment;
import com.cityant.main.fragment.konck.ParticipantFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
* 小额抢详情
* @author lvfl
* @time 2016/12/4 17:14
*/
public class KonckDetailsActivity extends BaseToolbarActivity {

    @Bind(R.id.pagerStrip)
    protected TabLayout pagerStrip;
    @Bind(R.id.viewpager)
    protected ViewPager viewpager;
    public static void startActivity(Context context){
        Intent intent = new Intent(context,KonckDetailsActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konck_details_layout);
        initFragmentPager(viewpager,pagerStrip);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_konck_details_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationOnClickListener(v -> KonckDetailsActivity.this.finish());
        ActionBarHelper helper = createActionBarHelper();
        helper.init();
        helper.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    public void initFragmentPager(ViewPager viewPager, TabLayout pagerSlidingTabStrip) {
        final ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ParticipantFragment.newInstance(0));
        fragmentList.add(GoodsDetailsFragment.newInstance(0));

        List<String> titleList = new ArrayList<>();
        titleList.add("参与人员");
        titleList.add("商品详情");
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        pagerSlidingTabStrip.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }
}
