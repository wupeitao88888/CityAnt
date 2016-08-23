package com.cityant.main.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.cityant.main.R;
import com.cityant.main.bean.MyFrends;
import com.cityant.main.utlis.FaceConversionUtil;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/20.
 */
public class MYChatActivity extends ActivitySupport {
    public static InputMethodManager mInputMethodManager;
    public static float inSampleSize;
    private int screenHeight;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychat);
        MyFrends myFrends = (MyFrends) getIntent().getSerializableExtra("MyFrend");
        setCtenterTitle(myFrends.getUser_name());
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setScreen();
        calculateEmojiSize();

    }


    /**
     * 根据分辨率计算表情的尺寸
     *
     * @return float
     */
    private void calculateEmojiSize() {
        // 计算当前分辨率与1280*800的比例
        float heightRatio = (float) screenHeight / (float) 1280;
        float widthRatio = (float) screenWidth / (float) 800;
        float inSampleSize1 = heightRatio > widthRatio ? heightRatio
                : widthRatio;
        inSampleSize = inSampleSize1 < 1 ? inSampleSize1 : 2f / 3f;
    }

    private void setScreen() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        if (screenWidth > screenHeight) {
            screenWidth = screenWidth ^ screenHeight;
            screenHeight = screenWidth ^ screenHeight;
            screenWidth = screenWidth ^ screenHeight;
        }
    }
}
