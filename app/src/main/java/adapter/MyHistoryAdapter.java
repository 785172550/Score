package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sean.score.R;

import java.util.List;

import Base.BaseListAdapter;
import entity.Patient;

/**
 * Created by Administrator on 2016/6/23.
 */
public class MyHistoryAdapter extends BaseListAdapter<Patient> {


	public MyHistoryAdapter(Context context, List list){
		super(context,list);
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){

			convertView = mInflater.inflate(R.layout.histoy_item,null);
			viewHolder = new ViewHolder();
			viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			viewHolder.tv_pname = (TextView) convertView.findViewById(R.id.tv_pname);
			viewHolder.content = (LinearLayout) convertView.findViewById(R.id.l_content);

			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if(position %2 == 1){
			viewHolder.content.setBackgroundResource(R.color.histor_color);
		}
		if(position == 0){
			viewHolder.content.setBackgroundResource(R.color.white);
		}
		viewHolder.tv_time.setText(list.get(position).getDate());
		viewHolder.tv_num.setText(list.get(position).getNum());
		viewHolder.tv_pname.setText(list.get(position).getName());

		return convertView;
	}

	class ViewHolder{
		TextView tv_time,tv_num,tv_pname;
		LinearLayout content;
	}
}
