package cui.litang.cuinews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 头条新闻的Viewpager
 * 
 * @author Kevin
 * 
 */
public class TopNewsViewPager extends ViewPager {

	int startX;
	int startY;

	public TopNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TopNewsViewPager(Context context) {
		super(context);
	}

	/**
	 * 事件分发, 请求父控件及祖宗控件是否拦截事件 1. 右划, 而且是第一个页面, 需要父控件拦截 2. 左划, 而且是最后一个页面, 需要父控件拦截
	 * 3. 上下滑动, 需要父控件拦截
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		
			getParent().requestDisallowInterceptTouchEvent(true);// 不要拦截,
																	// 这样是为了保证ACTION_MOVE调用
		

		return super.dispatchTouchEvent(ev);
	}

}
