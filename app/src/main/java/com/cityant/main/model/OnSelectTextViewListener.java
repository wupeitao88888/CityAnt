package com.cityant.main.model;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iloomo.utils.L;

/**
 * Created by wupeitao on 2017/2/7.
 */

public class OnSelectTextViewListener implements View.OnClickListener {

    private TextView select_a;
    private TextView select_b;
    private TextView select_c;
    private TextView select_d;
    private int type;
    private Button submit;
    private int position;

    public OnSelectTextViewListener(TextView select_a, TextView select_b, TextView select_c, TextView select_d, int type, Button submit, int position) {
        this.select_a = select_a;
        this.select_b = select_b;
        this.select_c = select_c;
        this.select_d = select_d;
        this.type = type;
        this.submit = submit;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        TextView onview = (TextView) v;
        int id = onview.getId();
        int id1 = select_a.getId();


        switch (type) {
            case 0://双选


                break;
            case 1://三选
                break;
            case 2://四选
                break;
        }
    }


}
