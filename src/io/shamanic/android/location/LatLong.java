package io.shamanic.android.location;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.widget.Toast;

/**
 * service to get the current user's location information
 * 
 * @author khinds
 */
public class LatLong {

	private Geocoder geocoder;
	private String bestProvider;
	private List<Address> user = null;
	public double lat;
	public double lng;
	public String timestamp;

	/**
	 * get the latitude and longitude of the user's device
	 * 
	 * @param context
	 * @return
	 */
	public void getLatLong(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		bestProvider = lm.getBestProvider(criteria, false);
		Location location = lm.getLastKnownLocation(bestProvider);

		if (location == null) {
			Toast.makeText(context, "Getting your last known location", Toast.LENGTH_SHORT).show();
		} else {
			geocoder = new Geocoder(context);
			try {
				user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				lat = (double) user.get(0).getLatitude();
				lng = (double) user.get(0).getLongitude();
				timestamp = (String) (DateFormat.format("MM-dd-yyyy hh:mm:ss", new java.util.Date()));

				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
				SharedPreferences.Editor prefsEditor = preferences.edit();
				prefsEditor.putString("Longitude", String.valueOf(lat) + "");
				prefsEditor.putString("Latitude", String.valueOf(lng) + "");
				prefsEditor.commit();
				Toast.makeText(context, "Retrieved your current location", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
			}
		}
	}
}
