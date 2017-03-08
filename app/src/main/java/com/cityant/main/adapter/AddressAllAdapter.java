package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.AddressList;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 2017/3/6.
 */

public class AddressAllAdapter extends CommonAdapter {
    public AddressAllAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressList addressList = (AddressList) mDatas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_addressall, null);
        }
        TextView name = ViewHolder.get(convertView, R.id.name);
        StrUtil.setText(name, addressList.getName());
        return convertView;
    }
}
