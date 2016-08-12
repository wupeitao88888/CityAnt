package com.iloomo.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.baidu.mobstat.StatService;
import com.iloomo.paysdk.R;
import com.iloomo.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by wupeitao on 16/1/7.
 */
public class FragmentSupport extends Fragment implements IFragmentSupport {
    public Context context;
    public String title;
    public View rootView;// 缓存Fragment view
    public TitleBar titleBar;
    private LinearLayout linearLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();
        if (rootView == null) {


            linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);// 设置布局LinearLayout的属性
            View customtitle = LayoutInflater.from(context).inflate(
                    R.layout.layout_activitytitle, null);
            titleBar = (TitleBar) customtitle.findViewById(R.id.lc_title);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            // 自己的布局
            View customContentView = initView();
            linearLayout.addView(customtitle, lp1);
            LinearLayout.LayoutParams lpcontent = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.addView(customContentView, lpcontent);
            rootView=linearLayout;
            setCtenterTitle(title);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return setTitleBar(this.rootView);
    }





    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public void onResume() {
        super.onResume();
        /**
         * 百度统计
         * 页面起始（注意： 每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 如果该FragmentActivity包含了几个全页面的fragment，那么可以在fragment里面加入就可以了，这里可以不加入。如果不加入将不会记录该Activity页面。
         */
//        StatService.onResume(this);
        /***
         * 百度统计
         */
        StatService.onPageStart(getActivity(), title);
        MobclickAgent.onPageStart(title);
    }

    @Override
    public void onPause() {
        super.onPause();
        /**
         * 百度统计
         * 页面结束（注意： 每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 如果该FragmentActivity包含了几个全页面的fragment，那么可以在fragment里面加入就可以了，这里可以不加入。如果不加入将不会记录该Activity页面。
         */
//        StatService.onPause(this);
        StatService.onPageEnd(getActivity(), title);
        MobclickAgent.onPageEnd(title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(rootView);
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    public void setContent(int layout) {
        this.rootView = LayoutInflater.from(context).inflate(layout, null);
    }

    @Override
    public View initView() {

        return rootView;
    }

    @Override
    public View setTitleBar(View view) {
        return view;
    }

    public String mString(int string) {
        // TODO Auto-generated method stub
        return getResources().getString(string);
    }
    /**
     * 设置titleb中间
     *
     * @param title
     */
    protected void setRemoveTitle() {
        linearLayout.removeViewAt(0);
    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    protected void setCtenterTitle(String title) {
        titleBar.setCenterTitle(title);
    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    protected void setCtenterTitle(int title) {
        titleBar.setCenterTitle(mString(title));
    }

    /**
     * 设置左边的是否显示
     */
    protected void isLeftVisibility(boolean isVisibit) {
        titleBar.isLeftVisibility(isVisibit);
    }

    /**
     * 设置中间标题是否显示
     */
    protected void isCenterVisibility(boolean isVisibit) {
        titleBar.isCenterVisibility(isVisibit);
    }

    /**
     * 设置右边标题内容
     */
    protected void setRightTitle(String msg) {
        titleBar.setRightTitle(msg);
    }

    /**
     * 设置右边标题内容字体颜色
     */
    protected void setRightTitle(int msg) {
        titleBar.setRightTitle(msg);
    }

    /**
     * 设置右边图片内容
     */
    protected void setRightTitleRes(int msg) {
        titleBar.setRightTitleRes(msg);
    }

    /**
     * 设置右边标题内容
     */
    protected void setRightTitleListener(View.OnClickListener l) {
        titleBar.setRightTitleListener(l);
    }

    /**
     * 设置右边图片内容
     */
    protected void setRightTitleImageListener(View.OnClickListener l) {
        titleBar.setRightTitleImageListener(l);
    }

    /**
     * 设置title背景颜色
     */
    public void setBackGb(int color) {
        titleBar.setBackGb(color);
    }


    /**
     * 设置”返回“是否隐藏
     */
    public void isLeftTitleVisibility(boolean isVisibit) {
        titleBar.isLeftTitleVisibility(isVisibit);
    }

    /**
     * 设置返回监听
     */
    public void setOnclickBackListener(View.OnClickListener l) {
        titleBar.setOnclickBackListener(l);
    }

    /**
     * 设置左边的内容
     */
    public void setLeftTitle(String msg) {
        titleBar.setLeftTitle(msg);
    }

    /**
     * 设置左边的内容字体颜色
     */
    public void setLeftTitleColor(int msg) {
        titleBar.setLeftTitleColor(msg);
    }


    /**
     * 设置左边的图片
     */
    public void setLeftImage(int draw) {
        titleBar.setLeftImage(draw);
    }

}
