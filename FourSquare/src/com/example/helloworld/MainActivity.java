package com.example.helloworld;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new QueryFourSquare().execute(); // execute thread
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

// thread definition
class QueryFourSquare extends AsyncTask<Void, Void, Void> {

	@Override
	protected Void doInBackground(Void... params) {
		
		String client_id = "U0YLO2X2GKDIXAHUVF51OOP5TUDXG0VOXEY5YIEEO45ODH1O";
		String client_secret = "E2SHC2LGHTK3OV3SCSSBFMWLEO4ERAK5RL2BGZIN31UQ0FAK";
		String query = "Chipotle";	

		// Include call to latitude/longitude
		double latitude = 35.9925;
		double longitude = -78.9017;

		JSONObject results = null;
		try {
			URL url = new URL( String.format( "https://api.foursquare.com/v2/venues/search?client_id=%s&client_secret=%s&v=20130815&ll=%f,%f&query=%s", client_id, client_secret, latitude, longitude, query ) );
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream stream = connection.getInputStream();

			BufferedReader in = new BufferedReader( new InputStreamReader( stream ) );


			String inputLine;
			StringBuilder sb = new StringBuilder();
			while ( (inputLine = in.readLine()) != null )
				sb.append( inputLine );
			in.close();
			
			results = new JSONObject( sb.toString() );
		}
		catch (Exception e) { // Yes. I'm a bad person.
			e.printStackTrace();
		}

        Log.v( "JSON Results: ", results.toString() );
        
        /*
         * Results object can also be iterated over, e.g.
         *  for(Iterator<String> iter = json.keys();iter.hasNext();) {
    	 *  	String key = iter.next();
    	 *  	...
		 *  }
         */
        
        return null;
	}
}

