package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.IncomeList;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 2016/10/23.
 */

public class ToadyReseiveAdapter extends CommonAdapter {
    public ToadyReseiveAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IncomeList incomeList = (IncomeList) mDatas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_toadyreseive, null);
        }
        TextView type = (TextView) ViewHolder.get(convertView, R.id.type);
        TextView price = (TextView) ViewHolder.get(convertView, R.id.price);


        if ("0".equals(incomeList.getType())) {
            StrUtil.setText(type, "蚂蚁仓库");
        } else if ("1".equals(incomeList.getType())) {
            StrUtil.setText(type, "蚂蚁豆");
        } else if ("3".equals(incomeList.getType())) {
            StrUtil.setText(type, "工资");
        } else if ("4".equals(incomeList.getType())) {
            StrUtil.setText(type, "兼职");
        } else if ("9".equals(incomeList.getType())) {
            StrUtil.setText(type, "取消友情购");
        }
        StrUtil.setText(type, incomeList.getType());
        StrUtil.setText(price, incomeList.getPrice());
        return convertView;
    }

}
