package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cityant.main.R;
import com.cityant.main.bean.NeedDetails;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.bean.BaseModel;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;
import com.iloomo.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;

/**
* 需求详情
* @author Lvfl
* created at 2016/9/7 15:43
*/
public class HomeDetailsActivity extends ActivitySupport implements ThreadCallBack{

    private ListView details_listView;
    private List<NeedDetails.Data.Reply_list> reply_list = new ArrayList<>();
    private RelativeLayout sing_up_rl;
    private TextView commit_sing_up;
    private View sing_up_view;
    private String need_id;
    private DetailsAdapter adapter;
    private ImageView logo_image;
    private TextView user_name;
    private TextView sex_text;
    private TextView auth_text;
    private ImageView tag_text;
    private TextView title_text;
    private TextView price_text;
    private TextView price_company;
    private TextView demand_time;
    private TextView demand_adr;
    private TextView demand_sex;
    private TextView introduction_text;
    private TextView places_text;
    private TextView content_comment_text;
    private TextView post_comment_text;
    private TextView cancel_btn;
    private TextView commit_btn;

    public static void startActivity(Context context,String need_id) {
        Intent intent = new Intent(context, HomeDetailsActivity.class);
        intent.putExtra("need_id",need_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details_layout);
        need_id = getIntent().getStringExtra("need_id");
        initView();
    }

    private void initView() {
        setCtenterTitle("详情");
        View view = LayoutInflater.from(context).inflate(R.layout.activity_home_details_head_layout,null);
        logo_image = (ImageView) view.findViewById(R.id.logo_image);
        user_name = (TextView) view.findViewById(R.id.user_name);
        sex_text = (TextView) view.findViewById(R.id.sex_text);
        auth_text = (TextView) view.findViewById(R.id.auth_text);
        tag_text = (ImageView) view.findViewById(R.id.tag_text);
        title_text = (TextView) view.findViewById(R.id.title_text);
        price_text = (TextView) view.findViewById(R.id.price_text);
        price_company = (TextView) view.findViewById(R.id.price_company);
        demand_time = (TextView) view.findViewById(R.id.demand_time);
        demand_adr = (TextView) view.findViewById(R.id.demand_adr);
        demand_sex = (TextView) view.findViewById(R.id.demand_sex);
        introduction_text = (TextView) view.findViewById(R.id.introduction_text);
        places_text = (TextView) view.findViewById(R.id.places_text);
        content_comment_text = (TextView) view.findViewById(R.id.content_comment_text);
        post_comment_text = (TextView) view.findViewById(R.id.post_comment_text);

        details_listView = (ListView) findViewById(R.id.details_listView);
        cancel_btn = (TextView) findViewById(R.id.cancel_btn);
        commit_btn = (TextView) findViewById(R.id.commit_btn);
        sing_up_rl = (RelativeLayout) findViewById(R.id.sing_up_rl);
        commit_sing_up = (TextView) findViewById(R.id.commit_sing_up);
        sing_up_view = (View) findViewById(R.id.sing_up_view);
        details_listView.addHeaderView(view);
        adapter = new DetailsAdapter();
        details_listView.setAdapter(adapter);
        commit_sing_up.setOnClickListener(v -> sing_up_rl.setVisibility(View.VISIBLE));
        sing_up_view.setOnClickListener(v -> sing_up_rl.setVisibility(View.GONE));
        cancel_btn.setOnClickListener(v -> sing_up_rl.setVisibility(View.GONE));
        commit_btn.setOnClickListener(v -> {
            ToastUtil.show(this,"",ToastUtil.SHOW_TOAST);
        });
        getNeedDetails();
    }

    private void getNeedDetails(){
        Map<String,String> parameter = new HashMap<>();
        parameter.put("need_id",need_id);
        new AsyncHttpPost<NeedDetails>(this, MYAppconfig.NEED_NEEDINFO,parameter,
                MYTaskID.NEED_NEEDINFO_ID,NeedDetails.class,this);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {
    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        if(resultCode == MYTaskID.NEED_NEEDINFO_ID){
            try {
                NeedDetails needDetails = (NeedDetails) modelClass;
                if("200".equals(needDetails.getCode())){
                    setDetailsData(needDetails);
                    if(null != needDetails.data.reply_list && needDetails.data.reply_list.size() > 0){
                        reply_list.clear();
                        reply_list.addAll(needDetails.data.reply_list);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtil.show(this,"数据请求失败",ToastUtil.SHOW_TOAST);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setDetailsData(NeedDetails needDetails) {
        Glide.with(this).load(needDetails.data.getUser_avar()).into(logo_image);
        user_name.setText(needDetails.data.getUser_name());
        title_text.setText(needDetails.data.getNeed_title());
        price_text.setText(needDetails.data.getPay_price());
        price_company.setText("/"+needDetails.data.getPay_unit());
        demand_time.setText("时间："+needDetails.data.getNeed_time());
        demand_adr.setText("地点："+needDetails.data.getAddress());
        String need_sex = "男";
        switch (needDetails.data.getNeed_sex()){
            case "0":
                need_sex = "男";
                break;
            case "1":
                need_sex = "女";
                break;
            case "2":
                need_sex = "男女不限";
                break;
        }
        demand_sex.setText("性别要求："+need_sex);
        introduction_text.setText("性别要求："+ needDetails.data.getNeed_content());
        places_text.setText(needDetails.data.getNeed_man());
        content_comment_text.setText(needDetails.data.getNeed_content());
        String sex = "男";
        switch (needDetails.data.getSex()){
            case "0":
                sex = "男";
                break;
            case "1":
                sex = "女";
                break;
        }
        sex_text.setText(sex + "  "+needDetails.data.getArea() +"  "+ needDetails.data.getAge());
        post_comment_text.setText("0".equals(needDetails.data.getIs_report()) ? "举报" : "已举报");
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
    }

    class DetailsAdapter extends BaseAdapter{

        public DetailsAdapter(){

        }

        @Override
        public int getCount() {
            return reply_list.size();
        }

        @Override
        public Object getItem(int i) {
            return reply_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            NeedDetails.Data.Reply_list reply_list = (NeedDetails.Data.Reply_list) getItem(i);
            if(null == view){
                view = LayoutInflater.from(context).inflate(R.layout.home_details_item_layout,null);
            }
            ImageView user_icon = ViewHolder.get(view,R.id.user_icon);
            TextView message_user_name = ViewHolder.get(view,R.id.message_user_name);
            TextView reply_content_text = ViewHolder.get(view,R.id.reply_content_text);
            message_user_name.setText(reply_list.getUser_name());
            reply_content_text.setText(reply_list.getContent());
            Glide.with(HomeDetailsActivity.this).load(reply_list.getUser_avar()).into(user_icon);
            return view;
        }
    }
}
