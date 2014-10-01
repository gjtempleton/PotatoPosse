package com.example.potatoposse.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.CategoryHandler;
import com.example.potatoposse.utils.HTTPHandler;
import com.example.potatoposse.utils.ZipHandler;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity 
{	
	Typeface font;
	String locations[][] = new String[2][3];
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        firstTimeSetup();
        
        setContentView(R.layout.main);
        
        font = Typeface.createFromAsset(getAssets(), "fontawesome.ttf");
        
        setupNavBar();
	    setupTabs();
    }
    
    private void firstTimeSetup(){
    	locations[0][0] = (this.getDir("zips", 0).toString() + "media.zip");
		locations[0][1] = this.getDir("images", 0).toString();
		locations[0][2] = "media.zip";
		locations[1][0] = (this.getDir("zips", 0).toString() + "tests.zip");
		locations[1][1] = this.getDir("testImages", 0).toString();
		locations[1][2] = "tests.zip";
		
    	String lastUpdateDate = PreferenceManager.getDefaultSharedPreferences(this).getString("LAST_TIME_UPDATED", null);
        if (lastUpdateDate == null)
        {
        	AssetManager assets = getAssets();
        	for(int i=0; i<2; i++){
    			File file = new File( locations[i][1]);
    	        if (file.exists())
    			{
    				file.delete();
    			}
    	        try {
    	        	if (!new File(file.getParent()).exists())
    	        	{
    	        		new File(file.getParent()).mkdir();
    	        	}
    	        	
    	        	file.createNewFile();
    	        	FileOutputStream outStream = new FileOutputStream(file);
    				InputStream inStream = assets.open(locations[i][2]);
    				byte[] buff = new byte[5 * 1024];
    				int len;
    				while ((len = inStream.read(buff)) != -1)
    				{
    					outStream.write(buff, 0, len);
    				}

    				outStream.flush();
    				outStream.close();
    				inStream.close();
    				ZipHandler.unpackZip(locations[i][1], file.getAbsolutePath());
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
        	File file = new File( this.getDatabasePath("potatoDB").toString());
        	Log.w("THIS IS ABSO PATH", file.getAbsolutePath());
            if (file.exists())
    		{
    			file.delete();
    		}
            
            try {
            	if (!new File(file.getParent()).exists())
	        	{
	        		new File(file.getParent()).mkdir();
	        	}
            	
            	file.createNewFile();
            	FileOutputStream outStream = new FileOutputStream(file);
    			InputStream inStream = assets.open("potatoDB.db");
    			byte[] buff = new byte[5 * 1024];

    			int len;
    			while ((len = inStream.read(buff)) != -1)
    			{
    				outStream.write(buff, 0, len);
    			}

    			outStream.flush();
    			outStream.close();
    			inStream.close();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	Date now = new Date();
        	String formatted = new SimpleDateFormat("yyyyMMdd").format(now);
        	
        	PreferenceManager.getDefaultSharedPreferences(this).edit().putString("LAST_TIME_UPDATED", formatted).commit();
        }
        
    }
    
    private void setupNavBar()
    {
    	TableLayout navbar = (TableLayout)findViewById(R.id.navbar);
        
        TextView title = new TextView(this);
		title.setTypeface(font, Typeface.BOLD);	
		title.setPadding(20, 20, 20, 20);
		title.setBackgroundColor(this.getResources().getColor(R.color.jh_blue));
		title.setTextColor(Color.WHITE);
		title.setTextSize(20f);
		title.setGravity(Gravity.RIGHT);
		title.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v) 
			{
				HTTPHandler httpHandler = new HTTPHandler(getApplicationContext());
				if (httpHandler.getServerUpdateDate())
				{
					Toast.makeText(MainActivity.this, "Database up to date", Toast.LENGTH_SHORT).show();
				}
				else
				{
					httpHandler.downloadDatabaseFile();
					Toast.makeText(MainActivity.this, "Database successfully updated", Toast.LENGTH_SHORT).show();
					httpHandler.downloadMediaZips();
					Toast.makeText(MainActivity.this, "Media files successfully updated", Toast.LENGTH_SHORT).show();
				}
			}			
		});
		title.setText(getString(R.string.ic_download));
		navbar.addView(title);
    }
    
    private void setupTabs()
    {
    	final TabHost tabHost = getTabHost();
    	
    	Intent intent;
	    String[] CATEGORIES = CategoryHandler.getCategories();
	    boolean first = true;
	    for (int i=0; i<CATEGORIES.length; i++)
	    {
	    	intent = new Intent().setClass(this, SymptomListActivity.class);
	    	if (!first) intent.removeExtra("TYPE");
		    intent.putExtra("TYPE", CATEGORIES[i]);
		    tabHost.addTab(tabHost.newTabSpec(CATEGORIES[i]).setIndicator(CATEGORIES[i]).setContent(intent));
	    }
	    
	    tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() 
	    {			
			public void onTabChanged(String id) 
			{
				setTabColors(tabHost);
			}
		});	    
	    tabHost.setCurrentTab(0);
	    setTabColors(tabHost);
    }
    
    private void setTabColors(TabHost tabHost)
    {   	
    	for (int i=0; i<tabHost.getTabWidget().getTabCount(); i++)
    	{
			TextView tv = (TextView)tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			tv.setTypeface(font);
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        params.setMargins(0, 20, 0, 20);
	        tv.setLayoutParams(params);
		    tv.setTextSize(30);
		    tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);		    
		    tv.setText(getString(CategoryHandler.getIcon(i)));
			
    		if (tabHost.getTabWidget().getChildAt(i).isSelected()) //this tab is currently selected
    		{
    			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(this.getResources().getColor(R.color.jh_blue));
    			tv.setTextColor(Color.WHITE);
    		}
    		else //this tab is NOT currently selected
    		{
    			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(0x00000000);
    			tv.setTextColor(Color.BLACK);
    		}   		
    	}
    }
}