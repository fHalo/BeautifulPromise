package com.beautifulpromise.application.checkpromise;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.google.android.maps.*;

public class WorkCheckFeedActivity extends Activity{
	TextView PromiseName_TextView;
	TextView Period_TextView;
	TextView Content_TextView;
	EditText Feed_EditBox;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkpromise_workcheck_feed_activity);
		
		Intent intent= getIntent();
		String aa= intent.getStringExtra("Time");
		Content_TextView = (TextView)findViewById(R.id.checkpromise_workcheckfeed_content_text);
		Content_TextView.setText(aa);
	}
	
}
