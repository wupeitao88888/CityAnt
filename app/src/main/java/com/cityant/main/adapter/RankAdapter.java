package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.EvaluateList;
import com.cityant.main.bean.IncomeList;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;
import com.iloomo.widget.CostomRatingBar;

import java.util.List;

/**
 * Created by wupeitao on 2016/10/23.
 */

public class RankAdapter extends CommonAdapter {
    public RankAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EvaluateList incomeList = (EvaluateList) mDatas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_rank, null);
        }

        ImageView user_avar = ViewHolder.get(convertView, R.id.user_avar);
        TextView user_name = ViewHolder.get(convertView, R.id.user_name);
        TextView add_time = ViewHolder.get(convertView, R.id.add_time);
        CostomRatingBar overall_rating = ViewHolder.get(convertView, R.id.overall_rating);

        PImageLoaderUtils.getInstance().setImageHead(user_avar, incomeList.getUser_avar(), context);
        StrUtil.setText(user_name, incomeList.getUser_name());
        StrUtil.setText(add_time, incomeList.getAdd_time());
        overall_rating.setMark(Float.parseFloat(incomeList.getRating()));
        return convertView;
    }

}
