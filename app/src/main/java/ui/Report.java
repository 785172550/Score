package ui;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.sean.score.R;

import java.text.DecimalFormat;
import java.util.List;

import Base.BaseActivity;
import entity.ImagePath;
import entity.Patient;
import myview.HeaderLayout;
import myview.YesNoDialog;

/**
 * Created by Administrator on 2016/6/19.
 */
public class Report extends BaseActivity implements View.OnLongClickListener, View.OnClickListener{

	RelativeLayout /*report_name,report_gender,report_age,report_num,report_time,*/
			report_score1, report_score2,report_score3,report_score4,look,select_me;

	Patient patient,patient2;
	TextView r_name,r_gender,r_age,r_num,r_time,r_score1,
			res_pci,res_pci_m,res_CABG,res_CABG_m,r_score3,r_score4,r_recom,r_select,r_img;

	ImageView r_img_icon;

	//哪个页面
	int meg = 1;

	List<ImagePath> list;

	private static final String TAG = "Report";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_report);
		//获得病人信息
		getdata();
		//patient =  new Patient("朱晓伟","01",55,"男","2016-5-5");
		initview();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Bundle bundle = intent.getExtras();
		if (bundle.getSerializable("patient") != null)
			patient = (Patient) bundle.getSerializable("patient");
		Log.d(TAG, "getdata: "  + patient.getNum() + patient.getName());
		save_data();
		initview();
	}

	private void initview() {

		initTopBarForBothWithText("测评报告", null, new HeaderLayout.onRightImageButtonClickListener() {
			@Override
			public void onClick(View v) {
				save_data();
			}
		}, R.drawable.base_action_bar_back_bg_selector, new HeaderLayout.onLeftImageButtonClickListener() {
			@Override
			public void onClick() {
				finish();
			}
		});

		look = (RelativeLayout) findViewById(R.id.look);
		look.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Report.this,ImgShow.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("patient",patient);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		findviewid();
		setdata();
	}

	private void save_data() {
		List list = Patient.findWithQuery(Patient.class,
				"select * from patient where num = ?",patient.getNum());
		if(list.isEmpty()){
			patient.save();
			Log.d(TAG, "save_data: ---" + "num" + patient.getNum() + "name" + patient.getName() +
			 "img" + patient.getImg());
		}else {
			patient2 = (Patient) list.get(0);
			update_patient();
			Log.d(TAG, "save_data: ---" + "num" + patient.getNum() + "name" + patient.getName() +
					"img" + patient.getImg());
		}
		ShowToast("保存成功！");

	}

	private void update_patient() {
		patient2.setName(patient.getName());
		patient2.setGender(patient.getGender());
		patient2.setAge(patient.getAge());
		//patient2.setNum(patient.getNum());  NUM 一样的
		patient2.setDate(patient.getDate());
		patient2.setSyntax1(patient.getSyntax1());
		patient2.setPCI(patient.getPCI());
		patient2.setPCI_Death(patient.getPCI_Death());
		patient2.setCABG(patient.getCABG());
		patient2.setCABG_Death(patient.getCABG_Death());
		patient2.setSyntax3(patient.getSyntax3());
		patient2.setSyntax4(patient.getSyntax4());
		patient2.setRec_ope(patient.getRec_ope());
		patient2.setChoice(patient.getChoice());
		patient2.save();
	}

	private void findviewid() {
		r_name = (TextView) findViewById(R.id.r_name);
		r_gender = (TextView) findViewById(R.id.r_gender);
		r_age = (TextView) findViewById(R.id.r_age);
		r_num = (TextView) findViewById(R.id.r_num);
		r_time = (TextView) findViewById(R.id.r_time);
		r_score1 = (TextView) findViewById(R.id.r_score1);
		res_pci = (TextView) findViewById(R.id.res_pci);
		res_pci_m = (TextView) findViewById(R.id.res_pci_m);
		res_CABG = (TextView) findViewById(R.id.res_CABG);
		res_CABG_m = (TextView) findViewById(R.id.res_CABG_m);
		r_score3 = (TextView) findViewById(R.id.r_score3);
		r_score4 = (TextView) findViewById(R.id.r_score4);
		r_recom = (TextView) findViewById(R.id.r_recom);
		r_select = (TextView) findViewById(R.id.r_select);
		r_img = (TextView) findViewById(R.id.r_img);
//		report_name = (RelativeLayout) findViewById(R.id.report_name);
//		report_gender = (RelativeLayout) findViewById(R.id.report_gender);
//		report_age = (RelativeLayout) findViewById(R.id.report_age);
//		report_num = (RelativeLayout) findViewById(R.id.report_num);
//		report_time = (RelativeLayout) findViewById(R.id.report_time);
		report_score1 = (RelativeLayout) findViewById(R.id.report_score1);
		report_score2 = (RelativeLayout) findViewById(R.id.report_score2);
		report_score3 = (RelativeLayout) findViewById(R.id.report_score3);
		report_score4 = (RelativeLayout) findViewById(R.id.report_score4);

		select_me = (RelativeLayout) findViewById(R.id.select_me);

		r_img_icon = (ImageView) findViewById(R.id.r_img_icon);
//		report_name.setOnClickListener(this);
//		report_gender.setOnClickListener(this);
//		report_age.setOnClickListener(this);
//		report_num.setOnClickListener(this);
//		report_time.setOnClickListener(this);

		//云端数据为2 报告页面不能点击
		if(meg == 1){
			report_score1.setOnClickListener(this);
			report_score2.setOnClickListener(this);
			report_score3.setOnClickListener(this);
			report_score4.setOnClickListener(this);
			select_me.setOnClickListener(this);

			report_score1.setOnLongClickListener(this);
			report_score2.setOnLongClickListener(this);
			report_score3.setOnLongClickListener(this);
			report_score4.setOnLongClickListener(this);
			select_me.setOnLongClickListener(this);
		}
	}

	private void setdata() {
		list = patient.getImg();
		if(!list.isEmpty()){
			r_img.setText("点击查看");
			r_img_icon.setVisibility(View.VISIBLE);
		}
		if(patient == null)
			Log.d("=======", "setdata: ");
		if(patient.getName() != null) r_name.setText(patient.getName());
		if(patient.getGender() != null) r_gender.setText(patient.getGender());
		if(patient.getAge() != 0) r_age.setText(patient.getAge() + "");
		if(patient.getNum() != null) r_num.setText(patient.getNum());
		if(patient.getDate() != null) r_time.setText(patient.getDate());

		//保留一位小数
		DecimalFormat df = new DecimalFormat("#0.0");

		r_score1.setText(patient.getSyntax1() + "分");

		res_pci.setText(df.format(patient.getPCI()));
		res_pci_m.setText(df.format(patient.getPCI_Death()) + "%");

		res_CABG.setText(df.format(patient.getCABG()));
		res_CABG_m.setText(df.format(patient.getCABG_Death()) + "%");
		if(patient.getSyntax3() != null) r_score3.setText(patient.getSyntax3());

		//保留2位小数
		DecimalFormat df2 = new DecimalFormat("#0.00");
		r_score4.setText(df2.format(patient.getSyntax4()));
		if(patient.getRec_ope() != null)r_recom.setText(patient.getRec_ope());

		r_select.setText(patient.getChoice());
	}


	public void  getdata() {
		Bundle bundle = getIntent().getExtras();
		if (bundle.getSerializable("patient") != null)
			patient = (Patient) bundle.getSerializable("patient");
		meg = getIntent().getIntExtra("meg",1);
		Log.d(TAG, "getdata: "  + patient.getNum() + patient.getName());
	}




	@Override
	public void onClick(View v) {

		Class<?> cls = null;
		switch (v.getId()){
			case R.id.report_score1:
				cls = ScoreI2.class;
				Intent intent = new Intent(Report.this, cls);
				Bundle bundle = new Bundle();
				bundle.putSerializable("patient",patient);
				intent.putExtras(bundle);
				if(patient.getSyntax1() == 0)
					startActivity(intent);
//				finish();
				break;
			case R.id.report_score2:
				cls = ScoreII.class;
				Intent intent1 = new Intent(Report.this,cls);
				Bundle bundle1 = new Bundle();
				bundle1.putSerializable("patient",patient);
				intent1.putExtras(bundle1);
				if(patient.getPCI() == 0 && patient.getCABG() ==0)
					startActivity(intent1);
//				finish();
				break;
			case R.id.report_score3:
				cls = EuroScore.class;
				Intent intent2 = new Intent(Report.this,cls);
				Bundle bundle2 = new Bundle();
				bundle2.putSerializable("patient",patient);
				intent2.putExtras(bundle2);
				if(patient.getSyntax3() == null)
					startActivity(intent2);
//				finish();
				break;
			case R.id.report_score4:
				cls = NersScore.class;
				Intent intent3 = new Intent(Report.this,cls);
				Bundle bundle3 = new Bundle();
				bundle3.putSerializable("patient",patient);
				intent3.putExtras(bundle3);
				if(patient.getSyntax4() == 0)
				startActivity(intent3);
//				finish();
				break;
			case R.id.select_me:
				if(r_select.getText().toString().equals("无")){
					final String[] title = new String[]{"直接PCI","择期PCI","CABG","杂交","放弃手术干预"};
					ActionSheet.createBuilder(Report.this,getSupportFragmentManager())
							.setCancelButtonTitle("取消")
							.setOtherButtonTitles(title)
							.setCancelableOnTouchOutside(true)
							.setListener(new ActionSheet.ActionSheetListener() {
								@Override
								public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

								}

								@Override
								public void onOtherButtonClick(ActionSheet actionSheet, int index) {
									r_select.setText(title[index]);
									patient.setChoice(title[index]);
									save_data();
								}
							}).show();
				}
				break;
		}
	}


	public Dialog createdialog(String title, String message, DialogInterface.OnClickListener click1,
							   DialogInterface.OnClickListener click2){
//		AlertDialog.Builder builder = new AlertDialog.Builder(Report.this)
//				.setTitle(title)
//				.setMessage(message)
//				.setPositiveButton("确定",click1)
//			.setNegativeButton("取消",click2);

		YesNoDialog.Builder builder = new YesNoDialog.Builder(Report.this)
		.setMessage(message)
				.setPositiveButton("确定",click1)
				.setNegativeButton("取消",click2);

		Dialog dialog = builder.create();
		return dialog;
	}


	@Override
	public boolean onLongClick(View view) {

		final View v = view;
		Dialog dialog = createdialog("提示", "是否修改已经保存的数据",
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Class<?> cls = null;
				switch (v.getId()){
					case R.id.report_score1:
						cls = ScoreI2.class;
						Intent intent = new Intent(Report.this, cls);
						Bundle bundle = new Bundle();
						bundle.putSerializable("patient",patient);
						intent.putExtras(bundle);
						//if(patient.getSyntax1() == 0)
						startActivity(intent);
//				finish();
						break;
					case R.id.report_score2:
						cls = ScoreII.class;
						Intent intent1 = new Intent(Report.this,cls);
						Bundle bundle1 = new Bundle();
						bundle1.putSerializable("patient",patient);
						intent1.putExtras(bundle1);
						//if(patient.getPCI() == 0 && patient.getCABG() ==0)
						startActivity(intent1);
//				finish();
						break;
					case R.id.report_score3:
						cls = EuroScore.class;
						Intent intent2 = new Intent(Report.this,cls);
						Bundle bundle2 = new Bundle();
						bundle2.putSerializable("patient",patient);
						intent2.putExtras(bundle2);
						//if(patient.getSyntax3() == null)
						startActivity(intent2);
//				finish();
						break;
					case R.id.report_score4:
						cls = NersScore.class;
						Intent intent3 = new Intent(Report.this,cls);
						Bundle bundle3 = new Bundle();
						bundle3.putSerializable("patient",patient);
						intent3.putExtras(bundle3);
						//if(patient.getSyntax4() == 0)
						startActivity(intent3);
//				finish();
						break;
					case R.id.select_me:
						final String[] title = new String[]{"直接PCI","择期PCI","CABG","杂交","放弃手术干预"};
						ActionSheet.createBuilder(Report.this,getSupportFragmentManager())
								.setCancelButtonTitle("取消")
								.setOtherButtonTitles(title)
								.setCancelableOnTouchOutside(true)
								.setListener(new ActionSheet.ActionSheetListener() {
									@Override
									public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

									}

									@Override
									public void onOtherButtonClick(ActionSheet actionSheet, int index) {
										r_select.setText(title[index]);
										patient.setChoice(title[index]);
										save_data();
									}
								}).show();
						break;
				}
				dialogInterface.dismiss();
			}

		}, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});

		dialog.show();

		return true;
	}
}
