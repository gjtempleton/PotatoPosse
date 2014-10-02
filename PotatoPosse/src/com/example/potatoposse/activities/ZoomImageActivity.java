package com.example.potatoposse.activities;

import com.example.potatoposse.R;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.LinearLayout.LayoutParams;

public class ZoomImageActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.zoomimage);		
		
		TableLayout layout = (TableLayout)findViewById(R.id.zoomLayout);
		
		ImageView image = new ImageView(this);
        image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        image.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				finish();
			}        	
        });
        String path = getIntent().getExtras().getString("IMAGE_PATH");
        image.setImageBitmap(BitmapFactory.decodeFile(path));	
        layout.addView(image);
	}
}