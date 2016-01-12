package cui.litang.cuinews.home.news.newstab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import cui.litang.cuinews.NewsContentActivity;
import cui.litang.cuinews.R;
import cui.litang.cuinews.domain.NewsData.NewsChildData;
import cui.litang.cuinews.domain.TabData;
import cui.litang.cuinews.domain.TabData.TabNewsData;
import cui.litang.cuinews.domain.TabData.TopNewsData;
import cui.litang.cuinews.global.GlobalConstants;
import cui.litang.cuinews.home.news.BaseNewsMenuPager;
import cui.litang.cuinews.utils.SPUtils;
import cui.litang.cuinews.view.RefreshListView.OnRefreshListener;

public class NewsTabPager extends BaseNewsMenuPager{

	NewsChildData mChildData;
	private String mUrl;
	TabData mTabDetailData;
	private ArrayList<TabNewsData> mTabLVNewsList; // 新闻数据集合
	private ArrayList<TopNewsData> mTopNewsList;// 头条新闻数据集合
	
	@ViewInject(R.id.vp_news)
	private ViewPager mViewPager;
	
	private Handler mHandler;//自动轮播用到
	
	@ViewInject(R.id.tv_top_news_title)
	private TextView tv_top_news_title;
	
	@ViewInject(R.id.cricle_indicator)
	private CirclePageIndicator mCirclePageIndicator;
	
	@ViewInject(R.id.lv_list)
	private cui.litang.cuinews.view.RefreshListView lv_list;
	private String mMoreURL;
	private NewsLvAdapter lvAdapter;
	private TopNewsAdapter topNewsAdapter;

	
	
	public NewsTabPager(Activity activity,NewsChildData newsChildData) {
		super(activity);
		
		mChildData = newsChildData;
		mUrl = GlobalConstants.SERVER_URL + mChildData.url;
	}
	
	
	@Override
	public View initViews() {
		
		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		View headerView = View.inflate(mActivity, R.layout.list_header_topnews_item, null);
		
		
		ViewUtils.inject(this, view);
		ViewUtils.inject(this, headerView);
		lv_list.addHeaderView(headerView);
		//设置下拉刷新监听
		lv_list.setOnRefreshListener(new OnRefreshListener() {
			
			/**
			 * 刷新最新数据
			 */
			@Override
			public void onRefresh() {
				getDataFromServer();
			}

			/**
			 * 加载下一页
			 */
			@Override
			public void onLoadMore() {

				if(mMoreURL == null){
					Toast.makeText(mActivity, "没有下一页了", Toast.LENGTH_SHORT).show();
					lv_list.onRefreshComplete(false);// 收起加载更多的布局
					
				}else{
					getMoreDataFromServer();
				}
				
			}
			
		});
		
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("被点击:" + position);
				String readIds = SPUtils.getString(mActivity, "read_ids","");
				String readingId = mTabLVNewsList.get(position).id;
				
				if(!readIds.contains(readingId)){
					readIds = readIds+readingId+",";
					SPUtils.setString(mActivity, "read_ids", readIds);
				}
				
				changeReadState(view);// 实现局部界面刷新, 这个view就是被点击的item布局对象
				
				Intent intent = new Intent();
				intent.setClass(mActivity, NewsContentActivity.class);
				intent.putExtra("url", mTabLVNewsList.get(position).url);
				mActivity.startActivity(intent);
			}
		});
		
		
		
		return view;	
	
	}
	
	
	/**
	 * 改变已读新闻的显示颜色
	 * @param view
	 */
	protected void changeReadState(View view) {
		
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setTextColor(Color.GRAY);
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
				
				parseData(result,false);
				
				lv_list.onRefreshComplete(true); //隐藏头布局
			
			}
			
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
				
				lv_list.onRefreshComplete(false);  //隐藏头布局

				
			}
		});
	}
	
	/**
	 * 加载下一页数据
	 */
	private void getMoreDataFromServer() {
		
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mMoreURL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = (String) responseInfo.result;

				parseData(result, true);
				lv_list.onRefreshComplete(false);  //隐藏脚布局

				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
				lv_list.onRefreshComplete(false);  //隐藏脚布局
				
			}
		});
		
	}
	
	/**
	 * 
	 * @param result  结果json串
	 * @param isMore  是否是下一页数据（首页初始化ListView，下一页刷新ListView的数据）
	 */
	private void parseData(String result, boolean isMore) {
		
		Gson gson = new Gson();
		mTabDetailData = gson.fromJson(result, TabData.class);
		System.out.println("页签详情解析:" + mTabDetailData);
		String more = mTabDetailData.data.more;   //下一页数据
		if(!TextUtils.isEmpty(more)){
			mMoreURL = GlobalConstants.SERVER_URL +more;
		}else{
			mMoreURL = null;
		}
		
		if(!isMore){  //如果是第一页数据
			
			mTopNewsList = mTabDetailData.data.topnews;

			mTabLVNewsList = mTabDetailData.data.news;
		
			if (mTopNewsList != null) {
				topNewsAdapter = new TopNewsAdapter();
				mViewPager.setAdapter(topNewsAdapter);
				
				tv_top_news_title.setText(mTopNewsList.get(0).title);
				mCirclePageIndicator.setViewPager(mViewPager);
				
				 //如果我們要對ViewPager設置監聽，用indicator設置就行了  
				mCirclePageIndicator.setOnPageChangeListener(new OnPageChangeListener(){
	
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
				mCirclePageIndicator.onPageSelected(0);
				mCirclePageIndicator.setSnap(true);
				
				//设置自动轮播
				
				if (mHandler == null) {
					mHandler = new Handler() {
						public void handleMessage(android.os.Message msg) {
							int currentItem = mViewPager.getCurrentItem();

							if (currentItem < mTopNewsList.size() - 1) {
								currentItem++;
							} else {
								currentItem = 0;
							}

							mViewPager.setCurrentItem(currentItem);// 切换到下一个页面

							mHandler.sendEmptyMessageDelayed(0, 3000);// 继续延时3秒发消息,
																		// 形成循环
						};
					};

					mHandler.sendEmptyMessageDelayed(0, 3000);// 延时3秒后发消息
				}
			}
			if(mTabLVNewsList != null){
				lvAdapter = new NewsLvAdapter();
				lv_list.setAdapter(lvAdapter);
			}
			
		}else{//如果是加载下一页
			
			 ArrayList<TabNewsData> nextPageNews = mTabDetailData.data.news;
			 mTabLVNewsList.addAll(nextPageNews);
			 lvAdapter.notifyDataSetChanged();
			 topNewsAdapter.notifyDataSetChanged();

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
	
	/**
	 * 新闻列表适配器
	 * @author Cuilitang
	 *
	 */
	class NewsLvAdapter extends BaseAdapter{
		
		private BitmapUtils bitmapUtils;
		
		public NewsLvAdapter(){
			bitmapUtils = new BitmapUtils(mActivity);
			bitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
		}

		@Override
		public int getCount() {

			return mTabLVNewsList.size();
		}

		@Override
		public Object getItem(int position) {

			return mTabLVNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.list_news_item, null);
				vh = new ViewHolder();
				vh.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
				vh.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				vh.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
				convertView.setTag(vh);
			}else{
				vh = (ViewHolder) convertView.getTag();
			}
			
			TabNewsData item = (TabNewsData) getItem(position);
			vh.tvDate.setText(item.pubdate);
			vh.tvTitle.setText(item.title);
			bitmapUtils.display(vh.ivPic, item.listimage);
			String readIds = SPUtils.getString(mActivity, "read_ids", "");
			String[] idsArr = readIds.split(",");
			List<String> list = Arrays.asList(idsArr);
			if(list.contains(item.id)){
				vh.tvTitle.setTextColor(Color.GRAY);
			}
			return convertView;
		}
		
	}
	
	static class ViewHolder {
		public TextView tvTitle;
		public TextView tvDate;
		public ImageView ivPic;
	}
	
	

}
