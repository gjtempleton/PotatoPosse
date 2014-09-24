package com.example.potatoposse.activities;

import com.example.potatoposse.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TextView;

public class TestActivity extends Activity 
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
        setContentView(R.layout.test);
        
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome.ttf");
        
        String testName = getIntent().getExtras().getString("TEST_NAME");
        
        TableLayout layout = (TableLayout)findViewById(R.id.testLayout);
        
        TextView name = new TextView(this);
		name.setTypeface(font, Typeface.BOLD);	
		name.setPadding(20, 20, 20, 20);
		name.setBackgroundColor(this.getResources().getColor(R.color.jh_green));
		name.setTextColor(Color.WHITE);
		name.setTextSize(26f);
		name.setText(testName);
		layout.addView(name);
	}
}