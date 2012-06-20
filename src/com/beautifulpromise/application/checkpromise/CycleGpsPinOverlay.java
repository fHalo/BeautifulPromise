package com.beautifulpromise.application.checkpromise;

import java.util.ArrayList;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * 구글맵 PinOverlay클래스
 * @author ou
 *
 */
public class CycleGpsPinOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public CycleGpsPinOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	@Override
	protected OverlayItem createItem(int arg0) {
		return mOverlays.get(arg0);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlayItem(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
}