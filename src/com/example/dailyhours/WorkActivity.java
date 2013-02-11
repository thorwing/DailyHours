package com.example.dailyhours;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class WorkActivity extends ListActivity 
	implements LoaderManager.LoaderCallbacks<Cursor>{
	
	public static final String TAG = "WorkActivity";
	
	private ListView mTileList;
	private SimpleCursorAdapter mAdapter;
	private DailyHoursDbHelper mDbHelper;
	
	private static final String[] PROJECTION = new String[] {DailyHoursContract.Tile._ID,
		DailyHoursContract.Tile.COLUMNE_NAME_TITLE};
	
	private static final String SELECTION = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work);
		
		mTileList = (ListView)findViewById(android.R.id.list);
		mDbHelper = new DailyHoursDbHelper(this);
		
		// Populate the Tile list
        populateTileList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_work, menu);
		return true;
	}

	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
		return new CursorLoader(this, DailyHoursContract.Tile.CONTENT_URI, PROJECTION, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
		mAdapter.swapCursor(data);		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
		Log.v(TAG, "List item clicked");
        // Do something when a list item is clicked
    }
	
	/** Called when the user clicks the Add button */
	public void addTile(View view) {
		Intent intent = new Intent(this, EventsSelector.class);
		intent.putExtra(DailyHours.EVENT_CATEGORY, "work");		
		startActivity(intent);
	}		
	
	private void populateTileList() {
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		String sortOrder =
				DailyHoursContract.Tile.COLUMNE_NAME_TITLE + " DESC";
		
		Cursor c = db.query(
		    DailyHoursContract.Tile.TABLE_NAME,  // The table to query
		    PROJECTION,                               // The columns to return
		    null,                                // The columns for the WHERE clause
		    null,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    sortOrder                                 // The sort order
	    );
		
//		c.moveToFirst();
//		long itemId = c.getLong(
//		    c.getColumnIndexOrThrow(DailyHoursContract.Tile._ID)
//		);
		
//		System.out.println("result id is: " + (new Long(itemId)).toString());
		
		String[] fromColumns = {DailyHoursContract.Tile.COLUMNE_NAME_TITLE};			
		int[] toViews = {R.id.label}; // The TextView in simple_list_item_1							
				
		mAdapter = new SimpleCursorAdapter(this, 
			R.layout.rowlayout, c,			
            fromColumns, toViews, 0);        
//		
		setListAdapter(mAdapter);
				
//		mTileList.setAdapter(mAdapter);
//		mTileList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
		
//		getLoaderManager().initLoader(0, null, this);
	}
}
