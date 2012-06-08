package com.beautifulpromise.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.beautifulpromise.common.Var;

public class LockableScrollView extends HorizontalScrollView {

	public LockableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// true if we can scroll (not locked) 
    // false if we cannot scroll (locked) 
    private boolean mScrollable = true; 
 
    public void setIsScrollable(boolean scrollable) { 
        mScrollable = scrollable; 
    } 
    public boolean getIsScrollable()  {
        return mScrollable; 
    } 
 
    @Override 
    public boolean onTouchEvent(MotionEvent ev) { 
        switch (ev.getAction()) { 
            case MotionEvent.ACTION_DOWN: 
                // if we can scroll pass the event to the superclass 
                if (mScrollable) return super.onTouchEvent(ev); 
                // only continue to handle the touch event if scrolling enabled 
                return mScrollable; // mScrollable is always false at this point 
            default: 
                return super.onTouchEvent(ev); 
        } 
    } 
    
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		Log.e("menu : " + Var.menuShowFlag, "X :"+ ev.getX());
		
		if (Var.menuShowFlag) { // 메뉴바 show 상태
			if (ev.getX() > Var.LEFT_MENUBAR_CLOSE_RANGE) { // 메뉴바 넓이넘게 터치하면 touch event 뺐음
				mScrollable = true;
				return true;
			} else { // 메뉴바 범위내에서 터치하면 터치이벤트 뺏지않음
				mScrollable = true;
				return false;
			}
		} else { // 메뉴바 gone 상태
			if (ev.getX() > Var.LEFT_MENUBAR_OPEN_RANGE) { // 끝에 터치하지 않으면 lock함
				mScrollable = false;
				return false;
			} else { // 끝에 터치하면 unlock 함
				mScrollable = true;
				return true;
			}
			
		}
	}

} 


