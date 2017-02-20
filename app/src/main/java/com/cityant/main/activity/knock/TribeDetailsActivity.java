package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cityant.main.R;
import com.cityant.main.adapter.base.CommonAdapter;
import com.cityant.main.adapter.base.ViewHolder;
import com.cityant.main.bean.konck.TribeDetailsBean;
import com.cityant.main.global.MYTaskID;
import com.cityant.main.widget.CircleTransform;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.bean.ErrorBaseModel;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 部落详情
* @author lvfl
* @time 2016/12/4 12:54
*/
public class TribeDetailsActivity extends ActivitySupport implements ThreadCallBack{

    private String title_name;
    private String brand_id;
    private ListView tribe_listView;
    private CommonAdapter adapter;
    private ImageView tribe_head_image;
    private TextView follow_num_text;
    private TextView hot_num_text;
    private TextView tribe_single_text;
    private RelativeLayout follow_btn_rl;
    private ImageView follow_icon;
    private TextView follow_text;
    private RelativeLayout money_rl;
    private RelativeLayout chat_rl;
    private RelativeLayout more_follow_rl;
    private LinearLayout follow_ll;
    private LinearLayout brand_array_ll;
    private TextView post_knock;
    private TextView konck_record;
    private List<TribeDetailsBean.Data.GoodsList> goods_list = new ArrayList<>();


    public static void startActivity(Context context, String title_name,String brand_id){
        Intent intent = new Intent(context,TribeDetailsActivity.class);
        intent.putExtra("title_name",title_name);
        intent.putExtra("brand_id",brand_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title_name = getIntent().getStringExtra("title_name");
        brand_id = getIntent().getStringExtra("brand_id");
        setContentView(R.layout.activity_tribe_details_layout);
        setCtenterTitle(title_name+"");
        setRightTitleRes(R.drawable.icon_fenxiang);
        setRightTitleImageListener(v -> {
            ToastUtil.show(this,"分享",ToastUtil.SHOW_TOAST);
        });
        initView();
    }

    private void initView() {
        tribe_listView = (ListView) findViewById(R.id.tribe_listView);
        View head_view = LayoutInflater.from(this).inflate(R.layout.tribe_details_head_layout,null);
        tribe_head_image = (ImageView) head_view.findViewById(R.id.tribe_head_image);
        follow_num_text = (TextView) head_view.findViewById(R.id.follow_num_text);
        hot_num_text = (TextView) head_view.findViewById(R.id.hot_num_text);
        tribe_single_text = (TextView) head_view.findViewById(R.id.tribe_single_text);
        follow_btn_rl = (RelativeLayout) head_view.findViewById(R.id.follow_btn_rl);
        money_rl = (RelativeLayout) head_view.findViewById(R.id.money_rl);
        chat_rl = (RelativeLayout) head_view.findViewById(R.id.chat_rl);
        more_follow_rl = (RelativeLayout) head_view.findViewById(R.id.more_follow_rl);
        follow_ll = (LinearLayout) head_view.findViewById(R.id.follow_ll);
        brand_array_ll = (LinearLayout) head_view.findViewById(R.id.brand_array_ll);
        post_knock = (TextView) head_view.findViewById(R.id.post_knock);
        konck_record = (TextView) head_view.findViewById(R.id.konck_record);

//        adapter = new TribeDetailsAdapter(context,goods_list);
        adapter = new CommonAdapter(context,R.layout.tribe_details_item_layout, goods_list) {
            @Override
            protected void convert(ViewHolder viewHolder, Object item, int position) {
                viewHolder.setCircleImageUrl(R.id.user_image,goods_list.get(position).getUser_avar());
                viewHolder.setText(R.id.user_name,goods_list.get(position).getUser_name());
                viewHolder.setImageUrl(R.id.user_name,goods_list.get(position).getGoods_img());
                viewHolder.setText(R.id.goods_title,goods_list.get(position).getGoods_title());
                viewHolder.setText(R.id.price_text,goods_list.get(position).getRob_price());
                viewHolder.setText(R.id.total_num_text,"总需人数"+goods_list.get(position).getNeed_man());
                viewHolder.setText(R.id.need_num_text,"还需人数"+goods_list.get(position).getPoor_man());
                viewHolder.setOnClickListener(R.id.rob_btn,v -> {
                    KonckDetailsActivity.startActivity(context);
                });
            }
        };
        tribe_listView.addHeaderView(head_view);
        tribe_listView.setAdapter(adapter);

        more_follow_rl.setOnClickListener(v -> {
            TribeFollowActivity.startActivity(this,brand_id);
        });
        money_rl.setOnClickListener(v -> {
            TheWageActivity.startActivity(this);
        });

        getTribeDetailsData();
    }

    private View addFollowView() {
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.follow_add_view_layout, null);
        return view;
    }
    private View addView(String brand_ids) {
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.brand_item_layout, null);

        view.setOnClickListener(v -> {
            TribeDetailsActivity.startActivity(getContext(),"屈臣氏",brand_ids);
        });

        return view;
    }

    /**
     * 请求数据
     */
    public void getTribeDetailsData(){
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("brand_id",brand_id);
        parameter.put("token",MYAppconfig.loginUserInfoData.getToken());
        new AsyncHttpPost(this, MYAppconfig.BRAND_INFO,parameter, MYTaskID.BRAND_INFO_ID,TribeDetailsBean.class, context);
    }

    /**
     * 关注
     */
    public void sendGroupAdd(String groupid){
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("groupid",groupid);
        parameter.put("type","1");
        parameter.put("token",MYAppconfig.loginUserInfoData.getToken());
        new AsyncHttpPost(this, MYAppconfig.GROUP_ADD,parameter, MYTaskID.GROUP_ADD_ID,ErrorBaseModel.class, context);
    }

    /**
     * 取消关注
     */
    public void sendGroupRemove(String groupid){
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("groupid",groupid);
        parameter.put("type","1");
        parameter.put("token",MYAppconfig.loginUserInfoData.getToken());
        new AsyncHttpPost(this, MYAppconfig.GROUP_REMOVE,parameter, MYTaskID.GROUP_REMOVE_ID,ErrorBaseModel.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        if (resultCode == MYTaskID.BRAND_INFO_ID){
            TribeDetailsBean tribeDetailsBean = (TribeDetailsBean) modelClass;
            if ("200".equals(tribeDetailsBean.getCode())){
                if (null != tribeDetailsBean.data){
                    Glide.with(this).load(tribeDetailsBean.data.getBrand_img()).
                            bitmapTransform(new CircleTransform(this)).centerCrop().into(tribe_head_image);
                    hot_num_text.setText(tribeDetailsBean.data.getActive_count());
                    follow_num_text.setText(tribeDetailsBean.data.getFocus_number());
                    tribe_single_text.setText(tribeDetailsBean.data.getBrand_desc());
                    follow_btn_rl.setOnClickListener(v -> {
                        if ("0".equals(tribeDetailsBean.data.getIs_focus())){
                            sendGroupAdd(tribeDetailsBean.data.getGroupid());
                            follow_icon.setVisibility(View.VISIBLE);
                            follow_text.setText("关注");
                        }else{
                            sendGroupRemove(tribeDetailsBean.data.getGroupid());
                        }
                    });
                    for( int i = 0; i < tribeDetailsBean.data.other_brand_list.size() ; i++ ){
                        brand_array_ll.addView(addView(tribeDetailsBean.data.other_brand_list.get(i).getBrand_id()));
                    }
                    for( int i = 0; i < tribeDetailsBean.data.user_list.size() ; i++ ){
                        follow_ll.addView(addFollowView());
                    }
                    goods_list.addAll(tribeDetailsBean.data.getGoods_list());
                    adapter.notifyDataSetChanged();
                }
            }
        }else if (resultCode == MYTaskID.GROUP_ADD_ID){
            ErrorBaseModel baseModel = (ErrorBaseModel) modelClass;
            ToastUtil.show(this,baseModel.data.getCode_message()+"",ToastUtil.SHOW_TOAST);
        }else if (resultCode == MYTaskID.GROUP_REMOVE_ID){
            ErrorBaseModel baseModel = (ErrorBaseModel) modelClass;
            ToastUtil.show(this,baseModel.data.getCode_message()+"",ToastUtil.SHOW_TOAST);
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

    }
}
