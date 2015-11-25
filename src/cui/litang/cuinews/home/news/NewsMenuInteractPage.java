package cui.litang.cuinews.home.news;

import cui.litang.cuinews.home.news.newstab.BaseNewsMenuPager;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class NewsMenuInteractPage extends BaseNewsMenuPager {

	public NewsMenuInteractPage(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initViews() {
		TextView text = new TextView(mActivity);
		text.setText("菜单详情页-新闻-互动");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);

		return text;
	}

}
