package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.sean.score.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Base.BaseActivity;
import Calculate.Cal_ss2;
import adapter.ListSwitchAdapter;
import entity.Patient;

/**
 * Created by Administrator on 2016/6/10.
 */
public class ScoreII extends BaseActivity {

	ListView listView2;
	List<Map<String, String>> list2;
	Button calculate;

	// crcl lvef
	TextView age, sco1;
	EditText edt1, edt2;
	String edt_str1, edt_str2;

	Patient patient;

	PopupWindow popupWindow;
	String[] list1_str = new String[]{
			"从左冠状动脉开口至分叉并累及前降支、回旋支", "需要长期使用支气管扩张剂或类固醇治疗肺部疾病",
			"包括主动脉或非冠脉的其它动脉，运动跛行，血管搏动减弱或无脉，造影提示狭窄大于50%。"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score2);

		//patient = new Patient("朱晓伟","01",55,"男","2016-5-5");
		getdata();

		initview();
	}

	public void getdata() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle.getSerializable("patient") != null)
			patient = (Patient) bundle.getSerializable("patient");
	}

	private void initview() {

		initTopBarForLeft("Syntax II");
		calculate = (Button) findViewById(R.id.calculate);
		calculate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (iscompelet()) {
					calculate();
				} else {
					ShowToast("请把信息填写完整！");
				}
			}
		});

		//listView1 = (ListView) findViewById(R.id.listview1);
		listView2 = (ListView) findViewById(R.id.listview2);
		listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
				show_bubble(view, list1_str[position],1);
				return true;
			}
		});
		//setList_1();
		setList_2();

		edt1 = (EditText) findViewById(R.id.edt_1);
		edt2 = (EditText) findViewById(R.id.edt_2);

		age = (TextView) findViewById(R.id.tv_age);
		age.setText("年龄 :" + patient.getAge());
		sco1 = (TextView) findViewById(R.id.sco1);
		sco1.setText("Syntax I评分（Syntax Score I ） " + patient.getSyntax1() + "分");

	}

//	private void setList_1() {
//		list1 = new ArrayList();
//
//		Map<String,String> map1 = new HashMap();
//		map1.put("1","SYNTAX SCORE 1 （0-120）");
//		map1.put("2","");
//		list1.add(map1);
//
//		Map<String,String> map2 = new HashMap();
//		map2.put("1","AGE（YEARS 18-100）");
//		map2.put("2","");
//		list1.add(map2);
//
//		Map<String,String> map3 = new HashMap();
//		map3.put("1","CrCl（0-135）");
//		map3.put("2","");
//		list1.add(map3);
//
//		Map<String,String> map4 = new HashMap();
//		map4.put("1","LVEF（10-75");
//		map4.put("2","");
//		list1.add(map4);
//
//		listView1.setAdapter(new ListEditTextAdapter(ScoreII.this,list1));
//
//	}

	private void setList_2() {
		list2 = new ArrayList();
		Map<String, String> smap1 = new HashMap<>();
		smap1.put("1", "左主干病变 (LEFT MAIN) ★");
		smap1.put("2", "0");
		list2.add(smap1);

		Map<String, String> smap2 = new HashMap<>();
		smap2.put("1", "慢性阻塞性肺疾病 (COPD) ★");
		smap2.put("2", "0");
		list2.add(smap2);

		Map<String, String> smap3 = new HashMap<>();
		smap3.put("1", "周围血管(狭窄>50%) PVD ★");
		smap3.put("2", "0");
		list2.add(smap3);

		listView2.setAdapter(new ListSwitchAdapter(ScoreII.this, list2, 1));
	}

	public void calculate() {

		int crcl = Integer.parseInt(edt_str1);
		int lvef = Integer.parseInt(edt_str2);

		Map map1 = list2.get(0);
		int lms = map1.get("2").equals("1") ? 1 : 0;

		Map map2 = list2.get(1);
		int copd = map2.get("2").equals("1") ? 1 : 0;

		Map map3 = list2.get(2);
		int pvd = map3.get("2").equals("1") ? 1 : 0;

		int gender = patient.getGender().equals("男") ? 1 : 0;

		if (crcl > 0 && crcl < 135 && lvef > 10 && lvef < 75) {
			Cal_ss2 cal = new Cal_ss2();
			double[] result = cal.calcSyntaxScore2(patient.getSyntax1(), patient.getAge(), crcl, lvef, lms,
					gender, copd, pvd);
			patient.setPCI(result[0]);
			patient.setCABG(result[1]);
			patient.setPCI_Death(result[2]);
			patient.setCABG_Death(result[3]);
			//推荐治疗
			patient.setRec_ope(value2str(result));

			Intent intent = new Intent(ScoreII.this, ResultShow.class);
			intent.putExtra("activity", 2);

			Bundle bundle = new Bundle();
			bundle.putSerializable("patient", patient);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();

		} else {
			ShowToast("填写信息不规范！");
		}

	}

	private String value2str(double[] result) {

		//p>0.05 两者都可以选，p<0.05如果PCI 积分大于CABG 推荐CABG反之PCI
		if (result[4] > 0.05) {
			return "CABG或PCI";
		} else {
			if (result[0] > result[1])
				return "CABG";
			else
				return "PCI";
		}
	}

	public boolean iscompelet() {

		edt_str1 = edt1.getText().toString();
		edt_str2 = edt2.getText().toString();
		if (edt_str1 != null && edt_str2 != null && !edt_str1.equals("") && !edt_str2.equals("")) {
			return true;
		}
		return false;
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
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
