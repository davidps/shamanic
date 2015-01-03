package com.davidps.shamanic.location;

import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.preference.PreferenceManager;

/**
 * user's current / best known location
 * 
 * @author khinds
 */
public class CurrentLocation {

	/** user's latitude and longitude */
	public String latitude;
	public String longitude;

	/** user's area name and region name */
	public String areaNameValue;
	public String regionValue;
	
	/** timestamp for current location */
	public String timestamp;

	/**
	 * get the latitude and longitude location for the user
	 * 
	 * @param context
	 * @throws JSONException 
	 */
	public void getLocation(Context context) throws JSONException {

		/** get user's latitude and longitude */
		LatLong latLong = new LatLong();
		latLong.getLatLong(context);

		/** get most recent latitude and longitude for weather API */
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		latitude = preferences.getString("Latitude", null);
		longitude = preferences.getString("Longitude", null);

		/** default to new york, ny if all else fails */
		if (longitude == null && latitude == null) {
			longitude = "40.714623";
			latitude = "-74.006605";
			
		}
	}

	/**
	 * get the location name for the user as far as a city, country or state
	 * 
	 * @param context
	 */
	public void getLocationName(Context context, String longitude, String latitude) {
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		try {
			List<Address> list = geocoder.getFromLocation(Double.parseDouble(longitude), Double.parseDouble(latitude), 1);
			if (list != null && list.size() > 0) {
				Address address = list.get(0);
				areaNameValue = address.getLocality();
				regionValue = address.getCountryName();
			}
		} catch (Exception e) {
		}
	}
}
