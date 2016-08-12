package com.cityant.main.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;
import com.iloomo.utils.L;

/**
 * Created by wupeitao on 16/8/8.
 */
public class LoginActivity extends ActivitySupport {
    private EditText phone_number;
    private EditText password_number;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRemoveTitle();
        initView();
    }

    private void initView() {
        phone_number = (EditText) findViewById(R.id.phone_number);
        password_number = (EditText) findViewById(R.id.password_number);
        login_button = (Button) findViewById(R.id.login_button);
        phone_number.addTextChangedListener(new EditChangedListener());
        password_number.addTextChangedListener(new EditWatcherListener());
    }


    @Override
    protected void onStart() {
        super.onStart();
        login_button.setPressed(true);
        login_button.setClickable(false);
    }

    public void onLoginClick(View view) {
        mIntent(context, IndexFragment.class);
    }

    public void onQQLoginClick(View view) {
        mIntent(context, BindingActivity.class);
    }

    public void onWeiXinLoginClick(View view) {
        mIntent(context, BindingActivity.class);
    }

    public void onWeboLoginClick(View view) {
        mIntent(context, BindingActivity.class);
    }

    public void onForgetPasswordClick(View view) {
        mIntent(context, ForgetPasswordActivity.class);
    }

    public void onNewUserClick(View view) {
        mIntent(context, NewUserActivity.class);
    }
    class EditWatcherListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (password_number.getText().length() > 0) {
                if (phone_number.getText().length() > 0) {
                    login_button.setPressed(false);
                    login_button.setClickable(true);
                }else{
                    login_button.setPressed(true);
                    login_button.setClickable(false);

                }
            }else{
                login_button.setPressed(true);
                login_button.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    }

    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (phone_number.getText().length() > 0) {
                if (password_number.getText().length() > 0) {

                    login_button.setPressed(false);
                    login_button.setClickable(true);
                }else{
                    login_button.setPressed(true);
                    login_button.setClickable(false);
                }
            }else{
                login_button.setPressed(true);
                login_button.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}

