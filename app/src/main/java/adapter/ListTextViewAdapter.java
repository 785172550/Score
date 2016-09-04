package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sean.score.R;

import java.util.List;
import java.util.Map;

import Base.BaseListAdapter;

/**
 * Created by Administrator on 2016/6/15.
 */
public class ListTextViewAdapter extends BaseListAdapter {

	public ListTextViewAdapter(Context context, List list){
		super(context,list);
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.tv_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_1);
			viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv_2);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Map map = (Map) list.get(position);
		viewHolder.tv1.setText(map.get("1").toString() + ": "+map.get("2").toString());

		return convertView;
	}

	class ViewHolder{
		TextView tv1,tv2;
	}

	public void updateList(List list) {
		this.list = list;
		this.notifyDataSetInvalidated();
	}
}
