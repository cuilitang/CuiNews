package cui.litang.cuinews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 重写请求父控件事件拦截方法的ViewPager
 * 重写了DispatchTouchEvent 的ViewPager
 * @author Cuilitang
 *
 */
public class DteViewPager extends ViewPager {

	public DteViewPager(Context context) {
		super(context);
		// Auto-generated constructor stub
	}
	
	public DteViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if(getCurrentItem()!=0){
			getParent().requestDisallowInterceptTouchEvent(true);//请求父控件不要拦截事件
		}
		return super.dispatchTouchEvent(ev);
	}
	
	

}
