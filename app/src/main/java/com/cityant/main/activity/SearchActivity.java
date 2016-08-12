package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;
import com.iloomo.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfl on 2016/8/11.
 */
public class SearchActivity extends ActivitySupport {

    private GridView search_gridView;
    private List<String> list = new ArrayList<>();

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
        search_gridView = (GridView) findViewById(R.id.search_gridView);
        for(int i = 0; i < 10; i++){
            list.add(i+"");
        }
        SearchGridAdapter adapter = new SearchGridAdapter();
        search_gridView.setAdapter(adapter);
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
