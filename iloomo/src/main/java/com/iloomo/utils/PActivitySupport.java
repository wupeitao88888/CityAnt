package com.iloomo.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iloomo.paysdk.R;


/**
 * Actity 基类
 *
 * @author wpt
 */
@SuppressLint("NewApi")
public class PActivitySupport extends FragmentActivity  {


    protected Context context = null;

    private com.iloomo.widget.PTitleBar PTitleBar;
    private LinearLayout linearLayout;


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 沉浸通知栏
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        super.onCreate(savedInstanceState);
        initDate();
    }

    private void initDate() {
        // TODO Auto-generated method stub
        context = this;

    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);// 设置布局LinearLayout的属性
        View customtitle = LayoutInflater.from(this).inflate(
                R.layout.playout_activitytitle, null);
        PTitleBar = (com.iloomo.widget.PTitleBar) customtitle.findViewById(R.id.lc_title);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 自己的布局
        View customContentView = LayoutInflater.from(this).inflate(layoutResID,
                null);
        linearLayout.addView(customtitle, lp1);
        LinearLayout.LayoutParams lpcontent = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.addView(customContentView, lpcontent);

        super.setContentView(linearLayout);
    }

    protected void mIntent(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        packageContext.startActivity(intent);
    }

    /**
     * 设置titleb中间
     *
     * @param
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
        PTitleBar.setCenterTitle(title);
    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    protected void setCtenterTitle(int title) {
        PTitleBar.setCenterTitle(getResources().getString(title));
    }

    /**
     * 设置左边的是否显示
     */
    protected void isLeftVisibility(boolean isVisibit) {
        PTitleBar.isLeftVisibility(isVisibit);
    }

    /**
     * 设置中间标题是否显示
     */
    protected void isCenterVisibility(boolean isVisibit) {
        PTitleBar.isCenterVisibility(isVisibit);
    }

    /**
     * 设置右边标题内容
     */
    protected void setRightTitle(String msg) {
        PTitleBar.setRightTitle(msg);
    }

    /**
     * 设置右边标题内容字体颜色
     */
    protected void setRightTitle(int msg) {
        PTitleBar.setRightTitle(msg);
    }



    /**
     * 设置右边标题内容
     */
    protected void setRightTitleListener(OnClickListener l) {
        PTitleBar.setRightTitleListener(l);
    }



    /**
     * 设置title背景颜色
     */
    public void setBackGb(int color) {
        PTitleBar.setBackGb(color);
    }



    /**
     * 设置返回监听
     */
    public void setOnclickBackListener(OnClickListener l) {
        PTitleBar.setOnclickBackListener(l);
    }




    /**
     * 设置左边的图片
     */
    public void setLeftImage(int draw) {
        PTitleBar.setLeftImage(draw);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindDrawables(linearLayout);
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view != null) {
            return;
        }
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if(view instanceof  ImageView){
            releaseImageView((ImageView) view);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
    public void releaseImageView(ImageView imageView) {
        Drawable d = imageView.getDrawable();
        if (d != null)
            d.setCallback(null);
        imageView.setImageDrawable(null);
        imageView.setBackgroundDrawable(null);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();

    }




}
