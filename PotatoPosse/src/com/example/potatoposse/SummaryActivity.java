package com.example.potatoposse;

import com.example.potatoposse.SymptomList.ThumbnailAdapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
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
		
		//TODO: THIS AIN'T WORKING
//		Bundle temp = getIntent().getExtras();
//		Log.w("WHEEEE", temp.toString());
		
		//get name of symptom from intent
		String search = getIntent().getExtras().getString("SYMPTOM_NAME");
		
		String[] response = null;
		SQLiteHandler mySQLiteHandler = (SQLiteHandler)getIntent().getSerializableExtra("SQLITEHANDLER");
		
		if (search != null)
		{
			response = mySQLiteHandler.getBreakdown(search);
			Log.w("RESPONSE_STRING", response.toString());
			//setListAdapter(new ThumbnailAdapter(this, R.layout.row, response));			
		}
	
		TableLayout layout = (TableLayout)this.findViewById(R.id.summaryLayout);
		
		TextView name = new TextView(this);
		name.setText(search);		
		layout.addView(name);
		
		ImageView image = new ImageView(this);
		image.setImageResource(R.drawable.potato);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		LinearLayout.LayoutParams params = new LayoutParams(displaymetrics.widthPixels, displaymetrics.widthPixels);
		image.setPadding(20, 20, 20, 20);
		image.setLayoutParams(params);
		layout.addView(image);
		
		TextView category = new TextView(this);
		Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome.ttf");
		category.setTypeface(font);
		category.setText("CATEGORY");
		layout.addView(category);
		
		TextView test = new TextView(this);
		test.setText("TEST");
		layout.addView(test);
		
		TextView description = new TextView(this);
		description.setText("DESCRIPTION");
		layout.addView(description);
	}
}