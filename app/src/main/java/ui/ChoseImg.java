package ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.sean.score.R;

import java.util.ArrayList;
import java.util.List;

import Base.BaseActivity;
import Util.UILImageLoader;
import Util.UILPauseOnScrollListener;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import entity.ImagePath;
import entity.Patient;
import myview.HeaderLayout;

/**
 * Created by Administrator on 2016/6/19.
 */
public class ChoseImg extends BaseActivity {

	private List<PhotoInfo> mPhotoList;
	Button add_img;

	GridView gv;

	View mParent;
	View mBg;
	PhotoView mPhotoView;
	Info mInfo;

	AlphaAnimation in = new AlphaAnimation(0, 1);
	AlphaAnimation out = new AlphaAnimation(1, 0);

	BaseAdapter adapter;

	Patient patient;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_chose);
		mPhotoList = new ArrayList<>();

		getdata();
		//patient = (Patient) Patient.findById(Patient.class,1);
		initview();



	}

	public void  getdata() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle.getSerializable("patient") != null)
			patient = (Patient) bundle.getSerializable("patient");
	}

	private void initview() {

		initTopBarForBothWithText("添加造影图片", "完成", new HeaderLayout.onRightImageButtonClickListener() {
					@Override
					public void onClick(View v) {

						for (PhotoInfo photoInfo : mPhotoList) {
							//本地缓存
							ImagePath path = new ImagePath(photoInfo.getPhotoPath());
							path.setnum(patient.getNum());
							Log.d("path--",  path.getPath() + "  " +path.getnum());
							path.save();
						}
						//######  把图片路径存入了患者表

						patient.save();
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putSerializable("patient", patient);
						intent.putExtras(bundle);
						setResult(2, intent);
						finish();
					}
				}, R.drawable.base_action_bar_back_bg_selector,
				new HeaderLayout.onLeftImageButtonClickListener() {
					@Override
					public void onClick() {
						finish();
					}
				});

		add_img = (Button) findViewById(R.id.add_img);
		add_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ActionSheet.createBuilder(ChoseImg.this,getSupportFragmentManager())
						.setCancelButtonTitle("取消")
						.setOtherButtonTitles("打开相机","打开相册")
						.setListener(new ActionSheet.ActionSheetListener() {
							@Override
							public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

							}

							@Override
							public void onOtherButtonClick(ActionSheet actionSheet, int index) {
									if(index == 0)OpenCama();
									if(index == 1) OpenGallery();
							}
						}).show();


			}
		});

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
		mPhotoView = (PhotoView) findViewById(R.id.img_pv);
		mPhotoView.setScaleType(ImageView.ScaleType.FIT_START);
		initgv();
	}

	private void initgv() {

		gv = (GridView) findViewById(R.id.gv);
		adapter = new BaseAdapter() {
			@Override
			public int getCount() {
				return mPhotoList.size();
			}

			@Override
			public Object getItem(int position) {
				return mPhotoList.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				PhotoView p = new PhotoView(ChoseImg.this);
				p.setLayoutParams(new AbsListView.LayoutParams(
						(int) (getResources().getDisplayMetrics().density * 100),
						(int) (getResources().getDisplayMetrics().density * 100)));
				p.setScaleType(ImageView.ScaleType.CENTER_CROP);
				//p.setImageResource(imgs[position]);
				ImageLoader.getInstance()
						.displayImage("file://" + mPhotoList.get(position).getPhotoPath(),
								new ImageViewAware(p),
								null, null, null);

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
				for(PhotoInfo photoInfo:mPhotoList){
					path.add(photoInfo.getPhotoPath());
				}
				Intent intent = new Intent(ChoseImg.this,PhotoviewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("path",  path);
				intent.putExtras(bundle);
				intent.putExtra("cur",position);
				startActivity(intent);

				/*ImageLoader.getInstance()
						.displayImage("file://" + mPhotoList.get(position).getPhotoPath(),
								new ImageViewAware(mPhotoView), null, null, null);
				//mPhotoView.setImageResource(imgs[position]);
				//隐藏按钮
				add_img.setVisibility(View.INVISIBLE);

				mBg.startAnimation(in);
				mParent.setVisibility(View.VISIBLE);
				mBg.setVisibility(View.VISIBLE);
				mPhotoView.animaFrom(mInfo);*/
			}
		});

		gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

				Dialog dialog = createdialog("确定删除？", "确定删除？", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						mPhotoList.remove(position);
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
						add_img.setVisibility(View.VISIBLE);
					}
				});
			}
		});
	}

	//打开相机
	private void OpenCama() {

		//自定义主题 已经和应用一样
		ThemeConfig themeConfig = new ThemeConfig.Builder()
				.setTitleBarBgColor(Color.rgb(12, 159, 227))
				.setFabNornalColor(Color.rgb(12, 159, 227))
				.setFabPressedColor(Color.rgb(0x01, 0x83, 0x93))
				.setCheckSelectedColor(Color.rgb(12, 159, 227))
				.setCropControlColor(Color.rgb(0x00, 0xac, 0xc1)).build();
		//功能设置

		final FunctionConfig functionConfig = new FunctionConfig.Builder()
				.setSelected(mPhotoList)
				.setMutiSelectMaxSize(10)//配置多选数量
				.setEnablePreview(true)//预览功能
				.build();

		cn.finalteam.galleryfinal.ImageLoader imageLoader = new UILImageLoader();
		PauseOnScrollListener pauseOnScrollListener = null;
		pauseOnScrollListener = new UILPauseOnScrollListener(false, true);

		//核心设置
		CoreConfig coreConfig =
				new CoreConfig.Builder(ChoseImg.this, imageLoader, themeConfig)
						.setFunctionConfig(functionConfig)
						.setPauseOnScrollListener(pauseOnScrollListener)
						.setNoAnimcation(true)
						.build();
		GalleryFinal.init(coreConfig);

		GalleryFinal.openCamera(1, functionConfig, mOnHanlderResultCallback);
	}

	private void OpenGallery(){
		//自定义主题 已经和应用一样
		ThemeConfig themeConfig = new ThemeConfig.Builder()
				.setTitleBarBgColor(Color.rgb(12, 159, 227))
				.setFabNornalColor(Color.rgb(12, 159, 227))
				.setFabPressedColor(Color.rgb(0x01, 0x83, 0x93))
				.setCheckSelectedColor(Color.rgb(12, 159, 227))
				.setCropControlColor(Color.rgb(0x00, 0xac, 0xc1)).build();
		//功能设置

		final FunctionConfig functionConfig = new FunctionConfig.Builder()
				.setSelected(mPhotoList)
				.setMutiSelectMaxSize(10)//配置多选数量
				.setEnablePreview(true)//预览功能
				.build();

		cn.finalteam.galleryfinal.ImageLoader imageLoader = new UILImageLoader();
		PauseOnScrollListener pauseOnScrollListener = null;
		pauseOnScrollListener = new UILPauseOnScrollListener(false, true);

		//核心设置
		CoreConfig coreConfig =
				new CoreConfig.Builder(ChoseImg.this, imageLoader, themeConfig)
						.setFunctionConfig(functionConfig)
						.setPauseOnScrollListener(pauseOnScrollListener)
						.setNoAnimcation(true)
						.build();
		GalleryFinal.init(coreConfig);

		GalleryFinal.openGalleryMuti(2,functionConfig,mOnHanlderResultCallback);
	}


	//相机回调
	private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
			new GalleryFinal.OnHanlderResultCallback() {
				@Override
				public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
					if (resultList != null) {
						mPhotoList.addAll(resultList);
						adapter.notifyDataSetChanged();
					}
				}

				@Override
				public void onHanlderFailure(int requestCode, String errorMsg) {
					Toast.makeText(ChoseImg.this, errorMsg, Toast.LENGTH_SHORT).show();
				}
			};


	@Override
	public void onBackPressed() {
		if (mParent.getVisibility() == View.VISIBLE) {
			mBg.startAnimation(out);
			mPhotoView.animaTo(mInfo, new Runnable() {
				@Override
				public void run() {
					mParent.setVisibility(View.GONE);
					add_img.setVisibility(View.VISIBLE);
				}
			});
		} else {
			super.onBackPressed();
		}
	}
}
