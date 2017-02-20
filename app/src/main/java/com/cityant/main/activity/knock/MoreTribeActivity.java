package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.adapter.base.CommonAdapter;
import com.cityant.main.adapter.base.ViewHolder;
import com.cityant.main.base.BaseToolbarActivity;
import com.cityant.main.bean.BrandList;
import com.cityant.main.bean.SearchTribeBean;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
* 更多部落
* @author Lvfl
* created at 2017/2/15 10:54
*/

public class MoreTribeActivity extends BaseToolbarActivity implements ThreadCallBack{

    @Bind(R.id.more_listView)
    protected ListView more_listView;
    @Bind(R.id.back_image)
    protected ImageView back_image;
    @Bind(R.id.tribe_edit)
    protected EditText tribe_edit;
    @Bind(R.id.search_tribe)
    protected TextView search_tribe;
    private List<BrandList> tribe_list = new ArrayList<>();
    private CommonAdapter adapter;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,MoreTribeActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_tribe_layout;
    }

    private void initToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationOnClickListener(v -> MoreTribeActivity.this.finish());
        ActionBarHelper helper = createActionBarHelper();
        helper.init();
        helper.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbar();
        adapter = new CommonAdapter(this,R.layout.search_tribe_item_layout,tribe_list){

            @Override
            protected void convert(ViewHolder viewHolder, Object item, int position) {
                viewHolder.setCircleImageUrl(R.id.tribe_image,tribe_list.get(position).getBrand_img());
                viewHolder.setText(R.id.tribe_name,tribe_list.get(position).getBrand_name());
            }
        };

        more_listView.setAdapter(adapter);
        more_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TribeDetailsActivity.startActivity(MoreTribeActivity.this,"屈臣氏",tribe_list.get(position).getBrand_id());
            }
        });
    }

    @Override
    protected void initListeners() {
        search_tribe.setOnClickListener(v -> {
            if (TextUtils.isEmpty(tribe_edit.getText().toString())){
                ToastUtil.show(this,"请输入您要搜索的品牌",ToastUtil.SHOW_TOAST);
                return;
            }
            Map<String, String> parameter = new HashMap<>();
            parameter.put("keyword",tribe_edit.getText().toString());
            new AsyncHttpPost(MoreTribeActivity.this, MYAppconfig.TRIBE_BRAND_INDEX, parameter, MYTaskID.TRIBE_BRAND_INDEX_ID,
                    SearchTribeBean.class, this);
        });
        back_image.setOnClickListener(v -> {
            this.finish();
        });
    }

    @Override
    protected void initData() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("keyword",tribe_edit.getText().toString());
        new AsyncHttpPost(MoreTribeActivity.this, MYAppconfig.TRIBE_BRAND_INDEX, parameter, MYTaskID.TRIBE_BRAND_INDEX_ID,
                SearchTribeBean.class, this);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        if (resultCode == MYTaskID.TRIBE_BRAND_INDEX_ID){
            try {
                SearchTribeBean searchTribeBean = (SearchTribeBean) modelClass;
                if ("200".equals(searchTribeBean.getCode())){
                    if (null != searchTribeBean && searchTribeBean.data.brand_list.size()>0){
                        tribe_list.addAll(searchTribeBean.data.brand_list);
                    }else{
                        ToastUtil.show(this,"没有您要搜索的部落",ToastUtil.SHOW_TOAST);
                    }
                } else {
                    ToastUtil.show(this,searchTribeBean.data.getCode_message(),ToastUtil.SHOW_TOAST);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtil.show(this,"没有您要搜索的部落",ToastUtil.SHOW_TOAST);
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
