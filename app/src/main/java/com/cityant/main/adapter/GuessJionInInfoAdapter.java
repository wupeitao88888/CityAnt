package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cityant.main.R;
import com.iloomo.base.CommonAdapter;

import java.util.List;

/**
 * Created by wupeitao on 16/9/3.
 */
public class GuessJionInInfoAdapter extends CommonAdapter {
    public GuessJionInInfoAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        CreateSmallGreabType meCrows = (CreateSmallGreabType) mDatas.get(i);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_guessjionininfoadapter, null);
        }

//        TextView name = ViewHolder.get(convertView, R.id.name);
//        RelativeLayout onIntem = ViewHolder.get(convertView, R.id.onIntem);
//        StrUtil.setText(name, meCrows.getName());
//        onIntem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent(context, CreateSmallGrabTypeChooseActivity.class).putExtra("type", meCrows.getValue()));
//            }
//        });

        return convertView;
    }
}
