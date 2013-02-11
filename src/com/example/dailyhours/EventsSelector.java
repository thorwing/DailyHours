package com.example.dailyhours;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.dailyhours.EventsXmlParser.Event;

public class EventsSelector extends Activity {	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_selector);
		
		Intent intent = getIntent();
		final String category = intent.getStringExtra(DailyHours.EVENT_CATEGORY);		
		
		AssetManager assetMgr = this.getAssets();		
		EventsXmlParser parser = new EventsXmlParser();
		InputStream stream = null;		
		try {
			stream = assetMgr.open("events.xml");
			final List<Event> events = parser.parse(stream, category);
			
			GridView gridView = (GridView) findViewById(R.id.gridview);
			gridView.setAdapter(new EventIconAdapter(this, events));		
			
			gridView.setOnItemClickListener(new OnItemClickListener() {			
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					String message = events.get(position).title;
					
					Intent intent = new Intent(v.getContext(), TileAdder.class);
					intent.putExtra(DailyHours.EVENT_TYPE, message);
					intent.putExtra(DailyHours.EVENT_CATEGORY, category);
					v.getContext().startActivity(intent);
					
//					Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
				}
			});
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

}
