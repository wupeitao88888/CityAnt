package com.cityant.main.fragment.konck;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.cityant.main.R;

/**
* 抢首页筛选
* @author Lvfl
* created at 2017/3/15 14:20
*/
public class KonckScreenDialogFragment extends DialogFragment {

    public static KonckScreenDialogFragment newInstance() {
        Bundle args = new Bundle();
        KonckScreenDialogFragment fragment = new KonckScreenDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.konck_screen_dialog_layout, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
    }
}
