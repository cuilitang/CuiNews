package cui.litang.cuinews.home;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import cui.litang.cuinews.base.BasePager;

public class GovAffairPager extends BasePager {

	public GovAffairPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		
		System.out.println("初始化政务数据....");
		tv_title.setText("政务");
		//btn_menu.setVisibility(View.GONE);
		setSlidingMenuEnable(false);
		
		TextView textView = new TextView(mActivity);
		textView.setText("政务页面内容");
		textView.setTextColor(Color.RED);
		textView.setTextSize(25);
		textView.setGravity(Gravity.CENTER);
		fl_content.addView(textView);
	}
}
