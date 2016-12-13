package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.cityant.main.R;
import com.cityant.main.bean.HomeSearch;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.DialogUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 搜索页面
* @author Lvfl
* created at 2016/12/12 14:16
*/
public class SearchActivity extends ActivitySupport implements ThreadCallBack {

    private List<String> list = new ArrayList<>();
    private EditText search_edit;
    private TagAdapter tagAdapter;
    private TagFlowLayout rg_trainer_field;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,SearchActivity.class);
        context.startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        setRemoveTitle();
        initView();
    }

    private void initView() {
        search_edit = (EditText) findViewById(R.id.search_edit);
        rg_trainer_field = (TagFlowLayout) findViewById(R.id.rg_trainer_field);
        for(int i = 0; i < 10; i++){
            list.add(i+"");
        }
        tagAdapter = new TagAdapter(list) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_grid_item_layout, null);
                TextView field_name_text = (TextView) view.findViewById(R.id.search_text);
                field_name_text.setOnClickListener(v -> {
                    searchInternet();
                });
                return view;
            }
        };
        rg_trainer_field.setAdapter(tagAdapter);
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    ((InputMethodManager) search_edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchInternet();
                    return true;
                }
                return false;
            }
        });
    }

    private void searchInternet(){
        DialogUtil.startDialogLoading(context);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("page", "1");
        parameter.put("page_size", "15");
        parameter.put("keyword", "手机");
        parameter.put("tag_id", "0");

        startHttpRequst("POST", MYAppconfig.HOME_SEARCH, parameter
                , MYTaskID.HOMESEARCH);
    }

    private void startHttpRequst(String requestType, String url,
                                Map<String, Object> parameter, int resultCode) {
        new AsyncHttpPost(this, url, parameter, resultCode,
                HomeSearch.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {

    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

    }
}
