package com.cityant.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.Address;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 2017/3/3.
 */

public class AddressAdapter extends CommonAdapter {


    public AddressAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_address, null);
        }
        TextView ad_username = ViewHolder.get(convertView, R.id.ad_username);
        TextView ad_phone = ViewHolder.get(convertView, R.id.ad_phone);
        TextView ad_address = ViewHolder.get(convertView, R.id.ad_address);
        RelativeLayout re_address = ViewHolder.get(convertView, R.id.re_address);
        Address address = (Address) mDatas.get(position);

        StrUtil.setText(ad_username, address.getName());
        StrUtil.setText(ad_phone, address.getMobile());
        StrUtil.setText(ad_address, address.getAddress());

        return convertView;
    }
}
