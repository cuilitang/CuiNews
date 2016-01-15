package cui.litang.cuinews;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import cui.litang.cuinews.fragment.ContentFragment;
import cui.litang.cuinews.fragment.LeftMenuFragment;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.Window;

/**
 * 首页
 * @author Cuilitang
 *
 */
public class MainActivity extends SlidingFragmentActivity {
	
	private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
	private static final String FRAGMENT_CONTENT = "fragment_content";

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main_container);

        setBehindContentView(R.layout.left_menu_container);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);   //1
        
        int width = getWindowManager().getDefaultDisplay().getWidth();
        slidingMenu.setBehindOffset(width*200/320);  //主屏幕留下的宽度
        
        initFragment();
    }

	private void initFragment() {

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),FRAGMENT_LEFT_MENU);  //transaction.replace(containerViewId, fragment, tag);
		transaction.replace(R.id.fl_content, new ContentFragment(),FRAGMENT_CONTENT);
		transaction.commit();
	}
	
	//获取侧边栏fragment
	public LeftMenuFragment getLeftMenuFragment(){
		
		FragmentManager manager = getSupportFragmentManager();
		LeftMenuFragment f = (LeftMenuFragment) manager.findFragmentByTag(FRAGMENT_LEFT_MENU);
		return f;
	}
	
	//获取主页面fragment
	public ContentFragment getContentFragment(){
		
		FragmentManager manager = getSupportFragmentManager();
		ContentFragment f = (ContentFragment) manager.findFragmentByTag(FRAGMENT_CONTENT);
		return f;
	}
}
