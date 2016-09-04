package fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sean.score.R;

import java.text.DecimalFormat;

import entity.Patient;

/**
 * Created by Administrator on 2016/6/17.
 */
public class Score4Frag extends Fragment {

	private View mView;
	private Context mcontext;
	Patient patient;
	TextView NersScore_res;

	public static Fragment newInstance(Patient patient){
		Score4Frag score4Frag = new Score4Frag();
		Bundle bundle = new Bundle();
		bundle.putSerializable("patient",patient);
		score4Frag.setArguments(bundle);
		return score4Frag;
	}

	public Score4Frag() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mcontext = getActivity();
		this.patient = (Patient) getArguments().getSerializable("patient");

		if (mView == null) {
			initView(inflater, container);
		}
		return mView;
	}

	private void initView(LayoutInflater inflater, ViewGroup container) {

		mView = inflater.inflate(R.layout.frag_score4, container, false);

		//保留2位小数
		DecimalFormat df2 = new DecimalFormat("#.00");
		NersScore_res = (TextView) mView.findViewById(R.id.NersScore_res);
		NersScore_res.setText(df2.format(patient.getSyntax4()));

	}
}
