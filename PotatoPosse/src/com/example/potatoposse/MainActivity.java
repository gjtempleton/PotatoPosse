package com.example.potatoposse;

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
        
        setContentView(R.layout.activity_main);
	    final TabHost tabHost = getTabHost();
	   	    
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    Intent intent = new Intent().setClass(this, SymptomList.class);
	    intent.putExtra("TYPE", "LEAF");
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    tabHost.addTab(tabHost.newTabSpec("LEAF").setIndicator("LEAF").setContent(intent));
	    
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, SymptomList.class);
	    intent.removeExtra("TYPE");
	    intent.putExtra("TYPE", "PEST");
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    tabHost.addTab(tabHost.newTabSpec("PEST").setIndicator("PEST").setContent(intent));
	    
		 // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, SymptomList.class);
	    intent.removeExtra("TYPE");
	    intent.putExtra("TYPE", "TUBER");
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    tabHost.addTab(tabHost.newTabSpec("TUBER").setIndicator("TUBER").setContent(intent));
	    
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
	        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        params.setMargins(0, 20, 0, 20);
	        tv.setLayoutParams(params);
		    tv.setTextSize(30);
		    tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		    tv.setTypeface(font);
		    
		    if (i == 0) 
		    	tv.setText(getString(R.string.ic_leaf));
		    else if (i == 1)
		    	tv.setText(getString(R.string.ic_pest));
		    else if (i == 2) 
		    	tv.setText(getString(R.string.ic_tuber));
			
    		if (tabHost.getTabWidget().getChildAt(i).isSelected()) //if this tab is currently selected
    		{
    			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.rgb(51,181,229));
    			tv.setTextColor(Color.WHITE);
    		}
    		else //this tab is NOT currently selected
    		{
    			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(0x00000000);
    			tv.setTextColor(Color.BLACK);
    		}   		
    	}
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) 
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) 
//    {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) 
//        {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}