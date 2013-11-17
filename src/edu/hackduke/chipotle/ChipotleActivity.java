package edu.hackduke.chipotle;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class ChipotleActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	LocationClient mLocationClient;
	static Location location;
	// LocationView txt;
	static int radius;
	static String query;
	LocationRequest mLocationRequest;
	ArrayList<Location> chs;
	RelativeLayout frame;
	boolean started;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("on", "create");
		super.onCreate(savedInstanceState);
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		RadioGroup rg = new RadioGroup(this);
		RadioButton rb0 = new RadioButton(this);
		rb0.setText("1 mile");
		rb0.setId(0);
		RadioButton rb1 = new RadioButton(this);
		rb1.setId(1);
		rb1.setText("5 miles");
		RadioButton rb2 = new RadioButton(this);
		rb2.setText("10 miles");
		rb2.setId(2);
		rb0.setChecked(true);
		rg.addView(rb0, 0);
		rg.addView(rb1, 1);
		rg.addView(rb2, 2);
		this.frame = new RelativeLayout(this);
		rg.setY(height / 2 - 200);
		rg.setX(width / 3);
		frame.addView(rg);

		EditText et = new EditText(this);
		et.setHint("Query (e.g. \"Chipotle\")");

		et.setY(100);
		et.setX(width / 2 - 183);
		et.setId(42);
		frame.addView(et);

		TextView txt = new TextView(this);
		txt.setTextSize(20);
		frame.addView(txt);
		txt.setText("Notification Radius");
		txt.setY(et.getY() + 210);
		txt.setX(width / 2 - 157);
		frame.setBackgroundColor(Color.argb(50, 0, 255, 0));
		Button start = new Button(this);
		Button stop = new Button(this);
		start.setText("Start");
		start.setY(height / 2 + 70);
		stop.setText("Stop");
		stop.setY(height / 2 + 170);
		start.setX(width / 2 - 80);
		stop.setX(width / 2 - 80);
		frame.addView(start);
		frame.addView(stop);
		RadioGroup.OnCheckedChangeListener radioGroup = new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == -1) {
					setRadius(1609);
				} else if (checkedId == 0) {
					setRadius(1609);
				} else if (checkedId == 1) {
					setRadius(8047);
				} else if (checkedId == 2) {
					setRadius(16093);
				} else {
					setRadius(1609);
				}

			}
		};
		rg.setOnCheckedChangeListener(radioGroup);
		Button.OnClickListener oclStart = new Button.OnClickListener() {
			public void onClick(View v) {
				EditText box = (EditText) findViewById(42);
				ChipotleActivity.query = box.getText().toString();
				if(ChipotleActivity.radius == 0) setRadius(1609);
				if (!started) {
					start();
				} else
					ChipotleActivity.query = box.getText().toString();
				restart();
			}
		};
		start.setOnClickListener(oclStart);
		Button.OnClickListener oclStop = new Button.OnClickListener() {
			public void onClick(View v) {
				onStop();
			}
		};
		start.setOnClickListener(oclStart);
		stop.setOnClickListener(oclStop);
		this.setContentView(frame);

		/*
		 * Create a new location client, using the enclosing class to handle
		 * callbacks.
		 */

	}

	public void noRad() {
		Toast.makeText(this, "Please select a radius", Toast.LENGTH_SHORT);
	}

	public void restart() {
		mLocationClient.disconnect();
		mLocationClient.connect();
		Toast.makeText(this,
				"You will be notified when you near a point of interest.",
				Toast.LENGTH_SHORT).show();
	}

	public void start() {
		mLocationClient = new LocationClient(this, this, this);
		mLocationClient.connect();
		started = true;
		Toast.makeText(this,
				"You will be notified when you near a point of interest.",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStop() {
		// mLocationClient.removeGeofences(chs, this);
		if (mLocationClient != null) {
			if (mLocationClient.isConnected())
				mLocationClient.disconnect();
		}
		super.onStop();
		super.onDestroy();
		System.exit(0);
		// Disconnecting the client invalidates it.
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		Log.v("connecting", "client");
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Log.v("connected", "success");
		this.location = mLocationClient.getLastLocation();
		this.mLocationRequest = LocationRequest.create();
		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 5 seconds
		mLocationRequest.setInterval(1000);
		// Set the fastest update interval to 1 second
		mLocationRequest.setFastestInterval(500);
		mLocationClient.requestLocationUpdates(mLocationRequest, this);

		// this.txt = new LocationView(this);

		// frame.addView(this.txt);
		// setContentView(frame);

		FourSquare fs = new FourSquare(this, this.mLocationClient);
		fs.execute(this.location, this.mLocationClient, null);

	}

	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	public void setRadius(int radius) {
		ChipotleActivity.radius = radius;
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.v("connected", "failed");
	}

	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
	}
	
	String mapURL;
	Context c;
	
	
	protected void drawMap() {
		this.c = this;
		MapTask mt = new MapTask();
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;

		this.mapURL = "http://maps.googleapis.com/maps/api/staticmap?center=";
		this.mapURL += CheckNotification.nearest.getLatitude() + ","
				+ CheckNotification.nearest.getLongitude();
		this.mapURL += "&zoom=18";
		this.mapURL += "&scale=2&markers=color:blue%7C"
				+ ChipotleActivity.location.getLatitude() + ","
				+ ChipotleActivity.location.getLongitude();
		this.mapURL += "&markers=color:red%7C"
				+ CheckNotification.nearest.getLatitude() + ","
				+ CheckNotification.nearest.getLongitude();
		double mapWidth = width;
		double mapHeight = height;
		if (width > 1280 || height > 1280) {
			double ratio = ((double) width) / height;
			if (width >= height) {
				mapWidth = 1280;
				mapHeight = 1280 / ratio;
			} else {
				mapHeight = 1280;
				mapWidth = 1280 * ratio;
			}
		}
		this.mapURL += "&size=" + (int) (mapWidth / 2) + "x"
				+ (int) (mapHeight / 2);
		this.mapURL += "&sensor=true&visual_refresh=true";
		Log.d("mapurl", this.mapURL);
		mt.execute(mapURL);
		Log.d("map", "updating");
		ImageView iv = new ImageView(this);
		try {
			iv.setImageDrawable(mt.get());

		} catch (InterruptedException e) {
			Log.e("e", "interruptedException");
		} catch (ExecutionException e) {
			Log.e("e", "executionException");
		}
		Log.d("map", "updated");
		this.setContentView(iv);
	}

	

}
