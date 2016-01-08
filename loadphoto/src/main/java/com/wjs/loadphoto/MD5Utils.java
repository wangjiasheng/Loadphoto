package com.wjs.loadphoto;
import java.security.MessageDigest;


public class MD5Utils {

	// MD5加码。32位
	public static String MD5(String inStr) 
	{
		MessageDigest md5 = null;
		try 
		{
			md5 = MessageDigest.getInstance("MD5");
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++)
		{
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	// 可逆的加密算法
	public static String KL(String md5,String key) 
	{
		// String s = new String(inStr);
		char[] a = md5.toCharArray();
		char[] b = key.toCharArray();
		for (int i = 0; i < a.length; i++) 
		{
			for(int j=0;j<b.length;j++)
			{
				a[i]=(char)(a[i]^b[j]);
			}
		}
		String s = new String(a);
		return s;
	}

	// 加密后解密
	public static String JM(String md5,String key)
	{
		char[] a = md5.toCharArray();
		char[] b = new StringBuffer(key).reverse().toString().toCharArray();
		for (int i = 0; i < a.length; i++) 
		{
			for(int j=0;j<b.length;j++)
			{
				a[i]=(char)(a[i]^b[j]);
			}
		}
		String k = new String(a);
		return k;
	}

	// 测试主函数
	public static void main(String args[])
	{
		String s = new String("123123");
//		4297f44b13955235245b2497399d7a93
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + MD5(s));
		System.out.println("MD5后再加密：" + KL(MD5(s),"wjs"));
		System.out.println("解密为MD5后的：" + JM(KL(MD5(s),"wjs"),"wjs"));
	}
}

