package com.example.potatoposse.activities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.potatoposse.R;
import com.example.potatoposse.utils.Contact;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SendEmailActivity extends Activity {
    ListView listView ;
    EditText search;
    Button newAddress;
    ArrayAdapter<String> adapter;
    String img;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        img = getIntent().getExtras().getString("IMAGE");
        
        setContentView(R.layout.activity_send_email);
        
        search = (EditText) findViewById(R.id.search);
        		
        newAddress = (Button) findViewById (R.id.newAddress);
      
        newAddress.setOnClickListener( new OnClickListener(){
        	public void onClick(View view){
        		
                final AlertDialog ad = new AlertDialog.Builder(SendEmailActivity.this).create();
        		ad.setTitle("New Email Address");
        		ad.setMessage("Please enter email address:");
        		
        		final EditText input = new EditText(SendEmailActivity.this);
        		ad.setView(input);
        		
        		ad.setButton(DialogInterface.BUTTON_POSITIVE, "Enter",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						String exp = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
						CharSequence email =  input.getText().toString();
						
						Pattern pat = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
						Matcher mat = pat.matcher(email);	
						
						if(mat.matches()){
							sendEmail("Friend",input.getText().toString());	
							ad.dismiss();
						}
						else{
							 Toast.makeText(SendEmailActivity.this, "Please enter a valid email address!", Toast.LENGTH_SHORT).show();
						     
						}
						
					}
				});
        		
        		ad.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				});
        		
        		ad.show();
        	}
        });
        
        
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
            	 
      		 sendEmail((String) listView.getItemAtPosition(position), to);
                
              }

              
         }); 
        
        search.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
               SendEmailActivity.this.adapter.getFilter().filter(cs);   
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

    private void sendEmail(String to, String address){
		  Intent email = new Intent(Intent.ACTION_SEND);
		  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ address});
		  email.putExtra(Intent.EXTRA_SUBJECT, "Help identify this potato problem");
		  email.putExtra(Intent.EXTRA_TEXT, "Dear "+to+ "\n\nPlease help!");
	Log.e("img", img);	 
		   email.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+img));

		  // we need setType to prompts only email clients.
		  email.setType("message/rfc822");

		  startActivity(Intent.createChooser(email, "Choose an Email client :"));
  }
    
	private ArrayList<Contact> getNameEmailDetails() {

		ArrayList<Contact> contacts = new ArrayList<Contact>();
	    HashSet<String> emlRecsHS = new HashSet<String>();
	    Context context = SendEmailActivity.this;
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