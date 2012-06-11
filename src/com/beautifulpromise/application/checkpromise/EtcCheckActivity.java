package com.beautifulpromise.application.checkpromise;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beautifulpromise.R;
import com.beautifulpromise.application.HomeActivity;
import com.beautifulpromise.common.dto.AddPromiseDTO;
import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.common.utils.ImageUtils;
import com.beautifulpromise.database.CheckDAO;
import com.beautifulpromise.database.CheckDBHelper;
import com.beautifulpromise.parser.Controller;
import com.facebook.halo.application.types.Album;
import com.facebook.halo.application.types.Tags;
import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.connection.Albums;
import com.facebook.halo.application.types.connection.Friends;
import com.facebook.halo.application.types.connection.Photos;
import com.facebook.halo.application.types.infra.FacebookType;
import com.facebook.halo.framework.core.Connection;

public class EtcCheckActivity extends Activity {
	
	TextView PromiseName_TextView;
	TextView Period_TextView;
	Button Post_Btn;
	EditText Feed_EditBox;
	ImageView Upload_ImageView;

	Connection<Friends> friends;

	AddPromiseDTO promiseobject;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkpromise_etccheck_activity);

		PromiseName_TextView = (TextView) findViewById(R.id.checkpromise_etccheck_promisename_text);
		Period_TextView = (TextView) findViewById(R.id.checkpromise_etccheck_period_text);
		Post_Btn = (Button) findViewById(R.id.checkpromise_etccheck_post_btn);
		Feed_EditBox = (EditText) findViewById(R.id.checkpromise_etccheck_content_edit);
		Upload_ImageView = (ImageView) findViewById(R.id.checkpromise_etccheck_uploadimage_img);

		Post_Btn.setOnClickListener(buttonClickListener);

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
		
	}

	View.OnClickListener buttonClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.checkpromise_etccheck_post_btn:

				boolean result;
				Upload_ImageView.buildDrawingCache();
				Bitmap captureBitmap = Upload_ImageView.getDrawingCache();
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(Environment
							.getExternalStorageDirectory().toString()
							+ "/capture.jpeg");
					captureBitmap
							.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					captureBitmap = Bitmap.createScaledBitmap(captureBitmap,
							Upload_ImageView.getWidth(), Upload_ImageView.getHeight(),
							true);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				String path = ImageUtils.saveBitmap(EtcCheckActivity.this, captureBitmap);

				User user = Repository.getInstance().getUser();

				String albumId = null;
				Connection<Album> albums = user.albums();
				for (List<Album> albumList : albums)
					for (Album album : albumList) {

						System.out.println("Album ID : " + album.getId());
						if (album.getName().equals("아름다운 약속")) {
							albumId = album.getId();
							break;
						}
					}

				if (albumId == null) {
					Albums album = new Albums();
					album.setName("아름다운 약속");
					album.setMessage("당신이 약속을 지킴으로써 소중한 한 생명이 살아날 수 있습니다.");
					albumId = user.publishAlbums(album).getId();
				}

				Photos photos = new Photos();
				photos.setMessage(PromiseName_TextView.getText().toString() + "\n\n"
						+ "목표 기간 : " + Period_TextView.getText().toString() + "\n\n"
						+ Feed_EditBox.getText().toString() + "\n\n\n"
						+ "구글 Play 유료게임 1위!!! 팔라독 다운 받기\n"
						+ "http://bit.ly/LaEn8k\n");
				photos.setSource(path);
				photos.setFileName("Beautiful Promise");
				FacebookType type = user.publishPhotos(albumId, photos);

				Controller ctr = new Controller();
				ArrayList<String> HelperList = ctr.GetHelperList(promiseobject.getPostId());
				if(HelperList.size() != 0)
				{
					ArrayList<Tags> tags = new ArrayList<Tags>();
					for (String helper : HelperList) {
						Tags tag = new Tags();
						tag.setTagUid(helper);
						tags.add(tag);
					}
					result = user.publishTagsAtPhoto(type.getId(), tags);
				}
				else
				{
					result = true;
				}
				
				if (result) {
					Toast.makeText(EtcCheckActivity.this, "성공", Toast.LENGTH_SHORT).show();
					boolean aa = ctr.PublishCheck(promiseobject.getPostId(), type.getId());

					CheckDBHelper checkDBHelper = new CheckDBHelper(EtcCheckActivity.this);
					CheckDAO checkDAO = new CheckDAO(checkDBHelper);
					checkDAO.feedcheckupdate(promiseobject.getPostId(), 1);
					checkDAO.close();
					
					Intent intent = new Intent(EtcCheckActivity.this, HomeActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(EtcCheckActivity.this, "Upload에 실패하였습니다.",
							Toast.LENGTH_SHORT).show();
				}
				break;
				
			default:
				break;
			}
		}
	};

}
