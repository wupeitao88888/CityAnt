package com.cityant.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.MeCrows;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 16/8/26.
 */
public class MeCrowdAdapter extends CommonAdapter {
    public MeCrowdAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MeCrows meCrows=(MeCrows)mDatas.get(i);
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_mecrowd,null);
        }
        TextView crowd=(TextView)ViewHolder.get(convertView,R.id.crowd);
        StrUtil.setText(crowd,meCrows.getGroup_name());
        return convertView;
    }
}
