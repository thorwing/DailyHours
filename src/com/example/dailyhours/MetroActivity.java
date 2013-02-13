package com.example.dailyhours;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.dailyhours.model.Tile;

public abstract class MetroActivity extends Activity {
	protected int mLayoutId;
	protected String mCategory;
	protected List<Tile> mTiles;
	protected DailyHoursDbHelper mDbHelper;
	
	private static final String[] PROJECTION = new String[] {DailyHoursContract.Tile._ID,
		DailyHoursContract.Tile.COLUMNE_NAME_TITLE, DailyHoursContract.Tile.COLUMNE_NAME_CATEGORY, DailyHoursContract.Tile.COLUMNE_NAME_TYPE, DailyHoursContract.Tile.COLUMNE_NAME_ICON};
	
	public MetroActivity(){}
	
	public MetroActivity(int layoutId, String category) {		
		this.mLayoutId = layoutId;
		this.mCategory = category;			
	}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(mLayoutId);
		
		mDbHelper = new DailyHoursDbHelper(this);
		this.mTiles = getTiles();
		
		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new TileAdapter(this, mTiles));		
		
		gridView.setOnItemClickListener(new OnItemClickListener() {			
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Tile tile = mTiles.get(position);								
				StringBuffer message = new StringBuffer(position + "." + tile.title);				
				
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
			}
		});
	}	
	
	private List<Tile> getTiles(){
		List<Tile> tiles = new ArrayList();
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		String sortOrder =
				DailyHoursContract.Tile.COLUMNE_NAME_TITLE + " DESC";
		
		Cursor c = db.query(
		    DailyHoursContract.Tile.TABLE_NAME,  // The table to query
		    PROJECTION,                               // The columns to return
		    DailyHoursContract.Tile.COLUMNE_NAME_CATEGORY + "=?",                                // The columns for the WHERE clause
		    new String[] {mCategory},                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    sortOrder                                 // The sort order
	    );
		
		while (c.moveToNext()) {  
            int _id = c.getInt(c.getColumnIndex(DailyHoursContract.Tile._ID));  
            String title = c.getString(c.getColumnIndex(DailyHoursContract.Tile.COLUMNE_NAME_TITLE));  
            String category = c.getString(c.getColumnIndex(DailyHoursContract.Tile.COLUMNE_NAME_CATEGORY));
            String type = c.getString(c.getColumnIndex(DailyHoursContract.Tile.COLUMNE_NAME_TYPE));
            int iconId = c.getInt(c.getColumnIndex(DailyHoursContract.Tile.COLUMNE_NAME_ICON));
            tiles.add(new Tile(title, category, type, iconId));
            
            Log.v("db", "_id=>" + _id + ", title=>" + title);  
        }  
        c.close();  
		
        return tiles;
	}
}
