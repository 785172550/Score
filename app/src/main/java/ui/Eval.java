package ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sean.score.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Base.BaseActivity;
import adapter.EvalAdapter;
import entity.DiseasePosition;
import entity.Patient;
import myview.HeaderLayout;
import myview.YesNoDialog;

/**
 * Created by Administrator on 2016/6/12.
 * 测评报告页面
 */
public class Eval extends BaseActivity {


	Patient patient;
	TextView add_img,tv_score,new_add,new_add_res;
	ArrayList<Map>  mlist;
	ArrayList<DiseasePosition> list;
	ListView listView;
	EvalAdapter adapter;
	DiseasePosition dp;
	int list_position;
	double score1 = 0;
	int count = 0;

	RelativeLayout miman;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_eval);
		getdata();
		//patient =  new Patient("朱晓伟","01",55,"男","2016-5-5");

		initTopBarForBothWithText("评分结果", "完成", new HeaderLayout.onRightImageButtonClickListener() {
			@Override
			public void onClick(View v) {
				patient.setSyntax1(score1);
				Intent intent = new Intent(Eval.this,Report.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("patient",patient);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		}, R.drawable.base_action_bar_back_bg_selector, new HeaderLayout.onLeftImageButtonClickListener() {
			@Override
			public void onClick() {
				finish();
			}
		});

		initmlist(list);
		initview();
	}

	public void getdata() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		list = (ArrayList) bundle.get("list");

		if (bundle.getSerializable("patient") != null)
			patient = (Patient) bundle.getSerializable("patient");
	}

	private void initmlist(ArrayList<DiseasePosition> list) {
		mlist = new ArrayList<>();
		for (DiseasePosition diseasePosition : list) {
			Map<Integer, Object> map2 = new HashMap();
			map2.put(1, diseasePosition);
			map2.put(4,0.0);
			mlist.add(map2);
		}
		count = list.size();

		Map map = new HashMap();
		map.put(2, "已完成评分病变");
		map.put(4,0.0);
		mlist.add(map);
	}

	private void initview() {

		add_img = (TextView) findViewById(R.id.add_img);
		add_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eval.this, ChoseImg.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("patient", patient);
				intent.putExtras(bundle);
				startActivityForResult(intent,2);
			}
		});

		miman = (RelativeLayout) findViewById(R.id.miman);
		//弥漫小血管
		new_add = (TextView) findViewById(R.id.new_add);
		new_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				Intent intent = new Intent(Eval.this, CheckActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("patient", patient);
//				intent.putExtras(bundle);
//				startActivityForResult(intent,3);
			}
		});

		// 弥漫的分数显示
		new_add_res = (TextView) findViewById(R.id.new_add_res);


		tv_score = (TextView) findViewById(R.id.tv_score);
		listView = (ListView) findViewById(R.id.eval_l1);
		adapter = new EvalAdapter(Eval.this, mlist);
		int height = (int) (mlist.size()*
				(getResources().getDimension(R.dimen.text_view_height)+ 0.3));
		listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (view.findViewById(R.id.text) != null) ;
				else if (view.findViewById(R.id.tv_item) != null) { // 待评分
					list_position = position;
					Intent intent = new Intent(Eval.this, EvalQuestion.class);
					dp = (DiseasePosition) mlist.get(position).get(1);
					Bundle bundle = new Bundle();
					//cou 为1 就是最后一个
					int cou = islast();
					bundle.putSerializable("dp", dp);
					bundle.putInt("last",cou);
					intent.putExtras(bundle);
					count --;

					startActivityForResult(intent, 1);
				}else {
					list_position = position;
					Intent intent = new Intent(Eval.this, EvalQuestion.class);
					dp = (DiseasePosition) mlist.get(position).get(3);
					Bundle bundle = new Bundle();
					bundle.putSerializable("dp", dp);
					if(count == 0)
						bundle.putInt("last",1);
					else bundle.putSerializable("last",-1);
					intent.putExtras(bundle);
					startActivityForResult(intent, 2);
				}
			}
		});



	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 1){
			if (requestCode == 1) {
				mlist.remove(list_position);
				Map map = new HashMap();
				map.put(3,dp);
				map.put(4,data.getDoubleExtra("score",0.0));
				mlist.add(map);
				adapter.notifyDataSetChanged();
			}else if(requestCode == 2){
				mlist.get(list_position).put(4,data.getDoubleExtra("score",0.0));
				adapter.notifyDataSetChanged();
			}
			double sum = 0;
			for(Map map:mlist){
				sum =  sum + (double) map.get(4);
			}
			score1 = sum;
			tv_score.setText("总得分 : " + score1 + "分");
			if(count == 0){
				Dialog dialog = createdialog("", "是否进入 弥漫/小血管 评测", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent intent = new Intent(Eval.this, CheckActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("patient", patient);
						intent.putExtras(bundle);
						startActivityForResult(intent,3);
						dialogInterface.dismiss();
					}
				}, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
				dialog.show();
			}

		}else if(resultCode == 2){
			Bundle bundle = data.getExtras();
			patient = (Patient) bundle.getSerializable("patient");
		}else if(resultCode == 3){

//			miman.setVisibility(View.VISIBLE);
			double check_score = data.getDoubleExtra("score1",0.0);
			score1 += check_score;
			new_add_res.setText(check_score+ "");
			Log.d("000000", "onActivityResult: " + check_score);
			tv_score.setText("总得分 : " + score1 + "分");
		}
	}

	public int islast(){
		if(count == 1) return 1;
		else  return -1;
	}

	public Dialog createdialog(String title, String message, DialogInterface.OnClickListener click1,
							   DialogInterface.OnClickListener click2){

		YesNoDialog.Builder builder = new YesNoDialog.Builder(Eval.this)
				.setMessage(message)
				.setPositiveButton("确定",click1)
				.setNegativeButton("取消",click2);

		Dialog dialog = builder.create();
		return dialog;
	}
}
