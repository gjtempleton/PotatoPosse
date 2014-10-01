//TODO: CAN THIS BE DELETED?!?!

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
import android.preference.PreferenceManager;

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
	String lastUpdateOnServer;

	public HTTPHandler(String request, Context thisContext){
		CONTEXT = thisContext;
		lastTimeUpdated = PreferenceManager.getDefaultSharedPreferences(CONTEXT).getString("LAST_TIME_UPDATED", "NO_UPDATE");
		this.request = request;
		
		try 
		{
			response = client.execute(new HttpGet(request));
			StatusLine status = response.getStatusLine();
			
			if (status.getStatusCode()==HttpStatus.SC_OK)
			{
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
			}
			else
			{
		        //closes the connection
		        response.getEntity().getContent().close();
		        throw new IOException(status.getReasonPhrase());
		    }
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

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
				if (str.length()>2) lastUpdateOnServer = str;            	
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}

		return true;
	}

	public boolean downloadMediaZips()
	{
		try
		{
			for(int i = 0; i<2; i++){
				URL imagesUrl = new URL(TEST_IMAGES_LOCATION);
				if(i==0) imagesUrl = new URL(IMAGES_LOCATION);

				URLConnection ucon = imagesUrl.openConnection();
				ucon.setReadTimeout(5000);
				ucon.setConnectTimeout(10000);

				InputStream is = ucon.getInputStream();
				BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);
				/**
				 * Update last time database was updated
				 */
				PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit().putString("LAST_TIME_UPDATED", "myStringToSave").commit(); 

				/**
				 * Database download
				 */
				File file = new File( CONTEXT.getDir("zips", 0).toString() + "media.zip");

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

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean downloadDatabaseFile(final String path)
	{
		try
		{
			URL url = new URL(path);

			URLConnection ucon = url.openConnection();
			ucon.setReadTimeout(5000);
			ucon.setConnectTimeout(10000);

			InputStream is = ucon.getInputStream();
			BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);
			/**
			 * Update last time database was updated
			 */
			PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit().putString("LAST_TIME_UPDATED", "myStringToSave").commit(); 

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
