package com.example.dailyhours;


import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DailyHoursDbHelper extends SQLiteOpenHelper {
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "DailyHours.db";
	
	private static final String SQL_CREATE_TILES = 
		"CREATE TABLE " + DailyHoursContract.Tile.TABLE_NAME + " (" +
		DailyHoursContract.Tile._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +  
		DailyHoursContract.Tile.COLUMNE_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
		DailyHoursContract.Tile.COLUMNE_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
		DailyHoursContract.Tile.COLUMNE_NAME_CATEGORY + TEXT_TYPE + 
		" )";
	private static final String SQL_DELETE_TILES = 
		"DROP TABLE IF EXISTS " + DailyHoursContract.Tile.TABLE_NAME;	
	
//	private static final String SQL_CREATE_EVENTS = 
//			"CREATE TABLE " + DailyHoursContract.Event.TABLE_NAME + " (" +
//			DailyHoursContract.Event._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +  
//			DailyHoursContract.Event.COLUMNE_NAME_TITLE + TEXT_TYPE +
//			" )";
//	private static final String SQL_DELETE_EVENTS = 
//		"DROP TABLE IF EXISTS " + DailyHoursContract.Event.TABLE_NAME;	
	
	public DailyHoursDbHelper(Context context) {			
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}		
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TILES);
//		db.execSQL(SQL_CREATE_EVENTS);			
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over
		db.execSQL(SQL_DELETE_TILES);
//		db.execSQL(SQL_DELETE_EVENTS);
		onCreate(db);			
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }			
}
