package com.beautifulpromise.application.addpromise;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beautifulpromise.R;

/**
 * @description 캠페인 List 객체를 ListView에서 표시할 수 있게 해주는 Adapter
 * @author immk
 */
public class DonationAdapter extends BaseAdapter {
	
	Context context;
	private ArrayList<DonationDTO> donationList;
	private LayoutInflater inflater;
	ViewHolder holder;
	
	public DonationAdapter(Context context, ArrayList<DonationDTO> donationList) {
		this.context = context;
		this.setUserList(donationList);
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return getUserList().size();
	}

	@Override
	public Object getItem(int position) {
		return getUserList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public class ViewHolder {
		public TextView donationTitle;
		public TextView donationDetails;
		public ImageView donationImage;
		public LinearLayout donationLayout;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DonationDTO donation = getUserList().get(position);

		if(convertView == null) {
			convertView = inflater.inflate(R.layout.addpromise_donation_item, null);

			holder = new ViewHolder();
			holder.donationLayout = (LinearLayout) convertView.findViewById(R.id.donation_layout);
			holder.donationImage = (ImageView) convertView.findViewById(R.id.donation_image_view);
			holder.donationTitle = (TextView)convertView.findViewById(R.id.donation_title_text);
			holder.donationDetails = (TextView)convertView.findViewById(R.id.donation_details_text);
			convertView.setTag(holder);
		
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(donation != null) {			
			if(donation.getDrawable() != null)
				holder.donationImage.setImageDrawable(donation.getDrawable());
			holder.donationTitle.setText(donation.getTitle());
			holder.donationDetails.setText(donation.getDetails());
		}
		return convertView;
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public ArrayList<DonationDTO> getUserList() {
		return donationList;
	}

	public void setUserList(ArrayList<DonationDTO> donationList) {
		this.donationList = donationList;
	}

}
