package com.cityant.main.fragment.konck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cityant.main.R;
import com.cityant.main.adapter.base.CommonAdapter;
import com.cityant.main.adapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
* 商品详情
* @author lvfl
* @time 2016/11/20 20:11
*/
public class GoodsDetailsFragment extends Fragment {


    private List<String> list = new ArrayList<>();
    private CommonAdapter adapter;

    public static GoodsDetailsFragment newInstance(int color) {
        final Bundle bundle = new Bundle();

        final GoodsDetailsFragment fragment = new GoodsDetailsFragment();
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
        mListView = (ListView) view.findViewById(R.id.list_view);
        adapter = new CommonAdapter(getContext(),R.layout.goods_details_item_layout,list){

            @Override
            protected void convert(ViewHolder viewHolder, Object item, int position) {
                viewHolder.setImageUrl(R.id.goods_image,"http://pic.58pic.com/58pic/12/74/39/20b58PICcVh.jpg");
            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Click: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
