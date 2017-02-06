package com.cityant.main.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.GuessAdapter;
import com.cityant.main.adapter.GuessJionInInfoAdapter;
import com.iloomo.base.ActivitySupport;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by wupeitao on 2017/2/6.
 */

public class GuessJionInInfoActivity extends ActivitySupport {
    private GuessJionInInfoAdapter guessAdapter;
    private PtrClassicFrameLayout mPtrFrame;
    private ListView newfrendslist;
    private List<String> listS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessjionininfo);
        setCtenterTitle(R.string.jionin);

        newfrendslist = (ListView) findViewById(R.id.newfrendslist);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);

        guessAdapter = new GuessJionInInfoAdapter(context, listS);
        newfrendslist.setAdapter(guessAdapter);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
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
        updateData();
    }

    private void updateData() {

        for (int i = 0; i < 5; i++) {
            listS.add(i + "");
        }
        guessAdapter.notifyDataSetChanged();
        mPtrFrame.refreshComplete();
    }

}
