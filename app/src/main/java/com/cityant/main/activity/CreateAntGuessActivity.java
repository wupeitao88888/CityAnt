package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.GuessCreateModel;
import com.cityant.main.bean.GuessIndexModel;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.global.AppConfig;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.L;
import com.iloomo.utils.PImageLoaderUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lvfl on 2016/8/18.
 */
public class CreateAntGuessActivity extends ActivitySupport implements View.OnClickListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CreateAntGuessActivity.class);
        context.startActivity(intent);
    }


    private ImageView ant_image;//发起人头像
    private EditText ant_details_edit;//题干
    private TextView ant_details_edit_count;//字数
    private TextView double_select;
    private TextView three_select;
    private TextView four_select;
    private EditText antwer_a;
    private TextView antwer_a_count;
    private EditText antwer_b;
    private TextView antwer_b_count;
    private EditText antwer_c;
    private TextView antwer_c_count;
    private EditText antwer_d;
    private TextView antwer_d_count;

    private TextView true_a;
    private TextView true_b;
    private TextView true_c;
    private TextView true_d;

    private TextView all_pople;
    private TextView frends_pople;

    private EditText ant_true_edit;
    private EditText ant_filed_edit;


    private Button commit_btn;

    private LinearLayout option_a_ll;
    private LinearLayout option_b_ll;
    private LinearLayout option_c_ll;
    private LinearLayout option_d_ll;

    private LinearLayout answer_ll;

    private int type = -1;//选项(0:双选;1:三选;2:四选)
    private int qtrue = -1;//正确答案(1:A;2:B;3:C;4:D)
    private int is_friend = -1;//谁可参与(0:所有人;1:好友)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ant_guess_layout);
        initView();
    }

    private void initView() {
        ant_image = (ImageView) findViewById(R.id.ant_image);

        ant_details_edit = (EditText) findViewById(R.id.ant_details_edit);
        ant_details_edit_count = (TextView) findViewById(R.id.ant_details_edit_count);

        double_select = (TextView) findViewById(R.id.double_select);
        three_select = (TextView) findViewById(R.id.three_select);
        four_select = (TextView) findViewById(R.id.four_select);

        antwer_a = (EditText) findViewById(R.id.antwer_a);
        antwer_a_count = (TextView) findViewById(R.id.antwer_a_count);
        antwer_b = (EditText) findViewById(R.id.antwer_b);
        antwer_b_count = (TextView) findViewById(R.id.antwer_b_count);
        antwer_c = (EditText) findViewById(R.id.antwer_c);
        antwer_c_count = (TextView) findViewById(R.id.antwer_c_count);
        antwer_d = (EditText) findViewById(R.id.antwer_d);
        antwer_d_count = (TextView) findViewById(R.id.antwer_d_count);

        true_a = (TextView) findViewById(R.id.true_a);
        true_b = (TextView) findViewById(R.id.true_b);
        true_c = (TextView) findViewById(R.id.true_c);
        true_d = (TextView) findViewById(R.id.true_d);

        all_pople = (TextView) findViewById(R.id.all_pople);
        frends_pople = (TextView) findViewById(R.id.frends_pople);

        ant_true_edit = (EditText) findViewById(R.id.ant_true_edit);
        ant_filed_edit = (EditText) findViewById(R.id.ant_filed_edit);

        commit_btn = (Button) findViewById(R.id.commit_btn);

        option_a_ll = (LinearLayout) findViewById(R.id.option_a_ll);
        option_b_ll = (LinearLayout) findViewById(R.id.option_b_ll);
        option_c_ll = (LinearLayout) findViewById(R.id.option_c_ll);
        option_d_ll = (LinearLayout) findViewById(R.id.option_d_ll);

        answer_ll = (LinearLayout) findViewById(R.id.answer_ll);

        double_select.setOnClickListener(this);
        three_select.setOnClickListener(this);
        four_select.setOnClickListener(this);

        true_a.setOnClickListener(this);
        true_b.setOnClickListener(this);
        true_c.setOnClickListener(this);
        true_d.setOnClickListener(this);

        all_pople.setOnClickListener(this);
        frends_pople.setOnClickListener(this);

        commit_btn.setOnClickListener(this);
        ant_details_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ant_details_edit_count.setText(s.length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        antwer_a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                antwer_a_count.setText(s.length() + "/20");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        antwer_b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                antwer_b_count.setText(s.length() + "/20");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        antwer_c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                antwer_c_count.setText(s.length() + "/20");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        antwer_d.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                antwer_d_count.setText(s.length() + "/20");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        PImageLoaderUtils.getInstance().setImageHead(ant_image, MYAppconfig.loginUserInfoData.getUser_avar(), context);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.double_select:
                option_a_ll.setVisibility(View.VISIBLE);
                option_b_ll.setVisibility(View.VISIBLE);
                option_c_ll.setVisibility(View.GONE);
                option_d_ll.setVisibility(View.GONE);

                answer_ll.setVisibility(View.VISIBLE);

                true_a.setVisibility(View.VISIBLE);
                true_b.setVisibility(View.VISIBLE);
                true_c.setVisibility(View.GONE);
                true_d.setVisibility(View.GONE);

                double_select.setEnabled(false);
                three_select.setEnabled(true);
                four_select.setEnabled(true);
                type = 0;
                break;
            case R.id.three_select:
                option_a_ll.setVisibility(View.VISIBLE);
                option_b_ll.setVisibility(View.VISIBLE);
                option_c_ll.setVisibility(View.VISIBLE);
                option_d_ll.setVisibility(View.GONE);

                answer_ll.setVisibility(View.VISIBLE);

                true_a.setVisibility(View.VISIBLE);
                true_b.setVisibility(View.VISIBLE);
                true_c.setVisibility(View.VISIBLE);
                true_d.setVisibility(View.GONE);

                double_select.setEnabled(true);
                three_select.setEnabled(false);
                four_select.setEnabled(true);

                type = 1;
                break;
            case R.id.four_select:
                option_a_ll.setVisibility(View.VISIBLE);
                option_b_ll.setVisibility(View.VISIBLE);
                option_c_ll.setVisibility(View.VISIBLE);
                option_d_ll.setVisibility(View.VISIBLE);

                answer_ll.setVisibility(View.VISIBLE);

                true_a.setVisibility(View.VISIBLE);
                true_b.setVisibility(View.VISIBLE);
                true_c.setVisibility(View.VISIBLE);
                true_d.setVisibility(View.VISIBLE);

                double_select.setEnabled(true);
                three_select.setEnabled(true);
                four_select.setEnabled(false);

                type = 2;
                break;
            case R.id.true_a:
                true_a.setSelected(true);
                true_b.setSelected(false);
                true_c.setSelected(false);
                true_d.setSelected(false);
                qtrue = 1;
                break;
            case R.id.true_b:
                true_a.setSelected(false);
                true_b.setSelected(true);
                true_c.setSelected(false);
                true_d.setSelected(false);
                qtrue = 2;
                break;
            case R.id.true_c:
                true_a.setSelected(false);
                true_b.setSelected(false);
                true_c.setSelected(true);
                true_d.setSelected(false);
                qtrue = 3;
                break;
            case R.id.true_d:
                true_a.setSelected(false);
                true_b.setSelected(false);
                true_c.setSelected(false);
                true_d.setSelected(true);
                qtrue = 4;
                break;
            case R.id.all_pople:
                all_pople.setEnabled(false);
                frends_pople.setEnabled(true);
                is_friend = 0;
                break;
            case R.id.frends_pople:
                all_pople.setEnabled(true);
                frends_pople.setEnabled(false);
                is_friend = 1;
                break;
            case R.id.commit_btn:
                if (TextUtils.isEmpty(ant_details_edit.getText().toString())) {
                    showToast("题干不能为空");
                    return;
                }
                if (type == -1) {
                    showToast("选项不能为空");
                    return;
                }
                if (TextUtils.isEmpty(antwer_a.getText().toString())) {
                    showToast("答案A不能为空");
                    return;
                }
                if (TextUtils.isEmpty(antwer_b.getText().toString())) {
                    showToast("答案B不能为空");
                    return;
                }
                if (TextUtils.isEmpty(antwer_c.getText().toString())) {
                    showToast("答案C不能为空");
                    return;
                }
                if (TextUtils.isEmpty(antwer_d.getText().toString())) {
                    showToast("答案D不能为空");
                    return;
                }
                if (qtrue == -1) {
                    showToast("正确不能为空");
                }
                if (is_friend == -1) {
                    showToast("谁可参与不能为空");
                }
                if (TextUtils.isEmpty(ant_true_edit.getText().toString())) {
                    showToast("请输入答对奖励蚂蚁豆的个数");
                    return;
                }
                if (TextUtils.isEmpty(ant_filed_edit.getText().toString())) {
//                    showToast("请输入答错扣除蚂蚁豆的个数");
//                    return;
                }

                Map<String, Object> parameter = new HashMap<>();
                parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
                parameter.put("question", ant_details_edit.getText().toString());
                parameter.put("key1", antwer_a.getText().toString());
                parameter.put("key2", antwer_b.getText().toString());
                parameter.put("key3", antwer_c.getText().toString());
                parameter.put("key4", antwer_d.getText().toString());
                parameter.put("type", type+"");
                parameter.put("is_friend", is_friend+"");
                parameter.put("true", qtrue+"");
                parameter.put("bean", ant_true_edit.getText().toString());

                new AsyncHttpPost(new ThreadCallBack() {
                    @Override
                    public void onCallbackFromThread(String resultJson, Object modelClass) {

                    }

                    @Override
                    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                        GuessCreateModel guessCreateModel = (GuessCreateModel) modelClass;
                        if (guessCreateModel.getCode().equals("200")) {
                            ant_details_edit.setText("");

                            true_a.setVisibility(View.GONE);
                            true_b.setVisibility(View.GONE);
                            true_c.setVisibility(View.GONE);
                            true_d.setVisibility(View.GONE);

                            true_a.setSelected(false);
                            true_b.setSelected(false);
                            true_c.setSelected(false);
                            true_d.setSelected(false);

                            option_a_ll.setVisibility(View.GONE);
                            option_b_ll.setVisibility(View.GONE);
                            option_c_ll.setVisibility(View.GONE);
                            option_d_ll.setVisibility(View.GONE);

                            antwer_a.setText("");
                            antwer_c.setText("");
                            antwer_d.setText("");
                            antwer_b.setText("");
                            answer_ll.setVisibility(View.GONE);

                            all_pople.setEnabled(true);
                            frends_pople.setEnabled(true);

                            ant_true_edit.setText("");
                            ant_filed_edit.setText("");

                            type = -1;//选项(0:双选;1:三选;2:四选)
                            qtrue = -1;//正确答案(1:A;2:B;3:C;4:D)
                            is_friend = -1;//谁可参与(0:所有人;1:好友)
                        }
                        showToast(guessCreateModel.getData().getCode_message());
                    }

                    @Override
                    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

                    }

                    @Override
                    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                        GuessCreateModel guessCreateModel = (GuessCreateModel) modelClass;
                        showToast(guessCreateModel.getData().getCode_message());
                    }
                }, MYAppconfig.GUESS_CREATE, parameter, MYTaskID.GUESS_CREATE, GuessCreateModel.class, context);
                break;
        }
    }
}
