package fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.sean.score.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.PositionAdapter;
import entity.DiseasePosition;
import entity.Patient;
import ui.Eval;

/**
 * Created by Administrator on 2016/6/10.
 *
 */
public class PositionListFrag extends Fragment {

	private static final String TAG = "PositionListFrag-------";

	private View mView;
	private Context mcontext;

	PositionAdapter adapter;

	ListView listView;
	List<Map> list;
	ArrayList checklist;

	int mflag = 0;//1 左 2右
	Button next_btn;

	Patient patient;

	public PositionListFrag(){

	}

	public PositionListFrag(Context context, int flag, Patient patient){
		mcontext = context;
		mflag = flag;
		this.patient = patient;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			initView(inflater, container);
		}
		return mView;
	}

	private void initView(LayoutInflater inflater, ViewGroup container) {
		mView  = inflater.inflate(R.layout.position_list, container, false);
		listView = (ListView) mView.findViewById(R.id.mlistview);
		list = new ArrayList<Map>();

		//给list 赋值
		initlist(list,mflag);

		adapter = new PositionAdapter(mcontext,list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CheckBox check = (CheckBox) view.findViewById(R.id.check1);
				if (check.isChecked()){
					check.setChecked(false);
					list.get(position).put(2,false);
					adapter.notifyDataSetChanged();
				}else {
					check.setChecked(true);
					list.get(position).put(2,true);
					adapter.notifyDataSetChanged();
				}

			}
		});

		next_btn = (Button) mView.findViewById(R.id.next_btn);
		next_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(iscompelet()){
					to_next();
				}else {
					Toast.makeText(mcontext,"请把信息填写完整！",Toast.LENGTH_SHORT);
				}
			}
		});
	}

	private void to_next() {

		Intent intent = new Intent(mcontext, Eval.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("list",checklist);
		bundle.putSerializable("patient",patient);
		intent.putExtras(bundle);
		mcontext.startActivity(intent);
		getActivity().finish();
	}

	private void initlist(List list,int flag) {

		if(flag == 1){
			DiseasePosition [] dp = new DiseasePosition[]{
					new DiseasePosition(0,true,"RAC proximal","1","RAC"),
					new DiseasePosition(1,true,"RAC mid","2","RAC"),
					new DiseasePosition(2,true,"RAC distal","3","RAC"),
					new DiseasePosition(3,true,"Left main","5","LM"),
					new DiseasePosition(4,true,"LAD proximal","6","LAD"),
					new DiseasePosition(5,true,"LAD mid","7","LAD"),
					new DiseasePosition(6,true,"LAD apical","8","LAD"),
					new DiseasePosition(7,true,"First diagonal","9","LAD"),
					new DiseasePosition(8,true,"Add first diagonal","9a","LAD"),
					new DiseasePosition(9,true,"Second diagonal","10","LAD"),
					new DiseasePosition(10,true,"Add second diagonal","10a","LAD"),
					new DiseasePosition(11,true,"Proximal circumflex","11","LCX"),
					new DiseasePosition(12,true,"Intermediate/anterolateral","12","LCX"),
					new DiseasePosition(13,true,"Obtuse marginal","12a","LCX"),
					new DiseasePosition(14,true,"Obtuse marginal","12b","LCX"),
					new DiseasePosition(15,true,"Distal circumflex","13","LCX"),
					new DiseasePosition(16,true,"Left posterolateral","14","LCX"),
					new DiseasePosition(17,true,"Left posterolateral","14a","LCX"),
					new DiseasePosition(18,true,"Left posterolateral","14b","LCX"),
					new DiseasePosition(19,true,"Posterior descending","15","LCX"),
			};

			for (int t = 0;t<dp.length;t++){
				Map map = new HashMap();
				map.put(1,dp[t]);
				map.put(2,false);
				list.add(map);
			}
		}else {
			DiseasePosition []dp2 = new DiseasePosition[]{
					new DiseasePosition(0,false,"RAC proximal","1","RAC"),
					new DiseasePosition(1,false,"RAC mid","2","RAC"),
					new DiseasePosition(2,false,"RAC distal","3","RAC"),
					new DiseasePosition(3,false,"Posterior descending","4","RAC"),
					new DiseasePosition(4,false,"Posterolateral from RCA","16","RAC"),
					new DiseasePosition(5,false,"Posterolateral from RCA","16a","RAC"),
					new DiseasePosition(6,false,"Posterolateral from RCA","16b","RAC"),
					new DiseasePosition(7,false,"Posterolateral from RCA","16c","RAC"),
					new DiseasePosition(8,false,"Left main","5","LM"),
					new DiseasePosition(9,false,"LAD proximal","6","LAD"),
					new DiseasePosition(10,false,"LAD mid","7","LAD"),
					new DiseasePosition(11,false,"LAD apical","8","LAD"),
					new DiseasePosition(12,false,"First diagonal","9","LAD"),
					new DiseasePosition(13,false,"Add. first diagonal","9a","LAD"),
					new DiseasePosition(14,false,"Second diagonal","10","LAD"),
					new DiseasePosition(15,false,"Add. second diagonal","10a","LAD"),
					new DiseasePosition(16,false,"Proximal circumflex","11","LCX"),
					new DiseasePosition(17,false,"Intermediate/anterolateral","12","LCX"),
					new DiseasePosition(18,false,"Obtuse marginal","12a","LCX"),
					new DiseasePosition(19,false,"Obtuse marginal","12b","LCX"),
					new DiseasePosition(20,false,"Distal circumflex","13","LCX"),
					new DiseasePosition(21,false,"Left posterolateral","14","LCX"),
					new DiseasePosition(22,false,"Left posterolateral","14a","LCX"),
					new DiseasePosition(23,false,"Left posterolateral","14b","LCX"),

			};

			for(int t = 0;t<dp2.length;t++){
				Map map = new HashMap();
				map.put(1,dp2[t]);
				map.put(2,false);
				list.add(map);
			}
		}


	}

	public boolean iscompelet() {
		checklist =  new ArrayList();
		for (Map map : list) {
			if(map.get(2) == true){
				checklist.add(map);
			}
		}
		if(checklist.isEmpty())
			return false ;
		else
			return true;
	}
}
