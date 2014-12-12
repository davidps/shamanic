package com.davidps.shamanic.db;

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
	
	JSONLocation jloc = new JSONLocation();

	public SQLiteDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create location table
		String CREATE_LOCATION_TABLE = "CREATE TABLE location ( "
				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "latitude REAL, "
				+ "longitude REAL, " + "JSONLatLong TEXT,"
				+ "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";

		// create table
		db.execSQL(CREATE_LOCATION_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteDbHelper.class.getName(),
				"Upgrading the business from old 'n' busted " + oldVersion
						+ " to" + newVersion
						+ ", the new hotness. Destroys all data.");
		// Drop older location table if exists
		db.execSQL("DROP TABLE IF EXISTS location");

		// create fresh location table
		this.onCreate(db);
	}

	// Location table name
	private static final String TABLE_LOCATION = "location";

	// Location Table Columns names
	private static final String KEY_ID = "ID";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_JSONLATLONG = "JSONLatLong";
	private static final String KEY_TIMESTAMP = "timestamp";

	private static final String[] COLUMNS = { KEY_ID, KEY_LATITUDE,
			KEY_LONGITUDE, KEY_JSONLATLONG, KEY_TIMESTAMP };

	/** the current location for the user */
	protected CurrentLocation currentLocation;
	
	public void addLocationToDB(String latitude, String longitude, String timestamp) throws JSONException {
		// for logging
		Log.d("addLocation", TABLE_LOCATION.toString());

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		if (latitude != null)
			values.put(KEY_LATITUDE, latitude); // get latitude
		if (longitude != null)
			values.put(KEY_LONGITUDE, longitude); // get longitude
		values.put(KEY_TIMESTAMP, timestamp); // get timestamp
		values.put(KEY_JSONLATLONG, jloc.writeJSON(latitude, longitude, timestamp).toString()); // get JSON object from lat/long/timestamp

		// 3. insert
		db.insert(TABLE_LOCATION, // table
				null, // nullColumnHack
				values); // key/value -> keys = columnnames / values = columnvalues

		// 4. close
		db.close();
	}

}
