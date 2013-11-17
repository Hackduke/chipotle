package edu.hackduke.chipotle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class RadiusView extends View{
	public RadiusView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RadiusView(Context context) {
		super(context);
		this.init(context);
	}
	
	public RadiusView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	public void init(Context c) {
//		TextView txt = new TextView(c);
//		txt.setText("Notification radius:");
//		RadioButton rb1 = new RadioButton(c);
//		rb1.setText("1 mile");
//		RadioButton rb2 = new RadioButton(c);
//		rb2.setText("5 miles");
//		RadioButton rb3 = new RadioButton(c);
//		rb3.setText("10 miles");
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
	}
}
