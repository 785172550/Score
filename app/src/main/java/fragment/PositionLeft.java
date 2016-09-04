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
public class PositionLeft extends Fragment {

	private View mView;
	private Context mcontext;
	Patient patient;
	ArrayList checklist;

	DiseasePosition[] dp = new DiseasePosition[]{
			new DiseasePosition(0,true,"RAC proximal","1 右冠近端","1","RAC"),
			new DiseasePosition(1,true,"RAC mid","2 右冠中段","2","RAC"),
			new DiseasePosition(2,true,"RAC distal","3 右冠远段","3","RAC"),
			new DiseasePosition(3,true,"Left main","5 左主干","5","LM"),
			new DiseasePosition(4,true,"LAD proximal","6 前降支近段","6","LAD"),
			new DiseasePosition(5,true,"LAD mid","7 前降支中段","7","LAD"),
			new DiseasePosition(6,true,"LAD apical","8 前降支心尖段","8","LAD"),
			new DiseasePosition(7,true,"First diagonal","9 第一对角支","9","LAD"),
			new DiseasePosition(8,true,"Add first diagonal","9a 第一对角支a","9a","LAD"),
			new DiseasePosition(9,true,"Second diagonal","10 第二对角支","10","LAD"),
			new DiseasePosition(10,true,"Add second diagonal","10a 第二对角支a","10a","LAD"),
			new DiseasePosition(11,true,"Proximal circumflex","11 回旋支近段","11","LCX"),
			new DiseasePosition(12,true,"Intermediate/anterolateral","12 中间支","12","LCX"),
			new DiseasePosition(13,true,"Obtuse marginal","12a 第一钝缘支","12a","LCX"),
			new DiseasePosition(14,true,"Obtuse marginal","12b 第二钝缘支","12b","LCX"),
			new DiseasePosition(15,true,"Distal circumflex","13 回旋支远段","13","LCX"),
			new DiseasePosition(16,true,"Left posterolateral","14 左后侧支","14","LCX"),
			new DiseasePosition(17,true,"Left posterolateral","14a 左后侧支a","14a","LCX"),
			new DiseasePosition(18,true,"Left posterolateral","14b 左后侧支b","14b","LCX"),
			new DiseasePosition(19,true,"Posterior descending","15 回旋支-后降支","15","LCX"),
	};


	public PositionLeft() {
	}

	@SuppressLint("ValidFragment")
	public PositionLeft(Context context, Patient patient) {
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

		mView  = inflater.inflate(R.layout.position_img, container, false);

		checklist = new ArrayList();
//		BitmapDrawable mBitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.lefts);
		BitmapDrawable mBitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.lefts3);
		Bitmap bitmap = mBitmap.getBitmap();
		MatrixImageView matrixImageView = (MatrixImageView) mView.findViewById(R.id.img);
		matrixImageView.initd(true,dp,checklist);
		matrixImageView.setImageBitmap(bitmap);
		//初始化病变图片
	}

	public void start(){
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
		getActivity().finish();
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
