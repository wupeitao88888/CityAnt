package com.cityant.main.activity;

import android.os.Bundle;

import com.cityant.main.R;
import com.cityant.main.fragment.FragmentAdd;
import com.cityant.main.fragment.FragmentHome;
import com.cityant.main.fragment.FragmentKnock;
import com.cityant.main.fragment.FragmentMessage;
import com.cityant.main.fragment.FragmentMy;
import com.iloomo.base.TabFragmentActivity;
import com.iloomo.widget.MainTabHost;


/**
 * Created by wupeitao on 16/3/8.
 */
public class IndexFragment extends TabFragmentActivity {
    private MainTabHost mainTabSupport;
    //布局
    private Integer[] imgTab = {R.layout.tab_main_home, R.layout.tab_main_knock, R.layout.tab_main_add,
            R.layout.tab_main_message, R.layout.tab_main_my};
    //Fragment使用
    private Class[] classTab = {FragmentHome.class, FragmentKnock.class, FragmentAdd.class, FragmentMessage.class,
            FragmentMy.class};
    // tab选中背景 drawable 样式图片 背景都是同一个,背景颜色都是 白色。。。
    private Integer[] styleTab = {R.color.white, R.color.white, R.color.white, R.color.white, R.color.white,
            R.color.white};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mainTabSupport = new MainTabHost(IndexFragment.this, IndexFragment.this);
        mainTabSupport.setTag(5);
        mainTabSupport.setTabDrawable(imgTab);
        mainTabSupport.setTabFragment(classTab);
        mainTabSupport.setTabBackground(styleTab);
        setContentView(mainTabSupport);
    }


}
