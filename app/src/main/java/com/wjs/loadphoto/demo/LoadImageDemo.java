package com.wjs.loadphoto.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.wjs.loadphoto.ImageUtils;

import java.io.File;

public class LoadImageDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image_demo);
        ImageView iv= (ImageView) findViewById(R.id.mImageView);
        ImageUtils urls=new ImageUtils(this);
        urls.setAliveTime(1000 * 60 * 60);//文件缓存声明周期
        urls.setCacheDir("android/项目名/cache");  //缓存文件夹
        urls.setLoadfaileResourse(R.mipmap.ic_launcher);//加载失败图片
        urls.setLoadingResourse(R.mipmap.ic_launcher);//加载中图片
        urls.setSize(105, 68);//加载图片尺寸
        urls.setThreadPoolSize(5);//设置同时加载图片的数量
        String path="file://"+ Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"zhenmian.png";
        urls.loadImage(iv, path);
    }
}
