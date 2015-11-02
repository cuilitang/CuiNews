package cui.litang.cuinews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context) {
		super(context);
	}
	
	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	/**
	 * 啥都不做
	 * 这样就无法滑动了...
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		return false;
	}
	
	/**
	 * 表示事件是否拦截, 返回false表示不拦截
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		return false;
	}

}
