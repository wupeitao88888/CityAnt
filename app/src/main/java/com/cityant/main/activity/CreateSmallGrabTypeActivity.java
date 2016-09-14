package com.cityant.main.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.CreateSmallGrabTypeAdapter;
import com.cityant.main.bean.CreateSmallGreabType;
import com.iloomo.base.ActivitySupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wupeitao on 16/9/2.
 */
public class CreateSmallGrabTypeActivity extends ActivitySupport {

    private ListView type;
    private CreateSmallGrabTypeAdapter createSmallGrabTypeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createsmallgrabtype);
        setCtenterTitle(mString(R.string.mecreatek));
        initView();
    }

    private void initView() {
        type=(ListView)findViewById(R.id.type);
        List<CreateSmallGreabType> list=new ArrayList<>();
        CreateSmallGreabType createSmallGreabType=new CreateSmallGreabType();
        createSmallGreabType.setName("多人抢");
        createSmallGreabType.setValue("0");
        list.add(createSmallGreabType);
        CreateSmallGreabType bai=new CreateSmallGreabType();
        bai.setName("白人抢");
        bai.setValue("100");
        list.add(bai);
        CreateSmallGreabType shi=new CreateSmallGreabType();
        shi.setName("十人抢");
        shi.setValue("10");
        list.add(shi);
        CreateSmallGreabType wu=new CreateSmallGreabType();
        wu.setName("五人抢");
        wu.setValue("5");
        list.add(wu);
        CreateSmallGreabType debule=new CreateSmallGreabType();
        debule.setName("双人抢");
        debule.setValue("2");
        list.add(debule);
        createSmallGrabTypeAdapter=new CreateSmallGrabTypeAdapter(context,list);
        type.setAdapter(createSmallGrabTypeAdapter);
    }
}
