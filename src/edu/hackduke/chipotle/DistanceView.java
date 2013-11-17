package edu.hackduke.chipotle;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.location.Location;
import android.util.AttributeSet;
import android.widget.TextView;

public class DistanceView extends TextView{
	ChipotleActivity ma;
	static ArrayList<Location> ls;


	public DistanceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DistanceView(Context context) {
		super(context);
		this.ma = (ChipotleActivity) context;
		this.init(context);
	}
	
	public DistanceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	public void init(Context c) {
		this.setText("hi");
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		if(ls.get(0) != null) {
			this.setText("Distance: " + this.ma.location.distanceTo(ls.get(0)));
		}
	}
}
