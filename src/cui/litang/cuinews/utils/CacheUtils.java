package cui.litang.cuinews.utils;

import android.content.Context;

/**
 * 缓存工具类
 * @author Cuilitang
 *
 */
public class CacheUtils {
	
	/**
	 * 写入缓存
	 * @param ctx Context
	 * @param key url或md5(url)
	 * @param value content json
	 */
	public static void setCatche(Context ctx, String key, String value){
		
		SPUtils.setString(ctx, key, value);
	}
	
	/**
	 * 读取缓存
	 * @param ctx Context
	 * @param key url或md5(url)
	 * @return  content json
	 */
	public static String getCatche(Context ctx, String key){
		
		return SPUtils.getString(ctx, key, null);
	}
	
	

}
