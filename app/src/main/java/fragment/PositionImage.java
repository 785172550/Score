package fragment;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import entity.DiseasePosition;
import entity.Patient;
import myview.MyImg;

/**
 * Created by Administrator on 2016/6/10.
 */
public class PositionImage extends Fragment {

	private View mView;
	private Context mcontext;
	int mflag = 1;
	MyImg imageView;

	List<DiseasePosition> list;
	ListView listview;
	Patient patient;




//	public PositionImage(Context context,int flag,Patient patient){
//		mcontext = context;
//		mflag = flag;
//		this.patient = patient;
//		list = new ArrayList();
//	}
//
//	@Nullable
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		if (mView == null) {
//			initView(inflater, container);
//		}
//		return mView;
//	}

//	private void initView(LayoutInflater inflater, ViewGroup container) {
//
//		//mView  = inflater.inflate(R.layout.position_img, container, false);
//		//listview = (ListView) mView.findViewById(R.id.mlv);
////		for (int t = 0;t<dp.length;t++){
////			Map map = new HashMap();
////			map.put(1,dp[t]);
////			map.put(2,false);
////			list.add(map);
////		}
////
////		int height = (int) (list.size() * (getResources().getDimension(R.dimen.text_view_height)
////				+ 3));
////		listview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
//		final ImgListAdapter adapter = new ImgListAdapter(mcontext,list);
//		listview.setAdapter(adapter);
//
//
//		imageView = (MyImg) mView.findViewById(R.id.pos_img);
//		PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
//
//		imageView.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
//			@Override
//			public boolean onSingleTapConfirmed(MotionEvent e) {
//				return false;
//			}
//
//			@Override
//			public boolean onDoubleTap(MotionEvent e) {
//
//				return false;
//			}
//
//			@Override
//			public boolean onDoubleTapEvent(MotionEvent e) {
//				return false;
//			}
//		});
//
//		imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//			@Override
//			public void onPhotoTap(View view, float x, float y) {
//				Log.d("22222", "onPhotoTap: " + x + " y" + y);
//			}
//		});
//
//		imageView.setOnTouchListener(new View.OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//
//				float eventX = event.getX();
//				float eventY = event.getY();
//				float[] eventXY = new float[] {eventX, eventY};
//
//				Matrix invertMatrix = new Matrix();
//				((ImageView)v).getImageMatrix().invert(invertMatrix);
//				invertMatrix.mapPoints(eventXY);
//
//				int x = Integer.valueOf((int)eventXY[0]);
//				int y = Integer.valueOf((int)eventXY[1]);
//
//				Drawable imgDrawable = ((ImageView)v).getDrawable();
//				Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();
//
//				//Limit x, y range within bitmap
//				if(x < 0)
//				{
//					x = 0;
//				}
//				else if(x > bitmap.getWidth()-1)
//				{
//					x = bitmap.getWidth()-1;
//				}
//
//				if(y < 0)
//				{
//					y = 0;
//				}
//				else if(y > bitmap.getHeight()-1)
//				{
//					y = bitmap.getHeight()-1;
//				}
//
//				Log.d("img--", "onTouch: " + x +" "+ y);
//				Log.d("img--", "onTouch: " + eventX +" " + eventY);
//
//				Circle c = new Circle(event.getX(),event.getY());
//				Circle shape = new Circle(2.0,5.0);
//				Canvas canvas = new Canvas();
//
//				return false;
//			}
//		});
//
//		if (mflag == 1){
//			imageView.setImageResource(R.drawable.left_dom);
//			imageView.setScaleType(ImageView.ScaleType.FIT_START);
////			imageView.setOnClickListener(new View.OnClickListener() {
////				@Override
////				public void onClick(View v) {
////					int [] d = imageView.getitem();
////					for(int i = 0;i<d.length;i++){
////						if(d[i] == 1){
////							Log.d("dp -- ", "  " + i + dp[i].getName());
////							if(list.contains(dp[i])){
////								list.remove(dp[i]);
////								adapter.notifyDataSetChanged();
////							}else {
////								list.add(dp[i]);
////								adapter.notifyDataSetChanged();
////							}
////						}
////					}
////				}
////			});
//			mAttacher.update();
//		}else {
//			imageView.setScaleType(ImageView.ScaleType.FIT_START);
//			imageView.setImageResource(R.drawable.right_dom);
//		}
//
//	}

}
