package fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class Score2Frag extends Fragment {

	private View mView;
	private Context mcontext;
	Patient patient;
	TextView PCI,PCI_death,CABG,CABG_death,rec;


	public static Fragment newInstance(Patient patient){
		Score2Frag score2Frag = new Score2Frag();
		Bundle bundle = new Bundle();
		bundle.putSerializable("patient",patient);
		score2Frag.setArguments(bundle);
		return score2Frag;
	}

	public Score2Frag() {
	}


	@Nullable
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

		mView = inflater.inflate(R.layout.frag_score2, container, false);

		PCI = (TextView) mView.findViewById(R.id.PCI);
		PCI_death = (TextView) mView.findViewById(R.id.PCI_death);
		CABG = (TextView) mView.findViewById(R.id.CABG);
		CABG_death = (TextView) mView.findViewById(R.id.CABG_death);
		rec = (TextView) mView.findViewById(R.id.recom);
		//保留一位小数
		DecimalFormat df = new DecimalFormat("#.0");

		PCI.setText(df.format(patient.getPCI()));
		PCI_death.setText(df.format(patient.getPCI_Death()) + "%");
		CABG.setText(df.format(patient.getCABG()));
		CABG_death.setText(df.format(patient.getCABG_Death()) + "%");
		rec.setText(patient.getRec_ope());

	}
}
