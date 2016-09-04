package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.sean.score.R;

import java.util.List;
import java.util.Map;

import Base.BaseListAdapter;

/**
 * Created by Administrator on 2016/6/13.
 *
 */
public class ListSwitchAdapter extends BaseListAdapter {

	TextView textView;
	SwitchButton switchButton;
	int color;

	public ListSwitchAdapter(Context context, List list,int color){
		super(context,list);
		this.color = color;
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.list_edt_switch,null);
			 //= (EditText) convertView.findViewById(R.id.edt_1);

		}
		textView = (TextView) convertView.findViewById(R.id.t1);
		switchButton = (SwitchButton) convertView.findViewById(R.id.sw_btn);
		if(color == 1)
			switchButton.setThumbDrawableRes(R.drawable.switch_green);
		else if(color == 2)
			switchButton.setThumbDrawableRes(R.drawable.switch_blue);
		else switchButton.setThumbDrawableRes(R.drawable.switch_grey);
		switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Map map = (Map) list.get(position);
					map.put("2","1");
				}else {
					Map map = (Map) list.get(position);
					map.put("2","0");
				}
			}
		});

		Map map = (Map) list.get(position);
		textView.setText(map.get("1").toString());

		return convertView ;
	}
}
