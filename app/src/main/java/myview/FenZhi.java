package myview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sean.score.R;

/**
 * Created by Administrator on 2016/7/19.
 *
 */
public class FenZhi extends LinearLayout {

	private LayoutInflater mInflater;
	private View mView;
	TextView tv_title,img_title;
	RadioGroup radioGroup;
	private Itemchecked itemchecked;
	private Context context;

	String title,imgtitle;
	String[] answer;

	ImageView imageView;
	int[] rid;


	public FenZhi(Context context, String title,int[] rid,
					  Itemchecked itemchecked, String... answer) {
		super(context);
		this.title = title;
		this.answer = answer;
		this.itemchecked =itemchecked;
		this.context = context;
		this.rid = rid;
		init();
	}

	private void init() {
		mInflater = LayoutInflater.from(context);
		mView = mInflater.inflate(R.layout.question_fenzhi, null);

		tv_title = (TextView) mView.findViewById(R.id.mytitle);
		tv_title.setText(title);

		radioGroup = (RadioGroup) mView.findViewById(R.id.rad_group1);

		for(int i= 0;i<answer.length;i++){
			RadioButton rb = new RadioButton(context);
			rb.setText(answer[i]);
			rb.setTextColor(context.getResources().getColorStateList(R.color.question_selector));
			rb.setId(i);
			radioGroup.addView(rb);
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(rid[i]);
			LayoutParams layoutParams = new
					LinearLayout.LayoutParams(120,80);
			layoutParams.setMargins(0,10,0,0);
			imageView.setLayoutParams(layoutParams);
			radioGroup.addView(imageView);
		}

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				itemchecked.checked(FenZhi.this,checkedId);
			}
		});

//		img_title = (TextView) mView.findViewById(R.id.img_title);
//		img_title.setText(imgtitle);
//
//		imageView = (ImageView) mView.findViewById(R.id.ques_img);
//		imageView.setImageResource(rid);

		addView(mView);
	}


	public interface Itemchecked{
		void checked(View view,int checkedId);
	}
}
