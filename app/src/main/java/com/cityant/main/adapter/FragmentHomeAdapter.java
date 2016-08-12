package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cityant.main.R;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.ViewHolder;



import java.util.List;

/**
 * Created by wupeitao on 16/4/14.
 */
public class FragmentHomeAdapter extends CommonAdapter {

    public FragmentHomeAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.layout_fragmenthome_adapter, null);
        }
        TextView view = ViewHolder.get(convertView, R.id.content);

        view.setText(mDatas.get(position) + "");
        return convertView;
    }
}
