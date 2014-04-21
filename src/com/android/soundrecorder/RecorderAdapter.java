package com.android.soundrecorder;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.util.Log;
import android.widget.ProgressBar;

class RecorderAdapter extends SimpleCursorAdapter
{
	private LayoutInflater mInflater;
	private int displayName;
	private int title;
	private int duration;
	private int data;
	private long hour, min, sec;

	public RecorderAdapter(Context context, int layout, Cursor cursor, String[] from,
			int[] to) {
		super(context, layout, cursor, from, to);
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		
		cursor.moveToFirst();
		
		displayName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
		title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
		duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
		data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		if(view==null){
			view = mInflater.inflate(R.layout.listview_item,null);
		}

		TextView displayNameTextView = (TextView)view.findViewById(R.id._display_name);
		TextView titleTextView = (TextView)view.findViewById(R.id.title);
		//TextView durationTextView = (TextView)view.findViewById(R.id.duration);
		
		long mSeconds = cursor.getLong(duration);
		mSeconds/=1000;
		hour = mSeconds/3600;
		min = mSeconds/60;
		if(min >= 60)
			min = min % 60;
		sec = mSeconds % 60;
		StringBuilder stringBuilder = new StringBuilder();
		if( 0 == hour ){
			if( 0 == min ){
				if( 0 == sec ) sec = 1;
				stringBuilder.append(String.format("  %d sec", sec));
			}
			else{
				stringBuilder.append(String.format("  %d min ", min));
				stringBuilder.append(String.format("%d sec", sec));
			}
		}
		else{
			stringBuilder.append(String.format("  %d hour ", hour));
			stringBuilder.append(String.format("%d min ", min));
			stringBuilder.append(String.format("%d sec", sec));
		}
		displayNameTextView.setText(cursor.getString(displayName));
		titleTextView.setText(cursor.getString(title)+stringBuilder.toString());
		//durationTextView.setText(stringBuilder.toString());
		String dataStr = cursor.getString(data);
		view.setTag(dataStr);
		view.setTag(R.id.MEDIASTORE_AUDIO_MEDIA_DURATION, mSeconds);
		
		if( null != mPlayingData && null != mStateProgressBar ){
			RelativeLayout layout = (RelativeLayout) view;
			if (dataStr.equals(mPlayingData)) {
					RelativeLayout layout1 = (RelativeLayout) mStateProgressBar.getParent();
					if( null != layout1 ){
						layout1.removeView(mStateProgressBar);
					}
					layout.addView(mStateProgressBar, 0);
			} else {
					layout.removeView(mStateProgressBar);
			}
		}
	}
		
	private String mPlayingData = null;
	private ProgressBar mStateProgressBar = null;
	
	public String getPlayingData(){
		Log.i("zhengwenhui","RecorderAdapter getPlayingData:"+mPlayingData);		
		return mPlayingData;
	}
	
	public void setPlayingData(String data){
		mPlayingData = data;
		Log.i("zhengwenhui","RecorderAdapter setPlayingData:"+mPlayingData);
	}
	public void setProgressBar(ProgressBar data){
		mStateProgressBar = data;
	}
	
}
