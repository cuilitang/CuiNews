package cui.litang.cuinews.home.news;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import cui.litang.cuinews.MainActivity;
import cui.litang.cuinews.R;
import cui.litang.cuinews.domain.NewsData.NewsTabData;
import cui.litang.cuinews.home.news.newstab.BaseNewsMenuPager;
import cui.litang.cuinews.home.news.newstab.NewsTabPager;

public class NewsMenuMainPage extends BaseNewsMenuPager implements OnPageChangeListener{
	private ArrayList<NewsTabData> mNewsTabData;// 页签网络数据
	private ArrayList<NewsTabPager> mPagerList; //Viewpager 页面
	private ViewPager mViewPager;  //页签
	private TabPageIndicator mIndicator;

	public NewsMenuMainPage(Activity activity) {
		super(activity);
	}

	public NewsMenuMainPage(Activity mActivity, ArrayList<NewsTabData> children) {
		
		super(mActivity);
		mNewsTabData = children;
	}

	@Override
	public View initViews() {
		
		View view = View.inflate(mActivity, R.layout.news_menu_pager, null);
		mViewPager = (ViewPager)view.findViewById(R.id.vp_menu_detail);
		mIndicator = (TabPageIndicator)view.findViewById(R.id.indicator);
		// mViewPager.setOnPageChangeListener(this);//注意:当viewpager和Indicator绑定时,
				// 滑动监听需要设置给Indicator而不是viewpager
		ViewUtils.inject(this,view);
		mIndicator.setOnPageChangeListener(this);
		
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
		mIndicator.setViewPager(mViewPager);
		
	}
	
	/**
	 * 页签ViewPager适配器
	 * @author Cuilitang
	 *
	 */
	class NewsTabAdapter extends PagerAdapter{
		
		/**
		 * 重写此方法,返回页面标题,用于viewpagerIndicator的页签显示
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			
			return mNewsTabData.get(position).title;
		}

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

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// Auto-generated method stub
		
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		
		System.out.println("onPageSelected:" + state);
		MainActivity mainUi = (MainActivity)mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		if(state == 0){//只有在第一个页面(北京), 侧边栏才允许出来
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
		
		
	}
	
	@OnClick(R.id.btn_next)
	public void nextPage(View view) {
		
		int i = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++i);
	}

}


