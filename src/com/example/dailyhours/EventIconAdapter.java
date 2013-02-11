package com.example.dailyhours;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.dailyhours.EventsXmlParser.Event;

public class EventIconAdapter extends BaseAdapter {
	private Context mContext;
	public List<Event> mEvents; 
	
	public EventIconAdapter(Context c, List<Event> events) {
		mContext = c;
		mEvents= events;
	}
	
	public int getCount() {
		return mEvents.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(108, 108));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(6, 6, 6, 6);
        } else {
            imageView = (ImageView) convertView;
        }
        int resID = mContext.getResources().getIdentifier(mEvents.get(position).icon, "drawable", mContext.getPackageName());       

        imageView.setImageResource(resID);
        return imageView;
	}	
}
