package com.example.potatoposse.activities;

import com.example.potatoposse.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SplashActivity extends Activity{
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_splash);
        Button symptomsButton = (Button)findViewById(R.id.button1);
        Button takePictureButton = (Button)findViewById(R.id.button2);
        symptomsButton.setText("View Problems");
        takePictureButton.setText("Take and Email picture");
        symptomsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				Intent symptomsIntent = new Intent().setClass(getBaseContext(), MainActivity.class);
				startActivity(symptomsIntent);				
			}
		});
        takePictureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent pictureIntent = new Intent().setClass(getBaseContext(), TakePictureActivity.class);
				startActivity(pictureIntent);	
			}
		});
    }

}
