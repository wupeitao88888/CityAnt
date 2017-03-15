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
import com.cityant.main.activity.knock.KonckDetailsActivity;
import com.cityant.main.adapter.base.CommonAdapter;
import com.cityant.main.adapter.base.ViewHolder;
import com.cityant.main.bean.konck.RobIndex;
import com.cityant.main.fragment.BaseFragment;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 五人抢
* @author lvfl
* @time 2016/11/20 20:11
*/
public class FivePeopleFragment extends BaseFragment implements ThreadCallBack{

    public static final String TAG = "tag.FivePeopleFragment";

    private List<RobIndex.Data.robList> list = new ArrayList<>();
    private CommonAdapter<RobIndex.Data.robList> adapter;

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
        mListView = findView(view, R.id.list_view);
        adapter = new CommonAdapter<RobIndex.Data.robList>(getActivity(),R.layout.rob_item_layout, list) {
            @Override
            protected void convert(ViewHolder viewHolder, RobIndex.Data.robList robList, int position) {
                viewHolder.setCircleImageUrl(R.id.user_image,robList.getUser_avar());
                viewHolder.setText(R.id.user_name,robList.getUser_name());
                viewHolder.setVisible(R.id.relationship_text,"0".equals(robList.getIs_friend()) ? false : true);
                viewHolder.setImageUrl(R.id.goods_image,robList.getGoods_img());
                viewHolder.setText(R.id.goods_title,robList.getGoods_title());
                viewHolder.setText(R.id.total_num_text,"总需人数"+list.get(position).getNeed_man());
                viewHolder.setText(R.id.need_num_text,"还需人数"+list.get(position).getPoor_man());
                viewHolder.setOnClickListener(R.id.rob_btn,v -> {
                    KonckDetailsActivity.startActivity(getContext());
                });
            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Click: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        initData();
        return view;
    }

    /**
     * 请求数据
     */
    private void initData(){
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("rob_type", "2");
        new AsyncHttpPost(FivePeopleFragment.this, MYAppconfig.ROB_INDEX, parameter, MYTaskID.ROB_INDEX_ID,
                RobIndex.class, getContext());
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

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        if (resultCode == MYTaskID.ROB_INDEX_ID){
            RobIndex robIndex = (RobIndex) modelClass;
            if ("200".equals(robIndex.getCode()) && null != robIndex.data.rob_list && robIndex.data.rob_list.size() > 0){
                list.addAll(robIndex.data.rob_list);
                adapter.notifyDataSetChanged();
            } else {
                if (null != robIndex.data){
                    ToastUtil.show(getContext(),robIndex.data.getCode_message()+"",ToastUtil.SHOW_TOAST);
                    return;
                }
                ToastUtil.show(getContext(),"暂无数据",ToastUtil.SHOW_TOAST);
            }
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {
    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        if (resultCode == MYTaskID.ROB_INDEX_ID){
            ToastUtil.show(getContext(),"暂无数据",ToastUtil.SHOW_TOAST);
        }

    }
}
