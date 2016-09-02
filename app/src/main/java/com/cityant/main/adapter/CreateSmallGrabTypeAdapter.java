package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.CreateSmallGreabType;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 16/9/3.
 */
public class CreateSmallGrabTypeAdapter extends CommonAdapter {
    public CreateSmallGrabTypeAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        CreateSmallGreabType meCrows=(CreateSmallGreabType)mDatas.get(i);
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_createsmallgrabtype,null);
        }

        TextView name=ViewHolder.get(convertView,R.id.name);
        RelativeLayout onIntem=ViewHolder.get(convertView,R.id.onIntem);
        StrUtil.setText(name,meCrows.getName());
        onIntem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }
}
