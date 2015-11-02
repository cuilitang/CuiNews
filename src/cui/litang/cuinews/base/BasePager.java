package cui.litang.cuinews.base;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import cui.litang.cuinews.MainActivity;
import cui.litang.cuinews.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class BasePager {
	public Activity mActivity;
	public View mRootView;
	public TextView tv_title;
	public FrameLayout fl_content;
	public ImageButton btn_menu;
	
	public BasePager(Activity activity){
		mActivity = activity;
		initViews();
	}

	public void initViews() {
		
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
		tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
		fl_content = (FrameLayout) mRootView.findViewById(R.id.fl_content);
		btn_menu = (ImageButton) mRootView.findViewById(R.id.btn_menu);
		btn_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				toggleSlidingMenu();
			}
		});
		
		
		
	}

	/**
	 * 触发SlidingMenu
	 */
	public void toggleSlidingMenu() {
		
		MainActivity mainActivity = (MainActivity)mActivity;
		SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
		slidingMenu.toggle();
	}

	/**
	 * 初始化数据
	 */
	public void initData(){}
	
	/**
	 * 设置SlidingMenu是否禁用
	 * @param enable
	 */
	public void setSlidingMenuEnable(boolean enable){
		
		MainActivity mainActivity = (MainActivity)mActivity;
		SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

	}
}
