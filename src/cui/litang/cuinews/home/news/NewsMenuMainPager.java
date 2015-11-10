package cui.litang.cuinews.home.news;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import cui.litang.cuinews.R;
import cui.litang.cuinews.domain.NewsData.NewsTabData;
import cui.litang.cuinews.home.news.newstab.NewsTabPager;

public class NewsMenuMainPager extends BaseNewsMenuPager {
	
	private ArrayList<NewsTabData> mNewsTabData;// 页签网络数据
	private ArrayList<NewsTabPager> mPagerList; //Viewpager 页面
	private ViewPager mViewPager;  //页签

	public NewsMenuMainPager(Activity activity) {
		super(activity);
	}

	public NewsMenuMainPager(Activity mActivity, ArrayList<NewsTabData> children) {
		
		super(mActivity);
		mNewsTabData = children;
	}

	@Override
	public View initViews() {
		
		View view = View.inflate(mActivity, R.layout.news_menu_pager, null);
		mViewPager = (ViewPager)view.findViewById(R.id.vp_menu_detail);
		return view;
	}
	
	@Override
	public void initData() {
		
		mPagerList = new ArrayList<NewsTabPager>();
		for (int i = 0; i < mNewsTabData.size(); i++) {
			
			NewsTabPager newsTabPager = new NewsTabPager(mActivity, mNewsTabData.get(i));
			mPagerList.add(newsTabPager);
		}
		
		mViewPager.setAdapter(new NewsTabAdapter());
	}
	
	/**
	 * 页签ViewPager适配器
	 * @author Cuilitang
	 *
	 */
	class NewsTabAdapter extends PagerAdapter{

		@Override
		public int getCount() {

			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == object;
		}
		
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			NewsTabPager pager = mPagerList.get(position);
			container.addView(pager.mRootView);
			pager.initData();
			return pager.mRootView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View) object);
		}
	}

}


