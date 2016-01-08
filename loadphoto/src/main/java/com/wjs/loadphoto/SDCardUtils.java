package com.wjs.loadphoto;

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
}