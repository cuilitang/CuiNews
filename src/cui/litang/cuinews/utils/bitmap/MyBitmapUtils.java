package cui.litang.cuinews.utils.bitmap;

import cui.litang.cuinews.R;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyBitmapUtils {
	
	NetCacheUtils mNetCacheUtils;
	LocalCacheUtils mLocalCacheUtils;
	MemoryCacheUtils mMemoryCacheUtils;
	
	public MyBitmapUtils(){
		
		mMemoryCacheUtils = new MemoryCacheUtils();
		mLocalCacheUtils = new LocalCacheUtils();
		mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
	}
	
	public void display(ImageView ivPic,String url) {

		ivPic.setImageResource(R.drawable.news_pic_default);
		
		Bitmap bitmap = null;
		bitmap = mMemoryCacheUtils.getBitmapToMemory(url);
		if(bitmap!=null){
			ivPic.setImageBitmap(bitmap);
			System.out.println("从内存读取图片啦...");
			return;
		}
		
		bitmap = mLocalCacheUtils.getBitmapToLocal(url);
		if(bitmap!=null){
			ivPic.setImageBitmap(bitmap);
			System.out.println("从本地读取图片啦...");
			return;
		}
		
		mNetCacheUtils.getBitmapFromNet(ivPic, url);
		
		
	}

}
