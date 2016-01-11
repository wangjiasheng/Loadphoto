package com.wjs.loadphoto;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author 王家胜
 */
public class SDCardUtils 
{
	/**
	 * 判断sdcard是否挂载
	 * @return bool
	 */
	public static boolean isSDCardMounted()
	{
		String string = Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equalsIgnoreCase(string))
		{
			return true;
		}
		return false;
	}
	/**
	 * 获取缓存路径
	 * @param path 文件路径
	 * @return 缓存路径
	 */
	public static File getCatchDir(String path)
	{
		if(isSDCardMounted())
		{
			String filepath=Environment.getExternalStorageDirectory()+File.separator+path;
			File fileitem=new File(filepath);
			if(!fileitem.exists())
			{
				fileitem.mkdirs();
			}
			if(fileitem.exists())
			{
				return fileitem;
			}
		}
		return null;
	}
	public static boolean createDir(String path)
	{
		File file=new File(path);
		if(!file.exists())
		{
			return file.mkdirs();
		}
		return true;
	}

	/**
	 * @param context 上下文对象
	 * @return path /storage/emulated/0/Android/data/com.wjs.loadphoto.demo/files
	 */
	public static final String getExternalAndroidDir(Context context)
	{
		String path=context.getExternalFilesDir(null).getAbsolutePath();
		return path;
	}

	/**
	 * @param context 上下文对象
	 * @return path /data/data/com.wjs.loadphoto.demo/files
	 */
	public static final String getAndroidDir(Context context)
	{
		return context.getFilesDir().getAbsolutePath();
	}

	/**
	 * @param context 上下文对象
	 * @return path /storage/emulated/0/Android/data/com.wjs.loadphoto.demo/cache
	 */
	public static final String getExternalCacheDir(Context context)
	{
		return context.getExternalCacheDir().getAbsolutePath();
	}
	/**
	 * @param context 上下文对象
	 * @return path /data/data/com.wjs.loadphoto.demo/cache
	 */
	public static final String getCacheDir(Context context)
	{
		return context.getCacheDir().getAbsolutePath();
	}
}