package com.beautifulpromise.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.beautifulpromise.R;
import com.beautifulpromise.application.feedviewer.PromiseFeedList;
import com.beautifulpromise.common.Var;
import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.facebooklibrary.DialogError;
import com.beautifulpromise.facebooklibrary.Facebook;
import com.beautifulpromise.facebooklibrary.Facebook.DialogListener;
import com.beautifulpromise.facebooklibrary.FacebookError;
import com.beautifulpromise.facebooklibrary.SessionStore;
import com.facebook.halo.framework.common.AccessToken;

public class Intro extends Activity {
	
	Facebook mFacebook;
	
	ImageButton loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFacebook = new Facebook(Var.APP_ID);
		
		//이미 로긴 되있으면 바로 시작(자동 로그인 처리)
		if(SessionStore.restore(mFacebook, this)) {
			
			//TODO
			AccessToken.setAccessToken(mFacebook.getAccessToken());
			Repository.getInstance().setUser();
			startActivity(new Intent(Intro.this, HomeActivity.class));
			finish();
		}
		
		setContentView(R.layout.intro);
		
		//Login Button
		loginButton = (ImageButton) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(buttonClickListener);
	}
	
	View.OnClickListener buttonClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mFacebook.authorize(Intro.this, Var.PERMISSIONS , new DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                	SessionStore.save(mFacebook, getBaseContext());
                	//TODO
                    AccessToken.setAccessToken(mFacebook.getAccessToken());
                    Repository.getInstance().setUser();
                	startActivity(new Intent(Intro.this, PromiseFeedList.class));
                	finish();
                }
    
                @Override
                public void onFacebookError(FacebookError error) {}
    
                @Override
                public void onError(DialogError e) {}
    
                @Override
                public void onCancel() {}
            });
		}
	};
	
	//콜백함수를 사용하기위해
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
        mFacebook.authorizeCallback(requestCode, resultCode, data);
	}

}
