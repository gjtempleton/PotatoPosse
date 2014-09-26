package com.example.potatoposse.activities;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.ViewPagerAdapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
        
        TextView title = new TextView(this);
		title.setTypeface(font, Typeface.BOLD);	
		title.setPadding(20, 20, 20, 20);
		title.setBackgroundColor(this.getResources().getColor(R.color.jh_green));
		title.setTextColor(Color.WHITE);
		title.setTextSize(26f);
		title.setText(testName);
		layout.addView(title);
		
		int[] images = new int[]{ R.drawable.one, R.drawable.two, R.drawable.three };
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		ViewPager pager = new ViewPager(this);
		PagerAdapter adapter = new ViewPagerAdapter(this, images, font);
		pager.setAdapter(adapter);
		layout.addView(pager);
	}
}