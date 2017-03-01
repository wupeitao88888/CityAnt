package com.cityant.main.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.activity.CreateSmallGrabActivity;
import com.cityant.main.activity.knock.MoreTribeActivity;
import com.cityant.main.activity.knock.RelatedMeActivity;
import com.cityant.main.activity.knock.TribeDetailsActivity;
import com.cityant.main.adapter.ViewPagerAdapter;
import com.cityant.main.bean.konck.RobDetails;
import com.cityant.main.fragment.konck.DoublePeopleFragment;
import com.cityant.main.fragment.konck.FivePeopleFragment;
import com.cityant.main.fragment.konck.HundredPeopleFragment;
import com.cityant.main.fragment.konck.ManyPeopleFragment;
import com.cityant.main.fragment.konck.TenPeopleFragment;
import com.cityant.main.global.MYTaskID;
import com.cityant.main.utlis.ConfigurationFragmentCallbacks;
import com.cityant.main.widget.TabsLayout;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.FragmentSupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnFlingOverListener;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;


public class FragmentKnock extends FragmentSupport implements ConfigurationFragmentCallbacks,ThreadCallBack {

    private ListView mlist;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;
    private static final String ARG_LAST_SCROLL_Y = "arg.LastScrollY";
    private LinearLayout rob_linear_ll;
    private RelativeLayout launch_rl;
    private RelativeLayout relevant_rl;
    private TextView brand_more;

    private ScrollableLayout mScrollableLayout;

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_knock, null);
        setTitle("小额抢");
        setRightTitleRes(R.drawable.qiang_ding);
        setRightTitleImageListener(v -> {
            if(rob_linear_ll.getVisibility() == View.VISIBLE){
                rob_linear_ll.setVisibility(View.GONE);
            }else{
                rob_linear_ll.setVisibility(View.VISIBLE);
            }
        });
        final View header = view.findViewById(R.id.header);
        final LinearLayout brand_array_ll = (LinearLayout) view.findViewById(R.id.brand_array_ll);
        final TabsLayout tabs = (TabsLayout) view.findViewById(R.id.tabs);

        rob_linear_ll = (LinearLayout) view.findViewById(R.id.rob_linear_ll);

        mScrollableLayout = (ScrollableLayout) view.findViewById(R.id.scrollable_layout);
        mScrollableLayout.setDraggableView(tabs);

        launch_rl = (RelativeLayout) view.findViewById(R.id.launch_rl);
        relevant_rl = (RelativeLayout) view.findViewById(R.id.relevant_rl);

        brand_more = (TextView) view.findViewById(R.id.brand_more);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        final com.cityant.main.adapter.ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), getResources(), getFragments());
        viewPager.setAdapter(adapter);

        tabs.setViewPager(viewPager);
        mScrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return adapter.canScrollVertically(viewPager.getCurrentItem(), direction);
            }
        });
        mScrollableLayout.setOnFlingOverListener(new OnFlingOverListener() {
            @Override
            public void onFlingOver(int y, long duration) {
                adapter.getItem(viewPager.getCurrentItem()).onFlingOver(y, duration);
            }
        });

        mScrollableLayout.setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {

                rob_linear_ll.setVisibility(View.GONE);
                final float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = .0F;
                } else {
                    tabsTranslationY = y - maxY;
                }

                tabs.setTranslationY(tabsTranslationY);

                header.setTranslationY(y / 2);
            }
        });

        for( int i = 0; i < 10 ; i++ ){
            brand_array_ll.addView(addView());
        }
//        if (savedInstanceState != null) {
//            final int y = savedInstanceState.getInt(ARG_LAST_SCROLL_Y);
//            mScrollableLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    mScrollableLayout.scrollTo(0, y);
//                }
//            });
//        }
        launch_rl.setOnClickListener(v -> {
            CreateSmallGrabActivity.startActivity(getContext());
        });
        relevant_rl.setOnClickListener(v -> {
            RelatedMeActivity.startActivity(getContext());
        });
        brand_more.setOnClickListener(v -> {
            MoreTribeActivity.startActivity(getContext());
        });
        return view;
    }

    private View addView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater3 = LayoutInflater.from(getActivity());
        View view = inflater3.inflate(R.layout.brand_item_layout, null);

        view.setOnClickListener(v -> {
            TribeDetailsActivity.startActivity(getContext(),"屈臣氏","");
        });

        return view;
    }

    private void initData() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        new AsyncHttpPost(FragmentKnock.this, MYAppconfig.ROB_DETAILS_INFO, parameter, MYTaskID.ROB_DETAILS_INFO_ID,
                RobDetails.class, getContext());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_LAST_SCROLL_Y, mScrollableLayout.getScrollY());
        super.onSaveInstanceState(outState);
    }

    private List<BaseFragment> getFragments() {

        final FragmentManager manager = getFragmentManager();
        final List<BaseFragment> list = new ArrayList<>();

        ListViewFragment listViewFragment
                = (ListViewFragment) manager.findFragmentByTag(ListViewFragment.TAG);
        if (listViewFragment == null) {
            listViewFragment = ListViewFragment.newInstance(R.color.white);
        }
        ManyPeopleFragment manyPeopleFragment
                = (ManyPeopleFragment) manager.findFragmentByTag(ManyPeopleFragment.TAG);
        if (manyPeopleFragment == null) {
            manyPeopleFragment = ManyPeopleFragment.newInstance(R.color.white);
        }
        HundredPeopleFragment hundredPeopleFragment
                = (HundredPeopleFragment) manager.findFragmentByTag(HundredPeopleFragment.TAG);
        if (hundredPeopleFragment == null) {
            hundredPeopleFragment = HundredPeopleFragment.newInstance(R.color.white);
        }
        TenPeopleFragment tenPeopleFragment
                = (TenPeopleFragment) manager.findFragmentByTag(TenPeopleFragment.TAG);
        if (tenPeopleFragment == null) {
            tenPeopleFragment = TenPeopleFragment.newInstance(R.color.white);
        }
        FivePeopleFragment fivePeopleFragment
                = (FivePeopleFragment) manager.findFragmentByTag(FivePeopleFragment.TAG);
        if (fivePeopleFragment == null) {
            fivePeopleFragment = FivePeopleFragment.newInstance(R.color.white);
        }
        DoublePeopleFragment doublePeopleFragment
                = (DoublePeopleFragment) manager.findFragmentByTag(DoublePeopleFragment.TAG);
        if (doublePeopleFragment == null) {
            doublePeopleFragment = DoublePeopleFragment.newInstance(R.color.white);
        }
        Collections.addAll(list, listViewFragment, manyPeopleFragment,hundredPeopleFragment,tenPeopleFragment,fivePeopleFragment,doublePeopleFragment);

        return list;
    }

    @Override
    public void onFrictionChanged(float friction) {
        mScrollableLayout.setFriction(friction);
    }

    @Override
    public void openDialog(float friction) {
//        final ScrollableDialog dialog = ScrollableDialog.newInstance(friction);
//        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void openActivity(Intent intent) {
        startActivity(intent);
    }


    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {

    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

    }
}