package com.example.potatoposse.activities;

import java.util.Arrays;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.SQLiteHandler;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

public class SymptomListActivity extends ListActivity
{
	SQLiteHandler mySQLiteHandler;
	
	String[] response;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		String type = getIntent().getExtras().getString("TYPE");
		
		mySQLiteHandler = new SQLiteHandler(getBaseContext());
		response = mySQLiteHandler.getListOfProblemsByType(type);
		Arrays.sort(response);
		
		setListAdapter(new ThumbnailAdapter(this, R.layout.row, response));	
	}
	
	//array adapter to create list asynchronously and update it
	//needed as thumbnail creation is slow 
	public class ThumbnailAdapter extends ArrayAdapter<String>
	{
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
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
			}
			
			final String symptomName = response[position];
			
			ImageView thumbnail = (ImageView)row.findViewById(R.id.thumbnail);
			String path = mySQLiteHandler.getMainProblemImageByName(symptomName);
			if (path == null)
			{
				thumbnail.setImageResource(R.drawable.na);
			}
			else 
			{
				int index = path.lastIndexOf('/');
				path = path.substring(index+1);
				String directory = getApplicationContext().getDir("images", 0).toString();
				directory += getApplicationContext().getString(R.string.path_diseases);
				thumbnail.setImageBitmap(BitmapFactory.decodeFile(directory+"/"+path));
			}
			thumbnail.setPadding(20, 20, 20, 20);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
			params.gravity = Gravity.CENTER_VERTICAL;
			thumbnail.setLayoutParams(params);
			thumbnail.setVisibility(View.VISIBLE);
			
			final TextView label = (TextView)row.findViewById(R.id.label);
			label.setTypeface(MainActivity.FONT);
			label.setTextSize(18f);	
			label.setPadding(20, 81, 20, 81);
			DisplayMetrics displayMetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			int temp = displayMetrics.widthPixels;
			label.setMinimumWidth(temp-200);
			label.setGravity(Gravity.RIGHT);
			label.setText(symptomName);
			
			row.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					Intent summaryView = new Intent(getApplicationContext(), SummaryActivity.class);
					summaryView.putExtra("SYMPTOM_NAME", symptomName);
					startActivity(summaryView);					
				}
			});
			return row;
		}
	}
}