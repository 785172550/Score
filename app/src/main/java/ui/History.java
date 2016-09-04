package ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sean.score.CustomApplcation;
import com.sean.score.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Base.BaseActivity;
import Util.MyURL;
import adapter.MyHistoryAdapter;
import entity.Patient;
import myview.HeaderLayout;

/**
 * Created by Administrator on 2016/6/10.
 *
 */
public class History extends BaseActivity {

	// 一定要http字段
	public static String murl = MyURL.beifen;

	ListView listView;
	List<Patient> list, showlist;
	SearchView searchView;
	MyHistoryAdapter adapter1,adapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		// 获得所以的患者信息
		list = Patient.findWithQuery(Patient.class, "select * from patient");
		if(list.size()>80){
			ShowToast("您已经");
		}
		initview();
	}

	private void initview() {
		initTopBarForBothWithText("历史记录", "云备份", new HeaderLayout.onRightImageButtonClickListener() {
			@Override
			public void onClick(View v) {
				SendToWeb sendToWeb = new SendToWeb();
				sendToWeb.send();
			}
		}, R.drawable.base_action_bar_back_bg_selector, new HeaderLayout.onLeftImageButtonClickListener() {
			@Override
			public void onClick() {
				finish();
			}
		});

		listView = (ListView) findViewById(R.id.search_list);

		Collections.sort(list, new Comparator<Patient>() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm", Locale.CHINA);

			@Override
			public int compare(Patient lhs, Patient rhs) {
				long time1 = 0, time2 = 0;
				try {
					time1 = sdf.parse(lhs.getDate()).getTime();
					time2 = sdf.parse(rhs.getDate()).getTime();

				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (time1 < time2) return -1;
				return 0;
			}
		});

		adapter1  = new MyHistoryAdapter(History.this, list);
		listView.setAdapter(adapter1);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(History.this, Report.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("patient", list.get(position));
				intent.putExtras(bundle);
				intent.putExtra("meg",1);
				startActivity(intent);
				finish();
			}
		});

		//长按删除
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
				Dialog dialog = createdialog("提示", "是否删除？", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Patient patient = list.get(position);
						patient.delete();
						list.remove(position);
						adapter1.notifyDataSetChanged();
						ShowToast("成功删除");
						dialog.dismiss();

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

		searchView = (SearchView) findViewById(R.id.searchView);
		searchView.setIconifiedByDefault(true);
		searchView.onActionViewExpanded();
		searchView.setFocusable(false);
		searchView.clearFocus();

//      mSearchView.setIconifiedByDefault(true);
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
					adapter2 = new MyHistoryAdapter(History.this, showlist);
					listView.setAdapter(adapter2);
					listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							Intent intent = new Intent(History.this, Report.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("patient", showlist.get(position));
							intent.putExtras(bundle);
							intent.putExtra("meg",1);
							startActivity(intent);
							finish();
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
									dialog.dismiss();

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



	/**
	 * Created by Administrator on 2016/6/25.
	 *	往云端发送备份数据
	 *  备份新加字段 phone 和 choice
	 *
	 */
	class SendToWeb{


		private static final String TAG = "SendToWeb";

		List<Patient> list;
		List<Map> send_msg;

		SharedPreferences preferences;
		String phone;
		Context context;


		public void send(){

			list =  Patient.findWithQuery(Patient.class, "select * from patient");
			//User c_usr = User.findById(User.class,1);

			preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
			phone = preferences.getString("phone", "无");

			send_msg = new ArrayList<>();
			//保留一位小数
			DecimalFormat df = new DecimalFormat("#.0");

			for(Patient patient:list){
				Map<String,String> map = new HashMap();
				map.put("name",patient.getName());
				map.put("num",patient.getNum());
				map.put("date",patient.getDate());
				map.put("gender",patient.getGender());
				map.put("age",patient.getAge() + "");
				map.put("syntax1",patient.getSyntax1() + "");
				map.put("PCI",df.format(patient.getPCI()));
				map.put("PCI_Death",df.format(patient.getPCI_Death()) + "%");
				map.put("CABG",df.format(patient.getCABG()));
				map.put("CABG_Death",df.format(patient.getCABG_Death())+ "%");
				map.put("rec_ope",patient.getRec_ope());
				map.put("syntax3",patient.getSyntax3());
				map.put("syntax4",patient.getSyntax4() + "");
				//新加字段
				map.put("choice",patient.getChoice());
				map.put("phone",phone);
				send_msg.add(map);
			}

			JSONArray jsonArray = new JSONArray();
			int i = 0;
			for(Map map:send_msg){
				JSONObject jsonObject = new JSONObject(map);
				try {
					jsonArray.put(i,jsonObject);
					i++;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Map amap = new HashMap();
			amap.put("array",jsonArray);
			JSONObject all = new JSONObject(amap);
			Log.d(TAG, "send: " + all);

			JsonObjectRequest jr1 = new JsonObjectRequest(Request.Method.POST, murl,
					all, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject jsonObject) {
					Log.d(TAG, "onResponse: " +jsonObject);
					ShowToast("已成功备份到云端！");
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
