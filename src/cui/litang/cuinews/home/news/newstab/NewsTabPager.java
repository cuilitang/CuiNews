package cui.litang.cuinews.home.news.newstab;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import cui.litang.cuinews.domain.NewsData.NewsTabData;
import cui.litang.cuinews.home.news.BaseNewsMenuPager;

public class NewsTabPager extends BaseNewsMenuPager {

	NewsTabData mTabData;
	private TextView tvText;

	
	
	public NewsTabPager(Activity activity,NewsTabData newsTabData) {
		super(activity);
		
		mTabData = newsTabData;
	}

	@Override
	public View initViews() {

		tvText = new TextView(mActivity);
		tvText.setText("页签详情页");
		tvText.setTextColor(Color.RED);
		tvText.setTextSize(25);
		tvText.setGravity(Gravity.CENTER);
		return tvText;	
	
	}
	
	@Override
	public void initData() {

		tvText.setText(mTabData.title);
	}

}
