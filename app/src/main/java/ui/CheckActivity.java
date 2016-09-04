package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sean.score.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Base.BaseActivity;
import adapter.PositionAdapter;
import entity.DiseasePosition;
import myview.CheckLayout;

/**
 * Created by Administrator on 2016/7/16.
 * 	弥漫小血管病变
 */
public class CheckActivity extends BaseActivity {

	double sum=0;
	double score1;
	List<Map> dpl;
	ArrayList checklist;

//	Patient patient;
	CheckLayout checkLayout = null;
	//List<CheckBox> checkBoxes;
	//LinearLayout check_lin;
	ListView listView;
	TextView tv_score;
	PositionAdapter adapter;

	DiseasePosition[] dp2 = new DiseasePosition[]{
			new DiseasePosition(0,false,"RAC proximal","1 右冠近端","1","RAC"),
			new DiseasePosition(1,false,"RAC mid","2 右冠中段","2","RAC"),
			new DiseasePosition(2,false,"RAC distal","3 右冠远段","3","RAC"),
			new DiseasePosition(3,false,"Posterior descending","4 右冠后降支","4","RAC"),
			new DiseasePosition(4,false,"Posterolateral from RCA","16 右冠后侧支","16","RAC"),
			new DiseasePosition(5,false,"Posterolateral from RCA","16a 右冠后侧支a","16a","RAC"),
			new DiseasePosition(6,false,"Posterolateral from RCA","16b 右冠后侧支b","16b","RAC"),
			new DiseasePosition(7,false,"Posterolateral from RCA","16c 右冠后侧支c","16c","RAC"),
			new DiseasePosition(8,false,"Left main","5 左主干", "5","LM"),
			new DiseasePosition(9,false,"LAD proximal","6 前降支近段","6","LAD"),
			new DiseasePosition(10,false,"LAD mid","7 前降支中段","7","LAD"),
			new DiseasePosition(11,false,"LAD apical","8 前降支心尖段","8","LAD"),
			new DiseasePosition(12,false,"First diagonal","9 第一对角支","9","LAD"),
			new DiseasePosition(13,false,"Add. first diagonal","9a 第一对角支a", "9a","LAD"),
			new DiseasePosition(14,false,"Second diagonal","10 第二对角支","10","LAD"),
			new DiseasePosition(15,false,"Add. second diagonal","10a 第二对角支a","10a","LAD"),
			new DiseasePosition(16,false,"Proximal circumflex","11 回旋支近段","11","LCX"),
			new DiseasePosition(17,false,"Intermediate/anterolateral","12 中间支","12","LCX"),
			new DiseasePosition(18,false,"Obtuse marginal","12a 第一钝缘支","12a","LCX"),
			new DiseasePosition(19,false,"Obtuse marginal","12b 第二钝缘支","12b","LCX"),
			new DiseasePosition(20,false,"Distal circumflex","13 回旋支远段","13","LCX"),
			new DiseasePosition(21,false,"Left posterolateral","14 左后侧支","14","LCX"),
			new DiseasePosition(22,false,"Left posterolateral","14a 左后侧支a","14a","LCX"),
			new DiseasePosition(23,false,"Left posterolateral","14b 左后侧支b","14b","LCX"),
			new DiseasePosition(24,true,"Posterior descending","15 回旋支-后降支","15","LCX")
	};

	Button next_btn;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.check_act);
		getdata();
		//checkBoxes = new ArrayList<>();
		initview();
	}

	private void initview() {
		initTopBarForOnlyTitle("弥漫/小血管病变");
		//check_lin = (LinearLayout) findViewById(R.id.check_lin);

		initlist();
		listView = (ListView) findViewById(R.id.mlistview);
		adapter = new PositionAdapter(CheckActivity.this,dpl);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				CheckBox check = (CheckBox) view.findViewById(R.id.check1);
				if (check.isChecked()){
					check.setChecked(false);
					dpl.get(position).put(2,false);
					adapter.notifyDataSetChanged();
					cur_score();
				}else {
					check.setChecked(true);
					dpl.get(position).put(2,true);
					adapter.notifyDataSetChanged();
					cur_score();
				}
			}
		});

		//完成
		next_btn = (Button) findViewById(R.id.ccc_btn);
		next_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(iscompelet()){
					to_next();
				}else {
					Toast.makeText(CheckActivity.this,"请把信息填写完整！",Toast.LENGTH_SHORT);
				}
			}
		});
		tv_score = (TextView) findViewById(R.id.tv_score);
	}

	private void initlist() {
		dpl = new ArrayList<>();

		for(DiseasePosition dp:dp2){
			Map map = new HashMap();
				map.put(1,dp);
				map.put(2,false);
				dpl.add(map);
		}

	}

	public void getdata() {
		Intent intent = getIntent();
		 score1 = intent.getDoubleExtra("score",0);
//		Bundle bundle = intent.getExtras();
//		//list = (ArrayList) bundle.get("list");
//		if (bundle.getSerializable("patient") != null)
//			patient = (Patient) bundle.getSerializable("patient");

	}

	public boolean iscompelet() {
		checklist =  new ArrayList();
		for (Map map : dpl) {
			if(map.get(2) == true){
				checklist.add(map);
			}
		}
		if(checklist.isEmpty())
			return false ;
		else
			return true;
	}


	// 返回
	private void to_next() {

		Intent intent = new Intent(CheckActivity.this, Eval.class);
		intent.putExtra("score1",sum);
		setResult(3,intent);
		finish();
	}


	public void cur_score(){

		sum = 0;
		if(!dpl.isEmpty()){
			for(Map map:dpl){
				if(map.get(2) == true)
					sum++;
			}
		}
		tv_score.setText("score: " + sum);
	}
}
