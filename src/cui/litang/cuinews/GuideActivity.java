package cui.litang.cuinews;

import java.util.ArrayList;

import cui.litang.cuinews.utils.SPUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity {
	
	private static final int[] mImageIds = new int[] { R.drawable.guide_1,
		R.drawable.guide_2, R.drawable.guide_3 };
	private ArrayList<ImageView> mImageViewList;
	private ViewPager vp_guide;
	private LinearLayout ll_point_group;
	private View view_red_point;
	private Button btn_start;
	private int mPointWidth;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(featureId)
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		
		vp_guide = (ViewPager) findViewById(R.id.vp_guide);
		ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
		view_red_point = findViewById(R.id.view_red_point);
		btn_start = (Button) findViewById(R.id.btn_start);
		
		initViews();
		
		vp_guide.setAdapter(new GuideAdapter());
		vp_guide.setOnPageChangeListener(new GuidePageListener());
		
		//点击进入MainActivity
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SPUtils.setBoolean(GuideActivity.this, "is_user_guide_showed", true);
				
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});
	}

	/**
	 * 初始化界面
	 */
	private void initViews() {

		mImageViewList = new ArrayList<ImageView>();
		
		for(int i = 0; i < mImageIds.length; i++){
			
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(mImageIds[i]);
			mImageViewList.add(imageView);
		}
		
		//初始化灰点
		for(int i = 0; i < mImageIds.length; i++){
			
			View point = new View(this);
			point.setBackgroundResource(R.drawable.shape_point_gray);
			
			LinearLayout.LayoutParams params = new LayoutParams(10, 10);  //new LayoutParams(width, height);
			if(i>0) params.leftMargin = 10;
			point.setLayoutParams(params);
			
			ll_point_group.addView(point);
		}
		
		ll_point_group.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {

				System.out.println("layout 结束");
				
				ll_point_group.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				mPointWidth = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
				
			}
		});
		
		
		
	}
	
	/**
	 * ViewPager适配器
	 * @author Cuilitang
	 *
	 */
	private class GuideAdapter extends PagerAdapter{

		@Override
		public int getCount() {

			return mImageIds.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			container.addView(mImageViewList.get(position));
			return mImageViewList.get(position);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View)object);
		}
		
	}
	
	/**
	 * ViewPager滑动监听器
	 * @author Cuilitang
	 *
	 */
	private class GuidePageListener implements OnPageChangeListener{

		@Override
		public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
			
			System.out.println("当前位置:" + position + ";百分比:" + positionOffset+ ";移动距离:" + positionOffsetPixels);
			
			int len = (int)(mPointWidth*positionOffset) + position * mPointWidth;
			RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) view_red_point.getLayoutParams();
			params.leftMargin = len;
			view_red_point.setLayoutParams(params);
		}

		@Override
		public void onPageSelected(int position) {
			
			if(position == mImageIds.length - 1){
				btn_start.setVisibility(View.VISIBLE);
			}else{
				btn_start.setVisibility(View.INVISIBLE);
			}
			
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// Auto-generated method stub
			
		}
		
	}

}


