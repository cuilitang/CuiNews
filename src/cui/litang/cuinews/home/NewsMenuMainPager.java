package cui.litang.cuinews.home;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import cui.litang.cuinews.R;
import cui.litang.cuinews.domain.NewsData.NewsTabData;
import cui.litang.cuinews.view.BaseNewsMenuPager;

public class NewsMenuMainPager extends BaseNewsMenuPager {
	
	private ArrayList<NewsTabData> mNewsTabData;// 页签网络数据
	private ViewPager mViewPager;  //页签

	public NewsMenuMainPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	public NewsMenuMainPager(Activity mActivity, ArrayList<NewsTabData> children) {
		
		super(mActivity);
		mNewsTabData = children;
	}

	@Override
	public View initViews() {
		
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager)view.findViewById(R.id.vp_menu_detail);
		return view;
	}

}
