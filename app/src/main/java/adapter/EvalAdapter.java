package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sean.score.R;

import java.util.List;
import java.util.Map;

import entity.DiseasePosition;

/**
 * Created by Administrator on 2016/6/19.
 */
public class EvalAdapter extends BaseAdapter {

	public static int TEXT_VIEW = 0;
	public static int EVAL_VIEW = 1;
	public static int DONE_VIEW = 2;

	public List<Map> list;
	public Context context;
	private LayoutInflater Inflater;

	TextView textView;

	public EvalAdapter(Context context, List<Map> list) {
		this.list = list;
		this.context = context;
		Inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

			if (getItemType(position) == EVAL_VIEW) {
				convertView =  getItmeView(EVAL_VIEW);
				textView = (TextView) convertView.findViewById(R.id.tv_item);
				DiseasePosition dp = (DiseasePosition) list.get(position).get(1);
				textView.setText(dp.getCname()+"("+dp.getName()+")");
			}
			else {
				if (getItemType(position) == TEXT_VIEW) {
					convertView = getItmeView(TEXT_VIEW);
				} else {
					convertView = getItmeView(DONE_VIEW);
					textView = (TextView) convertView.findViewById(R.id.tv_done);
					DiseasePosition dp = (DiseasePosition) list.get(position).get(3);
					TextView score = (TextView) convertView.findViewById(R.id.tv_item_score);
					score.setText(list.get(position).get(4) + "");
					textView.setText(dp.getCname() + "(" + dp.getName() + ")");
				}
			}

		return convertView;
	}

	public int getItemType(int position){
		if(list.get(position).containsKey(1))
			return EVAL_VIEW;
		else if (list.get(position).containsKey(2))
			return TEXT_VIEW;
		else return DONE_VIEW;
	}
	public View getItmeView(int viewid){

		if(viewid == EVAL_VIEW) {
			return Inflater.inflate(R.layout.eval_item, null);
		}
		else if (viewid == TEXT_VIEW){
			return Inflater.inflate(R.layout.eval_tv,null);
		}else {
			return Inflater.inflate(R.layout.eval_done,null);
		}
	}
}
