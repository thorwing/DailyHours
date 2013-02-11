package com.example.dailyhours;

import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.widget.TabHost;

public class DailyHours extends TabActivity
		implements LoaderManager.LoaderCallbacks<Cursor>{

	public static final String TAG = "DailyHours";
	public final static String EVENT_CATEGORY = "com.example.dailyhours.EVENT_CATEGORY";
	public final static String EVENT_TYPE = "com.example.dailyhours.EVENT_TYPE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TabHost tabHost = getTabHost();			
		TabHost.TabSpec spec;  // Reusable TabSpec for each tab
		
		Intent intent;
		
		// Create an Intent to launch an Activity for the tab (to be reused)  
		intent = new Intent().setClass(this, WorkActivity.class);
	
		// Initialize a TabSpec for each tab and add it to the TabHost  
		spec = tabHost.newTabSpec("My Works").setIndicator("work").setContent(intent);
		tabHost.addTab(spec);
		
		// Do the same for other tabs
		intent = new Intent().setClass(this, PlayActivity.class);		
		
		spec = tabHost.newTabSpec("My plays").setIndicator("play").setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}		
}
