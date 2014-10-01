package com.example.potatoposse.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.CategoryHandler;
import com.example.potatoposse.utils.HTTPHandler;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        String lastUpdateDate = PreferenceManager.getDefaultSharedPreferences(this).getString("LAST_TIME_UPDATED", null);
        if (lastUpdateDate == null)
        {
        	Date now = new Date();
        	String formatted = new SimpleDateFormat("yyyyMMdd").format(now);
        	
        	PreferenceManager.getDefaultSharedPreferences(this).edit().putString("LAST_TIME_UPDATED", formatted).commit();
        }
        
        setContentView(R.layout.main);
        
        font = Typeface.createFromAsset(getAssets(), "fontawesome.ttf");
        
        setupNavBar();
	    setupTabs();
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