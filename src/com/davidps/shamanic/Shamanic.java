package com.davidps.shamanic;

import com.davidps.shamanic.db.SQLiteDbHelper;
import com.davidps.shamanic.location.CurrentLocation;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import org.json.JSONException;

/**
 * main activitity for game
 * 
 * @author shamanic inc.
 */
public class Shamanic extends Activity {

	
	
	/** the current location for the user */
	protected CurrentLocation currentLocation;

	/** Make a database for the user's location values **/
	SQLiteDbHelper db = new SQLiteDbHelper(this);
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shamanic);

		/** get the latitude and longitude for the user */
		currentLocation = new CurrentLocation();

		/** drop the values for the locations into the SQLiteDB, using SQLiteDbHelper */
		/** TODO: set up the method(s) by which we can repeatedly check for new location values
		 *  in fact, it appears that http://developer.android.com/training/location/receive-location-updates.html
		 *  has the kind of code we will need to implement. Location Services, indeed!
		 */  
		try {
			//db.onCreate(mDB);
			currentLocation.getLocation(getBaseContext());
			db.addLocation(currentLocation.latitude, currentLocation.longitude, currentLocation.timestamp);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextView latitude = (TextView) findViewById(R.id.latitude);
		
		latitude.setText(currentLocation.latitude);

		TextView longitude = (TextView) findViewById(R.id.longitude);
		longitude.setText(currentLocation.longitude);

		TextView areaNameValue = (TextView) findViewById(R.id.areaNameValue);
		areaNameValue.setText(currentLocation.areaNameValue);

		TextView regionValue = (TextView) findViewById(R.id.regionValue);
		regionValue.setText(currentLocation.regionValue);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}