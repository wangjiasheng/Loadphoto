package com.wjs.loadphoto;
/**
 * @author 王家胜
 *
 */
public class StringUtils 
{
	public synchronized static boolean isNotNull(String str)
	{
		if(str!=null&&!"".equals(str)&&!"0".equals(str)&&!"null".equals(str))
		{
			return true;
		}
		return false;
	}
}
