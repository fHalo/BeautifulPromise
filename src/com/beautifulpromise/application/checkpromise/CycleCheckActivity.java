package com.beautifulpromise.application.checkpromise;

import java.util.ArrayList;
import java.util.List;

import com.beautifulpromise.R;
import com.beautifulpromise.application.addpromise.AddPromiseDTO;
import com.beautifulpromise.application.addpromise.FriendViewDialog;
import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.connection.Checkins;
import com.facebook.halo.application.types.connection.Feed;
import com.facebook.halo.application.types.connection.Friends;
import com.facebook.halo.application.types.infra.FacebookType;
import com.facebook.halo.framework.common.AccessToken;
import com.facebook.halo.framework.core.Connection;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CycleCheckActivity extends MapActivity {

	Cycle_Gps_DBHelper gps_DBHelper;
	LocationListener mLocationListener;
	MapView mapview;
	MapController mc;
	List<Overlay> overlay;

	Connection<Friends> friends;

	AddPromiseDTO promiseDTO;

	Button PostBtn;
	Button CameraBtn;

	Double Latitude;
	Double Longitude;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkpromise_cyclecheck_activity);

		PostBtn = (Button) findViewById(R.id.checkpromise_cyclecheck_post_btn);
		CameraBtn = (Button) findViewById(R.id.checkpromise_cycle_camera_btn);

		PostBtn.setOnClickListener(buttonClickListener);
		CameraBtn.setOnClickListener(buttonClickListener);

		mapview = (MapView) findViewById(R.id.checkpromise_cyclecheck_mapview);
		mapview.setBuiltInZoomControls(true);
		mapview.setSatellite(false);
		mc = mapview.getController();

		SQLiteDatabase db;

		gps_DBHelper = new Cycle_Gps_DBHelper(this);
		db = gps_DBHelper.getReadableDatabase();

		Cursor cursor;

		cursor = db.rawQuery("SELECT latitude, longitude FROM gps", null);
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
		Cycle_Gps_PinOverlay mdio = new Cycle_Gps_PinOverlay(drawable);
		OverlayItem overlayitem = new OverlayItem(gp, "", "");
		mdio.addOverlayItem(overlayitem);
		overlay = mapview.getOverlays();
		overlay.add(mdio);

	}

	View.OnClickListener buttonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.checkpromise_cyclecheck_post_btn:
				User user = new User();
				user = user.createInstance("me");

				Checkins checkins = new Checkins();
				checkins.setMessage("Where R U ?");
				List<String> tags = new ArrayList<String>();
				tags.add("100001428910089"); // Friend1 Id
				tags.add("100001066448386"); // Friend2 Id
				checkins.setTags(tags);
				checkins.setCoordinates(Latitude.toString(),
						Longitude.toString()); // My Position
				checkins.setPlace("228600137193828"); // Place Position
				user.publishCheckins(checkins);

				break;

			case R.id.checkpromise_cycle_camera_btn:
				FriendViewDialog.Builder friendViewBuilder = new FriendViewDialog.Builder(
						CycleCheckActivity.this, friends.getData(),
						promiseDTO.getHelperList());
				Dialog friendDialog = friendViewBuilder.create();
				friendDialog.show();
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

	private List<Friends> getFriendList() {
		AccessToken
				.setAccessToken("AAACEdEose0cBABA2zfMNaWAKAslGBQgKKxJDAwpX2uIErZAfVZA4UnpGvGmIEGCaIJ8Kn2MWTGqa814f7qpjGLagMxImYy68NHoI864sC7IQKmhdFb");
		User user = new User();
		user = user.createInstance("me");
		friends = user.friends();
		return friends.getData();
	}
}