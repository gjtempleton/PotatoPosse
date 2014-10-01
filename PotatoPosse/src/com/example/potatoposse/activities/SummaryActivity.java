package com.example.potatoposse.activities;

import java.util.ArrayList;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.CirclePageIndicator;
import com.example.potatoposse.utils.CategoryHandler;
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
	private String name;
	
	private String[] CATEGORIES;
	private int TYPES;
	private String[] ICONS;
	
	private boolean[] types;
	private String[] data;
	private String[] imagePaths;
	
	private int COUNT = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.summary);		
		
		setupVariables();
		setupView();		
	}
	
	private void setupVariables()
	{		
		name = getIntent().getExtras().getString("SYMPTOM_NAME");
		SQLiteHandler mySQLiteHandler = new SQLiteHandler(getBaseContext());
		
		CATEGORIES = CategoryHandler.getCategories();
		TYPES = CATEGORIES.length;
		ICONS = new String[TYPES];
		
		types = mySQLiteHandler.getTypesByName(name);
		
		for (int i=0; i<TYPES; i++)
		{
			ICONS[i] = getString(CategoryHandler.getIcon(i));
			if (types[i]) COUNT++;
		}
		
		data = mySQLiteHandler.getProblemBreakdownByName(name);
		
		imagePaths = mySQLiteHandler.getProblemImagesByName(name);
		for (int j=0; j<imagePaths.length; j++)
		{
			int index = imagePaths[j].lastIndexOf('/');
			imagePaths[j] = imagePaths[j].substring(index+1);
		}
	}
	
	private void setupView()
	{
		TableLayout upper = (TableLayout)findViewById(R.id.upper);
		
		TextView title = new TextView(this);
		title.setTypeface(MainActivity.FONT, Typeface.BOLD);	
		title.setPadding(20, 20, 20, 20);
		title.setBackgroundColor(this.getResources().getColor(R.color.jh_purple));
		title.setTextColor(Color.WHITE);
		title.setTextSize(20f);
		title.setText(name);
		upper.addView(title);
		
		TableLayout lower = (TableLayout)findViewById(R.id.lower);
		
		if (imagePaths.length == 0)
		{
			ImageView blank = new ImageView(this);
			blank.setPadding(20, 20, 20, 20);
			blank.setImageResource(R.drawable.na);
			upper.addView(blank);
		}
		else
		{		
			ViewPager pager = new ViewPager(this);
			String directory = this.getDir("images", 0).toString();
			directory += this.getString(R.string.path_diseases);
			PagerAdapter adapter = new ViewPagerAdapter(this, directory, imagePaths);
			pager.setAdapter(adapter);
			upper.addView(pager);
			
			CirclePageIndicator circles = new CirclePageIndicator(this);
			circles.setPadding(0, 0, 0, 20);
			circles.setRadius(11f);
			circles.setFillColor(this.getResources().getColor(R.color.jh_purple));
			circles.setViewPager(pager);
			lower.addView(circles);
		}
		
		ScrollView scroll = new ScrollView(this);
		TableLayout inner = new TableLayout(this);
		inner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); 
		inner.setPadding(20, 20, 20, 20);
		
		TextView category = new TextView(this);
		category.setTypeface(MainActivity.FONT);
		category.setPadding(0, 0, 0, 30);
		category.setGravity(Gravity.CENTER_HORIZONTAL);
		category.setTextSize(18f);
		category.setText(getCategoryText());
		inner.addView(category);
		
		ImageView divider = new ImageView(this);
		divider.setImageResource(R.drawable.ruler);
		inner.addView(divider);
		
		TextView description = new TextView(this);
		description.setTypeface(MainActivity.FONT);
		description.setPadding(0, 30, 0, 30);
		description.setTextSize(18f);
		description.setText(data[CategoryHandler.getIndex("DESCRIPTION")]);
		inner.addView(description);
		
		ArrayList<Integer> tests = getTestIdList();		
		ArrayList<Button> buttons = new ArrayList<Button>();		
		for (int i=0; i<tests.size(); i++)
		{		
			final int id = tests.get(i);
			
			Button button = new Button(this);
			button.setTypeface(MainActivity.FONT);
			button.setPadding(0, 30, 0, 30);
			button.setTextSize(18f);
			button.getBackground().setColorFilter(this.getResources().getColor(R.color.jh_blue), PorterDuff.Mode.MULTIPLY);
			button.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					Intent testActivity = new Intent(getApplicationContext(), TestActivity.class);
					testActivity.putExtra("TEST_ID", id);
					startActivity(testActivity);					
				}
			});
			button.setText((tests.get(i)).toString());
			buttons.add(button);
		}
		
		for (Button button : buttons)
		{
			inner.addView(button);
		}
		
		TextView response = new TextView(this);
		response.setTypeface(MainActivity.FONT);
		response.setPadding(0, 30, 0, 30);
		response.setTextSize(18f);
		response.setText(data[CategoryHandler.getIndex("CONTROL")]);
		inner.addView(response);
		
		Button email = new Button(this);
		email.setTypeface(MainActivity.FONT);
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
	
	private String getCategoryText()
	{
		String text = "";
		for (int i=0; i<TYPES; i++)
		{
			if (types[i])
			{
				text += CATEGORIES[i] + " " + ICONS[i];
			}
			
			if (i < COUNT-1) text += "  |  "; //TODO: fix this!!!!!!!!!!111
		}
		return text;
	}
	
	private ArrayList<Integer> getTestIdList()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		String test1 = data[CategoryHandler.getIndex("TEST_1")];
		String test2 = data[CategoryHandler.getIndex("TEST_2")];
		
		if (test1 != null) list.add(Integer.parseInt(test1));
		if (test2 != null) list.add(Integer.parseInt(test2));
		
		return list;
	}
}