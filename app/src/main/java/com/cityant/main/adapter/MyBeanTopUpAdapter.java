package com.cityant.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.GoodsList;
import com.cityant.main.bean.MYBeanSell;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.L;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 2016/10/21.
 */

public class MyBeanTopUpAdapter extends CommonAdapter {
    public MyBeanTopUpAdapter(Context context, List mDatas) {
        super(context, mDatas);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MYBeanSell goodsList = (MYBeanSell) mDatas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_mybeantopup, null);
        }
        TextView bean_count = ViewHolder.get(convertView, R.id.bean_count);
        TextView bean_price = ViewHolder.get(convertView, R.id.bean_price);

        StrUtil.setText(bean_count, goodsList.getPar());
        StrUtil.setText(bean_price, "￥" + goodsList.getPrice());
        return convertView;
    }

}
