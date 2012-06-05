package com.beautifulpromise.application.addpromise;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beautifulpromise.R;
import com.beautifulpromise.common.dto.AddPromiseDTO;
import com.beautifulpromise.common.utils.DateUtils;
import com.beautifulpromise.common.utils.ImageUtils;
import com.beautifulpromise.database.DatabaseHelper;
import com.beautifulpromise.database.GoalsDAO;
import com.facebook.halo.application.types.Album;
import com.facebook.halo.application.types.Tags;
import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.connection.Albums;
import com.facebook.halo.application.types.connection.Friends;
import com.facebook.halo.application.types.connection.Photos;
import com.facebook.halo.application.types.infra.FacebookType;
import com.facebook.halo.framework.common.AccessToken;
import com.facebook.halo.framework.core.Connection;

public class UploadFeedDialog extends Dialog{
	
    public UploadFeedDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public UploadFeedDialog(Context context) {
		super(context);
	}
	public static class Builder {
    	 
        private Context context;
    	private UploadFeedDialog dialog;
    	
    	
    	LinearLayout promiseLayout;
    	
    	ImageView donationImage;
    	TextView titleText;
    	TextView donationTitle;
    	TextView dateText;
    	ImageView signImage;
    	TextView subContentText;
    	LinearLayout helperLayout;
    	
    	Button okayBtn;
    	Button cancelBtn;
    	
    	View layout;
    	AddPromiseDTO promiseDTO;
        
        public Builder(Context context, AddPromiseDTO promiseDTO) {
        	this.context = context;
        	this.promiseDTO = promiseDTO;
		}

		public UploadFeedDialog create() {
        	        	
        	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	dialog = new UploadFeedDialog(context, R.style.Theme_Dialog);
            layout = inflater.inflate(R.layout.addpromise_upload_feed_dialog, null);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            
            promiseLayout = (LinearLayout) layout.findViewById(R.id.promise_layout);
            
            donationImage = (ImageView) layout.findViewById(R.id.donation_image_view);
            titleText = (TextView) layout.findViewById(R.id.content_text);
            donationTitle = (TextView) layout.findViewById(R.id.donation_title_text);
            dateText = (TextView) layout.findViewById(R.id.date_text);
            signImage = (ImageView) layout.findViewById(R.id.sign_image);
            subContentText = (TextView) layout.findViewById(R.id.sub_content_textview);
            helperLayout = (LinearLayout) layout.findViewById(R.id.helper_layout);
            
            okayBtn = (Button) layout.findViewById(R.id.okay_button);
            cancelBtn = (Button) layout.findViewById(R.id.cancel_button);
            
            donationImage.setImageBitmap(promiseDTO.getDonation().getBitmap());
            titleText.setText(setContent());
            donationTitle.setText(setDontationText());
            dateText.setText(DateUtils.getDate());
            signImage.setImageBitmap(promiseDTO.getSignBitmap());
            
            subContentText.setText(promiseDTO.getContent());
            Bitmap bitmap = ((AddPromiseActivity)context).getHelperImage();
            helperLayout.setBackgroundDrawable(ImageUtils.bitmapToDrawable(bitmap));

            okayBtn.setOnClickListener(buttonClickListener);
            
            dialog.setContentView(layout);			
            return dialog;
        }
		
		View.OnClickListener buttonClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.okay_button:
					// Feed Upload
					promiseLayout.buildDrawingCache();
					Bitmap captureBitmap = promiseLayout.getDrawingCache();
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/capture.jpeg");
						captureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
						captureBitmap = Bitmap.createScaledBitmap(captureBitmap, promiseLayout.getWidth(), promiseLayout.getHeight(), true);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					String path = ImageUtils.saveBitmap(context, captureBitmap);
					
					AccessToken.setAccessToken("AAACEdEose0cBAHNrEBile5bKF9AhAodKZBRxMoEauNKYmjBCmGNQEZAZCZAxiCWHRzZB7IVRm1LiqMnKF3eQf63UGv2mmKGA7AxCR3S9KpvAwzP19cdYL");
					User user = new User();
					user = user.createInstance("100001428910089");
					
					String albumId = null ;
					Connection<Album> albums = user.albums();
					for(List<Album> albumList : albums)
						for(Album album : albumList){
							
							System.out.println("Album ID : " + album.getId());
							if(album.getName().equals("아름다운 약속")) {
								albumId = album.getId();
								break;
							}
						}
					
					if(albumId == null){
						Albums album = new Albums();
						album.setName("아름다운 약속");
						album.setMessage("아름다운 서약서");
						albumId = user.publishAlbums(album).getId();
					}
							
					Photos photos = new Photos();
					photos.setMessage(subContentText.getText().toString() + "\n\n" + "[아름다운 약속] 앱 다운 받기" + "\n" + "http://www.enjoybazar.com");
					photos.setSource(path);
					photos.setFileName("Beautiful Promise");
					FacebookType type = user.publishPhotos(albumId, photos);

					ArrayList<Tags> tags = new ArrayList<Tags>();
					int count=0;
					for(Friends friend : promiseDTO.getHelperList()){
						count++;
						Tags tag = new Tags();
						
						tag.setTagUid(friend.getId());
						tag.setX(""+(10*count));
						tag.setY(""+(70));
						tags.add(tag);
					}
					boolean result = user.publishTagsAtPhoto(type.getId(), tags);
					if(result){
//						//TODO DB저장
						DatabaseHelper databaseHelper = new DatabaseHelper(context);
						GoalsDAO dao = new GoalsDAO(databaseHelper);
						boolean rr = dao.insert(promiseDTO);
						Log.i("immk", "insert : " + rr);
						dialog.cancel();
					}else{
						Toast.makeText(context, "Upload에 실패하였습니다.", Toast.LENGTH_SHORT).show();
					}	
					break;
					
				case R.id.cancel_button:
					dialog.cancel();
					break;
				default:
					break;
				}
			}
		};

		private String setContent(){
			
			String str;
			
			str = promiseDTO.getStartDate() + " ~ " + promiseDTO.getEndDate() 
					+ "까지 " + "\"" + promiseDTO.getTitle() + "\" 목표를 성실히 수행하겠습니다. ";
			return str; 
		}
		
		private String setDontationText(){
			String str;
			
			str = "이 목표는 " + promiseDTO.getDonation().getTitle() + "와 함께 합니다.";
			return str; 
		}
		
    }
}
