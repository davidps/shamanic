package com.davidps.shamanic.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONLocation {

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
	
	public boolean IsJSONArrayPerimeter(JSONArray jarray) throws JSONException {
		int n = (jarray.length() - 1);
		if (jarray.getJSONObject(0) == jarray.getJSONObject(n))
			return true;
		else
			return false;
	}
	
	
}
