package myview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sean.score.R;

/**
 * Created by Administrator on 2016/6/21.
 */
public class QuestionLayout extends LinearLayout {

	private LayoutInflater mInflater;
	private View mView;
	TextView tv_title;
	RadioGroup radioGroup;
	private Itemchecked itemchecked;
	private Context context;

	String title;
	String[] answer;


	public QuestionLayout(Context context,String title,Itemchecked itemchecked,String...answer){
		super(context);
		this.title = title;
		this.answer = answer;
		this.itemchecked =itemchecked;
		this.context = context;
		init();
	}

	public void init() {

		mInflater = LayoutInflater.from(context);
		mView = mInflater.inflate(R.layout.question, null);

		tv_title = (TextView) mView.findViewById(R.id.mytitle);
		tv_title.setText(title);

		radioGroup = (RadioGroup) mView.findViewById(R.id.rad_group);
		for(int i= 0;i<answer.length;i++){
			RadioButton rb = new RadioButton(context);
			rb.setText(answer[i]);
			rb.setTextColor(context.getResources().getColorStateList(R.color.question_selector));
			//rb.setButtonDrawable(R.drawable.question_selector);
			rb.setId(i);
			radioGroup.addView(rb);
		}

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				itemchecked.checked(QuestionLayout.this,checkedId);
			}
		});

		addView(mView);
	}

	public interface Itemchecked{
		void checked(View view,int checkedId);
	}
}
