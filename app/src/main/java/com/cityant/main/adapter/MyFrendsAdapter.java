package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.hyphenate.easeui.bean.MyFrends;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 16/8/19.
 */
public class MyFrendsAdapter extends CommonAdapter {

    public MyFrendsAdapter(Context context, List<MyFrends> mDatas) {
        super(context, mDatas);
    }

    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        if (((MyFrends)mDatas.get(position)).getUser_name().length() == 1)// 如果是字母索引
            return false;// 表示不能点击
        return super.isEnabled(position);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MyFrends classList = (MyFrends) mDatas.get(i);
        if (classList.getUser_name().length() == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_index,
                    null);
            TextView username = ViewHolder.get(view, R.id.indexTv);
            username.setText(classList.getUser_name());
        } else {
            view = LayoutInflater.from(context).inflate(
                    R.layout.adapter_myfrends, null);
            ImageView hand = ViewHolder.get(view, R.id.hand);
            PImageLoaderUtils.getInstance().displayuserHand(classList.getUser_avar(), hand, context);
            TextView username = ViewHolder.get(view, R.id.username);
            StrUtil.setText(username,classList.getUser_name());
        }
        return view;
    }
}
