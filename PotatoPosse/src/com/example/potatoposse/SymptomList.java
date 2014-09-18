package com.example.potatoposse;

import java.io.File;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SymptomList extends ListActivity{
	String[][]response = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		String type = getIntent().getExtras().getString("TYPE");
		if(type!=null){
			SQLiteHandler myHandler = new SQLiteHandler(getBaseContext());
			response = myHandler.getSymptoms(type);
			String[] dummy = {"1", "2"};
			setListAdapter(new ThumbnailAdapter(this, R.layout.row, dummy));
			
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
				ProgressBar progress = (ProgressBar)row.findViewById(R.id.ProgressBar);
				final TextView textfilePath = (TextView)row.findViewById(R.id.FilePath);
				textfilePath.setText(response[position-1][0]);
				ImageView imageThumbnail = (ImageView)row.findViewById(R.id.Thumbnail);
				return row;
			}
		}
}
