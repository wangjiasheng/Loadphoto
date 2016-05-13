package com.wjs.loadphoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkBitmapUtils 
{
	private static Options getBitmapOption(String urlstring)
	{
		try
		{
			URL url = new URL(urlstring);
			HttpURLConnection con=(HttpURLConnection) url.openConnection();
			con.connect();
			InputStream stream = con.getInputStream();
		    Options options=new Options();
		    options.inJustDecodeBounds = true; 
		    BitmapFactory.decodeStream(stream,null,options);
		    stream.close();
		    con.disconnect();
		    return options;
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	public static Bitmap getBitmap(String urlstring,int width,int height)
	{
		Options option = null;
		if((option=getBitmapOption(urlstring))!=null)
		{
			Options potion=getDecodeMethod(option, width, height);
		    potion.inJustDecodeBounds=false;
			try
			{
				URL url = new URL(urlstring);
				HttpURLConnection con=(HttpURLConnection) url.openConnection();
				con.connect();
				InputStream stream = con.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(stream, null, potion);
				stream.close();
			    con.disconnect();
			    return bitmap;
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	public static Options getDecodeMethod(Options option,int width,int height)
	{
		return getCompressMethodWithWidthHeight(option, width, height);
	}
	public static Options getCompressMethodWithWidthHeight(Options option,int width,int height)
	{
		int outWidth = option.outWidth;
	    int outHeight = option.outHeight;
	    int scale=1;
	    if(outWidth>width||outHeight>height)
	    {
	    	int scaleX=outWidth/width;
	    	int scaleY=outHeight/height;
	    	if(scaleX<scaleY)
	    	{
	    		scale=scaleY;
	    	}
	    	else
	    	{
	    		scale=scaleX;
	    	}
	    }
	    option.inSampleSize=scale;
	    return option;
	}
}
