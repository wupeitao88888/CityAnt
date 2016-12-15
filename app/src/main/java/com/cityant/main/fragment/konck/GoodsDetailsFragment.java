package com.cityant.main.fragment.konck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cityant.main.R;
import com.cityant.main.adapter.recycleview.CommonAdapter;
import com.cityant.main.adapter.recycleview.MultiItemTypeAdapter;
import com.cityant.main.adapter.recycleview.base.ViewHolder;

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

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle sis) {

        final View view = inflater.inflate(R.layout.fragment_recyclerview_layout, parent, false);
        for(int i = 0;i < 10 ; i++){
            list.add(i+"");
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new CommonAdapter(getContext(),R.layout.goods_details_item_layout,list){

            @Override
            protected void convert(ViewHolder holder, Object o, int position) {
                holder.setImageUrl(R.id.goods_image,"http://pic.58pic.com/58pic/12/74/39/20b58PICcVh.jpg");
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(getActivity(), "Click: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }
}
