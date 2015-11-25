package cui.litang.cuinews.home.news;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class NewsMenuTopicPager extends BaseNewsMenuPager {

	public NewsMenuTopicPager(Activity activity) {
		super(activity);
		
	}

	@Override
	public View initViews() {
		
		TextView text = new TextView(mActivity);
		text.setText("菜单详情页-新闻-专题");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);

		return text;
	}

}
