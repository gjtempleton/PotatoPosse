package com.example.potatoposse;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SummaryActivity extends Activity{	
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
		
		TableLayout layout = (TableLayout)this.findViewById(R.id.summaryLayout);
		
		ImageView divider = new ImageView(this);
		divider.setImageResource(R.drawable.ruler);
		divider.setPadding(20, 20, 20, 20);
		
		TextView name = new TextView(this);
		name.setTypeface(font, Typeface.BOLD);	
		name.setPadding(20, 20, 20, 20);
		name.setBackgroundColor(this.getResources().getColor(R.color.jh_purple));
		name.setTextColor(Color.WHITE);
		name.setTextSize(26f);
		name.setText(data[NAME]);
		layout.addView(name);
		
		ImageView image = new ImageView(this);
		image.setImageResource(R.drawable.potato);
		image.setPadding(20, 20, 20, 20);
		LayoutParams imageParams = new LayoutParams(displayMetrics.widthPixels, displayMetrics.widthPixels);
		image.setLayoutParams(imageParams);
		layout.addView(image);
		
		TextView category = new TextView(this);
		category.setTypeface(font);
		category.setPadding(20, 20, 20, 20);
		category.setTextSize(18f);
		String icon = getString(FontHelper.getIcon(data[CATEGORY]));
		category.setText("Category: " + icon);
		layout.addView(category);
		
		layout.addView(divider);
		
		TextView description = new TextView(this);
		description.setTypeface(font);
		description.setPadding(20, 20, 20, 20);
		description.setTextSize(18f);
		description.setText(data[DESCRIPTION]);
		layout.addView(description);
		
		Button test = new Button(this);
		test.setTypeface(font);
		test.setPadding(20, 20, 20, 20);
		test.setTextSize(18f);
		test.setText(data[TEST]);
		layout.addView(test);
		
		TextView response = new TextView(this);
		response.setTypeface(font);
		response.setPadding(20, 20, 20, 20);
		response.setTextSize(18f);
		response.setText(data[RESPONSE]);
		layout.addView(response);		
	}
}