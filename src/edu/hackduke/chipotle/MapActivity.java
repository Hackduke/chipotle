package edu.hackduke.chipotle;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

public class MapActivity extends Activity {

	String mapURL;
	Context c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
