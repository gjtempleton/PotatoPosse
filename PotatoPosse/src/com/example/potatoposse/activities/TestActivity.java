package com.example.potatoposse.activities;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.CirclePageIndicator;
import com.example.potatoposse.utils.ViewPagerAdapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
        
        String name = getIntent().getExtras().getString("TEST_NAME");
        
        setupView(name);
	}
	
	private void setupView(String name)
	{
		Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome.ttf");
		
		TableLayout upper = (TableLayout)findViewById(R.id.upper);
        
        TextView title = new TextView(this);
		title.setTypeface(font, Typeface.BOLD);	
		title.setPadding(20, 20, 20, 20);
		title.setBackgroundColor(this.getResources().getColor(R.color.jh_green));
		title.setTextColor(Color.WHITE);
		title.setTextSize(20f);
		title.setText(name);
		upper.addView(title);
		
		int[] images = new int[]{ R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six };
		
		ViewPager pager = new ViewPager(this);
		PagerAdapter adapter = new ViewPagerAdapter(this, images, font);
		pager.setAdapter(adapter);
		upper.addView(pager);
		
		TableLayout lower = (TableLayout)findViewById(R.id.lower);
		
		CirclePageIndicator circles = new CirclePageIndicator(this);
		circles.setPadding(0, 0, 0, 20);
		circles.setRadius(11f);
		circles.setFillColor(this.getResources().getColor(R.color.jh_green));
		circles.setViewPager(pager);
		lower.addView(circles);
	}
}