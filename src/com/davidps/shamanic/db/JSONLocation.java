package com.davidps.shamanic.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONLocation {

	public JSONObject writeJSON(String lat, String lng, String timestamp) throws JSONException {
		
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("KEY_ID", Math.random());
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
}
