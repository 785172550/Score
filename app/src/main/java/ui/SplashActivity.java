package ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sean.score.R;

import Base.BaseActivity;

/**
 * Created by Administrator on 2016/6/25.
 */
public class SplashActivity extends BaseActivity {

	private Handler mMainHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

//			if(isFirstIn){
//				Intent intent = new Intent(SplashActivity.this,Register.class);
//				startActivity(intent);
//
//			}else {
//
//				if(renzheng){
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setClass(getApplication(), NewMain.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
//				}else {
//					Intent intent = new Intent(Intent.ACTION_MAIN);
//					intent.setClass(getApplication(), Renzheng.class);
//					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					startActivity(intent);
//				}
//			}
			// overridePendingTransition must be called AFTER finish() or startActivity, or it won't work.
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		}
	};

	boolean isFirstIn = true;
	boolean renzheng = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.splash);
		SharedPreferences preferences = getSharedPreferences("first_pref",
				MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);

		renzheng = preferences.getBoolean("renzheng",false);
		mMainHandler.sendEmptyMessageDelayed(0, 2000);
	}

	@Override
	public void onBackPressed() {

	}
}
