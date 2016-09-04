package ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.kyleduo.switchbutton.SwitchButton;
import com.sean.score.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Base.BaseActivity;
import Calculate.Cal_ss3;
import adapter.ListSwitchAdapter;
import entity.Patient;

/**
 * Created by Administrator on 2016/6/10.
 */
public class EuroScore extends BaseActivity implements View.OnClickListener,View.OnLongClickListener{

	ListView listView;
	List<Map<String, String>> list;
	TextView t_Renal, NYHA, t_LV, t_Ph, t_Urgency, t_Weight,CSS_class,t_Recent,t_Surgery;
	Button calculate;
	RelativeLayout rl_1,rl_2;
	int[] data;
	boolean[] data2;
	SwitchButton CSS_class_btn, Recent_btn, t_Surgery_btn;

	Patient patient;

	String[] list1_str = new String[]{
			"1、跛行；2、颈动脉闭塞或狭窄大于50%；3、截肢治疗动脉疾病；4、预先或计划干预腹主动脉、肢体动脉或颈动脉",
			"骨骼肌肉疾患或神经功能不全所致严重影响活动或日常功能",
			"既往切开过心包行心脏外科手术",
			"需要长期使用支气管扩张剂或类固醇的呼吸道疾病",
			"手术时需要抗生素治疗",
			"术前室速或室颤，猝死、术前心脏复苏、到麻醉科前气管插管，术前急性肾功能不全（无尿或少尿<10ml/hr）、使用正性肌力药物或IABP、室性心律失常",
	};

	private PopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_euro);
		//初始化值
		data = new int[6];
		Arrays.fill(data, -1);
		data2 = new boolean[]{false, false, false};

		getdata();

		initview();

		//patient = new Patient("朱晓伟", "01", 55, "男", "2016-5-5");
	}

	public void  getdata() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle.getSerializable("patient") != null)
			patient = (Patient) bundle.getSerializable("patient");
	}

	private void initview() {

		initTopBarForLeft("Euro Score II");
		listView = (ListView) findViewById(R.id.eur_lview);

		//初始化list的数据
		initdata();

		int height = (int) (list.size() * (getResources().getDimension(R.dimen.text_view_height)
				+ 0.3));
		listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
		listView.setAdapter(new ListSwitchAdapter(EuroScore.this, list,1));

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
				Log.d("-----------", "onItemLongClick: ");
				if(position != 6){
					show_bubble(view,list1_str[position],1);
				}
				return true;
			}
		});

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
		//初始化textview
		inittv();

		CSS_class_btn = (SwitchButton) findViewById(R.id.CSS_class_btn);
		Recent_btn = (SwitchButton) findViewById(R.id.Recent_btn);
		t_Surgery_btn = (SwitchButton) findViewById(R.id.t_Surgery_btn);

		CSS_class_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					data2[0] = true;
				} else {
					data2[0] = false;
				}
			}
		});

		Recent_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					data2[1] = true;
				} else {
					data2[1] = false;
				}
			}
		});

		t_Surgery_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					data2[2] = true;
				} else {
					data2[2] = false;
				}
			}
		});

//		CSS_class = (TextView) findViewById(R.id.CSS_class);
//		t_Recent = (TextView) findViewById(R.id.t_Recent);
		t_Surgery = (TextView) findViewById(R.id.t_Surgery);

//		CSS_class.setOnLongClickListener(this);
//		t_Recent.setOnLongClickListener(this);
		t_Surgery.setOnLongClickListener(this);

		rl_1 = (RelativeLayout) findViewById(R.id.rl_1);
		rl_2 = (RelativeLayout) findViewById(R.id.rl_2);


		rl_1.setOnLongClickListener(this);
		rl_2.setOnLongClickListener(this);
	}

	//初始化textview
	private void inittv() {
		t_Renal = (TextView) findViewById(R.id.t_Renal);
		NYHA = (TextView) findViewById(R.id.NYHA);
		t_LV = (TextView) findViewById(R.id.t_LV);
		t_Ph = (TextView) findViewById(R.id.t_Ph);
		t_Urgency = (TextView) findViewById(R.id.t_Urgency);
		t_Weight = (TextView) findViewById(R.id.t_Weight);

		t_Renal.setOnClickListener(this);
		NYHA.setOnClickListener(this);
		t_LV.setOnClickListener(this);
		t_Ph.setOnClickListener(this);
		t_Urgency.setOnClickListener(this);
		t_Weight.setOnClickListener(this);

	}

	//初始化list的数据
	private void initdata() {
		list = new ArrayList();
		Map<String, String> smap1 = new HashMap<>();
		smap1.put("1", "外周动脉疾病 ★");
		smap1.put("2", "0");
		list.add(smap1);

		Map<String, String> smap2 = new HashMap<>();
		smap2.put("1", "严重活动障碍 ★");
		smap2.put("2", "0");
		list.add(smap2);

		Map<String, String> smap3 = new HashMap<>();
		smap3.put("1", "既往心脏手术史 ★");
		smap3.put("2", "0");
		list.add(smap3);

		Map<String, String> smap4 = new HashMap<>();
		smap4.put("1", "慢性肺脏疾病 ★");
		smap4.put("2", "0");
		list.add(smap4);

		Map<String, String> smap5 = new HashMap<>();
		smap5.put("1", "活动性心内膜炎 ★");
		smap5.put("2", "0");
		list.add(smap5);


		Map<String, String> smap6 = new HashMap<>();
		smap6.put("1", "活动性心内膜炎 ★");
		smap6.put("2", "0");
		list.add(smap6);

		Map<String, String> smap7 = new HashMap<>();
		smap7.put("1", "应用胰岛素治疗糖尿病");
		smap7.put("2", "0");
		list.add(smap7);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.t_Renal:
				show_choose(R.id.t_Renal, new String[]{"正常 (CC >85 ml/min)",
						"中度 (CC >50 & <85)", "重度 (CC <50)", "透析 (regardless CC)"});
				break;
			case R.id.NYHA:
				show_choose(R.id.NYHA, new String[]{"I",
						"II", "III", "IV"});
				break;
			case R.id.t_LV:
				show_choose(R.id.t_LV, new String[]{"正常 (LVEF > 50%))",
						"中度 (LVEF 30% - 50% )", "严重 (LVEF 20% - 30%) ",
						"极严重 (20 % or less)"});
				break;
			case R.id.t_Ph:
				show_choose(R.id.t_Ph, new String[]{"无",
						"中度 (肺动脉收缩压 31-55mmHg) ", "严重 (肺动脉收缩压 >55mmHg) "
				});
				break;
			case R.id.t_Urgency:
				show_choose(R.id.t_Urgency, new String[]{"择期手术",
						"立即介入或外科手术", "次日手术", "术前心肺复苏抢救"
				});
				break;
			case R.id.t_Weight:
				show_choose(R.id.t_Weight, new String[]{"单纯CABG",
						"单项非CABG手术", "两项心脏手术", "三项心脏手术"
				});
				break;
		}
	}

	public void show_choose(final int rID, final String... titles) {

		ActionSheet.createBuilder(EuroScore.this, getSupportFragmentManager())
				.setCancelButtonTitle("取消")
				.setOtherButtonTitles(titles)
				.setCancelableOnTouchOutside(true)
				.setListener(new ActionSheet.ActionSheetListener() {
					@Override
					public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

					}

					@Override
					public void onOtherButtonClick(ActionSheet actionSheet, int index) {
						TextView textView = (TextView) findViewById(rID);
						textView.setText(titles[index]);
						setdata(rID, index);
					}
				}).show();
	}

	private void setdata(int id, int index) {

		switch (id) {
			case R.id.t_Renal:
				data[0] = index;
				break;
			case R.id.NYHA:
				data[1] = index;
				break;
			case R.id.t_LV:
				data[2] = index;
				break;
			case R.id.t_Ph:
				data[3] = index;
				break;
			case R.id.t_Urgency:
				data[4] = index;
				break;
			case R.id.t_Weight:
				data[5] = index;
				break;
		}
	}

	public boolean iscompelet() {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == -1) {
				return false;
			}
		}
		return true;
	}

	private void to_next() {
		Cal_ss3 cal = new Cal_ss3();

		//data 是6个弹框的值  data2 是 yes/no 的值 list是 listview里yes/no的值
		boolean[] data3 = new boolean[list.size()];
		for (int i = 0; i < list.size(); i++) {
			// map.get("2") 等于0 为false 等于1为true
			data3[i] = !list.get(i).get("2").equals("0");
		}

		double sex = patient.getGender().equals("男") ? 0 : 0.2196434;
		String res = cal.CalcMort(data, data2, data3, sex, patient);

		patient.setSyntax3(res);

		Intent intent = new Intent(EuroScore.this, ResultShow.class);
		intent.putExtra("activity", 3);

		Bundle bundle = new Bundle();
		bundle.putSerializable("patient", patient);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
		Log.d("-----", "to_next: " + res);
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


	@Override
	public boolean onLongClick(View view) {
		switch (view.getId()){
			case R.id.rl_1:
				show_bubble(view,"轻微活动即可引起心绞痛或静息性心绞痛",2);
				break;
			case R.id.rl_2:
				show_bubble(view,"90天内的心肌梗死",2);
				break;
			case R.id.t_Surgery:
				//show_bubble(view,"暂无");
				break;
		}
		return false;
	}
}
