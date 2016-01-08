package com.wjs.loadphoto;

import android.graphics.Bitmap;

/**
 * @author 314232332
 */
public class BitmapShear 
{
	/**
	 * @param bitmap 需要剪切的图片
	 * @param deswidth 剪切后的宽度
	 * @param desheight 剪切后的高度
	 * @return bitmap(裁剪后的图片)
	 */
	public static Bitmap cutBitmap(Bitmap bitmap,int deswidth,int desheight)
	{
		int bitmapwidth=bitmap.getWidth();    //计算图片的宽度
		int bitmapheight=bitmap.getHeight();  //计算图片的高度
		
		if(bitmapwidth>deswidth)              //如果图片的宽度大于目标图片的宽度
		{
			if(bitmapheight>desheight)        //如果图片的高度大于目标图片的高度
			{//宽高都大则宽高都切
				int left=(int)((1.0*bitmapwidth-deswidth)/2);   
				int top=(int)((1.0*bitmapheight-desheight)/2);  
				Bitmap bitmapresult = Bitmap.createBitmap(bitmap,left,top,deswidth, desheight);
				return bitmapresult;
			}
			else                        
			{
				int newdeswidth=(int)((1.0*deswidth/desheight)*bitmapheight);  //计算需要的宽度
				int left=(int)((1.0*bitmapwidth-newdeswidth)/2);
				int top=0;                  //高度小于目标高度不需要剪切
				Bitmap bitmapresult = Bitmap.createBitmap(bitmap,left,top,newdeswidth, bitmapheight);
				return bitmapresult;
			}
		}
		else                           
		{
			if(bitmapheight>desheight) //图片宽度小于目标图片的宽度，高度大于目标图片的高度
			{
				int newdesheight=(int)((1.0*desheight/deswidth)*bitmapwidth);//计算需要的高度
				int left=0;   //宽度不变
				int top=(int)((1.0*bitmapheight-newdesheight)/2);
				Bitmap bitmapresult = Bitmap.createBitmap(bitmap,left,top,bitmapwidth, newdesheight);
				return bitmapresult;
			}
			else   //图片宽高都小于目标，为了适应目标宽高比，仍然需要进行剪切
			{
				int newdesheight=(int)((1.0*desheight/deswidth)*bitmapwidth);    //假设宽相同计算出需要剪切的高度
				if(newdesheight>bitmapheight)   //宽度相同需要剪切的高度大于原图的高度,当宽高等比例放到高度相同时原图片的宽度大于目标的宽度，则宽需要裁剪
				{                              
					int newdeswidth=(int)((1.0*deswidth/desheight)*bitmapheight);
					int left=(int)((1.0*bitmapwidth-newdeswidth)/2);
					int top=0;
					Bitmap bitmapresult = Bitmap.createBitmap(bitmap,left,top,newdeswidth, bitmapheight);
					return bitmapresult;
				}
				else                        //需要裁剪的宽度大于目标宽度，则高需要裁剪
				{                               
					int left=0;
					int top=(int)((1.0*bitmapheight-newdesheight)/2);
					Bitmap bitmapresult = Bitmap.createBitmap(bitmap,left,top,bitmapwidth, newdesheight);
					return bitmapresult;
				}
			}
		}
	}
}
