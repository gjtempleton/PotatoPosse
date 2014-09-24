package com.example.potatoposse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HTTPHandler {
	HttpClient client = new DefaultHttpClient();
	String request;
	HttpResponse response;
	String responseString;
	
	public HTTPHandler(String request){
		this.request = request;
		try {
			response = client.execute(new HttpGet(request));
			StatusLine status = response.getStatusLine();
			if(status.getStatusCode()==HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        out.close();
		        responseString = out.toString();
			}
			else{
		        //Closes the connection.
		        response.getEntity().getContent().close();
		        throw new IOException(status.getReasonPhrase());
		    }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
