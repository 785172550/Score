package ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.sean.score.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Base.BaseActivity;
import Calculate.Cal_ss4;
import adapter.ListSwitchAdapter;
import entity.Patient;

/**
 * Created by Administrator on 2016/6/10.
 */
public class NersScore extends BaseActivity {

	ListView lv_1, lv_2, lv_3;
	List<Map<String, String>> data1, data2, data3;

	ListSwitchAdapter adapter1, adapter2, adapter3;

	Button calculate;
	Patient patient;
	double[] data;

	PopupWindow popupWindow;
	TextView LVfunction, eGFB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ners);
		initview();
		initdata();
		data = new double[16];
		Arrays.fill(data, -1);
		getdata1();
		//patient = new Patient("朱晓伟", "01", 55, "男", "2016-5-5");
	}

	public void  getdata1() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle.getSerializable("patient") != null)
			patient = (Patient) bundle.getSerializable("patient");
	}

	private void initdata() {


		data1 = new ArrayList();
		//add2list("LV function", "", data1);
		add2list("急性心肌梗死<12小时", "0", data1);
		add2list("心源性休克", "0", data1);
		add2list("糖尿病", "0", data1);
		//add2list("eGFR", "", data1);
		add2list("外周动脉疾病,且狭窄＞70%", "0", data1);
		int height = (int) (data1.size() * (getResources().getDimension(R.dimen.text_view_height))
				+ 0.3 );
		lv_1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
		adapter1 = new ListSwitchAdapter(NersScore.this, data1,1);
		lv_1.setAdapter(adapter1);


		data2 = new ArrayList();
		add2list("左主干开口或主干体部病变", "0", data2);
		add2list("末段分叉病变", "0", data2);
		add2list("末段非分叉病变", "0", data2);
		add2list("左主干完全闭塞≥3个月", "0", data2);
		add2list(" 左主冠严重钙化 ★", "0", data2);
		int height2 = (int) (data2.size() * (getResources().getDimension(R.dimen.text_view_height)
				+ 0.3));
		lv_2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height2));
		adapter2 = new ListSwitchAdapter(NersScore.this, data2,2);
		lv_2.setAdapter(adapter2);
		lv_2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
				if(position == 4)
					show_bubble(view,"当球囊不能扩张失败，需要旋磨",2);
				return true;
			}
		});

		data3 = new ArrayList<>();
		add2list("右冠优势/左冠优势,非CTO病变", "0", data3);
		add2list("LAD非CTO病变", "0", data3);
		add2list("CTO病变在(左冠优势)或(右冠优势)", "0", data3);
		add2list("LAD CTO病变", "0", data3);
		int height3 = (int) (data3.size() * (getResources().getDimension(R.dimen.text_view_height)
				+ 0.3));
		lv_3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height3));
		adapter3 = new ListSwitchAdapter(NersScore.this, data3,3);
		lv_3.setAdapter(adapter3);
	}

	private void initview() {
		initTopBarForLeft("Ners Score II");

		lv_1 = (ListView) findViewById(R.id.lv_1);
		lv_2 = (ListView) findViewById(R.id.lv_2);
		lv_3 = (ListView) findViewById(R.id.lv_3);

		calculate = (Button) findViewById(R.id.calculate);
		calculate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (iscompelet()) {
					to_next();
				} else {
					ShowToast("信息填写不完整！");
				}
			}
		});

		LVfunction = (TextView) findViewById(R.id.LVfunction);
		LVfunction.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				show_res(R.id.LVfunction, new String[]{">40%", ">30%&<=40%",
						">20%&<=30%", ">10%&<=20%"});
			}
		});
		eGFB = (TextView) findViewById(R.id.eGFB);
		eGFB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				show_res(R.id.eGFB, new String[]{">60", "50-60", "40-50", "30-40",
						"20-30", "10-20", "<=10"});
			}
		});


	}

	private void to_next() {

		Cal_ss4 cal = new Cal_ss4();
		double res = cal.calculate(data);
		patient.setSyntax4(res);
		Log.d("888", "to_next: "+res);
		Intent intent = new Intent(NersScore.this,ResultShow.class);
		intent.putExtra("activity", 4);

		Bundle bundle = new Bundle();
		bundle.putSerializable("patient", patient);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
//		for (int i = 0;i<data.length;i++){
//			Log.d("-----", "to_next: " + data[i]);
//		}
//		Log.d("-----", "to_next: " + res);
	}

	public void add2list(String s1, String s2, List list) {
		Map map = new HashMap();
		map.put("1", s1);
		map.put("2", s2);
		list.add(map);
	}

	public void show_res(final int Rid, final String... titles) {

		ActionSheet.createBuilder(NersScore.this, getSupportFragmentManager())
				.setCancelButtonTitle("取消")
				.setOtherButtonTitles(titles)
				.setCancelableOnTouchOutside(true)
				.setListener(new ActionSheet.ActionSheetListener() {
					@Override
					public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

					}

					@Override
					public void onOtherButtonClick(ActionSheet actionSheet, int index) {
						TextView tv = (TextView) findViewById(Rid);
						tv.setText(titles[index]);
					}
				}).show();

	}

	public boolean iscompelet() {
		data = getdata();
		for (int i = 0; i < data.length; i++) {
			if (d_equal(data[i],-1)) {
				return false;
			}
		}
		return true;
	}


	public double[] getdata() {

		//age
		int age = patient.getAge();
		if (age < 75) data[0] = 0;
		else if (age >= 75 && age < 80) data[0] = 1.34;
		else if (age >= 80 && age < 85) data[0] = 2.34;
		else data[0] = 3.34;

		// LV
		if (LVfunction.getText().equals(">40%")) data[1] = 0;
		else if (LVfunction.getText().equals(">30%&<=40%")) data[1] = 2.03;
		else if (LVfunction.getText().equals(">20%&<=30%")) data[1] = 3.03;
		else if (LVfunction.getText().equals(">10%&<=20%"))data[1] = 4.03;

		//AMI
		if (data1.get(0).get("2").equals("0")) data[2] = 0;
		else data[2] = 3.65;
		//Car
		if (data1.get(1).get("2").equals("0")) data[3] = 0;
		else data[3] = 4.17;
		//Dia
		if (data1.get(2).get("2").equals("0")) data[4] = 0;
		else data[4] = 1.47;
		//Per
		if (data1.get(3).get("2").equals("0")) data[5] = 0;
		else data[5] = 1.74;
		//eGFR
		if (eGFB.getText().equals(">60")) data[6] = 0;
		else if (eGFB.getText().equals("50-60")) data[6] = 1.82;
		else if (eGFB.getText().equals("40-50")) data[6] = 2.32;
		else if (eGFB.getText().equals("30-40")) data[6] = 3.82;
		else if (eGFB.getText().equals("20-30")) data[6] = 5.32;
		else if (eGFB.getText().equals("10-20")) data[6] = 6.82;
		else if (eGFB.getText().equals("<=10"))data[6] = 8.32;

		// ---------- data2 Ost
		if (data2.get(0).get("2").equals("0")) data[7] = 0;
		else data[7] = 1.18;
		// DBT
		if (data2.get(1).get("2").equals("0")) data[8] = 0;
		else data[8] = 12.9;
		// Dnb
		if (data2.get(2).get("2").equals("0")) data[9] = 0;
		else data[9] = 8.67;
		// LMT-CTO
		if (data2.get(3).get("2").equals("0")) data[10] = 0;
		else data[10] = 13.73;
		// SLM
		if (data2.get(4).get("2").equals("0")) data[11] = 0;
		else data[11] = 6.13;

		// -------- data3 RCA
		if (data3.get(0).get("2").equals("0")) data[12] = 0;
		else data[12] = 1.27;
		//  LAD
		if (data3.get(1).get("2").equals("0")) data[13] = 0;
		else data[13] = 5.21;
		//CTO in
		if (data3.get(2).get("2").equals("0")) data[14] = 0;
		else data[14] = 3.27;
		//CTO in LAD
		if (data3.get(3).get("2").equals("0")) data[15] = 0;
		else data[15] = 5.49;

		return data;
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	boolean d_equal(double num1,double num2)
	{
		if(Math.abs((num1-num2)) <0.0001)return true;
		else return false;
	}

	public void show_bubble(View v,String text,int color){
		int[] location = new int[2];
		v.getLocationInWindow(location);

		BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(this)
				.inflate(R.layout.bubble_layout,null);
		((TextView) bubbleLayout.findViewById(R.id.bubble_text)).setText(text);

		if(color == 1)
			((TextView) bubbleLayout.findViewById(R.id.bubble_text)).
					setTextColor(getResources().getColor(R.color.green_title));
		else if(color == 2)
			((TextView) bubbleLayout.findViewById(R.id.bubble_text)).
					setTextColor(getResources().getColor(R.color.bule_title));
		else ((TextView) bubbleLayout.findViewById(R.id.bubble_text)).
					setTextColor(getResources().getColor(R.color.gray_title));
		popupWindow = BubblePopupHelper.create(this, bubbleLayout);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0]+20, v.getHeight() + location[1]);
		bubbleLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				popupWindow.dismiss();
			}
		});
	}
}
