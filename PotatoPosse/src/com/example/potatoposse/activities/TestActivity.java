package com.example.potatoposse.activities;

import java.util.Arrays;
import java.util.Comparator;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.CirclePageIndicator;
import com.example.potatoposse.utils.SQLiteHandler;
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
	private int id;
	private String name;
	
	private String[][] images;
	private String[] paths;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
        setContentView(R.layout.test);
        
        setupVariables();         
        setupView();
	}
	
	private void setupVariables()
	{
		id = getIntent().getExtras().getInt("TEST_ID");
        
        SQLiteHandler mySQLiteHandler = new SQLiteHandler(getBaseContext());
        name = mySQLiteHandler.getTestNameById(id);
        
        images = mySQLiteHandler.getTestImagesById(id);
        
        paths = sortImages();
        for (int j=0; j<paths.length; j++)
		{
			int index = paths[j].lastIndexOf('/');
			paths[j] = paths[j].substring(index+1);
		}
	}
	
	private void setupView()
	{		
		TableLayout upper = (TableLayout)findViewById(R.id.upper);
        
        TextView title = new TextView(this);
		title.setTypeface(MainActivity.FONT, Typeface.BOLD);	
		title.setPadding(20, 20, 20, 20);
		title.setBackgroundColor(this.getResources().getColor(R.color.jh_green));
		title.setTextColor(Color.WHITE);
		title.setTextSize(20f);
		title.setText(name);
		upper.addView(title);
		
		ViewPager pager = new ViewPager(this);
		String directory = this.getDir("testImages", 0).toString();
		directory += this.getString(R.string.path_tests);
		PagerAdapter adapter = new ViewPagerAdapter(this, directory, paths);
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
	
	private String[] sortImages()
	{
		Arrays.sort(images, new Comparator<String[]>()
		{
			@Override
			public int compare(final String[] aposition, final String[] apath)
			{
				final String position = aposition[0];
				final String path = apath[0];
				return position.compareTo(path);
			}
		});		
		
		String[] result = new String[images.length];
		for (int i=0; i<result.length; i++)
		{
			result[i] = images[i][1];
		}
		return result;
	}
}