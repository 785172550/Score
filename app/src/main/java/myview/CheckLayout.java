package myview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.sean.score.R;

import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public class CheckLayout extends LinearLayout{

	String[] answer;
	List<CheckBox> checks;

	private LayoutInflater mInflater;
	private ViewGroup mView;
	private Context context;

	public CheckLayout(Context context,List<CheckBox> checks,String...answer) {
		super(context);
		this.context = context;
		this.checks = checks;
		this.answer = answer;
		init();
	}

	private void init() {
		mInflater = LayoutInflater.from(context);
		mView = (ViewGroup) mInflater.inflate(R.layout.check_ques, null);

		for(int i = 0;i<answer.length;i++){
			CheckBox cb = new CheckBox(context);
			cb.setText(answer[i]);
			mView.addView(cb);
			checks.add(cb);
		}
		addView(mView);
	}

}
