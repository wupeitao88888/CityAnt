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
public class BindingActivity extends ActivitySupport {
    private EditText phone_number;
    private EditText code_number;
    private Button sendcode;
    private Button login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        setCtenterTitle(mString(R.string.bindingphone));
        initView();
    }

    private void initView() {
        phone_number = (EditText) findViewById(R.id.phone_number);
        code_number = (EditText) findViewById(R.id.code_number);


        sendcode = (Button) findViewById(R.id.sendcode);
        login_button = (Button) findViewById(R.id.login_button);
        phone_number.addTextChangedListener(new EditChangedListener());
        code_number.addTextChangedListener(new EditWatcherListener());
        sendcode.setPressed(true);
        sendcode.setClickable(false);
        login_button.setPressed(true);
        login_button.setClickable(false);
    }


    public void onLoginClick(View view) {
        mIntent(context, IndexFragment.class);
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
                    if (code_number.getText().length() > 0) {
                        login_button.setPressed(false);
                        login_button.setClickable(true);
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
            } else {
                sendcode.setPressed(true);
                sendcode.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    class EditWatcherListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (code_number.getText().length() > 0) {
                if (phone_number.getText().length() > 0) {
                    if (phone_number.getText().length() == 11) {
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
                login_button.setPressed(true);
                login_button.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
