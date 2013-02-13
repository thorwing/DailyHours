package com.example.dailyhours;

import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class PlayActivity extends MetroActivity {
	public static final String PLAY_TYPE = "play";	
		
	public PlayActivity(){		
		super(R.layout.activity_play, PLAY_TYPE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_play, menu);
		return true;
	}	
	
	/** Called when the user clicks the Add button */
	public void addTile(View view) {
		Intent intent = new Intent(this, EventsSelector.class);
		intent.putExtra(DailyHours.EVENT_CATEGORY, mCategory);		
		startActivity(intent);
	}		
}
