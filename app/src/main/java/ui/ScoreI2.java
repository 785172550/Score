package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sean.score.R;

import Base.BaseActivity;
import entity.Patient;
import fragment.PositionLeft;
import fragment.PositionRight;
import myview.HeaderLayout;

/**
 * Created by Administrator on 2016/6/10.
 */
public class ScoreI2 extends BaseActivity {


	RadioGroup ra;

	PositionLeft positionLeft;
	PositionRight positionRight;

	Patient patient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score1_chose);
		getdata();
		initview();
	}

	public void  getdata() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle.getSerializable("patient") != null)
			patient = (Patient) bundle.getSerializable("patient");
	}

	private void initview() {

		initTopBarForBothWithText("选择病变位置", "开始评分>", new HeaderLayout.onRightImageButtonClickListener() {
			@Override
			public void onClick(View v) {

				if(ra.getCheckedRadioButtonId() == R.id.radio1){
					positionLeft.start();
				}else {
					positionRight.start();
				}

			}
		}, R.drawable.base_action_bar_back_bg_selector, new HeaderLayout.onLeftImageButtonClickListener() {
			@Override
			public void onClick() {
				finish();
			}
		});


		positionLeft = new PositionLeft(ScoreI2.this,patient);
		positionRight = new PositionRight(ScoreI2.this,patient);

		ra = (RadioGroup) findViewById(R.id.ra);
		//默认选中第一个
		ra.check(ra.getChildAt(0).getId());
		setfrag(R.id.radio1);

		ra.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int radio_id  =  group.getCheckedRadioButtonId();
				RadioButton radioButton = (RadioButton) findViewById(radio_id);
				setfrag(radio_id);
			}
		});
	}

	private void setfrag(int radio_id) {
		if (radio_id == R.id.radio1)
			getFragmentManager().beginTransaction().replace(R.id.frag_content,positionLeft).commit();
		else
			getFragmentManager().beginTransaction().replace(R.id.frag_content,positionRight).commit();
	}


}
