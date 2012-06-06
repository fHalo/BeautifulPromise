package com.beautifulpromise.application.checkpromise;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beautifulpromise.R;
import com.beautifulpromise.common.dto.AddPromiseDTO;
import com.beautifulpromise.common.utils.ImageUtils;
import com.google.android.maps.*;

public class WorkCheckFeedActivity extends Activity{
	TextView PromiseName_TextView;
	TextView Period_TextView;
	TextView Content_TextView;
	EditText Feed_EditBox;
	
	Button PostBtn;
	Button CameraBtn;
	
	AddPromiseDTO promiseobject;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkpromise_workcheck_feed_activity);
		
		PromiseName_TextView = (TextView) findViewById(R.id.checkpromise_workcheckfeed_promisename_text);
		Period_TextView = (TextView) findViewById(R.id.checkpromise_workcheckfeed_period_text);
		PostBtn = (Button) findViewById(R.id.checkpromise_workcheckfeed_post_btn);
		CameraBtn = (Button) findViewById(R.id.checkpromise_workcheckfeed_camera_btn);
		Content_TextView = (TextView)findViewById(R.id.checkpromise_workcheckfeed_content_text);
		
		
		Intent intent= getIntent();
		String time= intent.getStringExtra("Time");
		Content_TextView.setText(time);
		
		//home에서 객체 받아오기
		Object tempobject = getIntent().getExtras().get("PromiseDTO");
		promiseobject = (AddPromiseDTO) tempobject;
		
		//약속 제목 텍스트 설정
		PromiseName_TextView.setText(promiseobject.getTitle());
		
		//목표기간 텍스트 설정
		String StartTime = promiseobject.getStartDate();
		StartTime = StartTime.substring(0, 4) + "." + StartTime.substring(4, 6)+ "." + StartTime.substring(6, 8);
		
		String EndTime = promiseobject.getEndDate();
		EndTime = EndTime.substring(0, 4) + "." + EndTime.substring(4, 6)+ "." + EndTime.substring(6, 8);
		
		Period_TextView.setText(StartTime + " ~ " + EndTime);
		
		PostBtn.setOnClickListener(buttonClickListener);
		CameraBtn.setOnClickListener(buttonClickListener);
		
	}
	
	View.OnClickListener buttonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.checkpromise_workcheckfeed_post_btn:

				break;

			case R.id.checkpromise_workcheckfeed_camera_btn:
				CameraDialog.Builder cameraBuilder = new CameraDialog.Builder(WorkCheckFeedActivity.this);
				Dialog cameraDialog = cameraBuilder.create();
				cameraDialog.show();
				break;

			default:
				break;
			}
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Uri imageUri;
		Bitmap bitmap;
		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case CameraDialog.FINISH_TAKE_PHOTO:
				bitmap = (Bitmap) data.getExtras().get("data"); 
				String path = ImageUtils.saveBitmap(WorkCheckFeedActivity.this, bitmap);
				
				break;
				
			case CameraDialog.FINISH_GET_IMAGE:
				imageUri = data.getData();
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(imageUri, proj, null, null, null);
				cursor.moveToFirst();
				String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
				Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
				bitmap = ImageUtils.getResizedBitmap(imagePath);

				break;
			}
		}
	}
}
