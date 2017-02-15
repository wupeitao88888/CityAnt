package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.cityant.main.R;
import com.cityant.main.adapter.base.CommonAdapter;
import com.cityant.main.adapter.base.ViewHolder;
import com.cityant.main.base.BaseToolbarActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;

/**
* 更多部落
* @author Lvfl
* created at 2017/2/15 10:54
*/

public class MoreTribeActivity extends BaseToolbarActivity {

    @Bind(R.id.more_listView)
    protected ListView more_listView;
    @Bind(R.id.back_image)
    protected ImageView back_image;
    @Bind(R.id.tribe_edit)
    protected EditText tribe_edit;
    @Bind(R.id.search_tribe)
    protected TextView search_tribe;
    private List<String> tribe_list = new ArrayList<>();
    private CommonAdapter adapter;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,MoreTribeActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_tribe_layout;
    }

    private void initToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationOnClickListener(v -> MoreTribeActivity.this.finish());
        ActionBarHelper helper = createActionBarHelper();
        helper.init();
        helper.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbar();
        tribe_list.add("");
        tribe_list.add("");
        tribe_list.add("");
        tribe_list.add("");
        adapter = new CommonAdapter(this,R.layout.search_tribe_item_layout,tribe_list){

            @Override
            protected void convert(ViewHolder viewHolder, Object item, int position) {

            }
        };

        more_listView.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        search_tribe.setOnClickListener(v -> {

        });
        back_image.setOnClickListener(v -> {

        });
    }

    @Override
    protected void initData() {

    }
}
