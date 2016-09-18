package com.cityant.main.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.cityant.main.R;
import com.cityant.main.bean.MyFrends;
import com.cityant.main.utlis.FaceConversionUtil;
import com.iloomo.base.ActivitySupport;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wupeitao on 16/8/20.
 */
public class MYChatActivity extends ActivitySupport {
    public static InputMethodManager mInputMethodManager;
    public static float inSampleSize;
    private int screenHeight;
    private int screenWidth;
    private PtrClassicFrameLayout mPtrFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychat);
        MyFrends myFrends = (MyFrends) getIntent().getSerializableExtra("MyFrend");
        setCtenterTitle(myFrends.getUser_name());
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setScreen();
        calculateEmojiSize();
        initView();
    }

    private void initView() {
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    protected void updateData() {

//        DemoRequestData.getImageList(new RequestFinishHandler<JsonData>() {
//            @Override
//            public void onRequestFinish(final JsonData data) {
//                mPtrFrame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter.getDataList().clear();
//                        mAdapter.getDataList().addAll(data.optJson("data").optJson("list").toArrayList());
        mPtrFrame.refreshComplete();
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }, 0);
//            }
//        });
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
