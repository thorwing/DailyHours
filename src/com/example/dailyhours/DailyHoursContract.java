package com.example.dailyhours;

import android.net.Uri;
import android.provider.BaseColumns;

public class DailyHoursContract {
	public static String AUTHORITY = "com.example.dailyhours.model.DailyHoursContract";
	
	public static abstract class Tile implements BaseColumns {
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/tile");		
		
		public static final String TABLE_NAME = "tiles";
		public static final String COLUMNE_NAME_TITLE = "title";
		public static final String COLUMNE_NAME_CATEGORY = "category";
		public static final String COLUMNE_NAME_TYPE = "type";		
		public static final String COLUMNE_NAME_ICON = "icon";
		
		private Tile() {}	
	}	
	
	public static abstract class Event implements BaseColumns {
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/event");
		
		public static final String TABLE_NAME = "events";
		public static final String COLUMNE_NAME_TITLE = "title";
		public static final String COLUMNE_NAME_ICON = "icon";
		
		private Event() {}
	}
}
