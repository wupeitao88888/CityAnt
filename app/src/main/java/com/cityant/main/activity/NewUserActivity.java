package com.cityant.main.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/8.
 */
public class NewUserActivity extends ActivitySupport {
    private EditText phone_number;
    private EditText code_number;
    private Button sendcode;
    private Button login_button;
    private EditText pwnumber;
    private EditText re_pwnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        setCtenterTitle(mString(R.string.newuser));
        initView();
    }

    private void initView() {
        phone_number = (EditText) findViewById(R.id.phone_number);
        code_number = (EditText) findViewById(R.id.code_number);
        pwnumber = (EditText) findViewById(R.id.pwnumber);
        re_pwnumber = (EditText) findViewById(R.id.re_pwnumber);

        sendcode = (Button) findViewById(R.id.sendcode);
        login_button = (Button) findViewById(R.id.login_button);
        phone_number.addTextChangedListener(new EditChangedListener());
        code_number.addTextChangedListener(new EditChangedListener());
        pwnumber.addTextChangedListener(new EditChangedListener());
        re_pwnumber.addTextChangedListener(new EditChangedListener());
        sendcode.setPressed(true);
        sendcode.setClickable(false);
        login_button.setPressed(true);
        login_button.setClickable(false);
    }


    public void onLoginClick(View view) {
        mIntent(context, IndexFragment.class);
    }

    public void onSendCode(View view) {

    }

    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (phone_number.getText().length() > 0) {
                if (phone_number.getText().length() == 11) {

                    sendcode.setPressed(false);
                    sendcode.setClickable(true);
                    if (pwnumber.getText().length() > 0) {
                        if (re_pwnumber.getText().length() > 0) {
                            login_button.setPressed(false);
                            login_button.setClickable(true);
                        } else {
                            login_button.setPressed(true);
                            login_button.setClickable(false);
                        }
                    } else {
                        login_button.setPressed(true);
                        login_button.setClickable(false);
                    }
                } else {
                    sendcode.setPressed(true);
                    sendcode.setClickable(false);
                    login_button.setPressed(true);
                    login_button.setClickable(false);
                }
            } else

            {
                sendcode.setPressed(true);
                sendcode.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
