package cui.litang.cuinews.home;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class HomePager extends BasePager {

	public HomePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		
		System.out.println("初始化首页数据....");
		tv_title.setText("智慧北京");
		btn_menu.setVisibility(View.GONE);
		setSlidingMenuEnable(false);
		
		TextView textView = new TextView(mActivity);
		textView.setText("首页");
		textView.setTextColor(Color.RED);
		textView.setTextSize(25);
		textView.setGravity(Gravity.CENTER);
		fl_content.addView(textView);
	}
	
	

}
