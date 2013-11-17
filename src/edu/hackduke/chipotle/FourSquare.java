package edu.hackduke.chipotle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

public class FourSquare extends AsyncTask<Object, Void, Void>  {

	ChipotleActivity c;
	LocationClient lc;
	ArrayList<Geofence> gfs;
	ArrayList<Location> chs;

	public FourSquare(Context c, LocationClient lc) {
		this.c = (ChipotleActivity) c;
		this.lc = lc;
		this.gfs = new ArrayList<Geofence>();
	}

	@Override
	protected void onPostExecute(Void v) {

		Log.d("make", "notification");
		CheckNotification cn = new CheckNotification(this.c, this.chs);
		cn.execute();

	}

	@Override
	protected Void doInBackground(Object... arg0) {

		int radius = 160000;
		String client_id = "U0YLO2X2GKDIXAHUVF51OOP5TUDXG0VOXEY5YIEEO45ODH1O";
		String client_secret = "E2SHC2LGHTK3OV3SCSSBFMWLEO4ERAK5RL2BGZIN31UQ0FAK";
		String query = ChipotleActivity.query;

		Location l = (Location) arg0[0];
		// Include call to latitude/longitude
		double latitude = l.getLatitude();
		double longitude = l.getLongitude();

		JSONObject results = null;
		try {
			URL url = new URL(
					String.format(
							"https://api.foursquare.com/v2/venues/search?client_id=%s&client_secret=%s&v=20130815&ll=%f,%f&query=%s&radius=%d",
							client_id, client_secret, latitude, longitude,
							query, radius));
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream stream = connection.getInputStream();

			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));
			String inputLine;
			StringBuilder sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null)
				sb.append(inputLine);
			in.close();

			results = new JSONObject(sb.toString());
		} catch (Exception e) { // Yes. I'm a bad person.
			e.printStackTrace();
		}

		Log.v("JSON Results: ", results.toString());
		// for(Iterator<String> iter = results.keys(); iter.hasNext();) {
		// Log.v("thing", iter.next());
		// }
		//

		this.chs = new ArrayList<Location>();

		try {
			JSONObject response = (JSONObject) results.get("response");
			JSONArray venues = response.getJSONArray("venues");
			for (int i = 0; i < venues.length(); i++) {
				JSONObject thing = (JSONObject) venues.get(i);
				JSONObject loc = (JSONObject) thing.get("location");
				double lat = (Double) loc.get("lat");
				double lng = (Double) loc.get("lng");
				Location ch = new Location("");
				ch.setLatitude(lat);
				ch.setLongitude(lng);
				Log.v("ch", ch.getLatitude() + " " + ch.getLongitude());
				chs.add(ch);
			}
			DistanceView.ls = new ArrayList<Location>(chs);
			this.c.chs = DistanceView.ls;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		

		return null;
	}

	@Override
	protected void onCancelled() {
		// don't freak out
	}



	/*
	 * Results object can also be iterated over, e.g. for(Iterator<String> iter
	 * = json.keys();iter.hasNext();) { String key = iter.next(); ... }
	 */

}
