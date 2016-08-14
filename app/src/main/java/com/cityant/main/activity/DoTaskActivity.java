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
import android.widget.TextView;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;
import com.iloomo.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 做任务页面
 * Created by lvfl on 2016/8/11.
 */
public class DoTaskActivity extends ActivitySupport {

    private ListView listView;
    private List<String> list = new ArrayList<>();

    public static void startActivity(Context context){
        Intent intent = new Intent(context,DoTaskActivity.class);
        context.startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_task_layout);
        setCtenterTitle("做任务");
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        for(int i = 0; i < 10; i++){
            list.add(i+"");
        }
        TaskAdapter adapter = new TaskAdapter();
        listView.setAdapter(adapter);
    }

    class TaskAdapter extends BaseAdapter{

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
                view = LayoutInflater.from(context).inflate(R.layout.do_task_item_layout,null);
            }
            ImageView application_icon = ViewHolder.get(view,R.id.application_icon);
            TextView application_name = ViewHolder.get(view,R.id.application_name);
            TextView application_brief = ViewHolder.get(view,R.id.application_brief);
            TextView ant_dou = ViewHolder.get(view,R.id.ant_dou);
            TextView download_text = ViewHolder.get(view,R.id.download_text);

            return view;
        }
    }
}
