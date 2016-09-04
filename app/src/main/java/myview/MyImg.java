package myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/6/25.
 */
public class MyImg extends cn.finalteam.galleryfinal.widget.zoonview.PhotoView {



	public MyImg(Context context, AttributeSet attrs) {
		super(context, attrs);
		//init();
	}

	public MyImg(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//init();
	}

	Point[] points = new Point[]{
			new Point(228, 55), new Point(196, 247), new Point(287, 375), new Point(151, 492),
			new Point(289, 533), new Point(453, 622), new Point(509, 819), new Point(445, 507),
			new Point(526, 562), new Point(560, 629), new Point(570, 691), new Point(172, 552),
			new Point(249, 575), new Point(262, 627), new Point(264, 684), new Point(146, 684),
			new Point(210, 710), new Point(234, 761), new Point(276, 808), new Point(336, 850),
	};
	int[] item = new int[points.length];

	Point[] points2 = new Point[]{

	};



//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		/*switch (event.getAction()){
//			case MotionEvent.ACTION_UP:*/
//				Log.d(TAG, "onTouchEvent: " + event.getX() + "  " + event.getY());
//
////				for (int i = 0;i<points.length;i++){
////					if(c.in_cricle(points[i]) && item[i] !=1){
////						item[i] = 1;
////						break;
////					}else if(c.in_cricle(points[i]) && item[i] == 1){
////						item[i] = 0;
////						break;
////					}
////				}
////				break;
////		}
//		return super.onTouchEvent(event);
//	}



	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	public int[] getitem(){
		return item;
	}

}
