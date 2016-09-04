package fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sean.score.R;

import java.util.ArrayList;

import entity.DiseasePosition;
import entity.Patient;
import myview.MatrixImageView;
import ui.Eval;

/**
 * Created by Administrator on 2016/7/2.
 */
public class PositionRight extends Fragment{

	private View mView;
	private Context mcontext;
	Patient patient;
	ArrayList checklist;

	DiseasePosition[] dp2 = new DiseasePosition[]{
			new DiseasePosition(0,false,"RAC proximal","1 右冠近端","1","RAC"),
			new DiseasePosition(1,false,"RAC mid","2 右冠中段","2","RAC"),
			new DiseasePosition(2,false,"RAC distal","3 右冠远段","3","RAC"),
			new DiseasePosition(3,false,"Posterior descending","4 右冠后降支","4","RAC"),
			new DiseasePosition(4,false,"Posterolateral from RCA","16 右冠后侧支","16","RAC"),
			new DiseasePosition(5,false,"Posterolateral from RCA","16a 右冠后侧支a","16a","RAC"),
			new DiseasePosition(6,false,"Posterolateral from RCA","16b 右冠后侧支b","16b","RAC"),
			new DiseasePosition(7,false,"Posterolateral from RCA","16c 右冠后侧支c","16c","RAC"),
			new DiseasePosition(8,false,"Left main","5 左主干", "5","LM"),
			new DiseasePosition(9,false,"LAD proximal","6 前降支近段","6","LAD"),
			new DiseasePosition(10,false,"LAD mid","7 前降支中段","7","LAD"),
			new DiseasePosition(11,false,"LAD apical","8 前降支心尖段","8","LAD"),
			new DiseasePosition(12,false,"First diagonal","9 第一对角支","9","LAD"),
			new DiseasePosition(13,false,"Add. first diagonal","9a 第一对角支a", "9a","LAD"),
			new DiseasePosition(14,false,"Second diagonal","10 第二对角支","10","LAD"),
			new DiseasePosition(15,false,"Add. second diagonal","10a 第二对角支a","10a","LAD"),
			new DiseasePosition(16,false,"Proximal circumflex","11 回旋支近段","11","LCX"),
			new DiseasePosition(17,false,"Intermediate/anterolateral","12 中间支","12","LCX"),
			new DiseasePosition(18,false,"Obtuse marginal","12a 第一钝缘支","12a","LCX"),
			new DiseasePosition(19,false,"Obtuse marginal","12b 第二钝缘支","12b","LCX"),
			new DiseasePosition(20,false,"Distal circumflex","13 回旋支远段","13","LCX"),
			new DiseasePosition(21,false,"Left posterolateral","14 左后侧支","14","LCX"),
			new DiseasePosition(22,false,"Left posterolateral","14a 左后侧支a","14a","LCX"),
			new DiseasePosition(23,false,"Left posterolateral","14b 左后侧支b","14b","LCX"),
	};

	public PositionRight() {
	}

	@SuppressLint("ValidFragment")
	public PositionRight(Context context, Patient patient) {
		mcontext = context;
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

		mView  = inflater.inflate(R.layout.position_img2, container, false);

		MatrixImageView matrixImageView = (MatrixImageView) mView.findViewById(R.id.img2);
//		BitmapDrawable mBitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.rights);
		BitmapDrawable mBitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.rights3);
		Bitmap bitmap = mBitmap.getBitmap();
		matrixImageView.setImageBitmap(bitmap);
		checklist = new ArrayList();
		//初始化病变图片
		matrixImageView.initd(false,dp2,checklist);
	}

	public void start() {
		if(iscompelet()){
			to_next();
		}else {
			Toast.makeText(mcontext,"请把信息填写完整！",Toast.LENGTH_SHORT);
		}
	}

	private void to_next() {

		Intent intent = new Intent(mcontext, Eval.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("list",checklist);
		bundle.putSerializable("patient",patient);
		intent.putExtras(bundle);
		mcontext.startActivity(intent);
		//getActivity().finish();
	}

	public boolean iscompelet() {
//		for (Map map : list) {
//			if(map.get(2) == true){
//				checklist.add(map);
//			}
//		}
		if(checklist.isEmpty())
			return false;
		else
			return true;
	}
}
