package com.beautifulpromise.application.addpromise;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.beautifulpromise.R;
import com.beautifulpromise.common.utils.ImageUtils;

public class LoadDonation {
	
	Context context;
	
	LoadDonation (Context context) {
		this.context = context;
	}

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
		donation.setAfterDrawable(loadResource(parser.getAttributeValue(4)));
		
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
