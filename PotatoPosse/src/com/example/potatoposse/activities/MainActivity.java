package com.example.potatoposse.activities;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.CategoryHandler;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity 
{	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);

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
    	Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome.ttf");
    	
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