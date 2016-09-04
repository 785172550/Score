package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sean.score.R;

import Base.BaseActivity;
import entity.Patient;
import fragment.Score1Frag;
import fragment.Score2Frag;
import fragment.Score3Frag;
import fragment.Score4Frag;

/**
 * Created by Administrator on 2016/6/16.
 */
public class ResultShow extends BaseActivity {


	int page;
	Button save,reset;
	Context context;
	Patient patient;

	TextView title_text;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_show);
		initTopBarForOnlyTitle("评分结果");
		context = this;

		Intent intent = getIntent();
		page = intent.getIntExtra("activity",-1);
		Bundle bundle = intent.getExtras();
		patient = (Patient) bundle.getSerializable("patient");

		initview();
	}

	private void initview() {

		title_text  = (TextView) findViewById(R.id.title_text);

		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Class cls = null;
//				switch (page){
//					case 1:
//						cls = MainActivity.class;
//						break;
//					case 2:
//						cls = EuroScore.class;
//						break;
//					case 3:
//						cls = NersScore.class;
//						break;
//					case 4:
//						cls = Eval.class;
//						break;
//				}
				Intent intent = new Intent(context,Report.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("patient",patient);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		//重新计算
		reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Class cls = null;
				switch (page){
					case 1:
						cls = ScoreI.class;
						break;
					case 2:
						cls = ScoreII.class;
						break;
					case 3:
						cls = EuroScore.class;
						break;
					case 4:
						cls = NersScore.class;
						break;
				}

				Intent intent = new Intent(context,cls);
				Bundle bundle = new Bundle();
				bundle.putSerializable("patient",patient);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});

		switch (page){
			case 1:
				title_text.setText("Systax I");
				getFragmentManager().beginTransaction().
						replace(R.id.frag, Score1Frag.newInstance(patient)).commit();
				break;
			case 2:
				title_text.setText("Systax II");
				getFragmentManager().beginTransaction().
						replace(R.id.frag,Score2Frag.newInstance(patient)).commit();
				break;
			case 3:
				title_text.setText("Euro Score II");
				getFragmentManager().beginTransaction().
						replace(R.id.frag,Score3Frag.newInstance(patient)).commit();
				break;
			case 4:
				title_text.setText("Ners Score II");
				getFragmentManager().beginTransaction().
						replace(R.id.frag,Score4Frag.newInstance(patient)).commit();
				break;
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
}
