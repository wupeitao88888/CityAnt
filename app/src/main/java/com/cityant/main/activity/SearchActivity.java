package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.HomeBean;
import com.cityant.main.bean.HomeSearch;
import com.cityant.main.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.global.MApplication;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.securitycode.SecurityCodeUtils;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvfl on 2016/8/11.
 */
public class SearchActivity extends ActivitySupport implements ThreadCallBack {

    private GridView search_gridView;
    private List<String> list = new ArrayList<>();
    private EditText search_edit;

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
        search_gridView = (GridView) findViewById(R.id.search_gridView);
        for(int i = 0; i < 10; i++){
            list.add(i+"");
        }
        SearchGridAdapter adapter = new SearchGridAdapter();
        search_gridView.setAdapter(adapter);
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

        AsyncHttpPost httpRequest;
        startHttpRequst("POST", MYAppconfig.HOME_SEARCH, parameter
                , MYTaskID.HOMESEARCH);
    }

    private void startHttpRequst(String requestType, String url,
                                Map<String, Object> parameter, int resultCode) {
        AsyncHttpPost httpRequest;
        httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
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


    class SearchGridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            String string = (String) getItem(i);
            if(null == view){
                view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_grid_item_layout,null);
            }
            TextView search_text = ViewHolder.get(view,R.id.search_text);
            search_text.setText(string);
            return view;
        }
    }


}
