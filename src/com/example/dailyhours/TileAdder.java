package com.example.dailyhours;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TileAdder extends Activity {

	private DailyHoursDbHelper mDbHelper; 
	private String mType;
	private String mCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tile_adder);
		
		Intent intent = getIntent();
		mType = intent.getStringExtra(DailyHours.EVENT_TYPE);
		mCategory = intent.getStringExtra(DailyHours.EVENT_CATEGORY);
		
		TextView textview = (TextView)findViewById(R.id.textView1); 
		textview.setText(mType);
		
		mDbHelper = new DailyHoursDbHelper(this);						
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tile_adder, menu);
		return true;
	}	
	
	public void createTile(View view){
		EditText editText = (EditText)findViewById(R.id.editText1);
		String message = editText.getText().toString();
		System.out.println(message);	
		
		insertTileRecord(message, mType, mCategory);
		
		Intent intent = new Intent(this, DailyHours.class);
		startActivity(intent);
	}
	
	
	private void insertTileRecord(String title, String type, String category) {				
		// Opens the database object in "write" mode.	
        SQLiteDatabase db = mDbHelper.getWritableDatabase();        

        ContentValues values = new ContentValues();
        
        if (values.containsKey(DailyHoursContract.Tile.COLUMNE_NAME_TITLE) == false) {
            values.put(DailyHoursContract.Tile.COLUMNE_NAME_TITLE, title);
        }
        
        if (values.containsKey(DailyHoursContract.Tile.COLUMNE_NAME_TYPE) == false) {
            values.put(DailyHoursContract.Tile.COLUMNE_NAME_TYPE, type);
        }
        
        if (values.containsKey(DailyHoursContract.Tile.COLUMNE_NAME_CATEGORY) == false) {
            values.put(DailyHoursContract.Tile.COLUMNE_NAME_CATEGORY, category);
        }
        
        // Performs the insert and returns the ID of the new note.
        long rowId = db.insert(
        		DailyHoursContract.Tile.TABLE_NAME,        // The table to insert into.
    		null,
            values                           // A map of column names, and the values to insert into the columns.
        );

        // If the insert succeeded, the row ID exists.
        if (rowId > 0) {         
            return;
        }
        
        // If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
        throw new SQLException("Failed to insert row");		
	}		
}
