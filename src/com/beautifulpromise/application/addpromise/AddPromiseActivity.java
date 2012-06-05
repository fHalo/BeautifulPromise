package com.beautifulpromise.application.addpromise;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.beautifulpromise.R;
import com.beautifulpromise.common.dto.AddPromiseDTO;
import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.common.utils.DateUtils;
import com.beautifulpromise.common.utils.ImageUtils;
import com.beautifulpromise.database.DatabaseHelper;
import com.beautifulpromise.database.GoalsDAO;
import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.connection.Friends;
import com.facebook.halo.framework.common.AccessToken;
import com.facebook.halo.framework.core.Connection;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class AddPromiseActivity extends MapActivity {

	protected static final int TIME_DIALOG_ID = 100;

	Spinner goalSpinner;
	EditText goalTitleEdit;
	Button createBtn;
	
	LinearLayout dateLayout;
	TextView startDateText;
	TextView endDateText;
	
	LinearLayout dayRepeatLayout;
	TextView dayRepeatText;
	
	LinearLayout alarmLayout;
	TextView alarmTimeText;
	
	EditText contentEdit;
	
	GridView helperGrid;
	
	Button helperBtn;
	Button mapBtn;
	Button tutorialBtn;
	
	int startYear;
	int startMonth;
	int startDay;
	int endYear;
	int endMonth;
	int endDay;
	AddPromiseDTO promiseDTO;
	
	MapView mapView;
	MapController mapController;
	MyLocationOverlay myLocationOverlay;
	
	Connection<Friends> friends;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpromise_main_layout);
        
        setVariable();
        mapView = new MapView(this, getResources().getString(R.string.map_api_key));
        mapView.setClickable(true);
		mapView.displayZoomControls(true);
//		mapView.setBuiltInZoomControls(true);

		mapController = mapView.getController();
		mapController.setZoom(15);
		
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		myLocationOverlay.enableMyLocation();

		mapView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == event.ACTION_UP){
					int X = (int) event.getX();
					int Y = (int) event.getY();
					
					GeoPoint geoPoint = mapView.getProjection().fromPixels(X, Y);
					Log.i("immk", "위도: " + geoPoint.getLatitudeE6() +" 경도 : " + geoPoint.getLongitudeE6());
					
					mapController.animateTo(geoPoint);
					mapController.setZoom(15); 
					    
				    List<Overlay> mapOverlays = mapView.getOverlays();
		            Drawable drawable = getResources().getDrawable(R.drawable.ico_pin);
		            MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, AddPromiseActivity.this);
		            
		            OverlayItem overlayitem = new OverlayItem(geoPoint, "", "");
		            itemizedoverlay.addOverlay(overlayitem);
		            mapOverlays.clear();
		            mapOverlays.add(itemizedoverlay);
				}
				return false;
			}
		});
		
//		LocationManager locationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
//		Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
//		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
//		String best = locationMgr.getBestProvider(criteria, true);
//		locationMgr.requestLocationUpdates(best, 1000, 0, this);
      		
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalSpinner.setAdapter(adapter);
        goalSpinner.setOnItemSelectedListener(spinnerSelectListener);
        
        dateLayout.setOnClickListener(buttonClickListener);
        dayRepeatLayout.setOnClickListener(buttonClickListener);
        alarmLayout.setOnClickListener(buttonClickListener);
        createBtn.setOnClickListener(buttonClickListener);
        
        helperBtn.setOnClickListener(buttonClickListener);
        mapBtn.setOnClickListener(buttonClickListener);
        tutorialBtn.setOnClickListener(buttonClickListener);
        
		startYear = DateUtils.getYear();
		startMonth = DateUtils.getMonth();
		startDay = DateUtils.getDay();

		endYear = startYear;
		endMonth = startMonth;
		endDay = startDay + 1;
		
		setDate(startYear, startMonth, startDay, endYear, endMonth, endDay);
		
		setView(0);
		promiseDTO = new AddPromiseDTO();
		promiseDTO.setDayPeriod(new boolean[]{false, false, false, false, false, false, false});
		
//		DatabaseHelper databaseHelper = new DatabaseHelper(this);
//		GoalsDAO dao = new GoalsDAO(databaseHelper);
//		ArrayList<AddPromiseDTO> aa = dao.getList();
//		ArrayList<AddPromiseDTO> bb = dao.getGoalList(2);
//		Log.i("immk", bb.get(0).getTitle());
		
		getFriendList();

    }
    
    View.OnClickListener buttonClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.date_layout:
				DateDialog.Builder pickerViewBuilder = new DateDialog.Builder(AddPromiseActivity.this);
				pickerViewBuilder.setDate(startYear, startMonth, startDay, endYear, endMonth, endDay);
				Dialog dateDialog = pickerViewBuilder.create();
				dateDialog.show();
				break;
				
			case R.id.day_repeat_layout:
				RepeatDayDialog.Builder dayBuilder = new RepeatDayDialog.Builder(AddPromiseActivity.this, promiseDTO.getDayPeriod());
				Dialog dayDialog = dayBuilder.create();
				dayDialog.show();
				break;
				
			case R.id.alarm_time_layout:
				showDialog(TIME_DIALOG_ID);
				break;
				
			case R.id.helper_button:
				FriendViewDialog.Builder friendViewBuilder = new FriendViewDialog.Builder(AddPromiseActivity.this, friends.getData(), promiseDTO.getHelperList());
				Dialog friendDialog = friendViewBuilder.create();
				friendDialog.show();
				break;
				
			case R.id.google_map_button:
				MapViewDialog dialog = new MapViewDialog(AddPromiseActivity.this, R.style.Theme_Dialog, mapView);
				dialog.show();
				break;
				
			case R.id.tutorial_button:
				break;
				
			case R.id.create_button:
				
				//TODO promiseDTO 객체에 정보 담기 
				promiseDTO.setTitle(goalTitleEdit.getText().toString());
				promiseDTO.setStartDate(startDateText.getText().toString());
				promiseDTO.setEndDate(endDateText.getText().toString());
				promiseDTO.setContent(contentEdit.getText().toString());
				
				SignViewDialog.Builder signBuilder = new SignViewDialog.Builder(AddPromiseActivity.this, promiseDTO);
				Dialog signDialog = signBuilder.create();
				signDialog.show();
				break;
				
			default:
				break;
			}	
		}
	};
    
    AdapterView.OnItemSelectedListener spinnerSelectListener = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
			if(position == 0 ) {
				setView(0);
				promiseDTO.setCategoryId(0);
			}else if(position == 1 ) {
				setView(1);
				promiseDTO.setCategoryId(1);
			}else if(position == 2 ) {
				setView(2);
				promiseDTO.setCategoryId(2);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			promiseDTO.setCategoryId(0);
		
		}
	};
	
	TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			alarmTimeText.setText(""+ hourOfDay + "시 "  + minute + "분");
			promiseDTO.setTime(hourOfDay);
			promiseDTO.setMin(minute);
		}
	};
	
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			final Calendar calendar = Calendar.getInstance();
		    int mHour = calendar.get(Calendar.HOUR_OF_DAY);
		    int  mMinute = calendar.get(Calendar.MINUTE);
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, false);
		}
		return null;
	};
	
	private void setView (int position){
		
		switch (position) {
		case 0:
			dayRepeatLayout.setVisibility(View.VISIBLE);
			alarmLayout.setVisibility(View.VISIBLE);
			mapBtn.setVisibility(View.VISIBLE);
			break;
		case 1:
			dayRepeatLayout.setVisibility(View.VISIBLE);
			alarmLayout.setVisibility(View.VISIBLE);
			mapBtn.setVisibility(View.GONE);
			break;
		case 2:
			dayRepeatLayout.setVisibility(View.GONE);
			alarmLayout.setVisibility(View.GONE);
			mapBtn.setVisibility(View.GONE);
			break;
			
		default:
			break;
		}
	}
	
	public void setDate(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
		startDateText.setText(""+ startYear + "년 " + (startMonth+1) + "월 " + startDay + "일");
		endDateText.setText(""+ endYear + "년 " + (endMonth+1) + "월 " + endDay + "일");
		this.startYear = startYear;
		this.startMonth = startMonth;
		this.startDay = startDay;
		this.endYear = endYear;
		this.endMonth = endMonth;
		this.endDay = endDay;
	}
	
	private void setVariable(){
		
		goalSpinner = (Spinner) findViewById(R.id.goal_spinner);
        createBtn = (Button) findViewById(R.id.create_button);
    	goalTitleEdit = (EditText) findViewById(R.id.goal_title_edit);

        dateLayout = (LinearLayout) findViewById(R.id.date_layout);
        startDateText = (TextView) findViewById(R.id.start_date_text);
        endDateText = (TextView) findViewById(R.id.end_date_text);
        
        dayRepeatLayout = (LinearLayout) findViewById(R.id.day_repeat_layout);
        dayRepeatText = (TextView) findViewById(R.id.day_repeat_text);
        
        alarmLayout = (LinearLayout) findViewById(R.id.alarm_time_layout);
        alarmTimeText = (TextView) findViewById(R.id.alarm_time_text);
        
        contentEdit = (EditText) findViewById(R.id.content_edit);
        
        helperGrid = (GridView) findViewById(R.id.friend_image_gridview);
        
        helperBtn = (Button) findViewById(R.id.helper_button);
        mapBtn = (Button) findViewById(R.id.google_map_button);
        tutorialBtn = (Button) findViewById(R.id.tutorial_button);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Uri imageUri;
		Bitmap bitmap;
		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case CameraDialog.FINISH_TAKE_PHOTO:
				bitmap = (Bitmap) data.getExtras().get("data"); 
				String path = ImageUtils.saveBitmap(AddPromiseActivity.this, bitmap); // 
				
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

	@Override
	protected boolean isRouteDisplayed() {		
		return false;
	}
	
	private List<Friends> getFriendList(){
		User user = Repository.getInstance().getUser();
		friends = user.friends();
		return friends.getData();
	}
	
	public void setHelperFriends(ArrayList<Friends> helperList){
		
		if(helperList != null && helperList.size()>0){
			promiseDTO.setHelperList(helperList);
		}else{
			promiseDTO.setHelperList(null);
		}
		FriendImageAdapter adapter = new FriendImageAdapter(this, helperList);
		helperGrid.setAdapter(adapter);
	}

	public Bitmap getHelperImage() {
		return ImageUtils.capture(helperGrid);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
//		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected void onResume() {
		super.onResume();	
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.runOnFirstFix(new Runnable() {

			public void run() {
				mapController.setCenter(myLocationOverlay.getMyLocation());
				mapView.getOverlays().add(myLocationOverlay);
			}
		});
	}
}