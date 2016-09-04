package myview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.sean.score.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import entity.DiseasePosition;

/**
 * Created by Administrator on 2016/7/1.
 *
 */
public class MatrixImageView extends ImageView{

	public final static String TAG="MatrixImageView";

	public Context context;
	DiseasePosition[] dp;
	ArrayList<DiseasePosition> checklist;

	private GestureDetector mGestureDetector;
	/**  模板Matrix，用以初始化 */
	private  Matrix mMatrix=new Matrix();
	/**  图片长度*/
	private float mImageWidth;
	/**  图片高度 */
	private float mImageHeight;
	/**  原始缩放级别 */
	private float mScale;
	// 当前缩放级别
	private float cScale = 1f;

	Point[] points;

	Bitmap bitmap1;
	Bitmap bm;

	float ofx = 0f;
	float ofy = 0f;

	float clickx = 0;
	float clicky = 0;

	private OnMovingListener moveListener;
	private OnSingleTapListener singleTapListener;

	Set<Point> check;

	String[] numstring = new String[]{"①","②","③","④","⑤","⑥","⑦","⑧","⑨","⑩","⑪","⑫","⑬"
	,"⑭","⑮","⑯","⑰","⑱","⑲","⑳"
	};

//	public String getnum_string(String string){
//
//	}


	public MatrixImageView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;
		MatrixTouchListener mListener=new MatrixTouchListener();
		setOnTouchListener(mListener);

		mGestureDetector=new GestureDetector(getContext(), new GestureListener(mListener));

		//将缩放类型设置为CENTER_INSIDE，表示把图片居中显示,并且宽高最大值为控件宽高
		//setScaleType(ScaleType.FIT_CENTER);
		setScaleType(ScaleType.FIT_START);
	}

	public MatrixImageView(Context context) {

		super(context, null);
		this.context = context;
		MatrixTouchListener mListener = new MatrixTouchListener();
		setOnTouchListener(mListener);
		mGestureDetector = new GestureDetector(getContext(), new GestureListener(mListener));

		//将缩放类型设置为CENTER_INSIDE，表示把图片居中显示,并且宽高最大值为控件宽高
		//setScaleType(ScaleType.FIT_CENTER);
		setScaleType(ScaleType.FIT_START);
	}




	public void point_do(){

		Log.d(TAG, "point_do: "+ points[0].x );
		float scalex = getWidth()/1000f;
		float scaley = getHeight()/1000f;
		Log.d(TAG, "point_do: " + "scale "+scalex);

		for(int i = 0;i<points.length;i++){
			points[i].x *= scalex;
			points[i].y *= scaley;
		}

	}
	Matrix sb = new Matrix();

	public void zoombitmap(float scale){
		BitmapDrawable mBitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.yes_ok);
		Bitmap bitmap = mBitmap.getBitmap();
		bmatrix.set(sb);
		bmatrix.postScale(1.6f*scale,1.6f*scale);
		bitmap1 = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),
				bitmap.getHeight(),bmatrix,true);
	}

	public void initd(boolean isleft, DiseasePosition[] dp, ArrayList checklist){

		zoombitmap(1);
		if(isleft){
			points = new Point[]{
					new Point(194,50),new Point(167,251),new Point(258,381),new Point(118,505)//1 2 3 5
					,new Point(262,541),new Point(435,635),new Point(490,845),new Point(423,520)// 6789
					,new Point(510,574),new Point(550,650),new Point(570,705),new Point(138,570) // 9a 10 10a 11
					,new Point(217,593),new Point(222,645),new Point(240,699),new Point(121,688)// 12  12a 12b 13
					,new Point(175,733) ,new Point(187,775),new Point(231,826),new Point(312,874) //14 14a 14b 15
			};

			for(Point point:points){
				point.x -=5;
				point.y -=5;
			}
		}else {
			points = new Point[]{
					new Point(185,43),new Point(161,252),new Point(252,387),new Point(364,423), //1 2 3 4
					new Point(372,292),new Point(427,425),new Point(453,377),new Point(486,330), // 16 16a 16b 16c
					new Point(100,504),new Point(256,550),new Point(429,642),new Point(487,850), // 5 6 7 8
					new Point(421,522),new Point(500,578),new Point(538,651),new Point(551,717), // 9 9a 10 10a
					new Point(129,571),new Point(211,590),new Point(220,647),new Point(230,709), // 11 12 12a 12b
					new Point(102,699),new Point(164,723),new Point(199,784),new Point(240,836), // 13 14 14a 14b
			};
			for(Point point:points){
				point.x -=1;
				point.y -=2;
			}
		}

		this.dp = dp;
		this.checklist = checklist;
	}


	public void setOnMovingListener(OnMovingListener listener){
		moveListener=listener;
	}
	public void setOnSingleTapListener(OnSingleTapListener onSingleTapListener) {
		this.singleTapListener = onSingleTapListener;
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		// TODO Auto-generated method stub
		super.setImageBitmap(bm);
		this.bm = bm;
		//大小为0 表示当前控件大小未测量  设置监听函数  在绘制前赋值
		if(getWidth()==0){
			ViewTreeObserver vto = getViewTreeObserver();
			vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
			{
				public boolean onPreDraw()
				{
					initData();
					//赋值结束后，移除该监听函数
					MatrixImageView.this.getViewTreeObserver().removeOnPreDrawListener(this);
					return true;
				}
			});
		}else {
			initData();
		}
	}

	Matrix bmatrix = new Matrix();

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
//		float sx=0+cScale*192 ;
//		float sy=0+cScale*269 ;
//		sx += ofx;
//		sy += ofy;
		setpoint(canvas,bitmap1);

	//	Log.d(TAG, "onDraw: x " + sx + " y " + sy +" sclae " + cScale + " ofx " +ofx);
	}


	public void setpoint(Canvas canvas,Bitmap bitmap){

//		for(int i = 0;i<points.length;i++){
//			float sx= 0+points[i].x * cScale;
//			float sy= 0+points[i].y * cScale;
//			sx += ofx;
//			sy += ofy;
//			canvas.drawBitmap(bitmap,sx,sy,null);
//		}
		for(Point point:check){
			float sx= point.x * cScale;
			float sy= point.y * cScale;
			sx += ofx;
			sy += ofy;
			canvas.drawBitmap(bitmap,sx,sy,null);
		}

		//canvas.drawBitmap(bitmap,Dp2Px(context,110),Dp2Px(context,32),null);

	}

	public boolean isonchenck(float clickx,float clicky){
		Log.d(TAG, "isonchenck: " + points[0].x);

		for(int i = 0;i<points.length;i++){
			float sx= points[i].x * cScale;
			float sy= points[i].y * cScale;
			sx += ofx + bitmap1.getWidth()/2;
			sy += ofy + bitmap1.getHeight()/2;

			if(isaround(sx,sy,clickx,clicky)){ //如果点击到图标，就添加选中
				if(check.contains(points[i])) {
					check.remove(points[i]);
					checklist.remove(dp[i]);
				}
				else {
					check.add(points[i]);
					checklist.add(dp[i]);
					//弹出弹框
					Dialog dialog = createdialog(dp[i].getCname(), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		}
		return true;
	}

	public boolean isaround(float x,float y,float x2,float y2){
		float dx = x-x2;
		float dy = y-y2;

		if((float) Math.sqrt(dx * dx + dy * dy) < cScale*35){
			Log.d(TAG, "isaround: " +  Math.sqrt(dx * dx + dy * dy));
			return true;
		}
		return false;
	}
	/**
	 *   初始化模板Matrix和图片的其他数据
	 */
	private void initData() {
		//设置完图片后，获取该图片的坐标变换矩阵
		mMatrix.set(getImageMatrix());
		float[] values=new float[9];
		mMatrix.getValues(values);
		//图片宽度为屏幕宽度除缩放倍数
		mImageWidth=getWidth()/values[Matrix.MSCALE_X];
		mImageHeight=(getHeight()-values[Matrix.MTRANS_Y]*2)/values[Matrix.MSCALE_Y];
		mScale=values[Matrix.MSCALE_X];

		check = new HashSet<>();
		Log.d(TAG, "initData: " + getWidth());
		point_do();

	}

	public int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	public class MatrixTouchListener implements OnTouchListener{
		/** 拖拉照片模式 */
		private static final int MODE_DRAG = 1;
		/** 放大缩小照片模式 */
		private static final int MODE_ZOOM = 2;
		/**  不支持Matrix */
		private static final int MODE_UNABLE=3;
		/**   最大缩放级别*/
		float mMaxScale=1.5f;

		float mMinScale = 1f;

		/**   双击时的缩放级别*/
		float mDobleClickScale=1.5f;

		private int mMode = 0;//
		/**  缩放开始时的手指间距 */
		private float mStartDis;
		/**   当前Matrix*/
		private Matrix mCurrentMatrix = new Matrix();

		Matrix savedMatrix = new Matrix();

		/** 用于记录开始时候的坐标位置 */

		/** 和ViewPager交互相关，判断当前是否可以左移、右移  */
		boolean mLeftDragable;
		boolean mRightDragable;
		/**  是否第一次移动 */
		boolean mFirstMove=false;
		private PointF mStartPoint = new PointF();

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getActionMasked()) {
				case MotionEvent.ACTION_DOWN:
					//设置拖动模式
					mMode=MODE_DRAG;
					mStartPoint.set(event.getX(), event.getY());
					isMatrixEnable();
					startDrag();
					checkDragable();
					break;
				case MotionEvent.ACTION_UP:
					break;
				case MotionEvent.ACTION_CANCEL:
					reSetMatrix();
					stopDrag();
					break;
				case MotionEvent.ACTION_MOVE:
					if (mMode == MODE_ZOOM) {
						setZoomMatrix(event);
						savedMatrix.set(mMatrix);
					}else if (mMode==MODE_DRAG) {
						setDragMatrix(event);
					}else {
						stopDrag();
						ofx = 0;
						ofy = 0;
					}
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					if(mMode==MODE_UNABLE) return true;
					mMode=MODE_ZOOM;
					mStartDis = distance(event);

					break;
				case MotionEvent.ACTION_POINTER_UP:

					break;
				default:
					break;
			}
			return mGestureDetector.onTouchEvent(event);
		}

		/**
		 *   子控件开始进入移动状态，令ViewPager无法拦截对子控件的Touch事件
		 */
		private void startDrag(){
			if(moveListener!=null) moveListener.startDrag();

		}
		/**
		 *   子控件开始停止移动状态，ViewPager将拦截对子控件的Touch事件
		 */
		private void stopDrag(){
			if(moveListener!=null) moveListener.stopDrag();
		}

		/**
		 *   根据当前图片左右边缘设置可拖拽状态
		 */
		private void checkDragable() {
			mLeftDragable=true;
			mRightDragable=true;
			mFirstMove=true;
			float[] values=new float[9];
			getImageMatrix().getValues(values);
			//图片左边缘离开左边界，表示不可右移
			if(values[Matrix.MTRANS_X]>=0)
				mRightDragable=false;
			//图片右边缘离开右边界，表示不可左移
			if((mImageWidth)*values[Matrix.MSCALE_X]+values[Matrix.MTRANS_X]<=getWidth()){
				mLeftDragable=false;
			}
		}

		/**
		 *  设置拖拽状态下的Matrix
		 *  @param event
		 */
		public void setDragMatrix(MotionEvent event) {
			if(isZoomChanged()){
				float dx = event.getX() - mStartPoint.x; // 得到x轴的移动距离
				float dy = event.getY() - mStartPoint.y; // 得到x轴的移动距离
				//避免和双击冲突,大于10f才算是拖动
				if(Math.sqrt(dx*dx+dy*dy)>10f){
					mStartPoint.set(event.getX(), event.getY());
					//在当前基础上移动
					mCurrentMatrix.set(getImageMatrix());
					float[] values=new float[9];
					mCurrentMatrix.getValues(values);
					dy=checkDyBound(values,dy);
					dx=checkDxBound(values,dx,dy);
					ofx = ofx +dx;
					ofy = ofy +dy;

					mCurrentMatrix.postTranslate(dx, dy);
					setImageMatrix(mCurrentMatrix);
					//cScale = values[Matrix.MSCALE_X];
					//------
				}
			}else {
				stopDrag();
				ofx = 0;
				ofy = 0;
			}
		}

		/**
		 *  判断缩放级别是否是改变过
		 *  @return   true表示非初始值,false表示初始值
		 */
		private boolean isZoomChanged() {
			float[] values=new float[9];
			getImageMatrix().getValues(values);
			//获取当前X轴缩放级别
			float scale=values[Matrix.MSCALE_X];
			//cScale = scale;
			//获取模板的X轴缩放级别，两者做比较
			return scale!=mScale;
		}

		/**
		 *  和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
		 *  @param values
		 *  @param dy
		 *  @return
		 */
		private float checkDyBound(float[] values, float dy) {
			float height=getHeight();
			if(mImageHeight*values[Matrix.MSCALE_Y]<height)
				return 0;
			if(values[Matrix.MTRANS_Y]+dy>0)
				dy=-values[Matrix.MTRANS_Y];
			else if(values[Matrix.MTRANS_Y]+dy<-(mImageHeight*values[Matrix.MSCALE_Y]-height))
				dy=-(mImageHeight*values[Matrix.MSCALE_Y]-height)-values[Matrix.MTRANS_Y];
			return dy;
		}

		/**
		 *  和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
		 *  @param values
		 *  @param dx
		 *  @return
		 */
		private float checkDxBound(float[] values,float dx,float dy) {
			float width=getWidth();
			if(!mLeftDragable&&dx<0){
				//加入和y轴的对比，表示在监听到垂直方向的手势时不切换Item
				if(Math.abs(dx)*0.4f>Math.abs(dy)&&mFirstMove){
					stopDrag();
				}
				return 0;
			}
			if(!mRightDragable&&dx>0){
				//加入和y轴的对比，表示在监听到垂直方向的手势时不切换Item
				if(Math.abs(dx)*0.4f>Math.abs(dy)&&mFirstMove){
					stopDrag();
				}
				return 0;
			}
			mLeftDragable=true;
			mRightDragable=true;
			if(mFirstMove) mFirstMove=false;
			if(mImageWidth*values[Matrix.MSCALE_X]<width){
				return 0;

			}
			if(values[Matrix.MTRANS_X]+dx>0){
				dx=-values[Matrix.MTRANS_X];
			}
			else if(values[Matrix.MTRANS_X]+dx<-(mImageWidth*values[Matrix.MSCALE_X]-width)){
				dx=-(mImageWidth*values[Matrix.MSCALE_X]-width)-values[Matrix.MTRANS_X];
			}
			return dx;
		}

		/**
		 *  设置缩放Matrix
		 *  @param event
		 */
		private void setZoomMatrix(MotionEvent event) {
			//只有同时触屏两个点的时候才执行
			if(event.getPointerCount()<2) return;
			float endDis = distance(event);// 结束距离
			if (endDis > 10f)   { // 两个手指并拢在一起的时候像素大于10

				float scale = endDis / mStartDis;// 得到缩放倍数
				scale = (scale - 1)/80;
				scale = scale +1;
				scale = cScale*scale;


				//Log.d(TAG, "setZoomMatrix: " + scale);
				//mStartDis = endDis;//重置距离
				mCurrentMatrix.set(savedMatrix); //初始化Matrix
				float[] values=new float[9];
				mCurrentMatrix.getValues(values);

				scale = checkMaxScale(scale, values);

				//PointF centerF=getCenter(scale,values);
				mCurrentMatrix.postScale(scale, scale,0,0);
				zoombitmap(scale);
				setImageMatrix(mCurrentMatrix);
				cScale = scale;
				if(cScale > 1.2){

				}
				ofx = 0;
				ofy = 0;

			}
		}

		/**
		 *  获取缩放的中心点。
		 *  @param scale
		 *  @param values
		 *  @return
		 */
		private PointF getCenter(float scale,float[] values) {
			//缩放级别小于原始缩放级别时或者为放大状态时，返回ImageView中心点作为缩放中心点
			if(scale*values[Matrix.MSCALE_X]<mScale||scale>=1){
				return new PointF(getWidth()/2,getHeight()/2);
			}
			float cx=getWidth()/2;
			float cy=getHeight()/2;
			//以ImageView中心点为缩放中心，判断缩放后的图片左边缘是否会离开ImageView左边缘，是的话以左边缘为X轴中心
			if((getWidth()/2-values[Matrix.MTRANS_X])*scale<getWidth()/2)
				cx=0;
			//判断缩放后的右边缘是否会离开ImageView右边缘，是的话以右边缘为X轴中心
			if((mImageWidth*values[Matrix.MSCALE_X]+values[Matrix.MTRANS_X])*scale<getWidth())
				cx=getWidth();
			return new PointF(cx,cy);
		}

		/**
		 *  检验scale，使图像缩放后不会超出最大倍数
		 *  @param scale
		 *  @param values
		 *  @return
		 */
		private float checkMaxScale(float scale, float[] values) {
//			if(scale*values[Matrix.MSCALE_X]>mMaxScale)
//				scale=mMaxScale/values[Matrix.MSCALE_X];
//			if(scale*values[Matrix.MSCALE_X]<mMinScale)
//				scale=mMinScale/values[Matrix.MSCALE_X];
			if(scale<mMinScale) scale = mMinScale;
			if(scale>mMaxScale) scale=mMaxScale;
			return scale;
		}

		/**
		 *   重置Matrix
		 */
		private void reSetMatrix() {
			if(checkRest()){
				mCurrentMatrix.set(mMatrix);
				setImageMatrix(mCurrentMatrix);
			}else {
				//判断Y轴是否需要更正
				float[] values=new float[9];
				getImageMatrix().getValues(values);
				float height=mImageHeight*values[Matrix.MSCALE_Y];
				if(height<getHeight()){
					//在图片真实高度小于容器高度时，Y轴居中，Y轴理想偏移量为两者高度差/2，
					float topMargin=(getHeight()-height)/2;
					if(topMargin!=values[Matrix.MTRANS_Y]){
						mCurrentMatrix.set(getImageMatrix());
						mCurrentMatrix.postTranslate(0, topMargin-values[Matrix.MTRANS_Y]);
						setImageMatrix(mCurrentMatrix);
					}
				}
			}
		}

		/**
		 *  判断是否需要重置
		 *  @return  当前缩放级别小于模板缩放级别时，重置
		 */
		private boolean checkRest() {
			// TODO Auto-generated method stub
			float[] values=new float[9];
			getImageMatrix().getValues(values);
			//获取当前X轴缩放级别
			float scale=values[Matrix.MSCALE_X];
			//获取模板的X轴缩放级别，两者做比较
			return scale<mScale;
		}

		/**
		 *  判断是否支持Matrix
		 */
		private void isMatrixEnable() {
			//当加载出错时，不可缩放
			if(getScaleType()!=ScaleType.CENTER){
				setScaleType(ScaleType.MATRIX);
			}else {
				mMode=MODE_UNABLE;//设置为不支持手势
			}
		}

		/**
		 *  计算两个手指间的距离
		 *  @param event
		 *  @return
		 */
		public float distance(MotionEvent event) {
			float dx = event.getX(1) - event.getX(0);
			float dy = event.getY(1) - event.getY(0);
			/** 使用勾股定理返回两点之间的距离 */
			return (float) Math.sqrt(dx * dx + dy * dy);
		}

		/**
		 *   双击时触发
		 */
		public void onDoubleClick(){
			float scale=isZoomChanged()?1:mDobleClickScale;
			mCurrentMatrix.set(mMatrix);//初始化Matrix
			mCurrentMatrix.postScale(scale, scale,0,0);
			setImageMatrix(mCurrentMatrix);
			zoombitmap(scale);
			cScale = scale;
			if(cScale == 1){
				ofx=ofy=0;
			}
		}
	}


	private class  GestureListener extends GestureDetector.SimpleOnGestureListener {
		private final MatrixTouchListener listener;

		public GestureListener(MatrixTouchListener listener) {
			this.listener=listener;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			//捕获Down事件
			return true;
		}
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			//触发双击事件
			listener.onDoubleClick();
			return false;
		}
		@Override
		public boolean onSingleTapUp(MotionEvent event) {

			float eventX = event.getX();
			float eventY = event.getY();
//			float[] eventXY = new float[] {eventX, eventY};
//			Matrix invertMatrix = new Matrix();
//			getImageMatrix().invert(invertMatrix);
//			invertMatrix.mapPoints(eventXY);
//
//			int x = Integer.valueOf((int)eventXY[0]);
//			int y = Integer.valueOf((int)eventXY[1]);
//			clickx = Px2Dp(context,eventX);
//			clicky = Px2Dp(context,eventY);
			clickx = eventX;
			clicky = eventY;
			isonchenck(clickx,clicky);

			// point 勾图像的中心
			int x = (int) ((clickx - ofx - bitmap1.getWidth()/2)/(cScale * getWidth()/1000f ));
			int y = (int) ((clicky - ofy - bitmap1.getHeight()/2)/(cScale*getHeight()/1000f ));

			Log.d(TAG, "onSingleTapUp: x " + x +"  y "+y);

			invalidate();
			return false;
		}



		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
								float distanceX, float distanceY) {
//			float[] values = new float[9];
//			getImageMatrix().getValues(values);
//
//			float ox = ofx,oy = ofy;
//			ofx = ofx - distanceX;
//			ofy = ofy - distanceY;
//
//			//图片左边缘离开左边界，表示不可右移
//			if(values[Matrix.MTRANS_X]>=0)
//				ofx = ox;
//			//图片右边缘离开右边界，表示不可左移
//			if((mImageWidth)*values[Matrix.MSCALE_X]+values[Matrix.MTRANS_X]<=getWidth())
//				ofx = ox;
//
//			if(values[Matrix.MTRANS_Y] >= 0)
//				ofy = oy;
//			if((mImageHeight)*values[Matrix.MSCALE_Y]+values[Matrix.MTRANS_Y]<=getHeight())
//				ofy = oy;
			return false;
			//return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
							   float velocityY) {
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			super.onShowPress(e);
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			// TODO Auto-generated method stub
			return super.onDoubleTapEvent(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if(singleTapListener!=null) singleTapListener.onSingleTap();
			return super.onSingleTapConfirmed(e);
		}


	}
	/**
	 * @ClassName: OnChildMovingListener
	 * @Description:  MatrixImageView移动监听接口,用以组织ViewPager对Move操作的拦截
	 * @author LinJ
	 * @date 2015-1-12 下午4:39:32
	 *
	 */
	public interface OnMovingListener{
		public void  startDrag();
		public void  stopDrag();
	}

	/**
	 * @ClassName: OnSingleTapListener
	 * @Description:  监听ViewPager屏幕单击事件，本质是监听子控件MatrixImageView的单击事件
	 * @author LinJ
	 * @date 2015-1-12 下午4:48:52
	 *
	 */
	public interface OnSingleTapListener{
		public void onSingleTap();
	}

	public Dialog createdialog(String title, DialogInterface.OnClickListener click1){
//		AlertDialog.Builder builder = new AlertDialog.Builder(context)
//				.setTitle(title)
//				.setPositiveButton("确定",click1);
//
//		Dialog dialog = builder.create();
//		return dialog;
		CustomDialog.Builder builder = new CustomDialog.Builder(context)
				.setMessage(title)
				.setPositiveButton(click1);
		CustomDialog dialog = builder.create();
		return dialog;
	}

}
