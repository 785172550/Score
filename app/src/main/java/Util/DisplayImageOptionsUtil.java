package Util;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * 
 * @ClassName:DisplayImageOptionsUtil
 * @Description:配置ImageLoader参数
 * @Author: wyb
 *
 */
public class DisplayImageOptionsUtil {
	
	public static DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
			.cacheInMemory(true).cacheOnDisk(true)
			.imageScaleType(ImageScaleType.EXACTLY).considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565).build();

	/*public static DisplayImageOptions radiusOptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.default_head)
			.showImageForEmptyUri(R.drawable.default_head)
			.showImageOnFail(R.drawable.default_head).cacheInMemory(true)
			.cacheOnDisk(true).displayer(new RoundedBitmapDisplayer(12))
			.imageScaleType(ImageScaleType.EXACTLY).considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565).build();*/
	
}
