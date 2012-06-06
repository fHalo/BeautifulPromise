package com.beautifulpromise.application.checkpromise;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beautifulpromise.R;
import com.beautifulpromise.common.dto.AddPromiseDTO;
import com.beautifulpromise.common.utils.ImageUtils;
import com.facebook.halo.application.types.Album;
import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.connection.Albums;
import com.facebook.halo.application.types.connection.Friends;
import com.facebook.halo.application.types.connection.Photos;
import com.facebook.halo.application.types.infra.FacebookType;
import com.facebook.halo.framework.core.Connection;

public class EtcCheckActivity extends Activity {
	Button PostBtn;
	Button CameraBtn;
	EditText contentEdit;
	ImageView UploadImage;

	Connection<Friends> friends;

	AddPromiseDTO promiseDTO;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkpromise_etccheck_activity);

		PostBtn = (Button) findViewById(R.id.checkpromise_etccheck_post_btn);
		CameraBtn = (Button) findViewById(R.id.checkpromise_etccheck_camera_btn);
		contentEdit = (EditText) findViewById(R.id.checkpromise_etccheck_content_edit);
		UploadImage = (ImageView) findViewById(R.id.checkpromise_etccheck_uploadimage_img);

		PostBtn.setOnClickListener(buttonClickListener);
		CameraBtn.setOnClickListener(buttonClickListener);

		promiseDTO = new AddPromiseDTO();
	}

	View.OnClickListener buttonClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.checkpromise_etccheck_post_btn:

				UploadImage.buildDrawingCache();
				Bitmap captureBitmap = UploadImage.getDrawingCache();
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(Environment
							.getExternalStorageDirectory().toString()
							+ "/capture.jpeg");
					captureBitmap
							.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					captureBitmap = Bitmap.createScaledBitmap(captureBitmap,
							UploadImage.getWidth(), UploadImage.getHeight(),
							true);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				String path = ImageUtils.saveBitmap(EtcCheckActivity.this, captureBitmap);

				User user = new User();
				user = user.createInstance("me");

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
					album.setMessage("아름다운 서약서");
					albumId = user.publishAlbums(album).getId();
				}

				Photos photos = new Photos();
				photos.setMessage(contentEdit.getText().toString() + "\n\n"
						+ "[아름다운 약속] 앱 다운 받기" + "\n"
						+ "http://www.enjoybazar.com");
				photos.setSource(path);
				photos.setFileName("Beautiful Promise");
				FacebookType type = user.publishPhotos(albumId, photos);

//				ArrayList<Tags> tags = new ArrayList<Tags>();
//
//				Tags tag = new Tags();
//				tag.setTagUid("100001428910089");
//				tags.add(tag);
//				Tags taga = new Tags();
//				taga.setTagUid("100001066448386");
//				tags.add(taga);
//
//				boolean result = user.publishTagsAtPhoto(type.getId(), tags);
//				if (result) {
//					Toast.makeText(EtcCheckActivity.this, "성공",
//							Toast.LENGTH_SHORT).show();
//				} else {
//					Toast.makeText(EtcCheckActivity.this, "Upload에 실패하였습니다.",
//							Toast.LENGTH_SHORT).show();
//				}
				break;

			case R.id.checkpromise_etccheck_camera_btn:
				CameraDialog.Builder cameraBuilder = new CameraDialog.Builder(EtcCheckActivity.this);
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
				String path = ImageUtils.saveBitmap(EtcCheckActivity.this, bitmap);
				
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
