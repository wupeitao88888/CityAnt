package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.BrandList;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;


import java.util.List;

/**
 * Created by wupeitao on 2017/2/10.
 */

public class PopBrandListAdatpter extends com.iloomo.base.CommonAdapter {


    public PopBrandListAdatpter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BrandList brandList = (BrandList) mDatas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_popbrandlist, null);
        }
        TextView item = ViewHolder.get(convertView, R.id.item);
        StrUtil.setText(item, brandList.getBrand_name());
        return convertView;
    }
}
