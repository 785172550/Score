package ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mingle.widget.LoadingView;
import com.sean.score.CustomApplcation;
import com.sean.score.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Base.BaseActivity;
import Util.MyURL;
import adapter.MyHistoryAdapter;
import entity.Patient;

/**
 * Created by Administrator on 2016/7/16.
 */
public class CloudActivity extends BaseActivity {

	SearchView searchView;

	// 一定要http字段
	public static String murl = MyURL.cloud;
	private static final String TAG = "CloudActivity";

	LoadingView loadingView;
	ListView listView;

	List<Patient> list, showlist;

	MyHistoryAdapter adapter1, adapter2;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.cloud_act);
		initview();
	}

	private void initview() {
		initTopBarForLeft("云端数据");

		loadingView = (LoadingView) findViewById(R.id.loadView);
		list = new ArrayList<>();
		listView = (ListView) findViewById(R.id.show_list);
		adapter1 = new MyHistoryAdapter(CloudActivity.this, list);
		listView.setAdapter(adapter1);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Intent intent = new Intent(CloudActivity.this, Report.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("patient", list.get(position));
				intent.putExtras(bundle);
				intent.putExtra("meg", 2);
				startActivity(intent);
			}
		});
		listView.setVisibility(View.INVISIBLE);
		SendToWeb s = new SendToWeb();
		s.send();

		searchView = (SearchView) findViewById(R.id.searchView);
		searchView.setIconifiedByDefault(true);
		searchView.onActionViewExpanded();
		searchView.setFocusable(false);
		searchView.clearFocus();

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (newText != null) {
					showlist = new ArrayList<>();
					for (int i = 0; i < list.size(); i++) {
						String content = list.get(i).getNum();
						String content2 = list.get(i).getName();
						if (content.contains(newText) || content2.contains(newText)) {
							Patient patient = list.get(i);
							//map.put("id", String.valueOf(i));
							showlist.add(patient);
						}
					}
					adapter2 = new MyHistoryAdapter(CloudActivity.this, showlist);
					listView.setAdapter(adapter2);
					listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							Intent intent = new Intent(CloudActivity.this, Report.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("patient", showlist.get(position));
							intent.putExtras(bundle);
							intent.putExtra("meg", 1);
							startActivity(intent);
						}
					});
					//长按删除
					listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
						@Override
						public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
							Dialog dialog = createdialog("提示", "是否删除？", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Patient patient = showlist.get(position);
									patient.delete();
									list.remove(showlist.get(position));
									showlist.remove(position);
									adapter1.notifyDataSetChanged();
									adapter2.notifyDataSetChanged();
									ShowToast("成功删除");

								}
							}, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});
							dialog.show();
							return true;
						}
					});
				}

				return false;
			}
		});

	}

	//更新列表
	public void update(JSONObject jsonObject) {
		JSONArray jsonArray = null;
		try {
			jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject1 = jsonArray.getJSONObject(i);
				Patient patient = new Patient();
				Gson gson = new Gson();
				patient = gson.fromJson(jsonObject1.toString(), Patient.class);
				Log.d(TAG, "update: " + patient.toString());
				list.add(patient);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		//list  获取数据
		adapter1.notifyDataSetChanged();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				listView.setVisibility(View.VISIBLE);
				loadingView.setVisibility(View.INVISIBLE);
			}
		}, 1000);
	}


	class SendToWeb {
		SharedPreferences preferences;
		String phone;

		public void send() {

			preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
			phone = preferences.getString("phone", "无");
			//phone = "18380122721";


			Map amap = new HashMap();
			amap.put("phone", phone);
			JSONObject jsonObject = new JSONObject(amap);

			Log.d(TAG, "send: " + jsonObject.toString());
			JsonObjectRequest jr1 = new JsonObjectRequest(Request.Method.POST, murl,
					jsonObject, new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject jsonObject) {
					//Log.d(TAG, "onResponse: " +jsonObject.toString());
					update(jsonObject);
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError volleyError) {

				}
			});

			// 添加进队列
			jr1.setTag("wh");
			CustomApplcation.getRequestQuene().add(jr1);
		}
	}
}
