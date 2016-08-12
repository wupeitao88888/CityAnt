package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.cityant.main.R;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by lvfl on 2016/8/10.
 */
public class LocationChoiceAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    public LocationChoiceAdapter(List<String> list,Context context){
        this.list = list;
        this.context = context;
    }
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
            view = LayoutInflater.from(context).inflate(R.layout.location_choic_item,null);

        }
        TextView location_text = ViewHolder.get(view,R.id.location_text);
        GridView id_gridView = ViewHolder.get(view,R.id.id_gridView);
        GridViewAdapter adapter = new GridViewAdapter(list);
        id_gridView.setAdapter(adapter);

        return view;
    }

    class GridViewAdapter extends BaseAdapter{

        private List<String> list_grid;
        public GridViewAdapter(List<String> list_grid){
            this.list_grid = list_grid;
        }
        @Override
        public int getCount() {
            return list_grid.size();
        }

        @Override
        public Object getItem(int i) {
            return list_grid.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            String string = (String) getItem(i);
            if(null == view){
                view = LayoutInflater.from(context).inflate(R.layout.location_choice_city_item,null);
            }
            TextView city_text = ViewHolder.get(view,R.id.city_text);

            return view;
        }
    }

}
