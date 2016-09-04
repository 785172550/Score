package ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.sean.score.CustomApplcation;
import com.sean.score.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Base.BaseActivity;
import Util.MyURL;

/**
 * Created by Administrator on 2016/6/25.
 */
public class Register extends BaseActivity {

	EditText uname_edt,yanzheng,usr_name,hospital,room,role;
	Button get_btn,reg_btn;
	String phone_num;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	public static String url = MyURL.register;

	int num;
	private TimeCount time;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.reg);
		initTopBarForOnlyTitle("新用户注册");
		time = new TimeCount(60000, 1000);//构造CountDownTimer对象
		preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
		editor = preferences.edit();
		initview();
	}

	private void initSDK() {

		HashMap<String, Object> result = null;
		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

		//******************************注释*********************************************
		//*初始化服务器地址和端口                                                       *
		//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
		//*******************************************************************************
		restAPI.init("app.cloopen.com", "8883");

		//******************************注释*********************************************
		//*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
		//*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
		//*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
		//*******************************************************************************
		restAPI.setAccount("8a48b5514fd49643014fd6421bfb0718", "7d3034a28b5e4ef99f03abff624d4521");


		//******************************注释*********************************************
		//*初始化应用ID                                                                 *
		//*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
		//*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		//*******************************************************************************
		restAPI.setAppId("8a48b5515388ec1501539e56b5472125");


		//******************************注释****************************************************************
		//*调用发送模板短信的接口发送短信                                                                  *
		//*参数顺序说明：                                                                                  *
		//*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
		//*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
		//*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
		//*第三个参数是要替换的内容数组。																														       *
		//**************************************************************************************************

		//**************************************举例说明***********************************************************************
		//*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
		//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
		//*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
		//*********************************************************************************************************************
		result = restAPI.sendTemplateSMS(phone_num,"75446" ,new String[]{num +"","2"});

		Log.d("111","SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
	}

	private void initview() {

		//用户名 ---> 手机号
		uname_edt = (EditText) findViewById(R.id.uname_edt);
		//验证信息
		yanzheng = (EditText) findViewById(R.id.yanzheng);
		get_btn = (Button) findViewById(R.id.get_btn);
		reg_btn = (Button) findViewById(R.id.reg_btn);

		//医生姓名
		usr_name = (EditText) findViewById(R.id.usr_name);
		hospital= (EditText) findViewById(R.id.hospital);
		room= (EditText) findViewById(R.id.room);
		role= (EditText) findViewById(R.id.role);

		get_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// 获取手机号
				phone_num = uname_edt.getText().toString();
				String telRegex = "[1][3578]\\d{9}";
				boolean isphone;
				//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
				if (TextUtils.isEmpty(phone_num)) isphone = false;
				else isphone = phone_num.matches(telRegex);

				if(phone_num != null && isphone){
					time.start();
					// 1000-9999 的随机数
					num = (int)(Math.random() * (8999) +1000);
					ShowToast("短信已发送");
					// 在子线程中发送
					new Thread(){
						@Override
						public void run() {
							initSDK();
						}
					}.start();

				}else {
					ShowToast("请输入正确的手机号码！");
				}

			}
		});

		reg_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (iscompent())
					sendmeg();
//				Intent in = new Intent(Register.this,Renzheng.class);
//				editor.putBoolean("isFirstIn", false);
//				editor.putString("phone",phone_num);
//				editor.commit();
//				startActivity(in);
//				finish();
			}
		});
	}

	public boolean iscompent() {

		if(uname_edt.getText().equals("") || usr_name.getText().equals("") ||
				hospital.getText().equals("") || room.getText().equals("")
				|| uname_edt.getText() == null || usr_name.getText() == null
				||hospital.getText() == null || room.getText() == null){
			ShowToast("信息填写不完善！");
			return false;
		}else {
			String text = num+"";
			Log.d("11111", "iscompent: " + text);
			if(yanzheng.getText().toString().equals(text)){
				return true;
			}else {
			ShowToast("验证码填写错误");}
		}
		return false;
	}

	public void sendmeg(){

		String phone = uname_edt.getText().toString();

		String name = usr_name.getText().toString();
		String hos=  hospital.getText().toString();
		String ro =  room.getText().toString();

		String profess = role.getText().toString();

		Map map = new HashMap();
		map.put("name",name);
		map.put("phone",phone);
		map.put("hospital",hos);
		map.put("room",ro);
		map.put("profess",profess);

		JSONObject jsonObject = new JSONObject(map);

		Log.d("json", "sendmeg: " + jsonObject);
		JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jsonObject ,
				new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {
				try {
					String resp = (String) jsonObject.get("result");
					Log.d("11111111", "onResponse: " + resp);
					if(resp.equals("success")){
						Intent in = new Intent(Register.this,Renzheng.class);
						editor.putBoolean("isFirstIn", false);
						// 电话信息
						editor.putString("phone",phone_num);
						editor.commit();
						ShowToast("注册成功！");

//						//将用户电话写入表
//						User c_user = new User(uname_edt.getText().toString());
//						c_user.save();

						startActivity(in);
						finish();
					}else {
						ShowToast("该电话号码已注册！");
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ShowToast("注册失败！");
			}
		});
		// 添加进队列
		jor.setTag("wh");
		CustomApplcation.getRequestQuene().add(jor);
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
		}
		@Override
		public void onFinish() {//计时完毕时触发
			get_btn.setText("重新验证");
			get_btn.setClickable(true);
		}
		@Override
		public void onTick(long millisUntilFinished){//计时过程显示
			get_btn.setClickable(false);
			get_btn.setText(millisUntilFinished /1000+"秒");
		}
	}
}
