package edu.hackduke.chipotle;

import java.util.ArrayList;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

public class CheckNotification extends AsyncTask<Void, Void, Void> {

	int RADIUS;
	ChipotleActivity c;
	ArrayList<Location> chs;
	static Location nearest;

	public CheckNotification(ChipotleActivity c, ArrayList<Location> chs) {
		this.chs = new ArrayList<Location>(chs);
		this.c = c;
		this.RADIUS = ChipotleActivity.radius;
	}

	@Override
	protected Void doInBackground(Void... params) {
		Receiver rtis = new Receiver();
		boolean near = false;
		boolean called = false;
		Log.d("chs", RADIUS + "");
		nearest = new Location(chs.get(0));
		while (!this.isCancelled()) {
			for (Location x : this.chs) {
				// ids.add(x.getRequestId());
				near = false;
				if (this.c.location.distanceTo(x) < RADIUS) {
					Log.d("close", "to location");
					near = true;
					if (this.c.location.distanceTo(x) < this.c.location
							.distanceTo(nearest)) {
						nearest = new Location(x);
					}
				}
				// if (this.c.location.distanceTo(x) < 20) {
				// far = false;
				// }
			}
			if (!near) {
				called = false;
			}
			if (near && !called) {
				Log.d("entering", "intent thing");
				rtis.onReceive(this.c, null);
				called = true;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
