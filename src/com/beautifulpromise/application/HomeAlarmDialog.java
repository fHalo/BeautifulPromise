package com.beautifulpromise.application;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.checkpromise.CycleCheckActivity;
import com.beautifulpromise.application.checkpromise.WorkCheckActivity;
import com.beautifulpromise.common.dto.AddPromiseDTO;

public class HomeAlarmDialog extends Dialog {

	public HomeAlarmDialog(Context context, int theme) {
		super(context, theme);
	}

	public HomeAlarmDialog(Context context) {
		super(context);
	}

	public static class Builder {

		private Context context;
		View view;
		private HomeAlarmDialog dialog;

		Button okayBtn;
		Button cancelBtn;
		TextView contentText;
		
		AddPromiseDTO promiseObject;

		public Builder(Context context, AddPromiseDTO promiseObject) {
			this.context = context;
			this.promiseObject = promiseObject;
		}

		public HomeAlarmDialog create() {

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			dialog = new HomeAlarmDialog(context, R.style.Theme_Dialog);
			view = inflater.inflate(R.layout.home_alarm_dialog, null);

			okayBtn = (Button) view.findViewById(R.id.home_alram_okay_button);
			cancelBtn = (Button) view
					.findViewById(R.id.home_alram_cancel_button);
			contentText = (TextView) view
					.findViewById(R.id.home_alram_content_text);

			okayBtn.setOnClickListener(buttonClickListener);
			cancelBtn.setOnClickListener(buttonClickListener);

			contentText.setText(promiseObject.getTitle());
			dialog.setContentView(view);
			return dialog;
		}

		View.OnClickListener buttonClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.home_alram_okay_button:
					Intent intent = null;
					
					if (promiseObject.getCategoryId() == 0)
					{
						intent = new Intent(context, CycleCheckActivity.class);
					}else if ((promiseObject.getCategoryId() == 1))
					{
						intent = new Intent(context, WorkCheckActivity.class);
					}
					Bundle extras = new Bundle();
					extras.putSerializable("PromiseDTO", promiseObject);
					intent.putExtras(extras);
					
					((Activity) context).startActivityForResult(intent, 0);
					
					dialog.dismiss();
					((Activity) context).finish();
					break;

				case R.id.home_alram_cancel_button:
					((Activity) context).finish();
					dialog.dismiss();
					break;

				default:
					break;
				}
			}
		};
	}
	
	

}
