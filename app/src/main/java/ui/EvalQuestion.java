package ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sean.score.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Base.BaseActivity;
import entity.DiseasePosition;
import myview.CheckLayout;
import myview.FenZhi;
import myview.QuestionLayout;
import myview.SubQues;
import myview.SubQuesImg;

/**
 * Created by Administrator on 2016/6/20.
 *
 */
public class EvalQuestion extends BaseActivity implements FenZhi.Itemchecked,SubQuesImg.Itemchecked,SubQues.Itemchecked,QuestionLayout.Itemchecked {

	private static final String TAG = "EvalQuestion";

	Button next;

	DiseasePosition dp;
	LinearLayout lin;
	List<Integer> list;
	String[] is_not = new String[]{"否(No)", "是(Yes)"};
	double[] score1 = new double[20];
	double sum = 0;
	boolean to_kaikou = false;
	boolean to_kaikou2 = false;

	List<CheckBox> checkBoxes;
	double base = 0;
	double sco = 0;
	boolean isshowlen = false;
	CheckLayout checkLayout = null;

	ScrollView scrollView;
	private Handler mHandler = new Handler();

	TextView tv_score;

	int islast = -1;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.eval_ques);

		//patient = new Patient("朱晓伟", "01", 55, "男", "2016-5-5");
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		dp = (DiseasePosition) bundle.getSerializable("dp");
		islast = bundle.getInt("last");

		Log.d(TAG, "---- dp " + dp.getName() + "  " + dp.getNum());

		list = new ArrayList<>();
		checkBoxes = new ArrayList<>();

		Arrays.fill(score1, 0);
		initview();
	}

	private void initview() {

		initTopBarForOnlyTitle("Syntax I");
		scrollView = (ScrollView) findViewById(R.id.eval_scv1);

		tv_score = (TextView) findViewById(R.id.eval_score);

		lin = (LinearLayout) findViewById(R.id.liner_1);
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

//				if(islast == 1){
//					//计分
//					Intent intent = new Intent(EvalQuestion.this,CheckActivity.class);
//					cur_score(13);
//					intent.putExtra("score", sum);
//					startActivity(intent);
//					finish();
//				}else {
					//计分
					Intent intent = new Intent();
					cur_score(13);
					intent.putExtra("score", sum);
					setResult(1, intent);
					finish();
//				}
			}
		});

		Log.d(TAG, "initview: ---" + dp.getName());
		begin(dp);
	}

	private void begin(DiseasePosition dp) {

		boolean isleft = dp.isleft();
		int num = dp.getNum();
		//判断是否开口
		if (isleft) {
			if ((num == 0 || num == 3 || num == 4 || num == 11))
				to_kaikou = true;
		} else {
			if ((num == 0 || num == 8 || num == 9 || num == 16))
				to_kaikou = true;
		}


		if(num == 0||num == 4||num ==5 || num == 11)
			to_kaikou2 =true;


		//根据每个段设置基础分
		setvbase();
		//开始评分
		viewid.add(110);
		getquestion(false,viewid.get(0), "1.完全闭塞 Total occlusion (T.O.)", is_not);
	}

	private void setvbase() {
		if (dp.isleft()) {
			if (dp.getNum() < 3) {
				base = 0;
			} else if (dp.getNum() == 3) {
				base = 6;
			} else if (dp.getNum() == 4) {
				base = 3.5;
			} else if (dp.getNum() == 5) {
				base = 2.5;
			} else if (dp.getNum() > 5 && dp.getNum() < 9) {
				base = 1;
			} else if (dp.getNum() > 8 && dp.getNum() < 11) {
				base = 0.5;
			} else if (dp.getNum() == 11) {
				base = 2.5;
			} else if (dp.getNum() > 11 && dp.getNum() < 15) {
				base = 1;
			} else if (dp.getNum() == 15) {
				base = 1.5;
			} else {
				base = 1;
			}
		} else {
			if (dp.getNum() < 4) {
				base = 1;
			} else if (dp.getNum() < 8) {
				base = 0.5;
			} else if (dp.getNum() == 8) {
				base = 5;
			} else if (dp.getNum() == 9) {
				base = 3.5;
			} else if (dp.getNum() == 10) {
				base = 2.5;
			} else if (dp.getNum() < 14) {
				base = 1;
			} else if (dp.getNum() < 16) {
				base = 0.5;
			} else if (dp.getNum() == 16) {
				base = 1.5;
			} else if (dp.getNum() < 20) {
				base = 1;
			} else {
				base = 0.5;
			}
		}
	}

	String[] xianying;

	public String[] xianying(DiseasePosition dp) {
		if (dp.isleft()) {
			switch (dp.getLevel()) {
				case "1":
					xianying = new String[]{"无", "1", "2", "3"};
					break;
				case "2":
					xianying = new String[]{"无", "2", "3"};
					break;
				case "5":
					xianying = new String[]{"无", "5", "6", "7", "8", "11", "13", "14", "15"};
					break;
				case "6":
					xianying = new String[]{"无", "6", "7", "8"};
					break;
				case "7":
					xianying = new String[]{"无", "7", "8"};
					break;
				case "11":
					xianying = new String[]{"无", "11", "13", "14", "15"};
					break;
				case "13":
					xianying = new String[]{"无", "13", "14"};
					break;
			}
		} else {
			switch (dp.getLevel()) {
				case "1":
					xianying = new String[]{"无", "1", "2", "3", "4", "16"};
					break;
				case "2":
					xianying = new String[]{"无", "2", "3", "4", "16"};
					break;
				case "3":
					xianying = new String[]{"无", "3", "4", "16"};
					break;
				case "4":
					xianying = new String[]{"无","4","16"};
					break;
				case "5":
					xianying = new String[]{"无", "5", "6", "7", "8", "11", "13", "14"};
					break;
				case "6":
					xianying = new String[]{"无", "6", "7", "8"};
					break;
				case "7":
					xianying = new String[]{"无", "7", "8"};
					break;
				case "11":
					xianying = new String[]{"无", "11", "13", "14"};
					break;
				case "13":
					xianying = new String[]{"无", "13", "14"};
					break;
			}
		}
		return xianying;
	}

	public int get_xianying(int checkid){
		int s = 0;
		if(dp.isleft()){
			switch (dp.getLevel()){
				case "1":
					if(checkid == 0) s=3;
					else if(checkid == 3)s =1;
					break;
				case "2":
					if(checkid ==0)s = 1;
					break;
				case "5":
//					xianying = new String[]{"无", "闭塞段5", "6", "7", "8", "11", "13", "14", "15"};
					if(checkid == 0)s=4;
					else if(checkid == 3 ||checkid == 6)s=1;
					else if(checkid == 3||checkid == 7)s=2;
					else if(checkid == 8)s= 3;
					break;
				case "6":
					if(checkid == 0)s=2;
					else if(checkid == 3)s=1;
//					xianying = new String[]{"无", "闭塞段6", "7", "8"};
					break;
				case "7":
//					xianying = new String[]{"无", "闭塞段7", "8"};
					if(checkid == 0)s=1;
					break;
				case "11":
//					xianying = new String[]{"无", "闭塞段11", "13", "14", "15"};
					if(checkid == 0)s=3;
					else if(checkid == 3)s=1;
					else if(checkid==4) s =2;
					break;
				case "13":
//					xianying = new String[]{"无", "闭塞段13", "14"};
					if(checkid == 0)s=1;
					break;
			}
		}else {
			switch (dp.getLevel()) {
				case "1":
//					xianying = new String[]{"无", "闭塞段1", "2", "3", "4", "16"};
					if(checkid == 0)s=3;
					else if(checkid == 3)s=1;
					else if(checkid == 4)s=2;
					else if(checkid == 5)s=2;
					break;
				case "2":
//					xianying = new String[]{"无", "闭塞段2", "3", "4", "16"};
					if(checkid == 0)s=3;
					else if(checkid == 3)s=1;
					else if(checkid == 4)s=2;
					break;
				case "3":
//					xianying = new String[]{"无", "闭塞段3", "4", "16"};
					if(checkid == 0)s=2;
					else if(checkid == 3)s=1;
					break;
				case "4":
//					xianying = new String[]{"无","闭塞段4","16"};
					if(checkid == 0)s=1;
					break;
				case "5":
//					xianying = new String[]{"无", "闭塞段5", "6", "7", "8", "11", "13", "14"};
					if(checkid == 0)s=3;
					else if(checkid ==3||checkid == 6)s=1;
					else if(checkid == 4||checkid == 7)s=2;
					break;
				case "6":
//					xianying = new String[]{"无", "闭塞段6", "7", "8"};
					if(checkid == 0)s=2;
					else if(checkid == 3)s=1;
					break;
				case "7":
//					xianying = new String[]{"无", "闭塞段7", "8"};
					if(checkid == 0)s=1;
					break;
				case "11":
//					xianying = new String[]{"无", "闭塞段11", "13", "14"};
					if(checkid == 0)s=2;
					else if(checkid == 3)s=1;
					break;
				case "13":
//					xianying = new String[]{"无", "闭塞段13", "14"};
					if(checkid == 0)s=1;
					break;
			}
		}
		return s;
	}

	public boolean has_xian(DiseasePosition dp){
		if (dp.isleft()){
			if(dp.getLevel().equals("1") || dp.getLevel().equals("2")|| dp.getLevel().equals("5")||
					dp.getLevel().equals("6")|| dp.getLevel().equals("7")|| dp.getLevel().equals("11")||
					dp.getLevel().equals("13")){
				return true;
			}
		}else {
			if(dp.getLevel().equals("1") || dp.getLevel().equals("2")|| dp.getLevel().equals("3")||
					dp.getLevel().equals("4")||dp.getLevel().equals("5")|| dp.getLevel().equals("6")|| dp.getLevel().equals("7")||
					dp.getLevel().equals("11") || dp.getLevel().equals("13")){
				return true;}
		}
			return false;
	}

	public void getquestion(boolean ischild,int id, String title, String... answer) {

		if(ischild){
			SubQues questionLayout = new SubQues(EvalQuestion.this, title, this, answer);
			LinearLayout.LayoutParams layoutParams =
					new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
							, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMarginStart(50);
			questionLayout.setLayoutParams(layoutParams);
			questionLayout.setId(id);
			lin.addView(questionLayout);
		}else {
			QuestionLayout questionLayout = new QuestionLayout(EvalQuestion.this,
					title, this, answer);
			RelativeLayout.LayoutParams layoutParams =
					new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
							, RelativeLayout.LayoutParams.WRAP_CONTENT);
			questionLayout.setLayoutParams(layoutParams);
			questionLayout.setId(id);
			lin.addView(questionLayout);
		}
	}

	//删除当前问题下面所以问题
	public void removequestion(int cid) {

		List list = new ArrayList();
		for (int id : viewid) {
			if (id > cid) {
				//removequestion(id);
				Log.d(TAG, "removequestion: id " + id);
				lin.removeView(findViewById(id));
				list.add(id);
			}
		}
		// 不能在遍历的时候修改  会报多线程错误
		viewid.removeAll(list);
		if (checkLayout != null) {
			lin.removeView(checkLayout);
		}
		for(CheckBox checkBox:checkBoxes){
			checkBox.setChecked(false);
		}
		next.setVisibility(View.INVISIBLE);
	}

	int current = 110;
	List<Integer> viewid = new ArrayList();


	@Override
	public void checked(View view, int checkedId) {

		switch (view.getId()) {
			case 110: // 闭塞
				//是否选项 1否 2是
//				curQuestion(viewid, checkedId, 110, 116, 111, "三叉病变", "闭塞是否大于三个月",
//						new String[]{"否", "是", "未知"});
				if (current != 110) {
					//清除下面布局
					removequestion(110);
					current = 110;
					//清除评分
					romve_score(0);
					if (checkedId == 0) {
						sco = base * 2;
						oncheck(false,116, "2.三分叉病变 Trifurcation", is_not);
						isshowlen = true;
					} else {
						sco = base * 5;
						oncheck(true,111, "I.完全闭塞的时间（>3个月）" +
								"\nis Age of T.O. > 3 months?", new String[]{"否(No)", "是(Yes)", "未知(Unknown)"});
						isshowlen = false;
					}
				} else if (checkedId == 0) {
					sco = base * 2;
					oncheck(false,116, "2.三分叉病变 Trifurcation", is_not);
					isshowlen = true;
				} else {
					sco = base * 5;
					oncheck(true,111, "I.完全闭塞的时间（>3个月）" +
							"\nis Age of T.O. > 3 months?", new String[]{"否", "是", "未知"});
					isshowlen = false;
				}
				go_bottom();
				cur_score(0);
				break;
			case 111: // 闭塞时间
				if (current != 111) {
					//清除下面布局
					removequestion(111);
					current = 111;
					// 再选判断选中的
					//清除评分
					romve_score(0);
					if (checkedId == 0) {
						//oncheck(true,112, "II.钝圆残端 Indicate the first segment number of the T.O.", is_not);
						oncheck1(true,112, "II.钝圆残端 " +
								"\nIndicate the first segment number of the T.O.",
								"Blunt",R.drawable.blunt,is_not);
						score1[0] = 0;
					} else {
						//oncheck(true,112, "II.钝圆残端 Indicate the first segment number of the T.O.", is_not);
						oncheck1(true,112, "II.钝圆残端 " +
								"\nIndicate the first segment number of the T.O.",
								"Blunt",R.drawable.blunt,is_not);
						score1[0] = 1;
					}
				} else if (checkedId == 0) {
					//oncheck(true,112, "II.钝圆残端 Indicate the first segment number of the T.O.", is_not);
					oncheck1(true,112, "II.钝圆残端 " +
							"\nIndicate the first segment number of the T.O.",
							"Blunt",R.drawable.blunt,is_not);
					score1[0] = 0;
				} else {
					//oncheck(true,112, "II.钝圆残端 Indicate the first segment number of the T.O.", is_not);
					oncheck1(true,112, "II.钝圆残端 " +
							"\nIndicate the first segment number of the T.O.",
							"Blunt",R.drawable.blunt,is_not);
					score1[0] = 1;
				}
				go_bottom();
				cur_score(1);
				break;
			case 112:// 钝型端
				if (current != 112) {
					//清除下面布局
					removequestion(112);
					current = 112;
					//清除评分
					romve_score(1);
					// 再选判断选中的
					if (checkedId == 0) {
						//oncheck(true,113, "III.桥侧支 Bridging?", is_not);
						oncheck1(true,113, "III.桥侧支 " +
								"\nBridging?",
								"Bridging",R.drawable.brigding,is_not);
						score1[1] = 0;
					} else {
//						oncheck(true,113, "III.桥侧支 Bridging?", is_not);
						oncheck1(true,113, "III.桥侧支 " +
								"\nBridging?",
								"Bridging",R.drawable.brigding,is_not);
						score1[1] = 1;
					}
				} else if (checkedId == 0) {
//					oncheck(true ,113, "III.桥侧支 Bridging?", is_not);
					oncheck1(true,113, "III.桥侧支 " +
							"\nBridging?",
							"Bridging",R.drawable.brigding,is_not);
					score1[1] = 0;
				} else {
//					oncheck(true ,113, "III.桥侧支 Bridging?", is_not);
					oncheck1(true,113, "III.桥侧支 " +
							"\nBridging?",
							"Bridging",R.drawable.brigding,is_not);
					score1[1] = 1;
				}
				go_bottom();
				cur_score(2);
				break;

			case 113: // 桥侧支  ------------------> 判断显影

				if(has_xian(dp)){
					if (current != 113) {
						//清除下面布局
						removequestion(113);
						current = 113;
						//清除评分
						romve_score(2);
						// 再选判断选中的
						if (checkedId == 0) {
							score1[2] = 0;
							oncheck(true ,114, "IV.闭塞及已远首个显影节段（向前或逆向造影剂显影）" +
									"\nSpecify the first segment number beyond the total" +
									" occlusion that is visualized by antegrade or retrograde contrast.",
									xianying(dp));
						} else {
							oncheck(true ,114, "IV.闭塞及已远首个显影节段（向前或逆向造影剂显影）" +
											"\nSpecify the first segment number beyond the total" +
											" occlusion that is visualized by antegrade or retrograde contrast.",
									xianying(dp));
							score1[2] = 1;
						}
					} else if (checkedId == 0) {
						score1[2] = 0;
						oncheck(true,114, "IV.闭塞及已远首个显影节段（向前或逆向造影剂显影）" +
										"\nSpecify the first segment number beyond the total" +
										" occlusion that is visualized by antegrade or retrograde contrast.",
								xianying(dp));
					} else {
						oncheck(true,114, "IV.闭塞及已远首个显影节段（向前或逆向造影剂显影）" +
										"\nSpecify the first segment number beyond the total" +
										" occlusion that is visualized by antegrade or retrograde contrast.",
								xianying(dp));
						score1[2] = 1;
					}
				}else {// 没有显影
					if (current != 113) {
						//清除下面布局
						removequestion(113);
						current = 113;
						// 再选判断选中的
						if (checkedId == 0) {
							score1[2] = 0;
							oncheck2(true ,115, "V.涉及分支 \nIs there a sidebranch at the origin of the occlusion?",
									new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
									new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});
						} else {
							oncheck2(true ,115, "V.涉及分支 \nIs there a sidebranch at the origin of the occlusion?",
									new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
									new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});
							score1[2] = 1;
						}
					}else if (checkedId == 0) {
						score1[2] = 0;
						oncheck2(true ,115, "V.涉及分支 \nIs there a sidebranch at the origin of the occlusion?",
								new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
								new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});
					} else {
						oncheck2(true ,115, "V.涉及分支 \nIs there a sidebranch at the origin of the occlusion?",
								new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
								new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});
						score1[2] = 1;
					}

				}
				go_bottom();
				cur_score(3);
				break;
			case 114: //闭塞及以远最早显影
				if (current != 114) {
					//清除下面布局
					removequestion(114);
					current = 114;
					//清除评分
					romve_score(3);

					// 再选判断选中的
					if (checkedId == 0) {
//						oncheck(true,115, "V.涉及分支 Is there a sidebranch at the origin of the occlusion?",
//								new String[]{"无", "所以边支<1.5mm", "所以边支>1.5mm", "存在<1.5mm和>1.5mm"});
						oncheck2(true ,115, "V.涉及分支 \nIs there a sidebranch at the origin of the occlusion?",
								new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
								new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});

					} else if (checkedId == 3) {
						oncheck2(true ,115, "V.涉及分支 \nIs there a sidebranch at the origin of the occlusion?",
								new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
								new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});
					} else {

						oncheck2(true ,115, "V.涉及分支\nIs there a sidebranch at the origin of the occlusion?",
								new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
								new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});
					}
				} else if (checkedId == 0) {

					oncheck2(true ,115, "V.涉及分支 \nIs there a sidebranch at the origin of the occlusion?",
							new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
							new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});

				} else if (checkedId == 3) {

					oncheck2(true ,115, "V.涉及分支 \nIs there a sidebranch at the origin of the occlusion?",
							new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
							new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});
				} else {
					oncheck2(true ,115, "V.涉及分支 \nIs there a sidebranch at the origin of the occlusion?",
							new int[]{R.drawable.fenzhi1,R.drawable.fenzhi2,R.drawable.fenzhi21,R.drawable.fenzhi3},
							new String[]{"否", "所有分支 <1.5mm", "所有分支 >= 1.5mm", "分支 <1.5mm 和 >= 1.5mm 同时存在"});
				}
				score1[3] = get_xianying(checkedId);
				go_bottom();
				cur_score(4);
				break;

			case 115: //闭塞边支
				if (current != 115) {
					//清除下面布局
					removequestion(115);
					current = 115;
					//清除评分
					romve_score(4);
					// 再选判断选中的
					if (checkedId == 0 || checkedId == 1) {
						if (checkedId == 1) score1[4] = 1;
						if (checkedId == 0) score1[4] = 0;
						if (to_kaikou)
							oncheck(false,1111, "4.开口病变 Aorto Ostial lesion", is_not);
						else oncheck(false,1112, "5.严重扭曲 Severe Tortuosity", is_not);
					} else {
						score1[4] = 1;
						oncheck(false ,116, "2.三分叉病变 Trifurcation", is_not);
					}
				} else if (checkedId == 0 || checkedId == 1) {
					if (checkedId == 1) score1[4] = 1;
					if (checkedId == 0) score1[4] = 0;
					if (to_kaikou)
						oncheck(false ,1111, "4.开口病变 Aorto Ostial lesion", is_not);
					else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
				} else {
					score1[4] = 1;
					oncheck(false ,116, "2.三分叉病变 Trifurcation", is_not);
				}
				go_bottom();
				cur_score(5);
				break;
			case 116:// 三叉病变
				if (current != 116) {
					//清除下面布局
					removequestion(116);
					current = 116;
					//清除评分
					romve_score(4);
					// 再选判断选中的
					if (checkedId == 0) {
						oncheck(false ,118, "3.分叉病变 Bifurcation", is_not);
					} else {
						oncheck(true ,117, "涉及节段", new String[]{"1节段", "2节段", "3节段", "4节段"});
					}
				} else if (checkedId == 0) {
					oncheck(false ,118, "3.分叉病变 Bifurcation", is_not);
				} else {
					oncheck(true,117, "涉及节段", new String[]{"1节段", "2节段", "3节段", "4节段"});
				}
				go_bottom();
				cur_score(5);//++++
				break;
			case 117: // 节段
				if (current != 117) {
					//清除下面布局
					removequestion(117);
					current = 117;
					//清除评分
					romve_score(5);
					// 再选判断选中的
					if (checkedId == 0) {
						score1[5] = 3;
						if (to_kaikou)
							oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
						else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
					} else if (checkedId == 1) {
						score1[5] = 4;
						if (to_kaikou)
							oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
						else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
					} else if (checkedId == 2) {
						score1[5] = 5;
						if (to_kaikou)
							oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
						else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
					} else {
						score1[5] = 6;
						if (to_kaikou)
							oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
						else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
					}
				} else if (checkedId == 0) {
					score1[5] = 3;
					if (to_kaikou)
						oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
					else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
				} else if (checkedId == 1) {
					score1[5] = 4;
					if (to_kaikou)
						oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
					else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
				} else if (checkedId == 2) {
					score1[5] = 5;
					if (to_kaikou)
						oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
					else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
				} else {
					score1[5] = 6;
					if (to_kaikou)
						oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
					else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
				}
				go_bottom();
				cur_score(6);
				break;
			case 118://分叉病变
				if (current != 118) {
					//清除下面布局
					removequestion(118);
					current = 118;
					//清除评分
					romve_score(5);
					// 再选判断选中的
					if (checkedId == 0) {
						if(to_kaikou == true)
							oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
						else oncheck(false,1112,"5.严重扭曲 Severe Tortuosity",is_not);
					} else {
//						oncheck(true ,119, "media 分型",
//								new String[]{"media1,0,0", "media0,1,0", "media1,1,0", "media1,1,1"
//										, "media0,0,1", "media1,0,1", "media0,1,1"});

						oncheck2(true ,119, "media 分型",new int[]{R.drawable.medina100,R.drawable.medina010,R.drawable.medina110,
										R.drawable.medina111,R.drawable.medina001,R.drawable.medina101,
										R.drawable.medina011,},
								new String[]{"media1,0,0", "media0,1,0", "media1,1,0", "media1,1,1"
								, "media0,0,1", "media1,0,1", "media0,1,1"});
					}
				} else if (checkedId == 0) {
					if(to_kaikou == true)
						oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
					else oncheck(false,1112,"5.严重扭曲 Severe Tortuosity",is_not);
				} else {
					oncheck2(true ,119, "media 分型",new int[]{R.drawable.medina100,R.drawable.medina010,R.drawable.medina110,
									R.drawable.medina111,R.drawable.medina001,R.drawable.medina101,
									R.drawable.medina011,},
							new String[]{"media1,0,0", "media0,1,0", "media1,1,0", "media1,1,1"
									, "media0,0,1", "media1,0,1", "media0,1,1"});
				}
				go_bottom();
				cur_score(6);//++++
				break;
			case 119://media 分型
				if (current != 119) {
					//清除下面布局
					removequestion(119);
					current = 119;
					//清除评分
					romve_score(6);
					// 再选判断选中的
					if (checkedId < 3) {
						score1[6] = 1;
						oncheck1(true ,1110, "远端主支血管和分支之间的夹角<70º"+
								"\nBifurcation angulation (between " +
								"distal main vessel and side branch) < 70％","",R.drawable.b_a70_2, is_not);
					} else {
						score1[6] = 2;
						oncheck1(true ,1110, "远端主支血管和分支之间的夹角<70º"+
								" \nBifurcation angulation (between " +
								"distal main vessel and side branch) < 70％","",R.drawable.b_a70_2, is_not);
					}
				} else if (checkedId < 3) {
					score1[6] = 1;
					oncheck1(true ,1110, "远端主支血管和分支之间的夹角<70º"+
							" \nBifurcation angulation (between " +
							"distal main vessel and side branch) < 70％","",R.drawable.b_a70_2, is_not);
				} else {
					score1[6] = 2;
//					oncheck(true ,1110, "远端主支血管和分支之间的夹角<70º " +
//							"Bifurcation angulation (between " +
//							"distal main vessel and side branch) < 70％", is_not);
					oncheck1(true ,1110, "远端主支血管和分支之间的夹角<70º"+
							" \nBifurcation angulation (between " +
							"distal main vessel and side branch) < 70％","",R.drawable.b_a70_2, is_not);
				}
				go_bottom();
				cur_score(7);
				break;

			case 1110: // 角度是否小于70
				if (current != 1110) {
					//清除下面布局
					removequestion(1110);
					current = 1110;
					//清除评分
					romve_score(7);
					if (checkedId == 0) {
						score1[7] = 0;
						if (to_kaikou)
							oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
						else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
					} else {
						score1[7] = 1;
						if (to_kaikou) oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
						else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
					}
				} else if (checkedId == 0) {
					score1[7] = 0;
					if (to_kaikou)
						oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
					else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
				} else {
					score1[7] = 1;
					if (to_kaikou)
						oncheck(false ,1111, "4.主动脉开口病变 Aorto Ostial lesion", is_not);
					else oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
				}
				go_bottom();
				cur_score(8);
				break;
			case 1111: //开口病变
				if (current != 1111) {
					//清除下面布局
					removequestion(1111);
					current = 1111;
					//清除评分
					romve_score(8);
					if (checkedId == 0) {
						score1[8] = 0;
						oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
					} else {
						score1[8] = 1;
						oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
					}
				} else if (checkedId == 0) {
					score1[8] = 0;
					oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
				} else {
					score1[8] = 1;
					oncheck(false ,1112, "5.严重扭曲 Severe Tortuosity", is_not);
				}
				go_bottom();
				cur_score(9);
				break;
			case 1112: //严重扭曲
				if (current != 1112) {
					//清除下面布局
					removequestion(1112);
					current = 1112;
					//清除评分
					romve_score(9);
					if (checkedId == 0) {
						score1[9] = 0;
						if(isshowlen == true)
							oncheck(false ,1113, "6.长度>20mm Length >20 mm", is_not);
						else oncheck(false ,1114, "7.严重钙化 Heavy calcification", is_not);
					} else {
						score1[9] = 2;
						if(isshowlen == true)
							oncheck(false ,1113, "6.长度>20mm Length >20 mm", is_not);
						else oncheck(false ,1114, "7.严重钙化 Heavy calcification", is_not);
					}
				} else if (checkedId == 0) {
					score1[9] = 0;
					if(isshowlen == true)
						oncheck(false ,1113, "6.长度>20mm Length >20 mm", is_not);
					else oncheck(false ,1114, "7.严重钙化 Heavy calcification", is_not);
				} else {
					score1[9] = 2;
					if(isshowlen == true)
						oncheck(false ,1113, "6.长度>20mm Length >20 mm", is_not);
					else oncheck(false ,1114, "7.严重钙化 Heavy calcification", is_not);
				}
				go_bottom();
				cur_score(10);
				break;
			case 1113: //长度>20mm
				if (current != 1113) {
					//清除下面布局
					removequestion(1113);
					current = 1113;
					//清除评分
					romve_score(10);
					if (checkedId == 0) {
						score1[10] = 0;
						oncheck(false ,1114, "7.严重钙化 Heavy calcification", is_not);
					} else {
						score1[10] = 1;
						oncheck(false ,1114, "7.严重钙化 Heavy calcification", is_not);
					}
				} else if (checkedId == 0) {
					score1[10] = 0;
					oncheck(false ,1114, "7.严重钙化 Heavy calcification", is_not);
				} else {
					score1[10] = 1;
					oncheck(false ,1114, "7.严重钙化 Heavy calcification", is_not);
				}
				go_bottom();
				cur_score(11);
				break;
			case 1114: // 钙化
				if (current != 1114) {
					//清除下面布局
					removequestion(1114);
					current = 1114;
					//清除评分
					romve_score(11);
					if (checkedId == 0) {
						score1[11] = 0;
						oncheck(false ,1115, "8.血栓 Thrombus", is_not);
					} else {
						score1[11] = 2;
						oncheck(false ,1115, "8.血栓 Thrombus", is_not);
					}
				} else if (checkedId == 0) {
					score1[11] = 0;
					oncheck(false ,1115, "8.血栓 Thrombus", is_not);
				} else {
					score1[11] = 2;
					oncheck(false ,1115, "8.血栓 Thrombus", is_not);
				}
				go_bottom();
				cur_score(12);
				break;
			case 1115: //血栓
				if (current != 1115) {
					//清除下面布局
					removequestion(1115);
					current = 1115;
					//清除评分
					romve_score(12);
					if (checkedId == 0) {
						score1[12] = 0;
						//oncheck(false ,1116, "弥漫/小血管病变", is_not);
					} else {
						score1[12] = 1;
						//oncheck(false ,1116, "弥漫/小血管病变", is_not);
					}
				} else if (checkedId == 0) {
					score1[12] = 0;
					//oncheck(false ,1116, "弥漫/小血管病变", is_not);
				} else {
					score1[12] = 1;
					//oncheck(false ,1116, "弥漫/小血管病变", is_not);
				}
				go_bottom();
				next.setVisibility(View.VISIBLE);
				cur_score(13);
				break;
			case 1116: //弥漫/小血管病变

				if (checkedId != 0 && islast == 1)
					//选择病变位置
					getcheckLayout(dp);
				else {
					//清除下面布局
					removequestion(1116);
				}
				next.setVisibility(View.VISIBLE);
				go_bottom();
//				cur_score(13);
				break;
		}
	}

	public void getcheckLayout(DiseasePosition dp) {

		String[] name = null;
		if (dp.isleft()) {
			if (dp.getNum() < 3) {
				name = new String[]{"1 右冠近端", "2 右冠中段", "3右冠远段"};
			} else if (dp.getNum() == 3) {
				name = new String[]{"5 左主干", "6 前降支近段", "7 前降支中段", "8 前降支心尖段",
						"9 第一对角支", "9a 第一对角支a", "10第二对角支", "10a第二对角支a"
						, "11 回旋支近段", "12中间支", "12a 第一钝缘支"
						, "12b 第二钝缘支", "13 回旋支远段", "14 左后侧支", "14a 左后侧支a",
						"14b 左后侧支b", "15 回旋支-后降支"};
			} else if (dp.getNum() < 11) {
				name = new String[]{
						"6 前降支近段", "7 前降支中段", "8 前降支心尖段",
						"9 第一对角支", "9a 第一对角支a", "10第二对角支", "10a第二对角支a"
				};
			} else {
				name = new String[]{
						"11 回旋支近段", "12中间支", "12a 第一钝缘支"
						, "12b 第二钝缘支", "13 回旋支远段", "14 左后侧支", "14a 左后侧支a",
						"14b 左后侧支b", "15 回旋支-后降支"
				};
			}
		}else {
			if(dp.getNum() <8){
				name = new String[]{"1 右冠近端", "2 右冠中段", "3右冠远段",
						"4 右冠后降支", "16 右冠后侧支",
						"16a 右冠后侧支a", "16b 右冠后侧支b",
						"16c 右冠后侧支c"};
			}else if(dp.getNum() == 8){
				name = new String[]{"5 左主干", "6 前降支近段", "7 前降支中段", "8 前降支心尖段",
						"9 第一对角支","9a 第一对角支a", "10第二对角支", "10a第二对角支a"
						, "11 回旋支近段", "12中间支", "12a 第一钝缘支"
						, "12b 第二钝缘支", "13 回旋支远段", "14 左后侧支", "14a 左后侧支a",
						"14b 左后侧支b"
				};
			}else if(dp.getNum() < 16){
				name = new String[]{"6 前降支近段", "7 前降支中段", "8 前降支心尖段",
						"9 第一对角支","9a 第一对角支a", "10第二对角支", "10a第二对角支a"};
			}else {
				name = new String[]{"11 回旋支近段", "12中间支", "12a 第一钝缘支"
						, "12b 第二钝缘支", "13 回旋支远段", "14 左后侧支", "14a 左后侧支a",
						"14b 左后侧支b"};
			}
		}

		checkLayout = new CheckLayout(EvalQuestion.this, checkBoxes, name);
		for (CheckBox checkBox : checkBoxes) {
			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					cur_score(13);
				}
			});
		}
		lin.addView(checkLayout);

	}


//	public void curQuestion(List viewid, int checkedId, int cid, int nextid1,
//							int nextid2, String title1, String title2, String... ques) {
//		// 当前问题下面还有问题
//		if (current != cid) {
//			//清除下面布局
//			removequestion(cid);
//			current = cid;
//
//			// 再选判断选中的
//			if (checkedId == 0) {
//				oncheck(nextid1, title1, is_not);
//			} else if (checkedId == 1) {
//				oncheck(nextid2, title2, ques);
//			}
//		} else if (checkedId == 0) {
//			oncheck(nextid1, title1, is_not);
//		} else if (checkedId == 1) {
//			oncheck(nextid2, title2, ques);
//		}
//	}


	public void oncheck(boolean ischild,int id, String title, String... qeus) {
		viewid.add(id);
		getquestion(ischild,id, title, qeus);
		current = id;
		Log.d(TAG, "oncheck: current id----" + id);
	}

	public void oncheck1(boolean ischild,int id, String title,String imgtitle,int rid, String... qeus) {
		viewid.add(id);
		//getquestion(ischild,id, title, qeus);
		SubQuesImg questionLayout = new SubQuesImg(EvalQuestion.this,title,imgtitle,rid,this,qeus);
		LinearLayout.LayoutParams layoutParams =
				new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
						, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMarginStart(50);
		questionLayout.setLayoutParams(layoutParams);
		questionLayout.setId(id);
		lin.addView(questionLayout);
		current = id;
		Log.d(TAG, "oncheck: current id----" + id);
	}

	 public void oncheck2(boolean ischild,int id, String title,int[] rid, String... qeus) {

		viewid.add(id);
		//getquestion(ischild,id, title, qeus);
		FenZhi questionLayout = new FenZhi(EvalQuestion.this,title,rid,this,qeus);
		LinearLayout.LayoutParams layoutParams =
				new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
						, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMarginStart(50);
		questionLayout.setLayoutParams(layoutParams);
		questionLayout.setId(id);
		lin.addView(questionLayout);
		current = id;
		Log.d(TAG, "oncheck: current id----" + id);
	}


	/*public void over() {
		sum = 0;
		for (int i = 0; i < score1.length; i++) {
			sum += score1[i];
		}
		if (!checkBoxes.isEmpty()) {
			for (CheckBox checkBox : checkBoxes) {
				if (checkBox.isChecked()) {
					sum++;
				}
			}
		}
		sum += sco;
	}*/

	public void go_bottom() {
		//这里必须要给一个延迟，如果不加延迟则没有效果。我现在还没想明白为什么
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				//将ScrollView滚动到底
				scrollView.fullScroll(View.FOCUS_DOWN);
			}
		}, 100);

	}

	public void cur_score(int len){

		sum = sco;
		for (int i = 0; i < len; i++) {
			sum += score1[i];
		}
		if (!checkBoxes.isEmpty()) {
			for (CheckBox checkBox : checkBoxes) {
				if (checkBox.isChecked()) {
					sum++;
				}
			}
		}

		tv_score.setText("score: " + sum);
	}

	public void romve_score(int i){
		for(int j = 0;j<score1.length;j++){
			if(j>i) score1[j] = 0;
		}
	}

	@Override
	public void onBackPressed() {
		ShowToast("没有评测完成不能退出！");
	}
}
