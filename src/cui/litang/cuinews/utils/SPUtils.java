package cui.litang.cuinews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPerference 工具类
 * @author Cuilitang
 *
 */
public class SPUtils {
	
	private static final String SP_NAME = "config";

	/**
	 * 读boolean值
	 * @param context 上下文
	 * @param key
	 * @param defValue 默认值
	 * @return 得到的值
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}
	
	/**
	 * 写boolean值
	 * @param context 上下文
	 * @param key 
	 * @param value 要写入的值
	 */
	public static void setBoolean(Context context, String key, boolean value) {
		
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

}
