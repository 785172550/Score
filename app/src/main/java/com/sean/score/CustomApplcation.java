package com.sean.score;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.orm.SugarContext;

/**
 * Created by Administrator on 2016/6/10.
 */
public class CustomApplcation extends Application {

	public static CustomApplcation mInstance;
	private static RequestQueue mrequest;
	private static final String TAG = "Application-----------";

	public CustomApplcation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		//数据库
		SugarContext.init(this);

		//初始化imageloader
		initImageLoader(this);

		// 初始化 消息队列
		mrequest = Volley.newRequestQueue(getApplicationContext());
	}

	// 单例模式，才能及时返回数据
	public static CustomApplcation getInstance() {
		return mInstance;
	}

	public static RequestQueue getRequestQuene() {
		return mrequest;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		SugarContext.terminate();
	}

	//初始化imageloader
	private void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 1);
		config.denyCacheImageMultipleSizesInMemory();
		config.memoryCacheSize(2 * 1024 * 1014);
		/*设置内存缓存的最大大小 默认为一个当前应用可用内存的1/8    */
		config.threadPoolSize(3);
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		//config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
}
