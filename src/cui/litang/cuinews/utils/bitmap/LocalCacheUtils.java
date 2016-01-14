package cui.litang.cuinews.utils.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import cui.litang.cuinews.utils.MD5Encoder;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class LocalCacheUtils {

	public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"zhbj_cache";
	
	/**
	 * 写缓存
	 * @param url
	 * @param result
	 */
	public void setBitmapToLocal(String url, Bitmap result) {
	
		try {
			String fileName = MD5Encoder.encode(url);
			File file = new File(CACHE_PATH,fileName);
			File parentFile = file.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
			
			result.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public Bitmap getBitmapToLocal(String url) {
		
		try {
			String MD5Url = MD5Encoder.encode(url);
			File file = new File(CACHE_PATH, MD5Url);
			if (file.exists()) {
				
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
				return bitmap;
			}
		} catch (Exception e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
		
	}


}
