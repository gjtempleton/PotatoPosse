package com.example.potatoposse.activities;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.CirclePageIndicator;
import com.example.potatoposse.utils.FontHelper;
import com.example.potatoposse.utils.SQLiteHandler;
import com.example.potatoposse.utils.ViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
		
		String search = getIntent().getExtras().getString("SYMPTOM_NAME");		
		
		String[] data = null;
		int CATEGORY = 0, NAME = 1, DESCRIPTION = 2, TEST = 3, RESPONSE = 4;
		
		if (search != null)
		{
			SQLiteHandler mySQLiteHandler = new SQLiteHandler(getBaseContext());
			data = mySQLiteHandler.getBreakdown(search);	
		}
		
//		DisplayMetrics displayMetrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		TableLayout upper = (TableLayout)findViewById(R.id.upper);
		
		TextView title = new TextView(this);
		title.setTypeface(font, Typeface.BOLD);	
		title.setPadding(20, 20, 20, 20);
		title.setBackgroundColor(this.getResources().getColor(R.color.jh_purple));
		title.setTextColor(Color.WHITE);
		title.setTextSize(20f);
		title.setText(data[NAME]);
		upper.addView(title);
		
//		ImageView image = new ImageView(this);
//		image.setImageResource(R.drawable.potato);
//		image.setPadding(20, 20, 20, 20);
//		LayoutParams imageParams = new LayoutParams(displayMetrics.widthPixels, displayMetrics.widthPixels);
//		image.setLayoutParams(imageParams);
//		upper.addView(image);
		
		int[] images = new int[]{ R.drawable.one, R.drawable.two, R.drawable.three };
		
		ViewPager pager = new ViewPager(this);
		PagerAdapter adapter = new ViewPagerAdapter(this, images, font);
		pager.setAdapter(adapter);
		upper.addView(pager);
		
		TableLayout lower = (TableLayout)findViewById(R.id.lower);
		
		CirclePageIndicator circles = new CirclePageIndicator(this);
		circles.setPadding(0, 0, 0, 20);
		circles.setRadius(11f);
		circles.setFillColor(this.getResources().getColor(R.color.jh_purple));
		circles.setViewPager(pager);
		lower.addView(circles);
		
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
		test.getBackground().setColorFilter(this.getResources().getColor(R.color.jh_blue), PorterDuff.Mode.MULTIPLY);
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
		
		Button email = new Button(this);
		email.setTypeface(font);
		email.setPadding(0, 30, 0, 30);
		email.setTextSize(18f);
		email.getBackground().setColorFilter(this.getResources().getColor(R.color.jh_blue), PorterDuff.Mode.MULTIPLY);
		email.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent takePictureActivity = new Intent(getApplicationContext(), TakePictureActivity.class);
				startActivity(takePictureActivity);					
			}
		});
		email.setText("Stuck? Email a friend");
		inner.addView(email);		
		
		scroll.addView(inner);	
		lower.addView(scroll);
	}
}