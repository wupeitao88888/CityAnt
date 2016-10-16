package com.cityant.main.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.EvaluateListModel;
import com.cityant.main.bean.FeedBackModel;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 16/8/13.
 */
public class IdeaActivity extends ActivitySupport implements ThreadCallBack {
    private EditText lc_feedback_idea;
    private TextView lc_feedback_residueIdea;
    private Button lc_feedback_submint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);
        setCtenterTitle(R.string.idea);

        initData();
    }

    private void initData() {
        lc_feedback_idea = (EditText) findViewById(R.id.lc_feedback_idea);
        lc_feedback_residueIdea = (TextView) findViewById(R.id.lc_feedback_residueIdea);
        lc_feedback_submint = (Button) findViewById(R.id.lc_feedback_submint);
        lc_feedback_idea.addTextChangedListener(mTextWatcher);
        lc_feedback_submint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback_idea = lc_feedback_idea.getText().toString();
                if (!TextUtils.isEmpty(feedback_idea)) {
                    sendFeedBack(feedback_idea);
                } else {
                    ToastUtil.showShort(context, "建议内容不能为空");
                }
            }
        });
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            this.temp = s;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            lc_feedback_residueIdea.setText(Integer.valueOf(150 - this.temp.length()) + "/150");
            if (this.temp.length() > 0) {
                lc_feedback_submint.setEnabled(true);
            } else {
                lc_feedback_submint.setEnabled(false);
            }

        }
    };

    public void sendFeedBack(String content) {
        DialogUtil.startDialogLoading(context);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("content", content);
        AsyncHttpPost httpRequest;
        httpRequest = new AsyncHttpPost(this, MYAppconfig.FEEDBACK, parameter, MYTaskID.FEEDBACK,
                FeedBackModel.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        DialogUtil.stopDialogLoading(context);
        switch (resultCode) {
            case MYTaskID.FEEDBACK:
                FeedBackModel feedBackModel = (FeedBackModel) modelClass;
                ToastUtil.showShort(context, feedBackModel.getData().getCode_message());
                finish();
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        DialogUtil.stopDialogLoading(context);
        switch (resultCode) {
            case MYTaskID.FEEDBACK:
                FeedBackModel feedBackModel = (FeedBackModel) modelClass;
                ToastUtil.showShort(context, feedBackModel.getData().getCode_message());
                break;
        }
    }
}
