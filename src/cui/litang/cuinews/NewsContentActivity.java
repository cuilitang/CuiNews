package cui.litang.cuinews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.TextSize;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class NewsContentActivity extends Activity implements OnClickListener {
	
	private WebView mWebView;
	private ImageButton btnBack;
	private ImageButton btnSize;
	private ImageButton btnShare;

	private ProgressBar pbProgress;
	
	private int mCurrentChooseItem  = 2;// 记录当前选中的item, 点击确定前
	private int mCurrentItem = 2;// 记录当前选中的item, 点击确定后
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_content);
		
		mWebView = (WebView) findViewById(R.id.wv_web);
		btnBack = (ImageButton) findViewById(R.id.btn_back);
		btnSize = (ImageButton) findViewById(R.id.btn_size);
		btnShare = (ImageButton) findViewById(R.id.btn_share);
		
		btnBack.setOnClickListener(this);
		btnSize.setOnClickListener(this);
		btnShare.setOnClickListener(this);
		
		pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
		
		
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setBuiltInZoomControls(true);
		settings.setUseWideViewPort(true);
		
		mWebView.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				
				pbProgress.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				System.out.println("网页开始结束");
				pbProgress.setVisibility(View.GONE);

			}
		});
		
		String url = getIntent().getStringExtra("url");
		mWebView.loadUrl(url);

		
	}

	
	/**
	 * OnClickListener
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_size:
			showChooseDialog();
			break;
		case R.id.btn_share:
			Toast.makeText(this, "此功能不可用", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		
	}

	
	
	/**
	 *显示字体大小选择提示框
	 */
	private void showChooseDialog() {
		
		
		
		String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
		"超小号字体" };
		
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("字体设置");
		builder.setSingleChoiceItems(items, mCurrentItem, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				mCurrentChooseItem = which;
			}
		});
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				WebSettings settings = mWebView.getSettings();
				switch (mCurrentChooseItem) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);
					break;
				case 4:
					settings.setTextSize(TextSize.SMALLEST);
					break;

				default:
					break;
				}
				
				mCurrentItem = mCurrentChooseItem;
			}
		});
		
		builder.setNegativeButton("取消", null);
		
		builder.show();
	}

	
	

}
