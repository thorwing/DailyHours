package com.example.dailyhours.model;

public class Tile {
	public final String title;
	public final String type;
	public final String category;
	public final int iconId;
	
	public Tile(String title, String category, String type, int iconId) {
		this.title = title;
		this.type = type;
		this.category = category;
		this.iconId = iconId;
	}	
}
