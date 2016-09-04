package com.cityant.main.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cityant.main.R;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.cityant.main.adapter.ViewPagerAdapter;
import com.cityant.main.utlis.ColorRandomizer;
import com.cityant.main.utlis.ConfigurationFragmentCallbacks;
import com.cityant.main.widget.TabsLayout;
import com.iloomo.base.FragmentSupport;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnFlingOverListener;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;


public class FragmentKnock extends FragmentSupport implements ConfigurationFragmentCallbacks {

    private ListView mlist;
    private FragmentHomeAdapter fragmentHomeAdapter = null;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;
    private static final String ARG_LAST_SCROLL_Y = "arg.LastScrollY";

    private ScrollableLayout mScrollableLayout;

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_knock, null);
        setTitle("抢");
        final View header = view.findViewById(R.id.header);
        final LinearLayout brand_array_ll = (LinearLayout) view.findViewById(R.id.brand_array_ll);
        final TabsLayout tabs = (TabsLayout) view.findViewById(R.id.tabs);

        mScrollableLayout = (ScrollableLayout) view.findViewById(R.id.scrollable_layout);
        mScrollableLayout.setDraggableView(tabs);

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
        return view;
    }

    private View addView() {
        View view = null;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater3 = LayoutInflater.from(getActivity());
        view = inflater3.inflate(R.layout.brand_item_layout, null);
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_LAST_SCROLL_Y, mScrollableLayout.getScrollY());
        super.onSaveInstanceState(outState);
    }

    private List<BaseFragment> getFragments() {

        final FragmentManager manager = getFragmentManager();
        final List<BaseFragment> list = new ArrayList<>();

//        ConfigurationFragment configurationFragment
//                = (ConfigurationFragment) manager.findFragmentByTag(ConfigurationFragment.TAG);
//        if (configurationFragment == null) {
//            configurationFragment = ConfigurationFragment.newInstance(colorRandomizer.next());
//        }

        ListViewFragment listViewFragment
                = (ListViewFragment) manager.findFragmentByTag(ListViewFragment.TAG);
        if (listViewFragment == null) {
            listViewFragment = ListViewFragment.newInstance(R.color.white);
        }
        ListViewFragment listViewFragment2
                = (ListViewFragment) manager.findFragmentByTag(ListViewFragment.TAG);
        if (listViewFragment2 == null) {
            listViewFragment2 = ListViewFragment.newInstance(R.color.white);
        }

//        ScrollViewFragment scrollViewFragment
//                = (ScrollViewFragment) manager.findFragmentByTag(ScrollViewFragment.TAG);
//        if (scrollViewFragment == null) {
//            scrollViewFragment = ScrollViewFragment.newInstance(colorRandomizer.next());
//        }
//
//        RecyclerViewFragment recyclerViewFragment
//                = (RecyclerViewFragment) manager.findFragmentByTag(RecyclerViewFragment.TAG);
//        if (recyclerViewFragment == null) {
//            recyclerViewFragment = RecyclerViewFragment.newInstance(colorRandomizer.next());
//        }
//
        Collections.addAll(list, listViewFragment, listViewFragment2);

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


}