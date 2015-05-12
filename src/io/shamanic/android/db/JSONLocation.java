package io.shamanic.android.db;

import java.util.ArrayList;
import java.util.List;

import io.shamanic.android.db.src_gen.DaoSession;
import io.shamanic.android.db.src_gen.user;
import io.shamanic.android.db.src_gen.userDao;
import io.shamanic.android.db.src_gen.userlocation;
import io.shamanic.android.db.src_gen.userlocationDao;
import io.shamanic.android.db.src_gen.userlocationDao.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONLocation {

	DaoSession daoSession;
	userlocationDao locDao;
	userDao userDao;
	
	public JSONObject writeJSON(String lat, String lng, String timestamp) throws JSONException {
		
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("KEY_ID", Math.floor(Math.random() * 10000));
			jobj.put("KEY_LATITUDE", lat);
			jobj.put("KEY_LONGITUDE", lng);
			jobj.put("KEY_TIMESTAMP", timestamp);
		} catch (JSONException e) {
			
		}
		return jobj;
	}

	public JSONArray writeJSONArray(JSONObject jobj) throws JSONException {
		JSONArray locArray = new JSONArray();
		return locArray.put(jobj);
	}
	
	public JSONArray setJSONArrayPerimeter(JSONArray jarray) throws JSONException {
		JSONArray perimeter = null;
		if (IsJSONArrayPerimeter(jarray))
			return perimeter;
		return null;
	}
	
	public String getJsonArrayPerimeter(JSONObject jobj) throws JSONException {
		JSONArray jarray = new JSONArray();
		//jarray.
		return dumpJsonToShamanicDb(jarray);
	}
	
	public boolean IsJSONArrayPerimeter(JSONArray jarray) throws JSONException {
		int n = (jarray.length() - 1);
		if (jarray.getJSONObject(0) == jarray.getJSONObject(n))
			return true;
		else
			return false;
	}
	
	private JSONArray createJSONArrayFromDAO(long userId) throws JSONException {
		JSONArray locArray = new JSONArray();
		locArray.put((double)userId);
		
		locDao = daoSession.getUserlocationDao();
		List<userlocation> locsList = locDao.queryBuilder().where(Properties.UserId.eq(userId)).list();
		
		//ArrayList<Double> values = new ArrayList<>();
		double[] values = new double[11];
		for (int i = 0; i < values.length; i++) {
			values[i] = locsList.get(i).getLatitude();
		}
		for (int j = 0; j < values.length; j++) {
			locArray.put(values[j]);
		}
				
		return locArray;
		
		
	}
	
	private String dumpJsonToShamanicDb(JSONArray locArray) {
		String stringArrayOfJson = "";
		//check for extenuating circumstances
		
		
		//get which user is requesting the dump, set up their postgres instance
		userDao = daoSession.getUserDao();
		userDao.getProperties();
		//long userId = userDao.queryBuilder().where(arg0, arg1)
		//everything's good, dump - need to get the userId from the session variable? or, no, just the GreenDao.
		
		//createJSONArrayFromDAO()
		try {
			stringArrayOfJson = locArray.toString(4);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//sendPostRequest(stringArrayOfJson);
	
		return stringArrayOfJson;
	}
	
	
	private void sendPostRequest(String stuffToSend) {
		// attach the JSON string to the response object
		
	}
	
}
