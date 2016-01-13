package cui.litang.cuinews.home.news;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import cui.litang.cuinews.R;
import cui.litang.cuinews.domain.Photos;
import cui.litang.cuinews.domain.Photos.PhotoInfo;
import cui.litang.cuinews.global.GlobalConstants;
import android.app.Activity;
import android.graphics.Color;
import android.provider.SyncStateContract.Constants;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsMenuPhotoPager extends BaseNewsMenuPager {

	private ListView lvPhoto;
	private GridView gvPhoto;
	private ArrayList<PhotoInfo> mPhotoNews;
	private ImageButton btn_photo;

	public NewsMenuPhotoPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	public NewsMenuPhotoPager(Activity mActivity, ImageButton btn_photo) {
		super(mActivity);
		this.btn_photo = btn_photo;
		
		btn_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				changeDisplay();
			}
		});
	}

	/**
	 * 初始化View
	 */
	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);
		
		lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
		gvPhoto = (GridView) view.findViewById(R.id.gv_photo);

		return view;
	}
	
	/**
	 * 初始化数据
	 */
	@Override
	public void initData() {
		
		getDataFromServer();
	}

	/**
	 * 从服务器下载数据
	 */
	private void getDataFromServer() {

		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalConstants.PHOTOS_URL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

				String result = (String) responseInfo.result;
				parseData(result);
				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, "连接服务器失败", Toast.LENGTH_SHORT)
				.show();
				error.printStackTrace();
			}
		} );
		
	}

	/**
	 * 解析json数据为对象
	 * @param result
	 */
	protected void parseData(String result) {
		 
		Gson gson = new Gson();
		Photos photos = gson.fromJson(result, Photos.class);
		mPhotoNews = photos.data.news;
		
		if(mPhotoNews!= null){
			
			PhotoAdapter adapter = new PhotoAdapter();
			lvPhoto.setAdapter(adapter);
			gvPhoto.setAdapter(adapter);
		}
	}
	
	/**
	 * ListView 与 GridView公用的Adapter
	 * @author Cuilitang
	 *
	 */
	class PhotoAdapter extends BaseAdapter{
		
		private BitmapUtils utils;
		
		public PhotoAdapter(){
			utils = new BitmapUtils(mActivity);
		}


		@Override
		public int getCount() {
			 
			return mPhotoNews.size();
		}

		@Override
		public PhotoInfo getItem(int position) {

			return mPhotoNews.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder vh;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.list_photo_item, null);
				vh = new ViewHolder();
				vh.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				vh.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
				convertView.setTag(vh);
			}else {
				vh = (ViewHolder) convertView.getTag();
			}
			
			PhotoInfo item = getItem(position);
			vh.tvTitle.setText(item.title);
			utils.display(vh.ivPic, item.listimage);
			
			return convertView;
		}
		
		
		
	}
	
	/**
	 * 列表元素缓存对象
	 * @author Cuilitang
	 *
	 */
	static class ViewHolder {
		public TextView tvTitle;
		public ImageView ivPic;
	}
	
	private boolean isListDisplay = true;// 是否是列表展示

	/**
	 * 切换展现方式
	 */
	private void changeDisplay() {
				
		if(isListDisplay){
			lvPhoto.setVisibility(View.GONE);
			gvPhoto.setVisibility(View.VISIBLE);
			btn_photo.setImageResource(R.drawable.icon_pic_list_type);
			isListDisplay = false;
		}else{
			
			lvPhoto.setVisibility(View.VISIBLE);
			gvPhoto.setVisibility(View.GONE);
			btn_photo.setImageResource(R.drawable.icon_pic_grid_type);
			isListDisplay = true;
		}
	}
	
	

}
