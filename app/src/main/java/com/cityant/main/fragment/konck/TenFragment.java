package com.cityant.main.fragment.konck;

import android.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.KonckAdapter;
import com.iloomo.base.FragmentSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfl on 2016/8/30.
 */
public class TenFragment extends FragmentSupport {

    private ListView listView;
    private List<String> list = new ArrayList<>();

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.tenfragment_layout,null);
        listView = (ListView) view.findViewById(R.id.listView);
        for(int i = 0;i<10;i++){
            list.add(i+"");
        }
        KonckAdapter adapter = new KonckAdapter(context,list);
        listView.setAdapter(adapter);
        return view;
    }


}
