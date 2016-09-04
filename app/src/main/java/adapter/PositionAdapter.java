package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sean.score.R;

import java.util.List;
import java.util.Map;

import Base.BaseListAdapter;
import entity.DiseasePosition;

/**
 * Created by Administrator on 2016/6/12.
 */
public class PositionAdapter extends BaseListAdapter<Map> {

	// 用来控制CheckBox的选中状况
	//private static HashMap<Integer, Boolean> isSelected;

	// 初始化isSelected的数据


	public PositionAdapter(Context context, List<Map> list) {
		super(context, list);
		//isSelected = new HashMap<Integer, Boolean>();
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.position_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
//			viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
//			viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check1);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}


		DiseasePosition diseasePosition = (DiseasePosition) list.get(position).get(1);
		viewHolder.tv1.setText(diseasePosition.getCname() + "("+diseasePosition.getName()+")");
//		viewHolder.tv2.setText(diseasePosition.getLevel());
//		viewHolder.tv3.setText(diseasePosition.getFenshu());
		Map map = (Map) list.get(position);
		viewHolder.checkBox.setChecked((Boolean) map.get(2));

		return convertView;
	}

	class ViewHolder{
		TextView tv1,tv2,tv3;
		CheckBox checkBox;
	}

}
