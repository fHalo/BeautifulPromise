package com.beautifulpromise.application.checkpromise;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class CycleCheckActivity extends MapActivity {

	CheckDBHelper gps_DBHelper;
	SQLiteDatabase db;
	
	LocationListener mLocationListener;
	MapView mapview;
	MapController mc;
	List<Overlay> overlay;
	
	AddPromiseDTO promiseobject;

	Connection<Friends> friends;
	
	TextView PromiseName_TextView;
	TextView Period_TextView;
	EditText Feed_EditBox;
	Button Post_Btn;
	Button Camera_Btn;

	Double Latitude;
	Double Longitude;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkpromise_cyclecheck_activity);

		PromiseName_TextView = (TextView) findViewById(R.id.checkpromise_cyclecheck_promisename_text);
		Period_TextView = (TextView) findViewById(R.id.checkpromise_cyclecheck_period_text);
		Feed_EditBox = (EditText) findViewById(R.id.checkpromise_cyclecheck_content_edit);
		Post_Btn = (Button) findViewById(R.id.checkpromise_cyclecheck_post_btn);
		Camera_Btn = (Button) findViewById(R.id.checkpromise_cycle_camera_btn);

		Post_Btn.setOnClickListener(buttonClickListener);
		Camera_Btn.setOnClickListener(buttonClickListener);
		
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

		mapview = (MapView) findViewById(R.id.checkpromise_cyclecheck_mapview);
		mapview.setBuiltInZoomControls(true);
		mapview.setSatellite(false);
		mc = mapview.getController();

		gps_DBHelper = new CheckDBHelper(this);
		db = gps_DBHelper.getWritableDatabase();
		
		db.delete("gps", null, null);
		ContentValues row;
		row = new ContentValues();
		row.put("promiseid", promiseobject.getId());
		row.put("latitude", 37.589207);
		row.put("longitude", 126.979294);
		db.insert("gps", null, row);

		Cursor cursor;
		
		int a = 12345678;
		
		try{
			cursor = db.rawQuery("SELECT latitude, longitude FROM gps WHERE promiseid=" + promiseobject.getId(), null);

			cursor.moveToNext();
			Latitude = cursor.getDouble(0);
			Longitude = cursor.getDouble(1);

			// 위도 경로 입력
			GeoPoint gp = new GeoPoint((int) (Latitude * 1000000),
					(int) (Longitude * 1000000));
			mc.animateTo(gp);
			mc.setZoom(18);

			// PIN만들기
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.comment_write);
			bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
			Drawable drawable = new BitmapDrawable(bitmap);
			CycleGpsPinOverlay mdio = new CycleGpsPinOverlay(drawable);
			OverlayItem overlayitem = new OverlayItem(gp, "", "");
			mdio.addOverlayItem(overlayitem);
			overlay = mapview.getOverlays();
			overlay.add(mdio);
		}catch (Exception e) {
			Toast.makeText(this, "GPS좌표값이 저장되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
		}
		

	}

	View.OnClickListener buttonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.checkpromise_cyclecheck_post_btn:

				boolean result;
				mapview.buildDrawingCache();
				Bitmap captureBitmap = mapview.getDrawingCache();
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(Environment
							.getExternalStorageDirectory().toString()
							+ "/capture.jpeg");
					captureBitmap
							.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					captureBitmap = Bitmap.createScaledBitmap(captureBitmap,
							mapview.getWidth(), mapview.getHeight(),
							true);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				String path = ImageUtils.saveBitmap(CycleCheckActivity.this, captureBitmap);

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
					Toast.makeText(CycleCheckActivity.this, "성공", Toast.LENGTH_SHORT).show();
					boolean aa = ctr.PublishCheck(promiseobject.getPostId(), type.getId());

					CheckDBHelper checkDBHelper = new CheckDBHelper(CycleCheckActivity.this);
					CheckDAO checkDAO = new CheckDAO(checkDBHelper);
					checkDAO.feedcheckupdate(promiseobject.getPostId(), 1);
					checkDAO.close();
					
					Intent intent = new Intent(CycleCheckActivity.this, HomeActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(CycleCheckActivity.this, "Upload에 실패하였습니다.",
							Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.checkpromise_cycle_camera_btn:
				CameraDialog.Builder cameraBuilder = new CameraDialog.Builder(CycleCheckActivity.this);
				Dialog cameraDialog = cameraBuilder.create();
				cameraDialog.show();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Uri imageUri;
		Bitmap bitmap;
		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case CameraDialog.FINISH_TAKE_PHOTO:
				bitmap = (Bitmap) data.getExtras().get("data"); 
				String path = ImageUtils.saveBitmap(CycleCheckActivity.this, bitmap);
				
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
