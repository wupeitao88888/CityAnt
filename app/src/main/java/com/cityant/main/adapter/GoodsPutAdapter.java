package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.GoodsList;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.BigDecimalUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;
import com.iloomo.widget.AmountView;

import java.util.List;

/**
 * Created by wupeitao on 2016/10/21.
 */

public class GoodsPutAdapter extends CommonAdapter {
    public GoodsPutAdapter(Context context, List mDatas) {
        super(context, mDatas);
        if (countChangeLienster != null)
            countChangeLienster.change(mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsList goodsList = (GoodsList) mDatas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_goodsputadapter, null);
        }
        ImageView goods_image = (ImageView) ViewHolder.get(convertView, R.id.goods_image);
        TextView price_text = (TextView) ViewHolder.get(convertView, R.id.price_text);
        TextView goods_title = (TextView) ViewHolder.get(convertView, R.id.goods_title);
        TextView need_num_text = (TextView) ViewHolder.get(convertView, R.id.need_num_text);
        Button btnDecrease = (Button) ViewHolder.get(convertView, R.id.btnDecrease);
        Button btnIncrease = (Button) ViewHolder.get(convertView, R.id.btnIncrease);
        TextView etAmount = (TextView) ViewHolder.get(convertView, R.id.etAmount);

        PImageLoaderUtils.getInstance().displaySmallpix(goodsList.getGoods_img(), goods_image, context);
        StrUtil.setText(price_text, "￥" + goodsList.getGoods_price());
        StrUtil.setText(goods_title, goodsList.getGoods_title());
        StrUtil.setText(need_num_text, "X" + goodsList.getNumber());

        etAmount.setText(goodsList.getOutnumber() + "");
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int sub = goodsList.getOutnumber();
                sub = sub - 1;
                if (sub <= 0) {
                    mDatas.remove(position);
                    notifyDataSetChanged();
                } else {
                    goodsList.setOutnumber(sub);
                    etAmount.setText(sub + "");
                }
                if (countChangeLienster != null)
                    countChangeLienster.change(mDatas);
            }
        });
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int add = goodsList.getOutnumber();
                add = add + 1;
                if (add > Integer.parseInt(goodsList.getNumber())) {
                    add = Integer.parseInt(goodsList.getNumber());
                }
                goodsList.setOutnumber(add);
                etAmount.setText(add + "");
                if (countChangeLienster != null)
                    countChangeLienster.change(mDatas);
            }
        });
        return convertView;
    }


    public String getAGoodsOut() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mDatas.size(); i++) {
            GoodsList goodsList = (GoodsList) mDatas.get(i);
            sb.append(goodsList.getGoods_id() + "|" + goodsList.getOutnumber());

            if (i < mDatas.size() - 1) {
                sb.append(",");
            }
        }
        L.e(sb.toString());
        return sb.toString();
    }

    public CountChangeLienster countChangeLienster;

    public void setCountChangeLienster(CountChangeLienster countChangeLienster) {
        this.countChangeLienster = countChangeLienster;
    }

    public interface CountChangeLienster {
        void change(List mDatas);
    }


}
