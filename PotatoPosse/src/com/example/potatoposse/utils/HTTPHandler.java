package com.example.potatoposse.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

public class HTTPHandler {
	HttpClient client = new DefaultHttpClient();
	final String TEXT_LOCATION = "http://54.72.106.175/static/lastUpdated.txt";
	final String DB_LOCATION = "http://54.72.106.175/static/db.sqlite3";
	final String IMAGES_LOCATION = "http://54.72.106.175/static/media.zip";
	final String TEST_IMAGES_LOCATION = "http://54.72.106.175/static/tests.zip";
	String request;
	HttpResponse response;
	String responseString;
	Context CONTEXT;
	String lastTimeUpdated;
	String lastUpdateOnServer = "";
	String locations[][] = new String[2][3];

	public HTTPHandler(Context thisContext)
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		CONTEXT = thisContext;
		locations[0][0] = (CONTEXT.getDir("zips", 0).toString() + "media.zip");
		locations[0][1] = CONTEXT.getDir("images", 0).toString();
		locations[0][2] = IMAGES_LOCATION;
		locations[1][0] = (CONTEXT.getDir("zips", 0).toString() + "tests.zip");
		locations[1][1] = CONTEXT.getDir("testImages", 0).toString();
		locations[1][2] = TEST_IMAGES_LOCATION;
		lastTimeUpdated = PreferenceManager.getDefaultSharedPreferences(CONTEXT).getString("LAST_TIME_UPDATED", "NO_UPDATE");
	}

	/**
	 * Returns true if app data is up to date, false otherwise
	 * @return
	 */
	public boolean getServerUpdateDate()
	{
		try
		{
			URL url = new URL(TEXT_LOCATION);

			URLConnection ucon = url.openConnection();
			ucon.setReadTimeout(5000);
			ucon.setConnectTimeout(10000);

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String str;
			//Set local lastUpdateOnServer to be date read in from server in form yyyyMMdd
			while ((str = in.readLine()) != null) {
				lastUpdateOnServer += str;            	
			}
			in.close();
			
			String lastUpdateOnDevice = PreferenceManager.getDefaultSharedPreferences(CONTEXT).getString("LAST_TIME_UPDATED", null);
			
			int serverDate = Integer.parseInt(lastUpdateOnServer);
			int deviceDate = Integer.parseInt(lastUpdateOnDevice);
			if(serverDate>deviceDate) return false;
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return true;
	}

	public boolean downloadMediaZips()
	{
		try
		{
			for(int i = 0; i<locations.length; i++){
				URL imagesUrl = new URL(locations[i][2]);

				URLConnection ucon = imagesUrl.openConnection();
				ucon.setReadTimeout(5000);
				ucon.setConnectTimeout(10000);

				InputStream is = ucon.getInputStream();
				BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);

				/**
				 * Database download
				 */
				File file = new File(locations[i][0]);

				if (file.exists())
				{
					file.delete();
				}
				file.createNewFile();

				FileOutputStream outStream = new FileOutputStream(file);
				byte[] buff = new byte[5 * 1024];

				int len;
				while ((len = inStream.read(buff)) != -1)
				{
					outStream.write(buff, 0, len);
				}

				outStream.flush();
				outStream.close();
				inStream.close();
				ZipHandler.unpackZip(locations[i][1], file.getAbsolutePath());
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean downloadDatabaseFile()
	{
		try
		{
			URL url = new URL(DB_LOCATION);

			URLConnection ucon = url.openConnection();
			ucon.setReadTimeout(5000);
			ucon.setConnectTimeout(10000);

			InputStream is = ucon.getInputStream();
			BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);
			/**
			 * Update last time database was updated
			 */
			PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit().putString("LAST_TIME_UPDATED", lastUpdateOnServer).commit(); 

			/**
			 * Database download
			 */
			File file = new File( CONTEXT.getDatabasePath("potatoDB").toString());

			if (file.exists())
			{
				file.delete();
			}
			file.createNewFile();

			FileOutputStream outStream = new FileOutputStream(file);
			byte[] buff = new byte[5 * 1024];

			int len;
			while ((len = inStream.read(buff)) != -1)
			{
				outStream.write(buff, 0, len);
			}

			outStream.flush();
			outStream.close();
			inStream.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
