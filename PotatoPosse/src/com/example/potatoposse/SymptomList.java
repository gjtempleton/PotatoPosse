package com.example.potatoposse;

import java.io.File;

import com.example.potatoposse.R.drawable;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SymptomList extends ListActivity
{
	String[]response = null;
	SQLiteHandler mySQLiteHandler;
	Intent summaryView;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		summaryView = new Intent(getApplicationContext(), SummaryActivity.class);
		
		String type = getIntent().getExtras().getString("TYPE");
		mySQLiteHandler = new SQLiteHandler(getBaseContext());
		
		if (type!=null)
		{
			response = mySQLiteHandler.getSymptoms(type);
			setListAdapter(new ThumbnailAdapter(this, R.layout.row, response));			
		}
	}
	
	//Array adapter to create list asynchronously and update it
	//Needed as thumbnail creation is slow 
	public class ThumbnailAdapter extends ArrayAdapter<String>{

		public ThumbnailAdapter(Context context, int textViewResourceId, String[] symptoms) 
		{
			super(context, textViewResourceId, symptoms);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			View row = convertView;
			if (row == null)
			{
				LayoutInflater inflater=getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
			}
			
			final String symptomName = response[position];
			
			final TextView label = (TextView)row.findViewById(R.id.FilePath);
			Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome.ttf");
			label.setTypeface(font);
			label.setTextSize(18f);	
			label.setPadding(20, 81, 20, 81);
			DisplayMetrics displayMetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			int temp = displayMetrics.widthPixels;
			label.setMinimumWidth(temp-200);
			label.setGravity(Gravity.RIGHT);
			label.setText(symptomName);
			
			ImageView thumbnail = (ImageView)row.findViewById(R.id.Thumbnail);
			thumbnail.setImageResource(R.drawable.potato);
			thumbnail.setPadding(20, 20, 20, 20);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
			thumbnail.setLayoutParams(params);
			thumbnail.setVisibility(View.VISIBLE);
			
			row.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					summaryView.putExtra("SYMPTOM_NAME", symptomName);
					startActivity(summaryView);					
				}
			});
			return row;
		}
	}
}