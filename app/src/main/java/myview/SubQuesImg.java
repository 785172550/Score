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
 * Created by Administrator on 2016/7/18.
 *
 * 钝圆 桥支 分叉
 */
public class SubQuesImg extends LinearLayout {

	private LayoutInflater mInflater;
	private View mView;
	TextView tv_title,img_title;
	RadioGroup radioGroup;
	private Itemchecked itemchecked;
	private Context context;

	String title,imgtitle;
	String[] answer;

	ImageView imageView;
	int rid;

	public SubQuesImg(Context context, String title,String imgtitle,int rid,
					  Itemchecked itemchecked, String... answer) {
		super(context);
		this.title = title;
		this.answer = answer;
		this.itemchecked =itemchecked;
		this.context = context;
		this.imgtitle = imgtitle;
		this.rid = rid;
		init();
	}

	private void init() {
		mInflater = LayoutInflater.from(context);
		mView = mInflater.inflate(R.layout.question3, null);

		tv_title = (TextView) mView.findViewById(R.id.mytitle);
		tv_title.setText(title);

		radioGroup = (RadioGroup) mView.findViewById(R.id.rad_group);
		for(int i= 0;i<answer.length;i++){
			RadioButton rb = new RadioButton(context);
			rb.setText(answer[i]);
			rb.setTextColor(context.getResources().getColorStateList(R.color.question_selector));
			rb.setId(i);
			radioGroup.addView(rb);
		}

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				itemchecked.checked(SubQuesImg.this,checkedId);
			}
		});

		img_title = (TextView) mView.findViewById(R.id.img_title);
		img_title.setText(imgtitle);

		imageView = (ImageView) mView.findViewById(R.id.ques_img);
		imageView.setImageResource(rid);

		addView(mView);
	}


	public interface Itemchecked{
		void checked(View view,int checkedId);
	}
}
