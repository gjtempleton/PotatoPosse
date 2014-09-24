package com.example.potatoposse.utils;

import java.util.ArrayList;
import java.util.HashSet;

import com.example.potatoposse.R;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SendEmail extends Activity {
    ListView listView ;
    EditText search;
    ArrayAdapter<String> adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email_layout);
        
        search = (EditText) findViewById(R.id.search);
        		
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        
        final ArrayList<Contact> contacts = getNameEmailDetails();
      Log.e("GOT HERE", "GOT HERE");  
      
        // Defined Array values to show in ListView
        ArrayList<String> names = new ArrayList<String>();;
      
         for (Contact c : contacts){
        	 names.add(c.getName());
          }
      
      
        String[] values = names.toArray(new String[names.size()]);
        
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

         adapter = new ArrayAdapter<String>(this,
         android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {

               // ListView Clicked item value
            	  
            	  String to = "";
            	  
            	 for(Contact c : contacts) 
            	 {
            		if((String) listView.getItemAtPosition(position) == c.getName())
            		{
                       to = c.getEmail();
                       break;
            		}
            	 }
            	 
      		  Intent email = new Intent(Intent.ACTION_SEND);
			  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
			  email.putExtra(Intent.EXTRA_SUBJECT, "Help identify this potato problem");
			  email.putExtra(Intent.EXTRA_TEXT, "Dear "+(String) listView.getItemAtPosition(position)+ "\n\nPlease help!");
 
			  // we need setType to prompts only email clients.
			  email.setType("message/rfc822");
 
			  startActivity(Intent.createChooser(email, "Choose an Email client :"));
                
              }

         }); 
        
        search.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
               SendEmail.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
    }

	public ArrayList<Contact> getNameEmailDetails() {

		ArrayList<Contact> contacts = new ArrayList<Contact>();
	    HashSet<String> emlRecsHS = new HashSet<String>();
	    Context context = SendEmail.this;
	    ContentResolver cr = context.getContentResolver();
	    String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID, 
	            ContactsContract.Contacts.DISPLAY_NAME,
	            ContactsContract.Contacts.PHOTO_ID,
	            ContactsContract.CommonDataKinds.Email.DATA, 
	            ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
	    String order = "CASE WHEN " 
	            + ContactsContract.Contacts.DISPLAY_NAME 
	            + " NOT LIKE '%@%' THEN 1 ELSE 2 END, " 
	            + ContactsContract.Contacts.DISPLAY_NAME 
	            + ", " 
	            + ContactsContract.CommonDataKinds.Email.DATA
	            + " COLLATE NOCASE";
	    String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
	    Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
	    if (cur.moveToFirst()) {
	        do {
	            // names comes in hand sometimes
	            String name = cur.getString(1);
	            Log.e("Name : ", name);
	            
	            String emlAddr = cur.getString(3);
	            Log.e("Email : ", emlAddr);
	            
	            // keep unique only
	            if (emlRecsHS.add(emlAddr.toLowerCase())) {
	                Contact con = new Contact();
	                
	                con.setEmail(emlAddr);
	                con.setName(name);
	                
	                contacts.add(con);
	            }
	        } while (cur.moveToNext());
	    }

	    cur.close();
	    return contacts;
	}
    
    
}