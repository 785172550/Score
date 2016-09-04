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
import android.widget.ListView;

import com.sean.score.R;

import java.util.ArrayList;
import java.util.List;

import adapter.SlidingAdapter;
import ui.CloudActivity;
import ui.FanKui;
import ui.History;
import ui.NewMain;

/**
 * Created by Administrator on 2016/6/10.
 */

public class SlidingFrag extends Fragment {

	private View mView;
	private Context mcontext;
	private ListView mlistview;

	List mdata;

	public SlidingFrag(){

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mcontext = getActivity();
		if (mView == null) {
			initView(inflater, container);
		}
		return mView;
	}

	private void initView(LayoutInflater inflater, ViewGroup container) {
		mdata = new ArrayList();
		mdata.add("风险评估");
		mdata.add("本地数据");
		mdata.add("云端数据");
		mdata.add("意见反馈");

		mView = inflater.inflate(R.layout.slide_main, container, false);

		mlistview = (ListView) mView.findViewById(R.id.lv_slid);
		mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			Class<?> cls = null;

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				switch (position){
					 case 0://风险评估
						cls = null;
						break;
					case 1://历史记录
						cls = History.class;
						break;
					case 2://云端数据
						cls = CloudActivity.class;
						break;
					case 3: //反馈
						cls = FanKui.class;
						break;
				}

				if(cls!=null){
					Intent intent = new Intent(mcontext, cls);
					startActivity(intent);
				}else NewMain.swicth_slindg();
			}
		});

		mlistview.setAdapter(new SlidingAdapter(mcontext,mdata));
	}
}
