package com.example.potatoposse;

import java.io.File;

import com.example.potatoposse.R.drawable;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SymptomList extends ListActivity{
	String[]response = null;
	SQLiteHandler mySQLiteHandler;
	Intent summaryView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		summaryView = new Intent(getApplicationContext(), SummaryActivity.class);
		
		String type = getIntent().getExtras().getString("TYPE");
		mySQLiteHandler = new SQLiteHandler(getBaseContext(), false);
		if(type!=null){
			response = mySQLiteHandler.getSymptoms(type);
			//Log.w("Crap", response.toString());
			setListAdapter(new ThumbnailAdapter(this, R.layout.row, response));			
		}
	}
	
	//Array adapter to create list asynchronously and update it
		//Needed as thumbnail creation is slow 
		public class ThumbnailAdapter extends ArrayAdapter<String>{

			public ThumbnailAdapter(Context context, int textViewResourceId, String[] symptoms) {
				super(context, textViewResourceId, symptoms);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View row = convertView;
				if(row==null){
					LayoutInflater inflater=getLayoutInflater();
					row=inflater.inflate(R.layout.row, parent, false);
				}
				final TextView textfilePath = (TextView)row.findViewById(R.id.FilePath);
				textfilePath.setText(response[position]);
				ImageView imageThumbnail = (ImageView)row.findViewById(R.id.Thumbnail);
				//imageThumbnail = (ImageView)findViewById(drawable.ic_tab_alerts_off);
				//imageThumbnail.setVisibility(View.VISIBLE);
				final String symptomName = response[position];
				row.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						summaryView.putExtra("SYMPTOM_NAME", symptomName);
						startActivity(summaryView);
						
					}
				});
				return row;
			}
		}
}
