package com.example.dailyhours;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.dailyhours.model.Event;

public class EventsSelector extends ListActivity {
			
	public static final String TAG = "ListActivity";	
	private SimpleAdapter mAdapter;
	private List<Event> mEvents;
	private String mCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_selector);
		
		Intent intent = getIntent();
		mCategory = intent.getStringExtra(DailyHours.EVENT_CATEGORY);						
		
		AssetManager assetMgr = this.getAssets();		
		EventsXmlParser parser = new EventsXmlParser(this);
		InputStream stream = null;		
		try {
			stream = assetMgr.open("events.xml");
			mEvents = parser.parse(stream, mCategory);					
			
			ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();  
			
			for(int i=0; i < mEvents.size(); i++)  
	        {  
	            HashMap<String, Object> map = new HashMap<String, Object>();  
	            map.put(DailyHoursContract.Event.COLUMNE_NAME_TITLE, mEvents.get(i).title);
	            map.put(DailyHoursContract.Event.COLUMNE_NAME_ICON, mEvents.get(i).iconId);  
	            listItem.add(map);  
	        }  
			
			String[] fromColumns = {DailyHoursContract.Event.COLUMNE_NAME_TITLE, DailyHoursContract.Event.COLUMNE_NAME_ICON};			
			int[] toViews = {R.id.label, R.id.icon};																	
			
			mAdapter = new SimpleAdapter(this, listItem, R.layout.listed_event, fromColumns, toViews);        			
			
			setListAdapter(mAdapter);						
		} 
		catch(IOException e) {}
		catch(XmlPullParserException e) {
			System.out.println(e.toString());
		}
		finally {}							
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_events_selector, menu);
		return true;
	}			
	
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {		 
		Event event = mEvents.get(position);
		
		Intent intent = new Intent(this, TileAdder.class);
		intent.putExtra(DailyHours.EVENT_CATEGORY, mCategory);
		intent.putExtra(DailyHours.EVENT_TYPE, event.title);
		intent.putExtra(DailyHours.EVENT_ICON, event.iconId);
		
		startActivity(intent);
    }
}
