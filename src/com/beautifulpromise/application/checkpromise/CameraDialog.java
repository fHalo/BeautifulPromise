package com.beautifulpromise.application.checkpromise;

import java.io.File;

import com.beautifulpromise.R;
import com.beautifulpromise.common.utils.StorageUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class CameraDialog extends Dialog{
	
	public static final int FINISH_TAKE_PHOTO = 3;
	public static final int FINISH_GET_IMAGE = 4;
	public static final String IMAGE_PATH = "image_path";
	
    public CameraDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public CameraDialog(Context context) {
		super(context);
	}
	public static class Builder {
    	 
        private Context context;
    	Button takeImageButton;
    	Button getImageButton;
    	private CameraDialog dialog;
    	View layout;

        public Builder(Context context) {
            this.context = context;
        }
        
        public CameraDialog create() {
        	        	
        	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	dialog = new CameraDialog(context, R.style.Theme_Dialog);
            layout = inflater.inflate(R.layout.addpromise_camera_dialog, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            
            takeImageButton = (Button) layout.findViewById(R.id.take_photo_button);
            getImageButton = (Button) layout.findViewById(R.id.get_photo_button);

            takeImageButton.setOnClickListener(buttonClick);
            getImageButton.setOnClickListener(buttonClick);
            dialog.setContentView(layout);			
            return dialog;
        }

        View.OnClickListener buttonClick = new View.OnClickListener() {

        	Intent intent;
        	
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.take_photo_button:
					String imagePath = StorageUtils.getFilePath(context)+".png";
					Uri pictureUri = Uri.fromFile(new File(imagePath));
					intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					((Activity)context).startActivityForResult(intent, FINISH_TAKE_PHOTO);
					dialog.dismiss();
					break;
				case R.id.get_photo_button:
					intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
					intent.putExtra("return-data", true);
					((Activity) context).startActivityForResult(intent, FINISH_GET_IMAGE);
					dialog.dismiss();
					break;
				default:
					break;
				}
			}
		};
        
    }
}
