package com.beautifulpromise.application.addpromise;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.beautifulpromise.R;
import com.beautifulpromise.common.dto.AddPromiseDTO;
import com.beautifulpromise.common.utils.ImageUtils;

public class DonationDialog extends Dialog{
	
    public DonationDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public DonationDialog(Context context) {
		super(context);
	}
	public static class Builder {
    	 
        private Context context;
    	private DonationDialog dialog;
    	View layout;
    	ListView donationListView;
    	Button okayBtn;
    	AddPromiseDTO promiseDTO;

        public Builder(Context context, AddPromiseDTO promiseDTO) {
            this.context = context;
            this.promiseDTO = promiseDTO;
        }
        
        public DonationDialog create() {
        	        	
        	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	dialog = new DonationDialog(context, R.style.Theme_Dialog);
            layout = inflater.inflate(R.layout.addpromise_donation_dialog, null);
            
            donationListView = (ListView) layout.findViewById(R.id.donation_list_view);
            
            ArrayList<DonationDTO> donationList = loadDonation();
            
            DonationAdapter adapter = new DonationAdapter(context, donationList);
            donationListView.setAdapter(adapter);
            donationListView.setOnItemClickListener(mItemClickListener);
            
            okayBtn = (Button) layout.findViewById(R.id.okay_button);
            
            okayBtn.setOnClickListener(buttonClick);
            dialog.setContentView(layout);			
            return dialog;
        }
        
        AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				DonationDTO donation = (DonationDTO) adapterView.getAdapter().getItem(position);
				promiseDTO.setDonation(donation);
				Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
			}
		};

		View.OnClickListener buttonClick = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.okay_button:
					
					if(promiseDTO.getDonation() != null){
						UploadFeedDialog.Builder uploadBuilder = new UploadFeedDialog.Builder(context, promiseDTO);
						Dialog uploadDialog = uploadBuilder.create();
						uploadDialog.show();
						dialog.dismiss();
					}else{
						Toast.makeText(context, "기부 프로젝트를 선택해주세요.", Toast.LENGTH_SHORT).show();
					}
					break;
				default:
					break;
				}
			}
		};
		
		protected ArrayList<DonationDTO> loadDonation() {
			ArrayList<DonationDTO> list = new ArrayList<DonationDTO>();
			XmlPullParser parser = context.getResources().getXml(R.xml.donation);
			try {
				while (parser.next() != XmlPullParser.END_DOCUMENT) {
					if ("donation".equals(parser.getName())) {
						list.add(add(parser));
						parser.next();
					}
				}
			} catch (XmlPullParserException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return list;
		}
		
		private DonationDTO add(XmlPullParser parser){
			
			DonationDTO donation = new DonationDTO();
			donation.setId(Integer.parseInt(parser.getAttributeValue(0)));
			donation.setTitle(parser.getAttributeValue(1));
			donation.setDetails(parser.getAttributeValue(2));
			donation.setDrawable(loadResource(parser.getAttributeValue(3)));
			
			return donation;
		}
		
		public Drawable loadResource(String path) {
			Drawable drawable = null;

			try {
				AssetManager assetManager = context.getAssets();
				InputStream is = assetManager.open(path);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				return ImageUtils.bitmapToDrawable(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return drawable;
		}
    }
}
