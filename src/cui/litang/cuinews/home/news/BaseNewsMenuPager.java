package cui.litang.cuinews.home.news;

import android.app.Activity;
import android.view.View;

/**
 * 侧边栏菜单详情页
 * @author Cuilitang
 *
 */
public abstract class BaseNewsMenuPager {
	
	public Activity mActivity;
	public View mRootView; // 根布局对象
	
	public BaseNewsMenuPager(Activity activity){
		
		mActivity = activity;
		mRootView = initViews();
		
	}

	public abstract View initViews();
	public void initData(){};

}
