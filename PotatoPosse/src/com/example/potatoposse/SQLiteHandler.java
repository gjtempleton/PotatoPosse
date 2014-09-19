package com.example.potatoposse;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class SQLiteHandler extends SQLiteOpenHelper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "PotatoDB";

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
						"test TEXT)";

				// create symptoms table
				db.execSQL(CREATE_SYMPTOM_TABLE);
		//Populate the symptoms table with dummy data

		String INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES ('LEAF', 'TEST LEAF', 'THIS IS A TEST LEAF', 'VISUAL')";
		db.execSQL(INSERT_DUMMY_DATA);
		
		INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES ('PEST', 'TEST PEST', 'THIS IS A TEST PEST', 'VISUAL')";
		db.execSQL(INSERT_DUMMY_DATA);
		
		INSERT_DUMMY_DATA = "INSERT INTO symptoms VALUES ('TUBER', 'TEST TUBER', 'THIS IS A TEST TUBER', 'VISUAL')";
		String INSERT_DUMMY_DATA_2 = "INSERT INTO symptoms VALUES ('TUBER', 'TEST TUBER NO 2', 'THIS IS A TEST TUBER', 'VISUAL')";
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

	public Bitmap[] getImages(String symptomName){
		Bitmap[] result;
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM IMAGES WHERE SYMPTOM = " + symptomName;
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