package com.android.soundrecorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

public class TimerTextView extends Button {
	private final Typeface mAndroidClockMonoThin, mAndroidClockMonoBold, mAndroidClockMonoLight;
	private final Typeface mRobotoLabel;
	private final Paint mPaintBig = new Paint();
	private final Paint mPaintBigThin = new Paint();
	private final float mBigFontSize = 56, mSmallFontSize = 56;
	
	private long mSeconds;
	private int xCenter,yCenter;
	private float widthText,heightText;
	private float height;
	private long hour, min, sec;
	private boolean init = false;

	@SuppressLint("ResourceAsColor")
	public TimerTextView(Context context, AttributeSet attrs) {  
		super(context, attrs);  

		mAndroidClockMonoThin = Typeface.createFromAsset(
				context.getAssets(), "fonts/AndroidClockMono-Thin.ttf");
		mAndroidClockMonoBold = Typeface.createFromAsset(
				context.getAssets(), "fonts/AndroidClockMono-Bold.ttf");
		mAndroidClockMonoLight = Typeface.createFromAsset(
				context.getAssets(), "fonts/AndroidClockMono-Light.ttf");
		mRobotoLabel= Typeface.create("sans-serif-condensed", Typeface.BOLD);

		int WhiteColor = context.getResources().getColor(android.R.color.white);

		mPaintBig.setAntiAlias(true);
		mPaintBig.setStyle(Paint.Style.STROKE);
		mPaintBig.setTextAlign(Paint.Align.CENTER);
		mPaintBig.setTypeface(mAndroidClockMonoBold);
		mPaintBig.setTextSize(mBigFontSize);
		mPaintBig.setColor(WhiteColor);

		mPaintBigThin.setAntiAlias(true);
		mPaintBigThin.setStyle(Paint.Style.STROKE);
		mPaintBigThin.setTextAlign(Paint.Align.CENTER);
		mPaintBigThin.setTypeface(mAndroidClockMonoThin);
		mPaintBigThin.setTextSize(mBigFontSize);
		mPaintBigThin.setColor(WhiteColor);
	}

	public void setInit(){
 		init = false;
	}
	
	public void setSeconds(long seconds){
		mSeconds = seconds;
		this.invalidate();
	}

	@Override  
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);  
		if( !init ){
			xCenter = getWidth() / 2;
			yCenter = getHeight() / 2;

			widthText = mPaintBigThin.measureText("00");
			heightText = mPaintBig.getTextSize();

			height = yCenter + heightText/2;
			init = true;
			
			Log.i("onDraw init","xCenter:"+xCenter+",yCenter:"+yCenter+",widthText:"+widthText+",heightText:"+heightText);
		}
		
		hour = mSeconds/3600;
		min = mSeconds/60;
		if(min >= 60)
			min = min % 60;
		sec = mSeconds % 60;
		String hourStr, minStr, secStr;
		if( 0 == hour ){
			if( 0 == min ){
				secStr = String.format("%02d", sec);
				canvas.drawText(secStr, xCenter, height, mPaintBigThin);
			}
			else{
				minStr = String.format("%02d", min);
				secStr = String.format("%02d", sec);
				canvas.drawText(minStr, xCenter - widthText/2, height, mPaintBig);
				canvas.drawText(secStr, xCenter + widthText/2, height, mPaintBigThin);
			}
		}
		else{
			hourStr = String.format("%02d", hour);
			minStr = String.format("%02d", min);
			secStr = String.format("%02d", sec);
			canvas.drawText(hourStr, xCenter - widthText, height, mPaintBigThin);
			canvas.drawText(minStr, xCenter, height, mPaintBig);
			canvas.drawText(secStr, xCenter + widthText, height, mPaintBigThin);
		}
	}
}
