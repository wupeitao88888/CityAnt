package com.cityant.main.zxing;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cityant.main.R;

public class ScanResultActivity extends Activity{

	private TextView user_name;
	private TextView scan_tip_message;
	private Button btn_login_web;
	private Button btn_cancel;
	private String scantipmessage;
	private Boolean result;
	private LinearLayout scan_success;
	private LinearLayout scan_fail;
	private Button scan_result;
	private LinearLayout cancel_ll;
	private TextView copy_scan_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_result);
		initview();
		Intent intent = getIntent();
		// String username = intent.getStringExtra("");
		result = intent.getBooleanExtra(MipcaActivityCapture.RESULT, false);
		scantipmessage = intent
				.getStringExtra(MipcaActivityCapture.SCAN_RESULT);
		// user_name.setText(username);
		// if(scantipmessage.indexOf("bg://")>=0){
		if (result) {
			scan_tip_message.setText("您的账号即将在电脑上登录天虎云商网页,\n请确认是否是本人操作。");
		} else {
			scan_success.setVisibility(View.GONE);
			scan_result.setText(scantipmessage);

		}

	}

	private void initview() {
		user_name = (TextView) findViewById(R.id.user_name);
		scan_tip_message = (TextView) findViewById(R.id.scan_tip_message);
		scan_result = (Button) findViewById(R.id.scan_result);
		btn_login_web = (Button) findViewById(R.id.btn_login_web);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		cancel_ll = (LinearLayout) findViewById(R.id.cancel_ll);
		copy_scan_content = (TextView) findViewById(R.id.copy_scan_content);
		scan_success = (LinearLayout) findViewById(R.id.scan_success);
		scan_fail = (LinearLayout) findViewById(R.id.scan_fail);
		copy_scan_content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				clipBoard.setText(scan_result.getText());
				Toast.makeText(ScanResultActivity.this, "内容已复制剪切板", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
