package com.example.potatoposse.activities;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.FontHelper;
import com.example.potatoposse.utils.SQLiteHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class SummaryActivity extends Activity
{	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.summary);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome.ttf");
		
		//get name of symptom from intent
		String search = getIntent().getExtras().getString("SYMPTOM_NAME");		
		
		String[] data = null;
		int CATEGORY = 0, NAME = 1, DESCRIPTION = 2, TEST = 3, RESPONSE = 4;
		
		if (search != null)
		{
			SQLiteHandler mySQLiteHandler = new SQLiteHandler(getBaseContext());
			data = mySQLiteHandler.getBreakdown(search);	
		}
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		TableLayout outer = (TableLayout)this.findViewById(R.id.summaryLayout);
		
		TextView name = new TextView(this);
		name.setTypeface(font, Typeface.BOLD);	
		name.setPadding(20, 20, 20, 20);
		name.setBackgroundColor(this.getResources().getColor(R.color.jh_purple));
		name.setTextColor(Color.WHITE);
		name.setTextSize(26f);
		name.setText(data[NAME]);
		outer.addView(name);
		
		ImageView image = new ImageView(this);
		image.setImageResource(R.drawable.potato);
		image.setPadding(20, 20, 20, 20);
		LayoutParams imageParams = new LayoutParams(displayMetrics.widthPixels, displayMetrics.widthPixels);
		image.setLayoutParams(imageParams);
		outer.addView(image);
		
		ScrollView scroll = new ScrollView(this);
		TableLayout inner = new TableLayout(this);
		inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); 
		inner.setPadding(20, 20, 20, 20);
		
		TextView category = new TextView(this);
		category.setTypeface(font);
		category.setPadding(0, 0, 0, 30);
		category.setGravity(Gravity.CENTER_HORIZONTAL);
		category.setTextSize(18f);
		String icon = getString(FontHelper.getIcon(data[CATEGORY]));
		category.setText(data[CATEGORY] + " " + icon);
		inner.addView(category);
		
		ImageView divider = new ImageView(this);
		divider.setImageResource(R.drawable.ruler);
		inner.addView(divider);
		
		TextView description = new TextView(this);
		description.setTypeface(font);
		description.setPadding(0, 30, 0, 30);
		description.setTextSize(18f);
		description.setText(data[DESCRIPTION]);
		inner.addView(description);
		
		final String testName = data[TEST];
		
		Button test = new Button(this);
		test.setTypeface(font);
		test.setPadding(0, 30, 0, 30);
		test.setTextSize(18f);
		test.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent testActivity = new Intent(getApplicationContext(), TestActivity.class);
				testActivity.putExtra("TEST_NAME", testName);
				startActivity(testActivity);					
			}
		});
		test.setText(data[TEST]);
		inner.addView(test);
		
		TextView response = new TextView(this);
		response.setTypeface(font);
		response.setPadding(0, 30, 0, 30);
		response.setTextSize(18f);
		response.setText(data[RESPONSE]);
		inner.addView(response);
			
		scroll.addView(inner);	
		outer.addView(scroll);
	}
}