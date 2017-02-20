package com.cityant.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.activity.CreateSmallGrabActivity;
import com.cityant.main.activity.CreateSmallGrabTypeChooseActivity;
import com.cityant.main.bean.CreateSmallGreabType;
import com.cityant.main.bean.GoodsList;
import com.cityant.main.bean.MGoods;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 16/9/3.
 */
public class CreateSmallGrabTypeChooseAdapter extends CommonAdapter {
    public CreateSmallGrabTypeChooseAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        GoodsList meCrows = (GoodsList) mDatas.get(i);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_createsmallgrabtypechoose, null);
        }


        ImageView type_img = ViewHolder.get(convertView, R.id.type_img);
        ImageView type_img_ed = ViewHolder.get(convertView, R.id.type_img_ed);


        TextView goods_info = ViewHolder.get(convertView, R.id.goods_info);
        TextView goods_price = ViewHolder.get(convertView, R.id.goods_price);
        TextView goods_specifications = ViewHolder.get(convertView, R.id.goods_specifications);
        TextView goods_all_pople = ViewHolder.get(convertView, R.id.goods_all_pople);
        Button create = ViewHolder.get(convertView, R.id.create);

        StrUtil.setText(goods_info, meCrows.getGoods_title());
        StrUtil.setText(goods_price, "￥" + meCrows.getRob_price());
//        StrUtil.setText(goods_specifications,"￥"+meCrows.getRob_price());
        StrUtil.setText(goods_all_pople, "需要人数：" + meCrows.getNeed_man());

        if ("2".equals(meCrows.getState())) {
            create.setEnabled(false);
            type_img_ed.setVisibility(View.VISIBLE);
        } else {
            create.setEnabled(true);
            type_img_ed.setVisibility(View.INVISIBLE);
        }
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < CreateSmallGrabTypeChooseActivity.aList.size(); i++) {
                    CreateSmallGrabTypeChooseActivity.aList.get(i).finish();
                }
                context.startActivity(new Intent(context,CreateSmallGrabActivity.class).putExtra("GoodsList",meCrows));
            }
        });
        return convertView;
    }
}
