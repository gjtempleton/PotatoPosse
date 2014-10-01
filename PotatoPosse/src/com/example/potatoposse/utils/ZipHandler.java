package com.example.potatoposse.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipHandler 
{
	public static boolean unpackZip(String path, String zipname)
	{       
	     InputStream is;
	     ZipInputStream zis;
	     
	     try 
	     { 
	         String filename;    
	         
	         File newDr = new File(path+"zipExample");
             newDr.mkdirs();
	         
	         is = new FileInputStream(path + zipname);
	         zis = new ZipInputStream(new BufferedInputStream(is));          
	         ZipEntry ze;
	         byte[] buffer = new byte[1024];
	         int count;
	         
	         while ((ze = zis.getNextEntry()) != null) 
	         {
	        	 filename = ze.getName();      	 
	             
	        	 //need to create directories if not exists, or will generate an exception
	             if (ze.isDirectory()) 
	             {
	            	 File fmd = new File(path + filename);
	                 fmd.mkdirs();
	                 continue;
	             }

	             FileOutputStream fout = new FileOutputStream(path + filename);

	             while ((count = zis.read(buffer)) != -1) 
	             {
	                 fout.write(buffer, 0, count);             
	             }

	             fout.close();               
	             zis.closeEntry();
	         }
	         zis.close();
	     } 
	     catch(IOException e)
	     {
	         e.printStackTrace();
	         return false;
	     }

	     return true;
	}
}