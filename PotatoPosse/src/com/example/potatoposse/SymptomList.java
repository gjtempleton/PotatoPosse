package com.example.potatoposse;

import android.app.Activity;
import android.os.Bundle;

public class SymptomList extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		String type = getIntent().getExtras().getString("TYPE");
		if(type!=null){
			SQLiteHandler myHandler = new SQLiteHandler(getBaseContext());
			String[][] rubbish = myHandler.getSymptoms(type);
		}
	}
}
