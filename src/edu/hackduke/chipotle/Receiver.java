package edu.hackduke.chipotle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Receiver extends BroadcastReceiver{

	
	ChipotleActivity c;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.d("intent", "received");
		this.c = (ChipotleActivity) arg0;
		Intent notificationIntent = new Intent(this.c,
				ChipotleActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this.c).setSmallIcon(R.drawable.notification_icon)
				.setContentTitle(ChipotleActivity.query + "!")
				.setContentText("You are near a " + ChipotleActivity.query);
		
		PendingIntent pendingNotification = PendingIntent.getActivity(
			this.c, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingNotification);
		NotificationManager mNotificationManager = (NotificationManager) this.c.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
		
		
	}
	
	

}
