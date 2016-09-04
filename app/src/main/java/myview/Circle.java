package myview;

import android.graphics.Point;

/**
 * Created by Administrator on 2016/6/25.
 */
public class Circle{

	//Point point;
	double x,y,x1,y1;
	int R = 12;

	public Circle(double x,double y) {
		this.x = x;
		this.y = y;
	}

	public boolean in_cricle(Point point){
		x1 = point.x;
		y1 = point.y;
		if(Math.abs(x1 - x) < R && Math.abs(x1- x) < R) return true;
		return false;
	}
}
