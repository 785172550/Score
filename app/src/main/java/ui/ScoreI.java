package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sean.score.R;

import Base.BaseActivity;
import entity.Patient;

/**
 * Created by Administrator on 2016/6/10.
 */
public class ScoreI extends BaseActivity {

	ImageView left_iv, right_iv;
	Patient patient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score1);
		getdata();
		//patient = new Patient("朱晓伟","01",55,"男","2016-5-5");
		initview();
	}

	public void getdata() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle.getSerializable("patient") != null)
			patient = (Patient) bundle.getSerializable("patient");
	}

	private void initview() {
		initTopBarForLeft("Syntax1");
		left_iv = (ImageView) findViewById(R.id.iv_left);
		right_iv = (ImageView) findViewById(R.id.iv_right);
		left_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ScoreI.this, ScoreI2.class);
				//左边
				intent.putExtra("tag", 1);
				Bundle bundle1 = new Bundle();
				bundle1.putSerializable("patient",patient);
				intent.putExtras(bundle1);
				startActivity(intent);
			}
		});

		right_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(ScoreI.this, ScoreI2.class);
				//右边
				intent.putExtra("tag", 2);
				Bundle bundle1 = new Bundle();
				bundle1.putSerializable("patient",patient);
				intent.putExtras(bundle1);
				startActivity(intent);
			}
		});
	}
}
