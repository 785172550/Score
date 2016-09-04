package ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.sean.score.R;

import java.util.ArrayList;
import java.util.List;

import Base.BaseActivity;
import entity.ImagePath;
import entity.Patient;

/**
 * Created by Administrator on 2016/6/21.
 */
public class ImgShow extends BaseActivity {

//	int[] imgs = new int[]{R.mipmap.ic_launcher,
//			R.mipmap.ic_launcher,
//			R.mipmap.ic_launcher,
//			R.mipmap.ic_launcher,
//			R.mipmap.ic_launcher,};

	private static final String TAG = "ImgShow";

	GridView gv;
	View mParent;
	View mBg;
	PhotoView mPhotoView;
	Info mInfo;

	List<ImagePath> list;

	AlphaAnimation in = new AlphaAnimation(0, 1);
	AlphaAnimation out = new AlphaAnimation(1, 0);
	Patient patient;
	BaseAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_imgshow);
		getdata();
		initTopBarForLeft("造影图片展示");
		initview();
	}

	public void  getdata() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle.getSerializable("patient") != null){
			patient = (Patient) bundle.getSerializable("patient");
		}
		list = patient.getImg();
		//Log.d(TAG, "getdata: " + list.get(0).getPath());
	}

	private void initview() {

//		PhotoView photoView = (PhotoView) findViewById(R.id.img);
//		// 启用图片缩放功能
//		photoView.enable();

		in.setDuration(300);
		out.setDuration(300);
		out.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mBg.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		mParent = findViewById(R.id.parent);
		mBg = findViewById(R.id.bg);
		mPhotoView = (PhotoView) findViewById(R.id.img);
		mPhotoView.setScaleType(ImageView.ScaleType.FIT_START);

		gv = (GridView) findViewById(R.id.gv);
		adapter = new BaseAdapter() {
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
				PhotoView p = new PhotoView(ImgShow.this);
				p.setLayoutParams(new AbsListView.LayoutParams(
						(int) (getResources().getDisplayMetrics().density * 100),
						(int) (getResources().getDisplayMetrics().density * 100)));
				p.setScaleType(ImageView.ScaleType.CENTER_CROP);
				//p.setImageResource(imgs[position]);
				ImageLoader.getInstance()
						.displayImage("file://" + list.get(position).getPath(),
								new ImageViewAware(p),
								null,null,null);
				// 把PhotoView当普通的控件把触摸功能关掉
				p.disenable();
				return p;
			}
		};

		gv.setAdapter(adapter);

		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				PhotoView p = (PhotoView) view;
				mInfo = p.getInfo();

				ArrayList<String> path = new ArrayList();
				for(ImagePath photoInfo:list){
					path.add(photoInfo.getPath());
				}
				Intent intent = new Intent(ImgShow.this,PhotoviewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("path", path);
				intent.putExtras(bundle);
				intent.putExtra("cur",position);
				startActivity(intent);

				/*ImageLoader.getInstance()
						.displayImage("file://" + list.get(position).getPath(),
								new ImageViewAware(mPhotoView),
								null,null,null);
				mBg.startAnimation(in);
				mBg.setVisibility(View.VISIBLE);
				mParent.setVisibility(View.VISIBLE);
				mPhotoView.animaFrom(mInfo);*/
			}
		});

		gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

				Dialog dialog = createdialog("确定删除？", "确定删除？", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						List<ImagePath> path = patient.getImg();
						for(ImagePath path1:path){
							if(path1.getPath().equals(list.get(position).getPath())){
								path1.delete();
							}
						}
						list.remove(position);
						adapter.notifyDataSetChanged();
					}
				}, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				dialog.show();
				return true;
			}
		});

		mPhotoView.enable();
		mPhotoView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBg.startAnimation(out);
				mPhotoView.animaTo(mInfo, new Runnable() {
					@Override
					public void run() {
						mParent.setVisibility(View.GONE);
					}
				});
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (mParent.getVisibility() == View.VISIBLE) {
			mBg.startAnimation(out);
			mPhotoView.animaTo(mInfo, new Runnable() {
				@Override
				public void run() {
					mParent.setVisibility(View.GONE);
				}
			});
		} else {
			super.onBackPressed();
		}
	}
}
