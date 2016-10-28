package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.GoodsList;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;
import com.iloomo.utils.ViewUtil;

import java.util.List;

/**
 * Created by wupeitao on 2016/10/21.
 */

public class StoreHouseAdapter extends CommonAdapter {
    public StoreHouseAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsList goodsList = (GoodsList) mDatas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_storehouseadapter, null);
        }
        ImageView goods_image = (ImageView) ViewHolder.get(convertView, R.id.goods_image);
        TextView price_text = (TextView) ViewHolder.get(convertView, R.id.price_text);
        TextView goods_title = (TextView) ViewHolder.get(convertView, R.id.goods_title);
        TextView need_num_text = (TextView) ViewHolder.get(convertView, R.id.need_num_text);

        PImageLoaderUtils.getInstance().displaySmallpix(goodsList.getGoods_img(), goods_image, context);
        StrUtil.setText(price_text, "￥" + goodsList.getGoods_price());
        StrUtil.setText(goods_title, goodsList.getGoods_title());
        StrUtil.setText(need_num_text, "X" + goodsList.getNumber());
        return convertView;
    }
}
