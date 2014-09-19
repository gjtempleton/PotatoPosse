package com.example.potatoposse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class SummaryActivity extends Activity{
	LinearLayout layout;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.summary);
		Log.w("Create", "Activity Created");
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