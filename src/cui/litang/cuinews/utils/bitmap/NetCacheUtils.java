package cui.litang.cuinews.utils.bitmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NetCacheUtils {
	
	private LocalCacheUtils mLocalCacheUtils;
	private MemoryCacheUtils mMemoryCacheUtils;

	public NetCacheUtils(LocalCacheUtils localCacheUtils,
			MemoryCacheUtils memoryCacheUtils) {
		mLocalCacheUtils = localCacheUtils;
		mMemoryCacheUtils = memoryCacheUtils;
	}
	
	public void getBitmapFromNet(ImageView ivPic,String url){
		new BitmapTask().execute(ivPic,url);
	}
	
	
	class BitmapTask extends AsyncTask<Object, Void, Bitmap>{

		private ImageView ivPic;
		private String url;
		
		@Override
		protected Bitmap doInBackground(Object... params) {
			
			ivPic = (ImageView) params[0];
			url = (String) params[1];
			
			ivPic.setTag(url);
			return downloadBitmap(url);
		}
		
		/**
		 * 不做任何处理
		 */
		@Override
		protected void onProgressUpdate(Void... values) {
			//  Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			
			if(result != null){
				String tag = (String) ivPic.getTag();
				
				if(url.equals(tag)){
					ivPic.setImageBitmap(result);
					mLocalCacheUtils.setBitmapToLocal(url,result);
					mMemoryCacheUtils.setBitmapToMemory(url,result);
					System.out.println("从网络缓存读取图片啦...");
				}
			}
		}
		
	}


	
	/*
	 * 从网络下载图片
	 * @param url 网络地址
	 * @return  图片对象
	 */
	public Bitmap downloadBitmap(String url) {

		HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				conn.setRequestMethod("GET");
				conn.connect();
				
				int code = conn.getResponseCode();
				if(code == 200){
					InputStream stream = conn.getInputStream();
					
					Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					options.inPreferredConfig = Bitmap.Config.RGB_565;
					
					Bitmap bitmap = BitmapFactory.decodeStream(stream,null,options);
					return bitmap;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				conn.disconnect();
			}
			
		return null;
	}

		
}
