package com.cityant.main.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.iloomo.base.FragmentSupport;


public class FragmentKnock extends FragmentSupport {

    private ListView mlist;
    private FragmentHomeAdapter fragmentHomeAdapter = null;

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_message, null);
        setTitle("抢");
//        mlist = (ListView) view.findViewById(R.id.mlist);
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            list.add("王尼玛" + i);
//        }
//        fragmentHomeAdapter = new FragmentHomeAdapter(context, list);
//        mlist.setAdapter(fragmentHomeAdapter);
//        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(context, ChatActivity.class));
//            }
//        });
        return view;
    }


}
