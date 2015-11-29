package cui.litang.cuinews.home.news.newstab;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import cui.litang.cuinews.R;
import cui.litang.cuinews.domain.NewsData.NewsChildData;
import cui.litang.cuinews.domain.TabData;
import cui.litang.cuinews.domain.TabData.TabNewsData;
import cui.litang.cuinews.domain.TabData.TopNewsData;
import cui.litang.cuinews.global.GlobalConstants;
import cui.litang.cuinews.home.news.BaseNewsMenuPager;

public class NewsTabPager extends BaseNewsMenuPager {

	NewsChildData mChildData;
	private String mUrl;
	TabData mTabDetailData;
	private ArrayList<TabNewsData> mTabLVNewsList; // 新闻数据集合
	private ArrayList<TopNewsData> mTopNewsList;// 头条新闻数据集合
	
	@ViewInject(R.id.vp_news)
	private ViewPager mViewPager;
	
	@ViewInject(R.id.tv_top_news_title)
	private TextView tv_top_news_title;
	
	@ViewInject(R.id.cricle_indicator)
	private CirclePageIndicator mCirclePageIndicator;

	
	
	public NewsTabPager(Activity activity,NewsChildData newsChildData) {
		super(activity);
		
		mChildData = newsChildData;
		mUrl = GlobalConstants.SERVER_URL + mChildData.url;
	}
	
	
	@Override
	public View initViews() {
		
		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		ViewUtils.inject(this, view);
		return view;	
	
	}
	
	@Override
	public void initData() {
		
		getDataFromServer();

	}

	private void getDataFromServer() {
		
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				
				String result = responseInfo.result;
				System.out.println("页签详情 返回结果:"+result);
				
				parseData(result);
			
			}

			

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
				
			}
		});
	}
	
	private void parseData(String result) {
		
		Gson gson = new Gson();
		mTabDetailData = gson.fromJson(result, TabData.class);
		System.out.println("页签详情解析:" + mTabDetailData);
		
		mTopNewsList = mTabDetailData.data.topnews;

		mTabLVNewsList = mTabDetailData.data.news;
		
		if (mTopNewsList != null) {
			
			mViewPager.setAdapter(new TopNewsAdapter());
			
			tv_top_news_title.setText(mTopNewsList.get(0).title);

			mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels) {
					//  Auto-generated method stub
					
				}

				@Override
				public void onPageSelected(int position) {
					tv_top_news_title.setText(mTopNewsList.get(position).title);
				}

				@Override
				public void onPageScrollStateChanged(int state) {
					//  Auto-generated method stub
					
				}
				
			});
			
			mCirclePageIndicator.setViewPager(mViewPager);
			mCirclePageIndicator.setSnap(true);
			mCirclePageIndicator.onPageSelected(0);
			
		}
		
		
		
	}
	
	/**
	 * 头条新闻适配器
	 * 
	 * @author Kevin
	 * 
	 */
	class TopNewsAdapter extends PagerAdapter {

		private BitmapUtils utils;

		public TopNewsAdapter() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.topnews_item_default);// 设置默认图片
		}

		@Override
		public int getCount() {
			return mTabDetailData.data.topnews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView image = new ImageView(mActivity);
			image.setScaleType(ScaleType.FIT_XY);// 基于控件大小填充图片

			TopNewsData topNewsData = mTopNewsList.get(position);
			utils.display(image, topNewsData.topimage);// 传递imagView对象和图片地址
			container.addView(image);
			
			System.out.println("instantiateItem....." + position);
			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	

}
