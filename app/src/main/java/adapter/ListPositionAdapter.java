package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sean.score.R;

import java.util.List;

import Base.BaseListAdapter;
import entity.DiseasePosition;

/**
 * Created by Administrator on 2016/6/25.
 */
public class ListPositionAdapter extends BaseListAdapter {

	public ListPositionAdapter(Context context, List list) {
		super(context, list);
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

		DiseasePosition diseasePosition= (DiseasePosition) list.get(position);
		viewHolder.tv1.setText(diseasePosition.getName());
		viewHolder.tv2.setText(diseasePosition.getLevel());

		return convertView;
	}

	public void updateList(List list) {
		this.list = list;
		this.notifyDataSetInvalidated();
	}

	class ViewHolder{
		TextView tv1,tv2;
	}
}
