package com.beautifulpromise.application;

import com.beautifulpromise.R;
import com.beautifulpromise.application.HomeAlarmDialog.Builder;
import com.beautifulpromise.common.dto.AddPromiseDTO;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.WindowManager;

public class HomeAlarmActivity extends Activity {
	AddPromiseDTO promiseobject;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_alarm_acitivity);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		
		Object tempobject = getIntent().getExtras().get("PromiseDTO");
		promiseobject = (AddPromiseDTO) tempobject;
		
		HomeAlarmDialog.Builder pickerViewBuilder = new Builder(HomeAlarmActivity.this, promiseobject);
		Dialog dateDialog = pickerViewBuilder.create();
		dateDialog.show();
	}
}
