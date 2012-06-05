package com.beautifulpromise.application.addpromise;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
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
            
            ArrayList<DonationDTO> donationList = new ArrayList<DonationDTO>();
            DonationDTO donation1  = new DonationDTO();
            Bitmap bitmap1= ImageUtils.drawableToBitmap(context.getResources().getDrawable(R.drawable.donation_image1));
            donation1.setId(1);
            donation1.setBitmap(bitmap1);
            donation1.setTitle("우물프로젝트");

            DonationDTO donation2  = new DonationDTO();
            Bitmap bitmap2= ImageUtils.drawableToBitmap(context.getResources().getDrawable(R.drawable.donation_image2));
            donation2.setId(2);
            donation2.setBitmap(bitmap2);
            donation2.setTitle("사랑의 학교짓기");
            
            donationList.add(donation1);
            donationList.add(donation2);
            donationList.add(donation1);
            donationList.add(donation2);
            donationList.add(donation2);
            donationList.add(donation1);
            donationList.add(donation1);
            donationList.add(donation2);
            
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
        
    }
}
