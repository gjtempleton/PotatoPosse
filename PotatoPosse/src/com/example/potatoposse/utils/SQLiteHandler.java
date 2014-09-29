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

	public SQLiteHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) { }

	@Override
	public void onOpen(SQLiteDatabase db)
	{
		DBHelper dbHelper = new DBHelper(db);
		dbHelper.runTests();
		dbHelper.runProblems();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS tests;" +
				   "DROP TABLE IF EXISTS problems;");
		this.onCreate(db);
	}

	public String[] getListOfProblemsByType(String type)
	{		
		String query = "SELECT name FROM problems WHERE "+type.toLowerCase()+"=1;";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		String[] data = new String[cursor.getCount()];
		
		int j = 0;		
		if (cursor.moveToFirst()) 
		{
			do 
			{
				data[j] = cursor.getString(0);
				j++;
			} 
			while (cursor.moveToNext());
		}		
		db.close();
		
		return data;
	}

	public boolean[] getTypesByName(String name)
	{
		String query = "SELECT leaf, pest, tuber FROM problems WHERE name='"+name+"';";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		boolean[] data = new boolean[3];
				
		if (cursor.moveToFirst()) 
		{
			do 
			{
				data[0] = cursor.getInt(0)>0;
				data[1] = cursor.getInt(1)>0;
				data[2] = cursor.getInt(2)>0;
			} 
			while (cursor.moveToNext());
		}		
		db.close();
		
		return data;
	}
	
	public String[] getProblemBreakdownByName(String name)
	{
		String selectQuery = "SELECT description, control FROM problems WHERE name='"+name+"';";		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		String[] data = new String[2];
	
		if (cursor.moveToFirst()) 
		{
			do 
			{
				data[0] = cursor.getString(0);
				data[1] = cursor.getString(1);
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