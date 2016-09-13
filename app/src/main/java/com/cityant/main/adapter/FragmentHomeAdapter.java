package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cityant.main.R;
import com.cityant.main.bean.HomeBean;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.ViewHolder;



import java.util.List;

/**
 * Created by wupeitao on 16/4/14.
 */
public class FragmentHomeAdapter extends CommonAdapter {

    public FragmentHomeAdapter(Context context, List<HomeBean.HomeData.NeedList> mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeBean.HomeData.NeedList needList = (HomeBean.HomeData.NeedList) getItem(position);
        if (convertView == null) {
            if(true){
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.layout_fragmenthome_adapter, null);
            }else{
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.layout_fragmenthome_adapter2, null);
            }
        }

        ImageView logo_image = ViewHolder.get(convertView, R.id.logo_image);
        TextView user_name = ViewHolder.get(convertView, R.id.user_name);
        TextView sex_text = ViewHolder.get(convertView, R.id.sex_text);
        TextView auth_text = ViewHolder.get(convertView, R.id.auth_text);
        TextView places_text = ViewHolder.get(convertView, R.id.places_text);
        TextView price_text = ViewHolder.get(convertView, R.id.price_text);
        TextView title_text = ViewHolder.get(convertView, R.id.title_text);
        TextView introduction_text = ViewHolder.get(convertView, R.id.introduction_text);
        TextView source_text = ViewHolder.get(convertView, R.id.source_text);


        Glide.with(context).load(needList.getUser_avar()).centerCrop().into(logo_image);
        user_name.setText(needList.getUser_name());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("0".equals(needList.getSex()) ? " 男  " :" 女  ");
        stringBuffer.append(needList.getAddress()+"  ");
        stringBuffer.append(needList.getAge()+"岁");
        sex_text.setText(stringBuffer.toString());
        auth_text.setVisibility("1".equals(needList.is_true) ? View.VISIBLE : View.GONE);
        places_text.setText(needList.getNeed_man());
        price_text.setText(needList.getPay_price());
        title_text.setText(needList.getNeed_title());
        introduction_text.setText(needList.getNeed_content());
        source_text.setText("0".equals(needList.getNeed_way()) ? "线上" : "线下");
        return convertView;
    }
}
