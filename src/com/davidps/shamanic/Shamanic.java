package com.davidps.shamanic;

import com.davidps.shamanic.location.CurrentLocation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * main activitity for game
 * 
 * @author shamanic inc.
 */
public class Shamanic extends Activity {

	/** the current location for the user */
	protected CurrentLocation currentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shamanic);

		/** get the latitude and longitude for the user */
		currentLocation = new CurrentLocation();
		currentLocation.getLocation(getBaseContext());

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