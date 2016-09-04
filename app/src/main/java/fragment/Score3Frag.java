package fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sean.score.R;

import entity.Patient;

/**
 * Created by Administrator on 2016/6/17.
 */
public class Score3Frag extends Fragment {

	private View mView;
	private Context mcontext;
	Patient patient;
	TextView EuroScore_res;


	public static Fragment newInstance(Patient patient){
		Score3Frag score3Frag = new Score3Frag();
		Bundle bundle = new Bundle();
		bundle.putSerializable("patient",patient);
		score3Frag.setArguments(bundle);
		return score3Frag;
	}

	public Score3Frag() {
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



		mView = inflater.inflate(R.layout.frag_score3, container, false);
		EuroScore_res = (TextView) mView.findViewById(R.id.EuroScore_res);

		EuroScore_res.setText(patient.getSyntax3());
	}

}
