package com.davidps.shamanic.db;

import java.util.List;

import com.davidps.shamanic.location.CurrentLocation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;

public class SQLiteDbHelper extends SQLiteOpenHelper {
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "shamanicDB";
	// Table names
	private static final String TABLE_LOCATION = "location";
	private static final String TABLE_PERIMETER = "perimeter";
	private static final String TABLE_TAG = "tags";
	// Location Table Columns names
	private static final String KEY_LOCATION_ID = "ID";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_JSONLATLONG = "JSONLatLong";
	private static final String KEY_TIMESTAMP = "timestamp";
	// Perimeter Table Column Names
	private static final String KEY_PERIMETER_ID = "ID";
	private static final String KEY_PERIMETER_DATA = "perimeter";
	private static final String KEY_CENTROID = "centroid";
	private static final String KEY_FEATURES = "features";
	private static final String KEY_PERIMETER_NAME = "name";
	private static final String KEY_PERIMETER_TIMESTAMP = "timestamp";
	// Tag Table Column Names
	private static final String KEY_TAG_ID = "ID";
	private static final String KEY_TAGLAT = "tagLat";
	private static final String KEY_TAGLONG = "tagLong";
	private static final String KEY_TAG_FEATURES = "features";
	private static final String KEY_TAG_NAME = "name";
	private static final String KEY_TAG_TIMESTAMP = "timestamp";

	private static final String[] COLUMNS = { KEY_LOCATION_ID, KEY_LATITUDE,
			KEY_LONGITUDE, KEY_JSONLATLONG, KEY_TIMESTAMP };
	
	// SQL statement to create location table
	private static final String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION + "( "
		+ KEY_LOCATION_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_LATITUDE + "TEXT, "
		+ KEY_LONGITUDE + "TEXT, " + KEY_JSONLATLONG +"TEXT, "
		+ KEY_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";
	
	private static final String CREATE_TABLE_PERIMETER = "CREATE TABLE " + TABLE_PERIMETER + "( "
			+ KEY_PERIMETER_ID + "INTEGER PRIMARY KEY, " + KEY_PERIMETER_DATA + "TEXT, " + KEY_CENTROID + "TEXT, "
			+ KEY_FEATURES + "TEXT, " + KEY_PERIMETER_NAME + "TEXT, " + KEY_PERIMETER_TIMESTAMP + "TEXT )";
	
	private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG + "( "
			+ KEY_TAG_ID + "INTEGER PRIMARY KEY, " + KEY_TAGLAT + "TEXT, " + KEY_TAGLONG + "TEXT, "
			+ KEY_TAG_FEATURES + "TEXT, " + KEY_TAG_NAME + "TEXT, "
			+ KEY_TAG_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";
	
	JSONLocation jloc = new JSONLocation();

	public SQLiteDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		// create table
		db.execSQL(CREATE_TABLE_LOCATION);
		db.execSQL(CREATE_TABLE_PERIMETER);
		db.execSQL(CREATE_TABLE_TAG);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteDbHelper.class.getName(),
				"Upgrading the business from old 'n' busted " + oldVersion
						+ " to" + newVersion
						+ ", the new hotness. Destroys all data.");
		// Drop older tables if they exist
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIMETER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);

		// create fresh tables
		this.onCreate(db);
	}


	/** the current location for the user */
	protected CurrentLocation currentLocation;
	
	public void addLocation(String latitude, String longitude, String timestamp) throws JSONException {
		// for logging
		Log.d("addLocation", TABLE_LOCATION.toString());

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.putNull(KEY_LOCATION_ID);
		if (latitude != null)
			values.put(KEY_LATITUDE, latitude); // get latitude
		if (longitude != null)
			values.put(KEY_LONGITUDE, longitude); // get longitude
		values.put(KEY_JSONLATLONG, jloc.writeJSON(latitude, longitude, timestamp).toString()); // get JSON object from lat/long/timestamp
		values.put(KEY_TIMESTAMP, timestamp); // get timestamp
		

		// 3. insert
		db.insert(TABLE_LOCATION, // table
				null, // nullColumnHack
				values); // key/value -> keys = columnnames / values = columnvalues

		// 4. close
		db.close();
	}
	
	public void addTag(String ID, String latitude, String longitude, String tagName, String timestamp) throws JSONException {
		
		Log.d("addTag", TABLE_TAG.toString());
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_TAG_ID, ID);
		if (latitude != null) //this doesn't work - if this is null, we should stop the operation
			values.put(KEY_TAGLAT, latitude); // get latitude
		if (longitude != null)
			values.put(KEY_TAGLONG, longitude);
		if (tagName != null)
			values.put(KEY_TAG_NAME, tagName);
		else
			values.putNull(KEY_TAG_NAME);
		values.put(KEY_TIMESTAMP, timestamp);
		
		db.insert(TABLE_TAG, null, values);
		
		//as the tag will also need to be referenced on the master location table, insert that data here
		addLocation(latitude, longitude, timestamp);
		
		db.close();
	}

	public List<JSONLocation> getAllPerimeters() {
		return null;	
	}
	
}
