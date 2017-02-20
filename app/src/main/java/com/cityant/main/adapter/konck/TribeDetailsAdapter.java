package com.cityant.main.adapter.konck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cityant.main.R;
import com.cityant.main.bean.konck.TribeDetailsBean;

import java.util.List;

/**
* 品牌部落adapter
* @author lvfl
* @time 2016/11/28 22:44
*/
public class TribeDetailsAdapter extends BaseAdapter {

    private Context context;
    private List<TribeDetailsBean.Data.GoodsList> list;
    public TribeDetailsAdapter(Context context, List<TribeDetailsBean.Data.GoodsList> goods_list){
        this.context = context;
        this.list = list;
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
        if(null == view){
            view = LayoutInflater.from(context).inflate(R.layout.tribe_details_item_layout,null);
        }
        return view;
    }
}
