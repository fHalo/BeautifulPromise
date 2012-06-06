package com.beautifulpromise.application.addpromise;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.beautifulpromise.R;
import com.beautifulpromise.common.dto.AddPromiseDTO;

public class SignViewDialog extends Dialog{
	

    public SignViewDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public SignViewDialog(Context context) {
		super(context);
	}
	public static class Builder {
    	 
        private Context context;
        View view;
    	private SignViewDialog dialog;
    	LinearLayout canvasLayout;
    	View signView;
    	Button okayBtn;
//    	Button removeBtn;
    	Button cancelBtn;
    	
        private Paint mPaint;
        AddPromiseDTO promiseDTO;
        
        public Builder(Context context, AddPromiseDTO promiseDTO ) {
            this.context = context;
            this.promiseDTO = promiseDTO;
        }
        
        public SignViewDialog create() {
        	        	
        	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	dialog = new SignViewDialog(context, R.style.Theme_Dialog);
            view = inflater.inflate(R.layout.addpromise_sign_canvas_dialog, null);
            
            canvasLayout = (LinearLayout) view.findViewById(R.id.sign_view);
            signView = new MyView(this.context);
            canvasLayout.addView(signView);
            
            okayBtn = (Button) view.findViewById(R.id.okay_button);
            cancelBtn = (Button) view.findViewById(R.id.cancel_button);
            
            okayBtn.setOnClickListener(buttonClickListener);
            cancelBtn.setOnClickListener(buttonClickListener);
            
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setColor(0xFF000000);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(12);
            
            dialog.setContentView(view);			
            return dialog;
        }
        
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.okay_button:
					canvasLayout.buildDrawingCache();
					Bitmap captureBitmap = canvasLayout.getDrawingCache();
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/capture.jpeg");
						captureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
						captureBitmap = Bitmap.createScaledBitmap(captureBitmap, canvasLayout.getWidth()/8, canvasLayout.getHeight()/8, true);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					promiseDTO.setSignBitmap(captureBitmap);
					DonationDialog.Builder donationBuilder = new DonationDialog.Builder(context, promiseDTO);
					Dialog donationDialog = donationBuilder.create();
					donationDialog.show();
					
					dialog.cancel();
					break;
					
//				case R.id.remove_button:
//					signView = null;
//					signView = new MyView(context);
//					canvasLayout.removeAllViews();
//					canvasLayout.addView(signView);
//					break;

				case R.id.cancel_button:
					dialog.cancel();
					break;
				default:
					break;
				}
			}
		};
        
    	public class MyView extends View {

    		private static final float MINP = 0.25f;
    		private static final float MAXP = 0.75f;

    		private Bitmap mBitmap;
    		private Canvas mCanvas;
    		private Path mPath;
    		private Paint mBitmapPaint;

    		public MyView(Context c) {
    			super(c);

    			mPath = new Path();
    			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    		}

    		@Override
    		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    			super.onSizeChanged(w, h, oldw, oldh);
    			mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    			mCanvas = new Canvas(mBitmap);
    		}

    		@Override
    		protected void onDraw(Canvas canvas) {
    			canvas.drawColor(0xFFFFFFFF);
    			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
    			canvas.drawPath(mPath, mPaint);
    		}

    		private float mX, mY;
    		private static final float TOUCH_TOLERANCE = 4;

    		private void touch_start(float x, float y) {
    			mPath.reset();
    			mPath.moveTo(x, y);
    			mX = x;
    			mY = y;
    		}

    		private void touch_move(float x, float y) {
    			float dx = Math.abs(x - mX);
    			float dy = Math.abs(y - mY);
    			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
    				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
    				mX = x;
    				mY = y;
    			}
    		}

    		private void touch_up() {
    			mPath.lineTo(mX, mY);
    			// commit the path to our offscreen
    			mCanvas.drawPath(mPath, mPaint);
    			// kill this so we don't double draw
    			mPath.reset();
    		}

    		@Override
    		public boolean onTouchEvent(MotionEvent event) {
    			float x = event.getX();
    			float y = event.getY();

    			switch (event.getAction()) {
    			case MotionEvent.ACTION_DOWN:
    				touch_start(x, y);
    				invalidate();
    				break;
    			case MotionEvent.ACTION_MOVE:
    				touch_move(x, y);
    				invalidate();
    				break;
    			case MotionEvent.ACTION_UP:
    				touch_up();
    				invalidate();
    				break;
    			}
    			return true;
    		}
    	}
	}
}
