package cui.litang.cuinews.home;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import cui.litang.cuinews.MainActivity;
import cui.litang.cuinews.domain.NewsData;
import cui.litang.cuinews.fragment.LeftMenuFragment;
import cui.litang.cuinews.global.GlobalConstants;
import cui.litang.cuinews.home.news.NewsMenuInteractPage;
import cui.litang.cuinews.home.news.NewsMenuMainPage;
import cui.litang.cuinews.home.news.NewsMenuPhotoPage;
import cui.litang.cuinews.home.news.NewsMenuTopicPage;
import cui.litang.cuinews.home.news.newstab.BaseNewsMenuPager;

public class NewsCenterPager extends BasePager {

	private ArrayList<BaseNewsMenuPager> mPagers;//侧边栏的四个详情页
	private NewsData mNewsData;

	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		
		System.out.println("初始化新闻中心数据....");
		tv_title.setText("新闻中心");
		//btn_menu.setVisibility(View.GONE);
		setSlidingMenuEnable(false);
		
		TextView textView = new TextView(mActivity);
		textView.setText("新闻中心临时页面内容");
		textView.setTextColor(Color.RED);
		textView.setTextSize(25);
		textView.setGravity(Gravity.CENTER);
		fl_content.addView(textView);
		
		getDataFromServer();
	}

	private void getDataFromServer() {
		
		HttpUtils httpUtils = new HttpUtils();
		
		httpUtils.send(HttpMethod.GET, GlobalConstants.CATEGORIES_URL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

				String result = responseInfo.result;
				System.out.println("返回结果"+result);
				
				parseData(result);
				
			}

			
			@Override
			public void onFailure(HttpException error, String msg) {
				
				Toast.makeText(mActivity, msg, 0).show();
				error.printStackTrace();
			}
		});
	}
	
	/**
	 * json解析
	 * @param result json字符串
	 */
	private void parseData(String result) {
		
		Gson gson = new Gson();
		mNewsData = gson.fromJson(result, NewsData.class);
		System.out.println("解析结果:" + mNewsData);
		
		//刷新侧边栏的数据
		MainActivity mainUi = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
		leftMenuFragment.setMenuData(mNewsData);
		
		// 准备4个菜单详情页
		mPagers = new ArrayList<BaseNewsMenuPager>();
		mPagers.add(new NewsMenuMainPage(mActivity,mNewsData.data.get(0).children));
		mPagers.add(new NewsMenuTopicPage(mActivity));
		mPagers.add(new NewsMenuPhotoPage(mActivity));
		mPagers.add(new NewsMenuInteractPage(mActivity));
		
		setCurrentNewsMenupager(0);//设置新闻为当前默认页
		
	}
	
	/**
	 * 设置当前新闻菜单页
	 * @param position
	 */
	public void setCurrentNewsMenupager(int position){
		
		BaseNewsMenuPager pager = mPagers.get(position);
		fl_content.removeAllViews();
		fl_content.addView(pager.mRootView);
		
		//设置当前页的标题
		String title = mNewsData.data.get(position).title;
		tv_title.setText(title);
		pager.initData();//初始化当前页的数据
	}


}
