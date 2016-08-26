package com.cityant.main.fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.cityant.main.R;
import com.cityant.main.activity.DoTaskActivity;
import com.cityant.main.activity.HomeDetailsActivity;
import com.cityant.main.activity.LocationChoiceActivity;
import com.cityant.main.activity.SearchActivity;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.cityant.main.bean.HomeBean;
import com.cityant.main.db.DBControl;
import com.cityant.main.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.FragmentSupport;
import com.iloomo.bean.BaseModel;
import com.iloomo.global.MApplication;
import com.iloomo.model.ApplicationLocationListener;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.DialogUtil;
import com.iloomo.widget.imgscroll.MyImgScroll;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentHome extends FragmentSupport implements AbsListView.OnScrollListener, View.OnClickListener, ThreadCallBack {

    private TextView position_text;
    private EditText search_edit;
    private LinearLayout sing_ll;
    private LinearLayout sing_display_ll;
    private ListView listView;
    private List<String> list = new ArrayList<>();
    private boolean isShow_title_tab;
    private LinearLayout home_top_ll;
    private TextView recommend_text_head;
    private TextView nearby_text_head;
    private TextView city_text_head;
    private TextView friends_text_head;
    private TextView recommend_text;
    private TextView nearby_text;
    private TextView city_text;
    private TextView friends_text;
    private String token = "";
    private MyImgScroll banner_scroll;
    private LinearLayout vb;

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        final View view = LayoutInflater.from(context).inflate(R.layout.fragment_home, null);
        setTitle("主页");
        listView = (ListView) view.findViewById(R.id.listView);
        home_top_ll = (LinearLayout) view.findViewById(R.id.home_top_ll);
        recommend_text = (TextView) view.findViewById(R.id.recommend_text);
        nearby_text = (TextView) view.findViewById(R.id.nearby_text);
        city_text = (TextView) view.findViewById(R.id.city_text);
        friends_text = (TextView) view.findViewById(R.id.friends_text);

        View head_view = LayoutInflater.from(context).inflate(R.layout.fragment_home_head_layout, null);
        banner_scroll = (MyImgScroll) head_view.findViewById(R.id.banner_scroll);
        vb = (LinearLayout) head_view.findViewById(R.id.vb);
        position_text = (TextView) head_view.findViewById(R.id.position_text);
        search_edit = (EditText) head_view.findViewById(R.id.search_edit);
        position_text.setOnClickListener(v -> LocationChoiceActivity.startActivity(context));
        search_edit.setOnClickListener(v -> SearchActivity.startActivity(context));
//        sing_ll.setOnClickListener(v -> DoTaskActivity.startActivity(context));
//        sing_display_ll.setOnClickListener(v -> DoTaskActivity.startActivity(context));

        View head_view_top = LayoutInflater.from(context).inflate(R.layout.fragment_home_head_top_layout, null);
        recommend_text_head = (TextView) head_view_top.findViewById(R.id.recommend_text_head);
        nearby_text_head = (TextView) head_view_top.findViewById(R.id.nearby_text_head);
        city_text_head = (TextView) head_view_top.findViewById(R.id.city_text_head);
        friends_text_head = (TextView) head_view_top.findViewById(R.id.friends_text_head);

        recommend_text_head.setOnClickListener(this);
        nearby_text_head.setOnClickListener(this);
        city_text_head.setOnClickListener(this);
        friends_text_head.setOnClickListener(this);
        recommend_text.setOnClickListener(this);
        nearby_text.setOnClickListener(this);
        city_text.setOnClickListener(this);
        friends_text.setOnClickListener(this);

        listView.addHeaderView(head_view);
        listView.addHeaderView(head_view_top);
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        FragmentHomeAdapter adapter = new FragmentHomeAdapter(context, list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeDetailsActivity.startActivity(context);
            }
        });
        token = MYAppconfig.loginUserInfoData.getToken();
        sendInternet();
        return view;
    }

    /**
     * 获取定位结果
     */
    private void getLocationResult() {
        MApplication.getInstance().setOnReceiveLocation(new ApplicationLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation var1) {
            }

            @Override
            public void fild() {
            }
        });
    }

    private void sendInternet() {
        DialogUtil.startDialogLoading(context);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("page", "1");
        parameter.put("page_size", "15");
        parameter.put("city_id", "");
        parameter.put("latitude", MApplication.getInstance().latitude + "");
        parameter.put("longitude", MApplication.getInstance().longitude + "");
        parameter.put("token", token);
        parameter.put("type", "0"); // type   类别(0:推荐,1:附近,2:同城,3:好友)

        AsyncHttpPost httpRequest;
        startHttpRequst("POST", MYAppconfig.HOME_INDEX, parameter
                , MYTaskID.HOME_INDEX);
    }

    public void startHttpRequst(String requestType, String url,
                                Map<String, Object> parameter, int resultCode) {

        AsyncHttpPost httpRequest;
        httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
                HomeBean.class, context);


    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int i1, int i2) {
        if (firstVisibleItem > 0 && !isShow_title_tab) {
            isShow_title_tab = true;
            home_top_ll.setVisibility(View.VISIBLE);
        } else if (firstVisibleItem < 1 && isShow_title_tab) {
            isShow_title_tab = false;
            home_top_ll.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_text_head:
            case R.id.recommend_text:
                setSelectTool();
                recommend_text_head.setSelected(true);
                recommend_text.setSelected(true);
                break;
            case R.id.nearby_text_head:
            case R.id.nearby_text:
                setSelectTool();
                nearby_text_head.setSelected(true);
                nearby_text.setSelected(true);
                break;
            case R.id.city_text_head:
            case R.id.city_text:
                setSelectTool();
                city_text_head.setSelected(true);
                city_text.setSelected(true);
                break;
            case R.id.friends_text_head:
            case R.id.friends_text:
                setSelectTool();
                friends_text_head.setSelected(true);
                friends_text.setSelected(true);
                break;
        }
    }

    private void setSelectTool() {
        if (recommend_text_head.isSelected()) {
            recommend_text_head.setSelected(false);
        }
        if (recommend_text.isSelected()) {
            recommend_text.setSelected(false);
        }
        if (nearby_text_head.isSelected()) {
            nearby_text_head.setSelected(false);
        }
        if (nearby_text.isSelected()) {
            nearby_text.setSelected(false);
        }
        if (city_text_head.isSelected()) {
            city_text_head.setSelected(false);
        }
        if (city_text.isSelected()) {
            city_text.setSelected(false);
        }
        if (friends_text_head.isSelected()) {
            friends_text_head.setSelected(false);
        }
        if (friends_text.isSelected()) {
            friends_text.setSelected(false);
        }
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {
        Log.e("---成功--", resultJson);
        HomeBean homeBean = (HomeBean) modelClass;
        setBanner_scroll(homeBean.data.banner_list);
        // TODO
    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        DialogUtil.stopDialogLoading(context);
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {
        Log.e("--失败---", resultJson);
    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        DialogUtil.stopDialogLoading(context);
        Log.e("--失败---", resultJson);
    }

    private void setBanner_scroll(List<HomeBean.HomeData.BannerList> banner_list) {
        List<View> listViews = new ArrayList<View>();
        for (int i = 0; i < banner_list.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {// 设置图片点击事件

                }
            });
            Glide.with(context).load(list.get(i)).into(imageView);
//            setImage(imageView, lista.get(i).getCover(), context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            listViews.add(imageView);
        }
        banner_scroll.stopTimer();
        //开始滚动
        banner_scroll.start(getActivity(), listViews, 4000, vb,
                R.layout.ad_bottom_item, R.id.ad_item_v,
                R.mipmap.ic_launcher, R.mipmap.icon_1_d);
    }
}
