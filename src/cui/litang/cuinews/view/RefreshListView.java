package cui.litang.cuinews.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import cui.litang.cuinews.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class RefreshListView extends ListView implements OnScrollListener,android.widget.AdapterView.OnItemClickListener{


	private static final int STATE_SLIP_REFRESH = 0;// 下拉刷新
	private static final int STATE_UP_REFRESH = 1;// 松开刷新
	private static final int STATE_REFRESHING = 2;// 正在刷新
	private int mCurrrentState = STATE_SLIP_REFRESH;// 当前状态
	
	
	private View mHeaderView;
	private TextView tvTitle;
	private TextView tvTime;
	private ProgressBar pbProgress;
	private ImageView ivArrow;
	private int startRawY = -1;
	private int endRawY;
	private int mHeaderViewHeight;
	private RotateAnimation animationUp;
	private RotateAnimation animationSliping;
	//加载更多
	private View mFooterView;
	private int mFooterViewHeight;
	//是否正在加载下一页
	private boolean isLoadingMore;
	
	private OnItemClickListener mOnItemClickListener;

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
	}


	public RefreshListView(Context context) {
		super(context);
		initHeaderView();
		initFooterView();
	}
	
	private void initFooterView() {
		
		mFooterView = View.inflate(getContext(), R.layout.refresh_listview_footer, null);
		this.addFooterView(mFooterView);
		
		mFooterView.measure(0, 0);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
		
		this.setOnScrollListener(this);
		
	}

	private void initHeaderView() {
		
		mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
		this.addHeaderView(mHeaderView);
		
		tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
		tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
		ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arr);
		pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);
		
		mHeaderView.measure(0, 0); 
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
		
		initArrowAnim();
		//tvTime.setText("最后刷新时间:"+getCurrentTime());
		
		
		
	}
	
	/**
	 * 获取当前系统时间并且格式化
	 * @return
	 */
	private String getCurrentTime() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 初始化箭头动画
	 */
	private void initArrowAnim() {
		animationUp = new RotateAnimation(0, -180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		animationUp.setDuration(200);
		animationUp.setFillAfter(true);
		 
		animationSliping = new RotateAnimation(-180,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		animationSliping.setDuration(200);
		animationSliping.setFillAfter(true);
		
		
		
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			startRawY = (int)ev.getRawY();
			
			break;
			
		case MotionEvent.ACTION_MOVE:
			
			if(startRawY == -1){
				
				startRawY = (int)ev.getRawY();
			}
			
			if(mCurrrentState == STATE_REFRESHING){
				break;
			}
			
			endRawY  = (int)ev.getRawY();
			int dy = endRawY - startRawY;
			
			if(dy > 0&&getFirstVisiblePosition() == 0){
				
				int padding = dy - mHeaderViewHeight;
				mHeaderView.setPadding(0, padding, 0, 0);
				if(padding > 0 && mCurrrentState !=STATE_UP_REFRESH){
					mCurrrentState = STATE_UP_REFRESH;
					refreshState();
				}else if (padding < 0 && mCurrrentState != STATE_SLIP_REFRESH) {
					
					mCurrrentState = STATE_SLIP_REFRESH;
					refreshState();
				}
				
				
				
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
						
			startRawY = -1;
			
			if(mCurrrentState == STATE_UP_REFRESH){
				mCurrrentState = STATE_REFRESHING;
				mHeaderView.setPadding(0, 0, 0, 0);
				refreshState();
			}else if (mCurrrentState == STATE_SLIP_REFRESH) {
				mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
			}
			break;
		

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	/**
	 * 刷新下拉控件的布局
	 */
	private void refreshState() {
		
		switch (mCurrrentState) {
		case STATE_SLIP_REFRESH:
			
			tvTitle.setText("下拉刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivArrow.startAnimation(animationSliping);
			break;
			
		case STATE_UP_REFRESH:
			tvTitle.setText("松手刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivArrow.startAnimation(animationUp);
			break;
		
		case STATE_REFRESHING:
			tvTitle.setText("正在刷新...");
			ivArrow.clearAnimation();//必须先清除动画，才能隐藏
			ivArrow.setVisibility(View.INVISIBLE);
			pbProgress.setVisibility(View.VISIBLE);
			
			//在页面上给ListView设置监听器
			if(mListener != null){
				mListener.onRefresh();
			}
			break;
			
			

		default:
			break;
		}
	}

	/**
	 * 下拉刷新接口,当正在刷新状态时候调用setOnRefreshListener
	 */
	
	OnRefreshListener mListener;
	
	public interface OnRefreshListener{
		
		public void onRefresh(); //

		public void onLoadMore();
				
	}
	
	public void setOnRefreshListener(OnRefreshListener listener){
		
		mListener = listener;
	}
	
	/**
	 * 下拉刷新 和加载下一页结束后处理头布局，和脚布局
	 * @param success 是否刷新成功
	 */
	public void onRefreshComplete(boolean success){
		
		if(isLoadingMore){
			mFooterView.setPadding(0, -mHeaderViewHeight, 0, 0);//隐藏脚布局
			isLoadingMore = false;
		}else{
			
			mCurrrentState = STATE_SLIP_REFRESH;
			tvTitle.setText("下拉刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
			
			if(success){
				tvTime.setText("最后刷新时间"+getCurrentTime());
			}
		}
	}
	
	
	/**
	 * OnScrollListener 下拉加载更多用到
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		
		if(scrollState == SCROLL_STATE_IDLE ||scrollState == SCROLL_STATE_FLING){
			
			if(getLastVisiblePosition() == getCount() - 1 && !isLoadingMore ){
				System.out.println("加载下一页");
				isLoadingMore = true;   //正在加载下一页
				
				mFooterView.setPadding(0, 0, 0, 0);  //显示页脚
				setSelection(getCount() - 1);
				
				
				if(mListener != null){
					mListener.onLoadMore(); //加载下一页
				}
			}
		}

		
	}

	/**
	 * OnScrollListener 下拉加载更多用到
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// Auto-generated method stub
		
	}

	
	/**重写方法，让postion 自动减去headerView占据的位置
	 * android.widget.AdapterView.OnItemClickListener
	 */
	
	public void setOnItemClickListener(OnItemClickListener listener){
		super.setOnItemClickListener(this);
		mOnItemClickListener = listener;
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(mOnItemClickListener != null){
			mOnItemClickListener.onItemClick(parent,view,position-getHeaderViewsCount(), id);
		}
	}
	
}
