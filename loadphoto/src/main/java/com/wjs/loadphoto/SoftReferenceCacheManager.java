package com.wjs.loadphoto;

import android.graphics.Bitmap;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class SoftReferenceCacheManager 
{
	private static SoftReferenceCacheManager msrm=null;
	private Map<String, SoftReference<Bitmap>> map=null;
	private SoftReferenceCacheManager()
	{
		map=new HashMap<String, SoftReference<Bitmap>>();
	}
	public static synchronized SoftReferenceCacheManager getInstance()
	{
		if(msrm==null)
		{
			msrm=new SoftReferenceCacheManager();
			System.out.println("创建软缓存");
		}
		return msrm;
	}
	public void putBitmap(String url,Bitmap bitmap)
	{
		if(map==null&&url==null)
			return;
		Log.i("wjs","软缓存保存");
		map.put(url, new SoftReference<Bitmap>(bitmap));
	}
	public Bitmap getBitmap(String url)
	{
		if(map==null&&url==null)
			return null;
		SoftReference<Bitmap> softReference = map.get(url);
		if(softReference!=null)
		{
			Log.i("wjs","软缓存命中");
			return softReference.get();
		}
		return null;
	}
	public void removeFromSoft(String url)
	{
		if(map==null&&url==null)
			return;
		SoftReference<Bitmap> bitmap = map.get(url);
		if(bitmap!=null)
		{
			Bitmap bitmap2 = bitmap.get();
			if(bitmap2.isRecycled())
			{
				bitmap2.recycle();
				bitmap2=null;
			}
			bitmap.clear();
		}
		bitmap=null;
		map.remove(url);
	}
}
