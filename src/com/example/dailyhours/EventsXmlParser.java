package com.example.dailyhours;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.example.dailyhours.model.Event;

public class EventsXmlParser {
	private static final String ns = null;
	private Context mContext;
	
	public EventsXmlParser(Context context) {
		mContext = context;
	}
	
	
	public List parse(InputStream in, String category) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
	        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	        parser.setInput(in, null);
	        parser.nextTag();
	        return readEvents(parser, category);
		} finally {
			in.close();			
		}                
	}
		
	private List readEvents(XmlPullParser parser, String category) throws XmlPullParserException, IOException {
	    List events = new ArrayList();

	    parser.require(XmlResourceParser.START_TAG, ns, category);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        // Starts by looking for the entry tag
	        if (name.equals("event")) {
	        	events.add(readEvent(parser));
	        } else {
	            skip(parser);
	        }
	    }  
	    return events;
	}
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }		
		
	private Event readEvent(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "event");
		String title = null, icon = null;
		while(parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			
			String name = parser.getName();
	        if (name.equals("title")) {
	            title = readTitle(parser);
	        } 
	        else if (name.equals("icon")) {
	            icon = readIcon(parser);
	        }
	        else {
	            skip(parser);
	        }
		}
		
		int iconID = mContext.getResources().getIdentifier(icon, "drawable", mContext.getPackageName());     
		return new Event(title, iconID);
	}
	
	// Processes title tags.
	private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, "title");
	    String title = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "title");
	    return title;
	}
	
	// Processes icon tags.
	private String readIcon(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, "icon");
	    String icon = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "icon");
	    return icon;
	}
	
	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}			
}
