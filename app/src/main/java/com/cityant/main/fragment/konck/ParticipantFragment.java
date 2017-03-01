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
import com.cityant.main.bean.konck.RobDetails;

import java.util.ArrayList;

/**
* 参与人员
* @author lvfl
* @time 2016/11/20 20:11
*/
public class ParticipantFragment extends Fragment {


    private ArrayList<RobDetails.Data.userList> par_list = new ArrayList<>();
    private CommonAdapter adapter;

    public static ParticipantFragment newInstance(ArrayList<RobDetails.Data.userList> par_list) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable("par_list",par_list);
        final ParticipantFragment fragment = new ParticipantFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle sis) {
        par_list = (ArrayList<RobDetails.Data.userList>) getArguments().getSerializable("par_list");
        final View view = inflater.inflate(R.layout.fragment_recyclerview_layout, parent, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new CommonAdapter(getContext(),R.layout.participant_item_layout,par_list){
            @Override
            protected void convert(ViewHolder holder, Object o, int position) {
                holder.setCircleImageUrl(R.id.user_image,par_list.get(position).getUser_avar());
                holder.setText(R.id.user_name,par_list.get(position).getUser_name());
                holder.setVisible(R.id.promoter_text,"0".equals(par_list.get(position).getIs_own()) ? false : true);
                holder.setVisible(R.id.winning_image,"0".equals(par_list.get(position).getIs_own()) ? false : true);
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
