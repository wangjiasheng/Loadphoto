package com.wjs.loadphoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.util.concurrent.Executors;

public class ImageUtils
{
	private int width=200;
	private int height=200;
	private Context context=null;
	private FileCacheMannager filtmanager=null;
	private LruCacheManager lrumanager=null;
	private int loadfaileresourse=-1;
	private int loading=-1;
	private int threadPoolSize=7;
	public ImageUtils(Context context) 
	{
		this.context=context;
		filtmanager=FileCacheMannager.getInstanse();
		lrumanager=LruCacheManager.getInstance();
	}
	/**
	 * 设置图片的宽和高
	 * @param width 图片的宽
	 * @param height 图片的高
	 * @return ImageUtils
	 */
	public ImageUtils setSize(int width,int height)
	{
		this.width=width;
		this.height=height;
		return this;
	}
	/**
	 * 设置加载失败的图片资源
	 * @param loadfaileresourse 加载失败的图片资源id
	 * @return ImageUtils
	 */
	public ImageUtils setLoadfaileResourse(int loadfaileresourse)
	{
		this.loadfaileresourse=loadfaileresourse;
		return this;
	}
	/**
	 * 设置加载中的图片资源
	 * @param loading 加载中的图片资源id
	 * @return ImageUtils
	 */
	public ImageUtils setLoadingResourse(int loading)
	{
		this.loading=loading;
		return this;
	}
	/**
	 * 设置图片缓存Dir
	 * @param cachedir 图片缓存dir
	 * @return ImageUtils
	 */
	public ImageUtils setCacheDir(String cachedir)
	{
		filtmanager.setCacheDir(cachedir);
		return this;
	}
	/**
	 * 设置线程池中的线程的数量
	 * @param threadPoolSize 线程的数量
	 */
	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}
	/**
	 * 设置图片的缓存周期，此缓存包括文件缓存，文件缓存必须有缓存周期，以便于服务器更换图片手机端也能得到相应更新
	 * @param time 缓存周期
	 * @return ImageUtils
	 */
	public ImageUtils setAliveTime(long time)
	{
		filtmanager.setAliveTime(time);
		return this;
	}
	public void loadImage(ImageView mImageView,String url)
	{
		if(mImageView!=null)
		{
			mImageView.setTag(314232332,url);
			LoadImageTask task = new LoadImageTask(new LoadImageListener()
			{
				@Override
				public void loadSucess(ImageView view,String url,Bitmap result) 
				{
					if(url.equals(view.getTag(314232332)))
				    {
						view.setImageBitmap(result);
				    }
				}
				@Override
				public void loadFaile(ImageView view,String url,Bitmap result)
				{
					view.setImageBitmap(result);
				}
				@Override
				public void loadPre(ImageView imageview,String url, Bitmap result) 
				{
					imageview.setImageBitmap(result);
				}
			},mImageView,url,width,height,loading,loadfaileresourse);
			task.executeOnExecutor(Executors.newFixedThreadPool(threadPoolSize));
		}
	}
	public Bitmap getLocalBitmapResourse(String url,int resourse)
	{
		if(resourse!=-1)
		{
			Bitmap bitmap = null;
			if((bitmap=lrumanager.getBitmap(url+width+"x"+height))!=null)
			{
			}
			else if((bitmap=filtmanager.getBitmap(url+width+"x"+height))!=null)
			{
				lrumanager.putBitmap(url+width+"x"+height, bitmap);
			}
			else
			{
				Bitmap loadingtemp = BitmapDecode.compressBitmapTo(context, resourse, width, height);
				if(loadingtemp!=null)
				{
					bitmap = BitmapShear.cutBitmap(loadingtemp, width, height);
					lrumanager.putBitmap(url + width + "x" + height, bitmap);
					filtmanager.putBitmap(url + width + "x" + height, bitmap);
					if (loadingtemp.isRecycled()) {
						loadingtemp.recycle();
					}
				}
			}
			return bitmap;
		}
		return null;
	}
	public Bitmap getLocalBitmapResourse(String url)
	{
		Bitmap bitmap = null;
		if(url.indexOf("file://")!=-1)
		{
			String filepath=url.substring(7);
			File fileobject=new File(filepath);
			if(!fileobject.exists())
			{
			}
			else if((bitmap=lrumanager.getBitmap(url+width+"x"+height))!=null)
			{
			}
			else if((bitmap=filtmanager.getBitmap(url+width+"x"+height))!=null)
			{
				lrumanager.putBitmap(url+width+"x"+height, bitmap);
			}
			else
			{
				Bitmap loadingtemp = BitmapDecode.compressBitmapTo(filepath, width, height);
				if(loadingtemp!=null)
				{
					bitmap = BitmapShear.cutBitmap(loadingtemp, width, height);
					lrumanager.putBitmap(url + width + "x" + height, bitmap);
					filtmanager.putBitmap(url + width + "x" + height, bitmap);
					if (loadingtemp.isRecycled()) {
						loadingtemp.recycle();
					}
				}
			}
		}
		return bitmap;
	}
	class LoadImageTask extends AsyncTask<String, Integer, Bitmap>
	{
		LoadImageListener listener=null;
		private ImageView view;
		private int loadingresourse;
		private int loadfaileresourse;
		int mwidth=200;
		int mheight=200;
		private String url;
		public LoadImageTask(LoadImageListener listener,ImageView view,String url,int width,int height,int loadingresourse,int loadfaileresourse) 
		{
			this.listener=listener;
			this.view=view;
			this.loadingresourse=loadingresourse;
			this.loadfaileresourse=loadfaileresourse;
			this.mwidth=width;
			this.mheight=height;
			this.url=url;
	    }
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			String url=context.getClass().toString()+"/loading";
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),loadingresourse);
			listener.loadPre(view,url,bitmap);
		}
		@Override
		protected Bitmap doInBackground(String... arg0) 
		{
			Bitmap bitmap = null;
			if((bitmap=lrumanager.getBitmap(url+mwidth+"x"+mheight))!=null)
			{
				return bitmap;
			}
			else if((bitmap=filtmanager.getBitmap(url+mwidth+"x"+mheight))!=null)
			{
				lrumanager.putBitmap(url+mwidth+"x"+mheight, bitmap);
				return bitmap;
			}
			else if(url.startsWith("http://")||url.startsWith("https://"))
			{
				if(NetworkUtils.isNetworkAvailable(context)&&((bitmap= NetworkBitmapUtils.getBitmap(url, mwidth, mheight))!=null))
				{
					Bitmap temp=bitmap;
					bitmap = BitmapShear.cutBitmap(bitmap, mwidth, mheight);
					if(temp.isRecycled())
					{
						temp.recycle();
					}
					lrumanager.putBitmap(url+mwidth+"x"+mheight, bitmap);
					filtmanager.putBitmap(url+mwidth+"x"+mheight, bitmap);
					return bitmap;
				}
			}
			else if(url.startsWith("file://"))
			{
				getLocalBitmapResourse(url);
			}
			return null;
		}
		@Override
		protected void onPostExecute(Bitmap result) 
		{
			if(result!=null)
			{
				listener.loadSucess(view,url,result);
			}
			else
			{
				String url=context.getClass().toString()+"/loadingfaile";
				Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),loadfaileresourse);
				listener.loadFaile(view,url,bitmap);
			}
		}
	}
	class DownThrea implements Runnable
	{
		
		public void run() 
		{
			
		}
		public void onPreExecute()
		{
			
		}
		public void doInBackground()
		{
			
		}
		public void onPostExecute()
		{
			
		}
	}
	interface LoadImageListener
	{
		public void loadPre(ImageView imageview, String url, Bitmap result);
		public void loadSucess(ImageView imageview, String url, Bitmap result);
		public void loadFaile(ImageView imageview, String url, Bitmap result);
	}
}
