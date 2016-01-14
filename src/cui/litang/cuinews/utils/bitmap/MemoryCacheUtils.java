package cui.litang.cuinews.utils.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoryCacheUtils {
	
	private LruCache<String, Bitmap> mMemoryCache;
	
	public MemoryCacheUtils(){
		
		long maxMemory = Runtime.getRuntime().maxMemory();
		mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory){
			
			@Override
			protected int sizeOf(String key, Bitmap value) {

				int byteCount = value.getRowBytes()*value.getHeight();
				return byteCount;
			}
		};
		
	}

	/**
	 * 写缓存
	 * @param url
	 * @param result
	 */
	public void setBitmapToMemory(String url, Bitmap result) {
		
		mMemoryCache.put(url, result);
	}
	

	/**
	 * 读缓存
	 * @param url
	 * @return
	 */
	public Bitmap getBitmapToMemory(String url) {

		return mMemoryCache.get(url);
		
	}


}
