package com.example.potatoposse;

import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;


public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);
        
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    SQLiteHandler mySQLiteHandler = new SQLiteHandler(getApplicationContext());

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, SymptomList.class);
<<<<<<< HEAD
	    intent.putExtra("TYPE", "LEAF");
=======
	    intent.putExtra("TYPE", "PLANT");
	    intent.putExtra("SQLITEHANDLER", mySQLiteHandler);

>>>>>>> origin/master
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("Leaf").setIndicator("Leaf", res.getDrawable(R.drawable.ic_tab_summary)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, SymptomList.class);
<<<<<<< HEAD
	    intent.putExtra("TYPE", "PEST");
=======
	    intent.removeExtra("TYPE");
	    intent.putExtra("TYPE", "INSECT");
	    intent.putExtra("SQLITEHANDLER", mySQLiteHandler);

>>>>>>> origin/master
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("Pest").setIndicator("Pest", res.getDrawable(R.drawable.ic_tab_volume)).setContent(intent);
	    tabHost.addTab(spec);
	    
		 // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, SymptomList.class);
	    intent.removeExtra("TYPE");
	    intent.putExtra("TYPE", "TUBER");
<<<<<<< HEAD
=======
	    intent.putExtra("SQLITEHANDLER", mySQLiteHandler);

>>>>>>> origin/master
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("Tuber").setIndicator("Tuber", res.getDrawable(R.drawable.ic_tab_alerts)).setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
