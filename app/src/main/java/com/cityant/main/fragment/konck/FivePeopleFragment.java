package com.cityant.main.fragment.konck;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cityant.main.R;
import com.cityant.main.adapter.RobAdapter;
import com.cityant.main.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
* 五人抢
* @author lvfl
* @time 2016/11/20 20:11
*/
public class FivePeopleFragment extends BaseFragment {

    public static final String TAG = "tag.FivePeopleFragment";

    private List<String> list = new ArrayList<>();

    public static FivePeopleFragment newInstance(int color) {
        final Bundle bundle = new Bundle();
        bundle.putInt(ARG_COLOR, color);

        final FivePeopleFragment fragment = new FivePeopleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle sis) {

        final View view = inflater.inflate(R.layout.fragment_list_view, parent, false);
        for(int i = 0;i < 10 ; i++){
            list.add(i+"");
        }
        mListView = findView(view, R.id.list_view);
        RobAdapter adapter = new RobAdapter(getActivity(), list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Click: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public CharSequence getTitle(Resources r) {
        return "五人抢";
    }

    @Override
    public String getSelfTag() {
        return TAG;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return mListView != null && mListView.canScrollVertically(direction);
    }

    @Override
    public void onFlingOver(int y, long duration) {
        if (mListView != null) {
            mListView.smoothScrollBy(y, (int) duration);
        }
    }
}
