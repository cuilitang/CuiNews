package cui.litang.cuinews.fragment;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cui.litang.cuinews.MainActivity;
import cui.litang.cuinews.R;
import cui.litang.cuinews.domain.NewsData;
import cui.litang.cuinews.domain.NewsData.NewsMenuData;
import cui.litang.cuinews.home.NewsCenterPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class LeftMenuFragment extends BaseFragment {

	@ViewInject(R.id.lv_list)
	private ListView lvList;
	private ArrayList<NewsMenuData> mMenuList;
	private int mCurrentPos;
	private MenuAdapter mAdapter;
	
	
	@Override
	public View initViews() {
		
		View view = View.inflate(mActivity, R.layout.fargment_left_menu, null);
		ViewUtils.inject(this,view);
		return view; 
	}
	
	
	@Override
	public void initData() {
		
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				mCurrentPos = position;
				mAdapter.notifyDataSetChanged();
				setCurrentMenuPager(position);
				toggleSlidingMenu();  //隐藏
			}
		});
		
	}
	
	/**
	 * 设置当前新闻菜单页
	 * @param position
	 */
	protected void setCurrentMenuPager(int position) {
		
		MainActivity mainUI = (MainActivity) mActivity;
		ContentFragment fragment = mainUI.getContentFragment();
		NewsCenterPager pager = fragment.getNewsCenterPager();
		pager.setCurrentNewsMenupager(position);
		
	}


	/**
	 * 切换SlidingMenu的状态
	 */
	protected void toggleSlidingMenu() {
		
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();//切换状态
	}


	/**
	 * 侧边栏数据适配器
	 * @author Cuilitang
	 *
	 */
	private class MenuAdapter extends BaseAdapter{

		@Override
		public int getCount() {
				
			return mMenuList.size();
		}

		@Override 
		public NewsMenuData getItem(int position) {
			
			return mMenuList.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = View.inflate(mActivity, R.layout.list_menu_item, null);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			NewsMenuData nmd = getItem(position);
			tv_title.setText(nmd.title);
			
			if(mCurrentPos == position){
				tv_title.setEnabled(true);
			}else{
				tv_title.setEnabled(false);
			}
			
			return view;
		}
		
		
	}

	/**
	 * 设置网络数据
	 * @param mNewsData
	 */
	public void setMenuData(NewsData data) {
		
		mMenuList = data.data;
		mAdapter = new MenuAdapter();
		lvList.setAdapter(mAdapter);
	}
	

}
