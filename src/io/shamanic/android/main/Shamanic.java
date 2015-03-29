package io.shamanic.android.main;

import java.io.IOException;
import java.util.List;
import io.shamanic.android.db.src_gen.DaoMaster;
import io.shamanic.android.db.src_gen.DaoMaster.DevOpenHelper;
import io.shamanic.android.db.src_gen.DaoSession;
import io.shamanic.android.db.src_gen.location;
import io.shamanic.android.db.src_gen.locationDao;
import io.shamanic.android.db.src_gen.locationDao.Properties;
import io.shamanic.android.location.CurrentLocation;

import io.shamanic.android.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.app.Activity;
import android.content.IntentSender;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

/**
 * main activitity for game
 * 
 * @author shamanic inc.
 */
public class Shamanic extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

	private static final String TAG = Shamanic.class.getSimpleName();
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
	private static final int CONNECTION_FAILURE_RESOLUTION_RESULT = 9000;

	private long userId = 1234;
	private Location mLastLocation;
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
	// boolean flag to toggle periodic location updates
	private boolean mRequestingLocationUpdates = false;
	private LocationRequest mLocationRequest;

	// Location updates intervals in sec
	private static int UPDATE_INTERVAL = 10000; // 10 sec
	private static int FATEST_INTERVAL = 5000; // 5 sec
	private static int DISPLACEMENT = 10; // 10 meters

	// UI elements
	private TextView lblLocation;
	private Button btnShowLocation, btnStartLocationUpdates, btnListAllLocations;

	/** the current location for the user */
	protected CurrentLocation currentLocation;

	/** Make a database for the user's location values **/
	SQLiteDatabase db;

	DaoMaster daoMaster;
	DaoSession daoSession;
	// UserDao userDao;
	locationDao sLocationDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shamanic);

		try {
			DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shamanic-db", null);
			db = helper.getWritableDatabase();
			daoMaster = new DaoMaster(db);
			daoSession = daoMaster.newSession();
			// userDao = daoSession.getUserDao();
			sLocationDao = daoSession.getLocationDao();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// Location loc = new Location();

		/** get the latitude and longitude for the user */
		// currentLocation = new CurrentLocation();

		if (checkPlayServices()) {
			buildGoogleApiClient();
			createLocationRequest();
			addLocation();
		}

		/**
		 * drop the values for the locations into the SQLiteDB, using
		 * SQLiteDbHelper
		 */
		/**
		 * TODO: set up the method(s) by which we can repeatedly check for new
		 * location values in fact, it appears that
		 * http://developer.android.com/
		 * training/location/receive-location-updates.html has the kind of code
		 * we will need to implement. Location Services, indeed!
		 */
		// try {
		// db.onCreate(mDB);
		// currentLocation = new CurrentLocation();
		// Context baseContext = getBaseContext();
		// currentLocation.getLocation(baseContext);
		// LatLng latLng = new LatLng(lat, longi);
		// db.addLocation(currentLocation.latitude,
		// currentLocation.longitude, currentLocation.timestamp);
		// } catch (JSONException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// TextView latitude = (TextView) findViewById(R.id.latitude);
		// lblLocation = (TextView) findViewById(R.id.lblLocation);
		lblLocation = (TextView) findViewById(R.id.lblLocation);
		btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
		btnStartLocationUpdates = (Button) findViewById(R.id.btnLocationUpdates);
		btnListAllLocations = (Button) findViewById(R.id.btnListAllLocations);

		// latitude.setText(currentLocation.latitude);

		// TextView longitude = (TextView) findViewById(R.id.longitude);
		// longitude.setText(currentLocation.longitude);

		// TextView areaNameValue = (TextView) findViewById(R.id.areaNameValue);
		// areaNameValue.setText(currentLocation.areaNameValue);

		// TextView regionValue = (TextView) findViewById(R.id.regionValue);
		// regionValue.setText(currentLocation.regionValue);

		btnShowLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				displayLocation();
			}
		});

		btnStartLocationUpdates.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				togglePeriodicLocationUpdates();
			}
		});

		btnListAllLocations.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				printAllLocations();
			}
		});

	}

	protected void printAllLocations() {

		List<location> locs = sLocationDao.queryBuilder().where(Properties.UserId.eq(userId)).orderAsc(Properties.Id)
				.list();

		//ListIterator<location> lit = locs.listIterator();
			
		for (int i = 0; i < locs.size(); i++) {
		//while (lit.hasNext()) {
			String id = locs.get(i).getId().toString();
			String lat = locs.get(i).getLatitude().toString();
			String lng = locs.get(i).getLongitude().toString();
			String userid = locs.get(i).getUserId().toString();
			String data = "Id: " + id + ", Latitude: " + lat + ", Longitude: " + lng + ", userId: " + userid + ".";
			Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
		}
	}

	private void addLocation() {
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

		if (mLastLocation != null) {
			location loc = new location(null, mLastLocation.getLatitude(), mLastLocation.getLongitude(), userId);
			// sLocation loc2 = new sLocation(null, 23.3, 23.4, userId);
			sLocationDao.insert(loc);
			Log.d(TAG, "Inserted location from GoogleAPI, ID: " + loc.getId());
		} else {
			// startLocationUpdates();
			location loc = new location(null, 12.3456789, 98.7654321, userId);
			sLocationDao.insert(loc);
			Log.d(TAG, "Inserted fake location.");
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();

		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		checkPlayServices();
		if (mGoogleApiClient != null)
			// Resuming the periodic location updates
			// if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates)
			// {
			// startLocationUpdates();
			addLocation();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		stopLocationUpdates();

	}

	protected void startLocationUpdates() {
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

	}

	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
	}

	private void togglePeriodicLocationUpdates() {
		if (!mRequestingLocationUpdates) {
			btnStartLocationUpdates.setText(getString(R.string.btn_stop_location_updates));
			mRequestingLocationUpdates = true;

			startLocationUpdates();
			Log.d(TAG, "you're updating your location.");
		} else {
			btnStartLocationUpdates.setText(getString(R.string.btn_start_location_updates));
			mRequestingLocationUpdates = false;

			stopLocationUpdates();
			Log.d(TAG, "you're not updating your location any more.");
		}
	}

	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FATEST_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setSmallestDisplacement(DISPLACEMENT); // 10 meters

	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		}
		return true;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_RESULT);
			} catch (IntentSender.SendIntentException e) {
				e.printStackTrace();
			}
		} else {
			Log.d(TAG, "Connection services failed with this result code: " + connectionResult.getErrorCode());
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		displayLocation();

		if (mRequestingLocationUpdates) {
			startLocationUpdates();
		}
	}

	private void displayLocation() {
		// get this stuff from the db instead..

		if (mLastLocation != null) {
			double latitude = mLastLocation.getLatitude();
			double longitude = mLastLocation.getLongitude();
			// double speed = mLastLocation.getSpeed();
			// calc speed from deltaLocation
			if (lblLocation != null)
				lblLocation.setText(latitude + ", " + longitude);
		} else
			lblLocation.setText("Could not retrieve location.");
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();

	}

	@Override
	public void onLocationChanged(Location location) {
		mLastLocation = location;
		displayLocation();
		addLocation();
		Log.d(TAG + " Locations", "Location added, " + location.getLatitude() + ", " + location.getLongitude()
				+ ", Time: " + location.getTime());
	}
}