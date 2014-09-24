package com.example.potatoposse.utils;

import java.io.ByteArrayInputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class SQLiteHandler extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "PotatoDB";
	
	private boolean firstTime = false;

	public SQLiteHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) { }

	@Override
	public void onOpen(SQLiteDatabase db)
	{
			// SQL statement to create symptoms table
			// Drop older symptoms table if existed
			db.execSQL("DROP TABLE IF EXISTS symptoms");
			String CREATE_SYMPTOM_TABLE = "CREATE TABLE symptoms (" +
					"category TEXT, "+
					"name TEXT, "+
					"description TEXT, "+
					"test TEXT, "+
					"response TEXT)";

			// create symptoms table
			db.execSQL(CREATE_SYMPTOM_TABLE);
			
			//Populate the symptoms table with dummy data
			String INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES (" +
					"'LEAF', " +
					"'LEAF NAME', " +
					"'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas efficitur justo mi, sed convallis leo convallis nec. Nulla lobortis et orci eu iaculis. Sed convallis egestas ipsum sed molestie. Nulla et enim facilisis, tempor erat vel, malesuada dolor. Nulla magna urna, vestibulum eu fermentum id, convallis at est. Nam sed euismod est.', " + 
					"'LEAF TEST', " + 
					"'Phasellus euismod elit in erat scelerisque, vitae aliquam libero commodo. Nulla suscipit fermentum ex nec dictum. Nam imperdiet varius cursus. Donec ultricies pharetra laoreet. Donec aliquet egestas felis, a hendrerit ante aliquam quis. Integer a enim vitae odio feugiat luctus rutrum eget lacus.')";
			db.execSQL(INSERT_DUMMY_DATA);

			INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES ('PEST', 'PEST NAME', 'PEST DESCRIPTION', 'PEST RESPONSE', 'PEST TEST')";
			db.execSQL(INSERT_DUMMY_DATA);

			INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES ('TUBER', 'TUBER NAME', 'TUBER DESCRIPTION', 'TUBER RESPONSE', 'TUBER TEST')";
			db.execSQL(INSERT_DUMMY_DATA);
			INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES ('TUBER', 'TUBER NAME 2', 'TUBER DESCRIPTION 2', 'TUBER RESPONSE 2', 'TUBER TEST 2')";
			db.execSQL(INSERT_DUMMY_DATA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS symptoms");
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
		
		if (cursor.moveToFirst()) 
		{
			do 
			{
				data[j] = cursor.getString(0);
				j++;
				// get the data into array, or class variable

			} 
			while (cursor.moveToNext());
		}
		
		db.close();
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
				// get the data into array, or class variable

			} 
			while (cursor.moveToNext());
		}
		db.close();
		
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
		
		if (cursor.moveToFirst()) 
		{
			do 
			{
				ByteArrayInputStream inputStream = new ByteArrayInputStream(cursor.getBlob(0));
				Bitmap b = BitmapFactory.decodeStream(inputStream);
				result[i] = b;
				i++;
			} 
			while (cursor.moveToNext());
		}
		db.close();
		return result;	
	}
}