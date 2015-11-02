package cui.litang.cuinews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import cui.litang.cuinews.utils.SPUtils;

/**
 * 闪屏页
 * 	1. 动画
 * 	2. 检测升级（没做）
 * 	3. 启动服务（没做）
 * @author Cuilitang
 *
 */
public class SplashActivity extends Activity {
	
	private RelativeLayout rl_root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash);
		
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);
		
		startAnimatiom();
		
	}
	
	/**
	 * 闪屏页动画效果
	 */
	private void startAnimatiom(){
		
		//new AnimationSet(shareInterpolator);
		AnimationSet animationSet = new AnimationSet(false);
		
		//new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue)
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(600);
		rotateAnimation.setFillAfter(true);
		
		//new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue)
		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(800);
		scaleAnimation.setFillAfter(true);
		
		//new AlphaAnimation(fromAlpha, toAlpha);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(2000);
		alphaAnimation.setFillAfter(true);
		
		animationSet.addAnimation(rotateAnimation);
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(alphaAnimation);
		
		rl_root.startAnimation(animationSet);
		
		animationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				junmpNextPage();
				
			}
		});
	}

	/**
	 * 跳转到下一页
	 */
	protected void junmpNextPage() {
		
		boolean isShowedGuide = SPUtils.getBoolean(SplashActivity.this, "is_user_guide_showed", false);
		
		if(isShowedGuide){
			
			//new Intent(packageContext, cls);
			startActivity(new Intent(SplashActivity.this,MainActivity.class));
		}else {
			
			startActivity(new Intent(SplashActivity.this,GuideActivity.class));
		}
		finish();
	}

}
