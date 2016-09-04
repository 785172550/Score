package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sean.score.R;

import java.util.List;

import Base.BaseListAdapter;

/**
 * Created by Administrator on 2016/6/10.
 */
public class SlidingAdapter extends BaseListAdapter {

	//List mdata;

//	int iconId[] = {
//			R.drawable.selector_risk,
//			R.drawable.selector_history,
//			R.drawable.selector_common,
//	};


	int iconId[] = {
			R.drawable.risk,
//			/*R.drawable.history_n,*/
			R.drawable.history_off,
			R.drawable.history_cloud,
			R.drawable.seedback,
	};

	public SlidingAdapter(Context context, List list) {
		super(context, list);
	}


	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		//设置每个 item 高度
//		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
//				LinearLayout.LayoutParams.MATCH_PARENT, 60);
//		convertView.setLayoutParams(lp);

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.slide_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.content = (LinearLayout) convertView.findViewById(R.id.slide_content);
			viewHolder.iv_item = (ImageView) convertView.findViewById(R.id.iv_item);
			viewHolder.tv = (TextView) convertView.findViewById(R.id.name_item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

//		if(position == 2||position==3){
//			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)viewHolder.iv_item.getLayoutParams();
//			lp.setMarginStart(85);
//			viewHolder.iv_item.setLayoutParams(lp);
////			Log.d("ddddd", "bindView: "+ list.get(position).toString());
//			viewHolder.tv.setTextSize(12);
//		}

		if (position == 0 || position == 3) {
			viewHolder.content.setBackgroundResource(R.color.slide_color);
			viewHolder.tv.setTextColor(mContext.getResources().getColor(R.color.white));
		}
//		if(position == 0){
//			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)viewHolder.iv_item.getLayoutParams();
//			viewHolder.iv_item.setLayoutParams(lp);
//			lp.setMarginStart(20);
//			viewHolder.tv.setTextSize(18);
//		}
		viewHolder.tv.setText(list.get(position).toString());
		viewHolder.iv_item.setImageResource(iconId[position]);


		return convertView;
	}

	class ViewHolder {
		LinearLayout content;
		TextView tv;
		ImageView iv_item;
	}

}
