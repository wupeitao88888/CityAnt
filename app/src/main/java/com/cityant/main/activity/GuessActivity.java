package com.cityant.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.cityant.main.R;
import com.cityant.main.adapter.GuessAdapter;
import com.cityant.main.bean.GuessIndexList;
import com.cityant.main.bean.GuessIndexModel;
import com.cityant.main.bean.NewFrendsModel;
import com.cityant.main.global.MYTaskID;
import com.cityant.main.utlis.StringHelper;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.L;
import com.iloomo.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by wupeitao on 16/8/13.
 */
public class GuessActivity extends ActivitySupport implements View.OnClickListener {

    private GuessAdapter guessAdapter;
    private PtrClassicFrameLayout mPtrFrame;
    private ListView newfrendslist;
    private List<GuessIndexList> listS = new ArrayList<>();
    private View handview;
    private RelativeLayout newInfo;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        setCtenterTitle(R.string.guess);
        newfrendslist = (ListView) findViewById(R.id.newfrendslist);
        handview = LayoutInflater.from(context).inflate(R.layout.guess_hreadview, null);
        newfrendslist.addHeaderView(handview);

        newInfo = (RelativeLayout) handview.findViewById(R.id.newInfo);
        newInfo.setOnClickListener(this);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);
        guessAdapter = new GuessAdapter(context, listS);
        newfrendslist.setAdapter(guessAdapter);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                L.e("上拉加载");
                updateData(true);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                L.e("下拉刷新");
                updateData(false);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, newfrendslist, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, newfrendslist, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f); // you can also set foot and header separately
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(1000);  // you can also set foot and header separately
        // default is false
        mPtrFrame.setPullToRefresh(false);

        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        updateData(false);
    }


    private void updateData(boolean isLoad) {
        netHttp(isLoad);
    }

    private void intent() {
        startActivity(new Intent(this, GuessJionInInfoActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newInfo:
                intent();
                break;
        }
    }

    public void netHttp(boolean isLoad) {
        if (isLoad) {
            page++;
        } else {
            page = 1;
        }
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("page", page + "");
        parameter.put("page_size", 20 + "");

        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                GuessIndexModel guessIndexModel = (GuessIndexModel) modelClass;
                if (isLoad) {
                    if (guessIndexModel.getData().getGuess_list().size() == 0) {
                        page--;
                    }
                    listS.addAll(guessIndexModel.getData().getGuess_list());
                } else {
                    listS.clear();
                    listS.addAll(guessIndexModel.getData().getGuess_list());
                }
                guessAdapter.notifyDataSetChanged();
                mPtrFrame.refreshComplete();
            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                mPtrFrame.refreshComplete();
            }
        }, MYAppconfig.GUESS_INDEX, parameter, MYTaskID.GUESS_INDEX, GuessIndexModel.class, context);
    }
}
