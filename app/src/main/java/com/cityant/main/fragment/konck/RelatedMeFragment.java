package com.cityant.main.fragment.konck;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.activity.knock.KonckDetailsActivity;
import com.cityant.main.adapter.base.CommonAdapter;
import com.cityant.main.adapter.base.ViewHolder;
import com.cityant.main.bean.konck.AndMyRob;
import com.cityant.main.fragment.BaseFragment;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 与我相关页面
* @author lvfl
* @time 2016/11/28 22:41
*/
public class RelatedMeFragment extends BaseFragment implements ThreadCallBack{

    public static final String TAG = "tag.RelatedMeFragment";

    private List<AndMyRob.Data.robList> rob_list = new ArrayList<>();
    private int state;
    private CommonAdapter adapter;

    public static RelatedMeFragment newInstance(int state) {
        final Bundle bundle = new Bundle();
        bundle.putInt("state", state);

        final RelatedMeFragment fragment = new RelatedMeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle sis) {
        state = getArguments().getInt("state");
        final View view = inflater.inflate(R.layout.fragment_list_view, parent, false);
        mListView = findView(view, R.id.list_view);
        adapter = new CommonAdapter(getActivity(),R.layout.related_me_item_layout, rob_list){

            @Override
            protected void convert(ViewHolder viewHolder, Object item, int position) {
                viewHolder.setText(R.id.goods_title,rob_list.get(position).getGoods_title());
                viewHolder.setImageUrl(R.id.goods_image,rob_list.get(position).getGoods_img());
                viewHolder.setCircleImageUrl(R.id.user_image,rob_list.get(position).getUser_avar());
                viewHolder.setText(R.id.price_text,"￥"+rob_list.get(position).getRob_price());
                viewHolder.setText(R.id.total_num_text,"总需人数："+rob_list.get(position).getNeed_man());
                viewHolder.setHtmlText(R.id.total_num_text,"<font color = #666666 >还需人数：</font><font color=#f4cc08>"+rob_list.get(position).getPoor_man()+"</font>");
                viewHolder.setText(R.id.user_name,rob_list.get(position).getUser_name());
                switch (state){
                    case 0:
                        viewHolder.setText(R.id.rod_content_text,"您没有抢到本次商品");
                        break;
                    case 1:
                        viewHolder.setText(R.id.rod_content_text,"已存入您的蚂蚁仓库");
                        break;
                    case 2:
                        viewHolder.setText(R.id.rod_content_text,"已退款至您的蚂蚁仓库");
                        break;
                }
                viewHolder.setText(R.id.rod_content_text,rob_list.get(position).getUser_name());
                viewHolder.setVisible(R.id.relationship_text,rob_list.get(position).getIs_friend().equals("0") ? false : true);
                switch (rob_list.get(position).getNeed_man()){
                    case "2":
                        viewHolder.setText(R.id.rob_nub,"双人抢");
                        break;
                    case "5":
                        viewHolder.setText(R.id.rob_nub,"五人抢");
                        break;
                    case "10":
                        viewHolder.setText(R.id.rob_nub,"十人抢");
                        break;
                    case "100":
                        viewHolder.setText(R.id.rob_nub,"百人抢");
                        break;
                    default:
                        viewHolder.setText(R.id.rob_nub,"多人抢");
                        break;
                }

            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KonckDetailsActivity.startActivity(getContext(),rob_list.get(position).getRob_id());
            }
        });

        getData();

        return view;
    }

    private void getData(){
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("state", state);
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        new AsyncHttpPost(RelatedMeFragment.this, MYAppconfig.ROB_MYROB, parameter, MYTaskID.ROB_MYROB_ID,
                AndMyRob.class, getContext());
    }

    @Override
    public CharSequence getTitle(Resources r) {
        return "双人抢";
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
        if (resultCode == MYTaskID.ROB_MYROB_ID){
            AndMyRob andMyRob = (AndMyRob) modelClass;
            if ("200".equals(andMyRob.getCode())){
                if (null != andMyRob.data && andMyRob.data.rob_list.size()>0){
                    rob_list.addAll(andMyRob.data.rob_list);
                }
            }
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

    }
}
