package com.example.potatoposse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class SummaryActivity extends Activity{
	LinearLayout layout;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		Log.w("Pause", "Activity Paused");
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		Log.w("Resume", "Activity Resumed");
	}
}