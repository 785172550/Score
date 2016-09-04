package adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sean.score.R;

import java.util.List;
import java.util.Map;

import Base.BaseListAdapter;

/**
 * Created by Administrator on 2016/6/13.
 */
public class ListEditTextAdapter extends BaseListAdapter {


	EditText edt;

	public ListEditTextAdapter(Context context, List list){
		super(context,list);
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.list_edt_item,null);
			edt = (EditText) convertView.findViewById(R.id.edt_1);
		}

		final Map map = (Map) list.get(position);
		edt.setHint(map.get("1").toString());
		edt.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				Map map = (Map) list.get(position);
				map.put("2",s.toString());
			}
		});

		return convertView;
	}
}
