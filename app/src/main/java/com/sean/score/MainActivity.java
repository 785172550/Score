package com.sean.score;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import Base.BaseActivity;
import entity.Patient;
import myview.HeaderLayout;
import ui.EuroScore;
import ui.NersScore;
import ui.Report;
import ui.ScoreI;
import ui.ScoreII;

public class MainActivity extends BaseActivity implements View.OnClickListener{


	ImageButton left_btn;
	ImageView iv1,iv2,iv3,iv4;

	Button save;
	//患者信息
	Patient patient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle bundle = getIntent().getExtras();
		if(bundle != null)
			patient = (Patient) bundle.getSerializable("patient");

		initTopBarForBothWithText("Score", "测评报告", new HeaderLayout.onRightImageButtonClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Report.class);
				Bundle bundle1 = new Bundle();
				bundle1.putSerializable("patient",patient);
				intent.putExtras(bundle1);
				startActivity(intent);
			}
		},
				R.drawable.base_action_bar_back_bg_selector, new HeaderLayout.onLeftImageButtonClickListener() {
			@Override
			public void onClick() {
				finish();
			}
		});

		initview();
	}

	private void initview() {


		/*left_btn = (ImageButton) findViewById(R.id.left_btn);
		left_btn.setOnClickListener(this);*/
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Report.class);
				Bundle bundle1 = new Bundle();
				bundle1.putSerializable("patient",patient);
				intent.putExtras(bundle1);
				startActivity(intent);
			}
		});

		iv1 = (ImageView) findViewById(R.id.iv_1);
		iv2 = (ImageView) findViewById(R.id.iv_2);
		iv3 = (ImageView) findViewById(R.id.iv_3);
		iv4 = (ImageView) findViewById(R.id.iv_4);

		iv1.setOnClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
		iv4.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		Class<?> cls = null;

		switch (v.getId()){
			case R.id.iv_1:
				cls = ScoreI.class;
				Intent intent = new Intent(MainActivity.this, cls);
				Bundle bundle = new Bundle();
				bundle.putSerializable("patient",patient);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.iv_2:
				cls = ScoreII.class;
				Intent intent1 = new Intent(MainActivity.this,cls);
				Bundle bundle1 = new Bundle();
				bundle1.putSerializable("patient",patient);
				intent1.putExtras(bundle1);
				startActivity(intent1);
				break;
			case R.id.iv_3:
				cls = EuroScore.class;
				Intent intent2 = new Intent(MainActivity.this,cls);
				Bundle bundle2 = new Bundle();
				bundle2.putSerializable("patient",patient);
				intent2.putExtras(bundle2);
				startActivity(intent2);
				break;
			case R.id.iv_4:
				cls = NersScore.class;
				Intent intent3 = new Intent(MainActivity.this,cls);
				Bundle bundle3 = new Bundle();
				bundle3.putSerializable("patient",patient);
				intent3.putExtras(bundle3);
				startActivity(intent3);
				break;
		}

	}
}
