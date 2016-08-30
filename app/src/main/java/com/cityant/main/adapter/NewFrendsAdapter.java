package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.MeCrows;
import com.cityant.main.bean.NewFrends;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 16/8/26.
 */
public class NewFrendsAdapter extends CommonAdapter {
    public NewFrendsAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        NewFrends meCrows=(NewFrends)mDatas.get(i);
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_newfrends,null);
        }
        ImageView newfrendshead=(ImageView) ViewHolder.get(convertView,R.id.newfrendshead);
        TextView newfrendsname=(TextView) ViewHolder.get(convertView,R.id.newfrendsname);
        StrUtil.setText(newfrendsname,meCrows.getUser_name());
        PImageLoaderUtils.displayuserHand(meCrows.getUser_avar(),newfrendshead,context);
        return convertView;
    }
}
