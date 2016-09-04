package ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sean.score.CustomApplcation;
import com.sean.score.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Base.BaseActivity;
import Util.MyURL;

/**
 * Created by Administrator on 2016/6/26.
 */
public class Renzheng extends BaseActivity {

	public static String url = MyURL.renzheng;

	private static final String TAG = "Renzheng";
	SharedPreferences.Editor editor;
	SharedPreferences preferences;
	String phone;
	ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_ren);

		preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
		editor = preferences.edit();
		phone = preferences.getString("phone", "无");
		initview();
	}

	private void initview() {
		initTopBarForOnlyTitle("等待授权认证");


		progressBar = (ProgressBar) findViewById(R.id.pro);

		//两分钟后关闭
		android.os.Handler handler = new android.os.Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				progressBar.setVisibility(View.INVISIBLE);
				ShowToast("抱歉，您还没有被认证！");
			}
		},1000*120);

		Map map = new HashMap();
		map.put("phone", phone);
		JSONObject jsonObject = new JSONObject(map);

		Log.d(TAG, "initview:---> " + jsonObject);

		JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {

						try {
							String string = (String) jsonObject.get("result");
							if (string.equals("success")) {
								editor.putBoolean("renzheng", true);
								editor.commit();
								Intent in = new Intent(Renzheng.this, NewMain.class);
								startActivity(in);
								finish();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		});
		// 添加进队列
		js.setTag("wh");
		CustomApplcation.getRequestQuene().add(js);
	}
}
