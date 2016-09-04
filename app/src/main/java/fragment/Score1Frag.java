package fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sean.score.R;

import entity.Patient;

/**
 * Created by Administrator on 2016/6/17.
 */
public class Score1Frag extends Fragment{
	private View mView;
	private Context mcontext;
	Patient patient;

	public static Fragment newInstance(Patient patient){
		Score1Frag score1Frag = new Score1Frag();
		Bundle bundle = new Bundle();
		bundle.putSerializable("patient",patient);
		score1Frag.setArguments(bundle);
		return score1Frag;
	}

	public Score1Frag() {
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

		mView = inflater.inflate(R.layout.frag_score1, container, false);

	}
}
