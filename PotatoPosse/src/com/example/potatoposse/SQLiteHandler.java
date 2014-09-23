package com.example.potatoposse;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class SQLiteHandler extends SQLiteOpenHelper{

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "PotatoDB";
	private boolean firstTime =false;

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onOpen(SQLiteDatabase db){
			// SQL statement to create symptoms table
			// Drop older symptoms table if existed
			db.execSQL("DROP TABLE IF EXISTS symptoms");
			String CREATE_SYMPTOM_TABLE = "CREATE TABLE symptoms ( " +
					"category TEXT, "+
					"name TEXT, "+
					"description TEXT, "+
					"response TEXT, "+
					"test TEXT)";

			// create symptoms table
			db.execSQL(CREATE_SYMPTOM_TABLE);
			//Populate the symptoms table with dummy data

			String INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES ('LEAF', 'TEST LEAF', 'THIS IS A TEST LEAF', 'THROW IT AWAY', 'VISUAL')";
			db.execSQL(INSERT_DUMMY_DATA);

			INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES ('PEST', 'TEST PEST', 'THIS IS A TEST PEST', 'THROW IT AWAY', 'VISUAL')";
			db.execSQL(INSERT_DUMMY_DATA);

			INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES ('TUBER', 'TEST TUBER', 'THIS IS A TEST TUBER', 'THROW IT AWAY', 'VISUAL')";
			String INSERT_DUMMY_DATA_2 = "INSERT INTO symptoms VALUES ('TUBER', 'TEST TUBER NO 2', 'THIS IS A TEST TUBER', 'THROW IT AWAY', 'VISUAL')";
			db.execSQL(INSERT_DUMMY_DATA);
			db.execSQL(INSERT_DUMMY_DATA_2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// Drop older symptoms table if existed
		db.execSQL("DROP TABLE IF EXISTS symptoms");

		// create fresh symptoms table
		this.onCreate(db);
	}

	public String[]  getSymptoms(String type)
	{
		String Table_Name="symptoms";

		String selectQuery = "SELECT name FROM  "+ Table_Name + " WHERE category='"+type+"'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		String[] data = new String[cursor.getCount()];
		int j = 0;
		if (cursor.moveToFirst()) {
			do {
				data[j] = cursor.getString(0);
				j++;
				// get  the  data into array,or class variable

			} while (cursor.moveToNext());
		}
		db.close();
		if(data!=null){
			for(int i =0; i<data.length; i++){
				Log.w("Data", data[i].toString());
			}
		}
		return data;
	}

	public String[] getBreakdown(String name)
	{
		String Table_Name="symptoms";

		String selectQuery = "SELECT * FROM  "+ Table_Name + " WHERE name='"+name+"'";
		Log.w("QUERY STRING", selectQuery);
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		String[] data = new String[cursor.getColumnCount()];
		int j = 0;
		if (cursor.moveToFirst()) {
			do {
				data[0] = cursor.getString(0);
				data[1] = cursor.getString(1);
				data[2] = cursor.getString(2);
				data[3] = cursor.getString(3);
				data[4] = cursor.getString(4);			
				j++;
				// get  the  data into array,or class variable

			} while (cursor.moveToNext());
		}
		db.close();
		if(data!=null){
			for(int i =0; i<data.length; i++){
				Log.w("Data", data[i].toString());
			}
		}
		return data;
	}

	public Bitmap[] getImages(String symptomName, boolean all){
		Bitmap[] result;
		SQLiteDatabase db = this.getReadableDatabase();
		String selection;
		if(all){
			selection = "*";
		}
		else{
			selection = "image1";
		}
		String query = "SELECT " + selection + " FROM IMAGES WHERE SYMPTOM = " + symptomName;
		Cursor cursor = db.rawQuery(query, null);
		result = new Bitmap[cursor.getCount()];
		int i =0;
		if (cursor.moveToFirst()) {
			do {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(cursor.getBlob(0));
				Bitmap b = BitmapFactory.decodeStream(inputStream);
				result[i] = b;
				i++;
			} while (cursor.moveToNext());
		}
		db.close();
		return result;	
	}
}