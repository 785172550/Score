package ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sean.score.CustomApplcation;
import com.sean.score.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Base.BaseActivity;
import Util.MyURL;
import myview.HeaderLayout;

/**
 * Created by Administrator on 2016/6/10.
 *
 */
public class FanKui extends BaseActivity {

	private static String Url = MyURL.seedback;

	private static final String TAG = "FanKui";
	
	ImageButton left_btn;
	TextView right_btn;
	EditText editText;
	SharedPreferences preferences;
	String phone;
	Button send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fankui);
		preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
		phone = preferences.getString("phone", "无");
		initview();
	}

	private void initview() {
		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String meg = editText.getText().toString();
				send_meg(meg);
			}
		});

		initTopBarForBothWithText("意见反馈", "", new HeaderLayout.onRightImageButtonClickListener() {
			@Override
			public void onClick(View v) {
			}
		}, R.drawable.back_n, new HeaderLayout.onLeftImageButtonClickListener() {
			@Override
			public void onClick() {
				finish();
			}
		});
		editText = (EditText) findViewById(R.id.etv);

	}

	//
	private void send_meg(String meg) {

		// 提交参数
		Map<String, Object> map = new HashMap<>();
		map.put("phone",phone);
		map.put("seedback",meg);

		JSONObject jsonObject2= new JSONObject(map);

		Log.d(TAG, "send_meg: " + jsonObject2);

		//post 发送
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, Url, jsonObject2,
				new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {
				Log.d(TAG, "onResponse: " + jsonObject);
			}
		},
				new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Log.d(TAG, "onErrorResponse: ");
			}
		});
		editText.setText("");
		ShowToast("已经收到反馈意见，我们会尽快处理！");
		// 添加进队列
		jr.setTag("wh");
		CustomApplcation.getRequestQuene().add(jr);
	}

}
