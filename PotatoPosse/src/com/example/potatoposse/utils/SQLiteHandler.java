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
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "potatoDB";
	
	private String PROBLEMS = "uploadingImages_problem";
	private String iPROBLEMS = "uploadingImages_probimg";
	
	private String TESTS = "uploadingImages_test";
	private String iTESTS = "uploadingImages_testimg";

	public SQLiteHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		Log.w("THIS IS DB", this.getReadableDatabase().getPath());
	}

	@Override
	public void onCreate(SQLiteDatabase db) { }

	@Override
	public void onOpen(SQLiteDatabase db)
	{
//		DBHelper dbHelper = new DBHelper(db);
//		dbHelper.runTests();
//		dbHelper.runProblems();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
//		db.execSQL("DROP TABLE IF EXISTS uploadingVideos_test;" +
//				   "DROP TABLE IF EXISTS uploadingImages_problem;");
//		this.onCreate(db);
	}

	public String[] getListOfProblemsByType(String type)
	{		
		String query = "SELECT name FROM "+PROBLEMS+" WHERE "+type.toLowerCase()+"=1;";
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
		String query = "SELECT leaf, pest, tuber FROM "+PROBLEMS+" WHERE name='"+name+"';";
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
		String query = "SELECT description, test1_id, test2_id, control FROM "+PROBLEMS+" WHERE name='"+name+"';";		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		String[] data = new String[4];
	
		if (cursor.moveToFirst()) 
		{
			do 
			{
				data[0] = cursor.getString(0);
				data[1] = cursor.getString(1);
				data[2] = cursor.getString(2);
				data[3] = cursor.getString(3);
			} 
			while (cursor.moveToNext());
		}
		db.close();
		
		return data;
	}
	
	private int getProblemIdByName(String name)
	{
		String query = "SELECT id FROM "+PROBLEMS+" WHERE name='"+name+"';";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		int id = 0;
		
		if (cursor.moveToFirst())
		{
			do
			{
				id = cursor.getInt(0);
			}
			while (cursor.moveToNext());
		}
		db.close();
		
		return id;
	}
	
	public String getMainProblemImageByName(String name)
	{
		int id = getProblemIdByName(name);
		
		String query = "SELECT picture FROM "+iPROBLEMS+" WHERE problem_id="+id+" AND main=1;";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		String data = null;
		
		if (cursor.moveToFirst())
		{
			do
			{
				data = cursor.getString(0);
			}
			while (cursor.moveToNext());
		}
		db.close();
		
		return data;
	}

	public String[] getProblemImagesByName(String name)
	{
		int id = getProblemIdByName(name);
		
		String query = "SELECT picture FROM "+iPROBLEMS+" WHERE problem_id="+id+";";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		String[] data = new String[cursor.getCount()];
		
		int i=0;
		if (cursor.moveToFirst())
		{
			do
			{
				data[i] = cursor.getString(0);
				i++;
			}
			while (cursor.moveToNext());
		}
		db.close();
		
		return data;
	}
	
	public String getTestNameById(int id)
	{
		String query = "SELECT name FROM "+TESTS+" WHERE id="+id+";";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		String name = null;
		
		if (cursor.moveToFirst())
		{
			do
			{
				name = cursor.getString(0);
			}
			while (cursor.moveToNext());
		}
		db.close();
		
		return name;
	}
	
	public String[][] getTestImagesById(int id)
	{		
		String query = "SELECT position, image FROM "+iTESTS+" WHERE test_id="+id+";";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		String[][] data = new String[cursor.getCount()][2];
		
		int i=0;
		if (cursor.moveToFirst())
		{
			do
			{
				data[i][0] = cursor.getString(0);
				data[i][1] = cursor.getString(1);
				i++;
			}
			while (cursor.moveToNext());
		}
		db.close();
		
		return data;
	}
}