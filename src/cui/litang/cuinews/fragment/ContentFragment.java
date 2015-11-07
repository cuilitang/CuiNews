package cui.litang.cuinews.fragment;

import java.util.ArrayList;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cui.litang.cuinews.R;
import cui.litang.cuinews.home.BasePager;
import cui.litang.cuinews.home.GovAffairPager;
import cui.litang.cuinews.home.HomePager;
import cui.litang.cuinews.home.NewsCenterPager;
import cui.litang.cuinews.home.SettingPager;
import cui.litang.cuinews.home.SmartServicePager;

public class ContentFragment extends BaseFragment {
	
	@ViewInject(R.id.rg_group)
	private RadioGroup mRadioGroup;
	@ViewInject(R.id.vp_content)
	private ViewPager mViewPager;
	private ArrayList<BasePager> mPagerList;

	@Override
	public View initViews() {
		
		View view = View.inflate(mActivity, R.layout.fargment_content, null);
		ViewUtils.inject(this,view);
		
		return view;
	}
	
	@Override
	public void initData() {
		
		mRadioGroup.check(R.id.rb_home);
		mPagerList = new ArrayList<BasePager>();
		mPagerList.add(new HomePager(mActivity));
		mPagerList.add(new NewsCenterPager(mActivity));
		mPagerList.add(new SmartServicePager(mActivity));
		mPagerList.add(new GovAffairPager(mActivity));
		mPagerList.add(new SettingPager(mActivity));
		
		mViewPager.setAdapter(new ContentAdapter());
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.rb_home:
					mViewPager.setCurrentItem(0,false);
					break;
				case R.id.rb_news:
					mViewPager.setCurrentItem(1,false);
					break;
				case R.id.rb_smart:
					mViewPager.setCurrentItem(2,false);
					break;
				case R.id.rb_gov:
					mViewPager.setCurrentItem(3,false);
					break;
				case R.id.rb_setting:
					mViewPager.setCurrentItem(4,false);
					break;

				default:
					break;
				}
				
			}
		});
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
				mPagerList.get(position).initData();
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// Auto-generated method stub
				
			}
		});
		
		mPagerList.get(0).initData();
	}
	
	class ContentAdapter extends PagerAdapter{

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

			BasePager basePager = mPagerList.get(position);
			container.addView(basePager.mRootView);
			return basePager.mRootView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View)object);
		}
	}
	
	/** 获取新闻中心页面
	 * @return NewsCenterPager
	 */
	public NewsCenterPager getNewsCenterPager(){
		
		return (NewsCenterPager) mPagerList.get(1);
	}

}
