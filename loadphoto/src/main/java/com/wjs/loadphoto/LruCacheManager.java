package com.wjs.loadphoto;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

public class LruCacheManager
{
	private static LruCacheManager mlcm=null;
	private LruCache<String, Bitmap> lru=null;
	private static SoftReferenceCacheManager msrm=null;
	private LruCacheManager()
	{
		msrm=SoftReferenceCacheManager.getInstance();
		int lrusize=(int)(Runtime.getRuntime().maxMemory()/8);
		if(lrusize<0)
		{
			lrusize=30*1024*1024;
			System.out.println("内存太大");
		}
		lru=new LruCache<String, Bitmap>(lrusize)
		{
			protected void entryRemoved(boolean evicted, String key,Bitmap oldValue, Bitmap newValue) 
			{
				if(evicted)
				{
					msrm.putBitmap(key, oldValue);
					System.out.println("图片从硬缓存移除存入软缓存");
				}
			}
			protected int sizeOf(String key, Bitmap value) 
			{
				return value.getRowBytes()*value.getHeight();
			}
		};
	};
	public static synchronized LruCacheManager getInstance()
	{
		if(mlcm==null)
		{
			mlcm=new LruCacheManager();
			System.out.println("创建硬缓存");
		}
		return mlcm;
	}
	public void putBitmap(String url,Bitmap bitmap)
	{
		if(lru==null)
			return;
		Log.i("wjs","硬缓存保存");
		lru.put(url, bitmap);
	}
	public Bitmap getBitmap(String url)
	{
		if(lru==null&&url==null)
			return null;
		Bitmap bitmap = lru.get(url);
		if(bitmap==null)
		{
			bitmap=msrm.getBitmap(url);
			if(bitmap!=null)
			{
				lru.put(url, bitmap);
				msrm.removeFromSoft(url);
				System.out.println("软缓存找到图片,图片被放入硬缓存并且从软缓存移除");
			}
		}
		else
		{
			System.out.println("硬缓存命中");
		}
		return bitmap;
	}
}
