package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.Created;
import com.cityant.main.bean.MeCrows;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 16/8/28.
 */
public class CreateAdapter extends CommonAdapter {

    public CreateAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Created meCrows=(Created)mDatas.get(i);
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_created,null);
        }

        return convertView;
    }
}
