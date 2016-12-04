package com.cityant.main.adapter.konck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
* 领工资adapter
* @author lvfl
* @time 2016/11/28 22:44
*/
public class TheWageAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    public TheWageAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(null == view){
            view = LayoutInflater.from(context).inflate(R.layout.the_wage_item_layout,null);
        }
        TextView rangking_text = ViewHolder.get(view, R.id.rangking_text);
        ImageView user_image = ViewHolder.get(view, R.id.user_image);
        TextView user_name = ViewHolder.get(view, R.id.user_name);
        ImageView mark_image = ViewHolder.get(view, R.id.mark_image);
        TextView mark_text = ViewHolder.get(view, R.id.mark_text);
        TextView the_wage_text = ViewHolder.get(view, R.id.the_wage_text);
        switch (i){
            case 0:
                rangking_text.setBackgroundResource(R.drawable.money_one);
                rangking_text.setText("");
                mark_image.setBackgroundResource(R.drawable.money_one_start);
                the_wage_text.setEnabled(true);
                the_wage_text.setVisibility(View.VISIBLE);
                break;
            case 1:
                rangking_text.setBackgroundResource(R.drawable.money_two);
                mark_image.setBackgroundResource(R.drawable.money_two_three);
                rangking_text.setText("");
                the_wage_text.setVisibility(View.VISIBLE);
                the_wage_text.setEnabled(false);
                the_wage_text.setSelected(false);
                break;
            case 2:
                rangking_text.setBackgroundResource(R.drawable.money_three);
                rangking_text.setText("");
                mark_image.setBackgroundResource(R.drawable.money_two_three);
                the_wage_text.setSelected(true);
                the_wage_text.setVisibility(View.VISIBLE);
                break;
            default:
                rangking_text.setText(i+"");
                mark_image.setBackgroundResource(R.drawable.money_two_three);
                the_wage_text.setVisibility(View.GONE);
                break;
        }
        return view;
    }
}
