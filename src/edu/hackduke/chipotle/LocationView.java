package edu.hackduke.chipotle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class LocationView extends TextView{
	
	ChipotleActivity ma;

	public LocationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LocationView(Context context) {
		super(context);
		this.ma = (ChipotleActivity) context;
		this.init(context);
	}
	
	public LocationView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	public void init(Context c) {
		this.setText(this.ma.location.getLatitude() + " " + this.ma.location.getLongitude());
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		this.setText(this.ma.location.getLatitude() + " " + this.ma.location.getLongitude());
	}
	
}
