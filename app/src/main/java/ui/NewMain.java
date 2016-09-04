package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sean.score.R;

import Base.BaseActivity;
import fragment.SlidingFrag;
import myview.HeaderLayout;

/**
 * Created by Administrator on 2016/6/14.
 */
public class NewMain extends BaseActivity {


	Button start_btn;
	ImageView img_slide;
	//侧滑
	static SlidingMenu slidingmenu;


	private static final String TAG = "NewMain---";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_newmain);

//		img_slide = (ImageView) findViewById(R.id.img_slide);
//		img_slide.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				slidingmenu.showMenu();
//			}
//		});
		initTopBarForBothWithText("中国冠脉介入指南推荐评分系统", "", new HeaderLayout.onRightImageButtonClickListener() {
					@Override
					public void onClick(View v) {
					}
				},
				R.drawable.slide_n, new HeaderLayout.onLeftImageButtonClickListener() {
					@Override
					public void onClick() {
						slidingmenu.showMenu();
					}
				});


		initview();

		// 初始化侧滑
		initsliding();
		//用fragment替换侧滑布局
		SlidingFrag slid_frag = new SlidingFrag();
		getFragmentManager().beginTransaction().replace(R.id.sliding_menu, slid_frag).commit();
	}

	private void initview() {
		start_btn = (Button) findViewById(R.id.start_btn);
		start_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(NewMain.this, StartDialog.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		});
	}

	private void initsliding() {

		slidingmenu = new SlidingMenu(this);
		slidingmenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingmenu.setMode(SlidingMenu.LEFT);
		slidingmenu.setBehindOffset(200);
		slidingmenu.setMenu(R.layout.sliding_layout);
		slidingmenu.setBehindScrollScale(0);
		//将侧边栏粘连到Activity上
		slidingmenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	}


	public static void swicth_slindg() {
		if (slidingmenu.isMenuShowing()) {
			slidingmenu.showContent();
		} else slidingmenu.showMenu();
	}
}
