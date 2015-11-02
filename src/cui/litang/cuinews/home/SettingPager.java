package cui.litang.cuinews.home;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import cui.litang.cuinews.base.BasePager;

public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		
		System.out.println("初始化设置数据....");
		tv_title.setText("设置");
		//btn_menu.setVisibility(View.GONE);
		setSlidingMenuEnable(false);
		
		TextView textView = new TextView(mActivity);
		textView.setText("设置页面内容");
		textView.setTextColor(Color.RED);
		textView.setTextSize(25);
		textView.setGravity(Gravity.CENTER);
		fl_content.addView(textView);
	}

}
