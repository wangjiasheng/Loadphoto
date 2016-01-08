package com.wjs.loadphoto;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileCacheMannager
{
	private FileCacheMannager() {}
	public static FileCacheMannager manager=null;
	private long modifi=1000*60*60;
	private String cachedir="wjs/cache/";
	public static FileCacheMannager getInstanse()
	{
		if(manager==null)
		{
			manager=new FileCacheMannager();
		}
		return manager;
	}
	public void setAliveTime(long modifi)
	{
		this.modifi=modifi;
	}
	public void setCacheDir(String cachedir)
	{
		this.cachedir=cachedir;
	}
	public boolean putBitmap(String url,Bitmap bitmap)
	{
		if(StringUtils.isNotNull(url)&&bitmap!=null)
		{
			File dir = SDCardUtils.getCatchDir(cachedir);
			if(dir!=null)
			{
				File filepath=new File(dir, MD5Utils.MD5(url));
				try
				{
					Log.i("wjs", "文件缓存保存");
					return bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(filepath));
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	public Bitmap getBitmap(String url)
	{
		if(StringUtils.isNotNull(url))
		{
			File dir = SDCardUtils.getCatchDir(cachedir);
			if(dir!=null)
			{
				File filepath=new File(dir,MD5Utils.MD5(url));
				if(System.currentTimeMillis()-filepath.lastModified()<modifi)
				{
					Log.i("wjs", "文件缓存命中");
					return BitmapFactory.decodeFile(filepath.getAbsolutePath());
				}
			}
		}
		return null;
	}
}
