package ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.bigkoo.pickerview.TimePickerView;
import com.sean.score.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import Base.BaseActivity;
import entity.Patient;

/**
 * Created by Administrator on 2016/7/16.
 *
 */
public class StartDialog extends BaseActivity implements View.OnClickListener{


	private static final String TAG = "StartDialog";
	ScrollView msv;
	private Handler mHandler = new Handler();


	EditText edt1, edt2, edt3;
	TextView edt4, edt5;
	LinearLayout lin4, lin5;
	Patient patient;
	Button start;

	TimePickerView pvTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startdialog);
		initview();
	}

	private void initview() {
		edt1 = (EditText) findViewById(R.id.edt_1);

		edt2 = (EditText) findViewById(R.id.edt_2);
		/*edt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});*/

		edt2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, final boolean hasFocus) {
				if (hasFocus) {
					go_bottom(2);
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							edt2.setFocusable(true);
//							edt2.callOnClick();
							//edt2.requestFocusFromTouch();
						}
					}, 150);
				}
			}
		});

		edt3 = (EditText) findViewById(R.id.edt_3);
		/*edt3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});*/

		edt3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					go_bottom(3);
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							edt3.setFocusable(true);
//							edt3.callOnClick();
							//	edt3.requestFocus();
						}
					}, 100);
				}
			}
		});

		edt4 = (TextView) findViewById(R.id.edt_4);
		edt5 = (TextView) findViewById(R.id.edt_5);

		lin4 = (LinearLayout) findViewById(R.id.lin4);
		lin4.setOnClickListener(this);

		lin5 = (LinearLayout) findViewById(R.id.lin5);
		lin5.setOnClickListener(this);

		msv = (ScrollView) findViewById(R.id.msv);

		// time picker view
		pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
		pvTime.setTime(new Date());
		pvTime.setCyclic(false);
		pvTime.setCancelable(true);
		//控制时间范围
//		Calendar calendar = Calendar.getInstance();
//		pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));

		//时间选择后回调
		pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				edt5.setText(getTime(date));
			}
		});

		start = (Button) findViewById(R.id.next_btn);
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (iscompelet()) {
					to_next();
					Log.d(TAG, edt1.getText().toString() +
							edt2.getText().toString() +
							edt4.getText().toString() +
							edt5.getText().toString());
				} else {
					ShowToast("信息填写不正确！");
				}
			}
		});
	}

	private void to_next() {
		Intent intent = new Intent(StartDialog.this, Report.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("patient", patient);
		intent.putExtras(bundle);
		intent.putExtra("meg",1);
		startActivity(intent);
		finish();
	}

	public boolean iscompelet() {
		if (edt1.getText() != null && edt2.getText() != null && edt3.getText() != null &&
				edt4.getText() != null && edt5.getText() != null && !edt1.getText().toString().equals("")
				&& !edt2.getText().toString().equals("") && !edt3.getText().toString().equals("") &&
				!edt4.getText().toString().equals("") && !edt5.getText().toString().equals("")) {

			int age = Integer.parseInt(edt3.getText().toString());
			if (age < 18 || age > 100) {
				return false;
			}
			// 姓名 住院号 年龄 性别 日期
			patient = new Patient(edt1.getText().toString(),
					edt2.getText().toString(), age,
					edt4.getText().toString(),
					edt5.getText().toString());
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.lin4:
				ActionSheet.createBuilder(StartDialog.this, getSupportFragmentManager())
						.setCancelButtonTitle("取消")
						.setOtherButtonTitles("男", "女")
						.setCancelableOnTouchOutside(true)
						.setListener(new ActionSheet.ActionSheetListener() {
							@Override
							public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

							}

							@Override
							public void onOtherButtonClick(ActionSheet actionSheet, int index) {
								if (index == 0) {
									edt4.setText("男");
								} else {
									edt4.setText("女");
								}

							}
						}).show();
				break;
			case R.id.lin5:
				//弹出时间选择器
				Log.d(TAG, "onClick: 55555");
				pvTime.show();
				break;

			case R.id.lin2:
			case R.id.lin3:
				//go_bottom();
				break;

		}
	}
	public void go_bottom(final int id) {

		if (!(msv.getScrollY() + msv.getHeight() >= msv.getChildAt(0).getMeasuredHeight())) {
			//这里必须要给一个延迟，如果不加延迟则没有效果。
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					//将ScrollView滚动到底
					msv.fullScroll(View.FOCUS_DOWN);
					if(id == 2)
						edt2.requestFocus();
					else edt3.requestFocus();
				}
			}, 100);
		}
	}

	public static String getTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
}
