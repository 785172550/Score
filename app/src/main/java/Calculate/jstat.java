package Calculate;

/**
 * Created by Administrator on 2016/6/16.
 *
 */
public class jstat {

	public static final double SQRT_32 = 5.656854249492380195206754896838;
	public static final double ONE_SQRT_2PI = 0.3989422804014327;

	public static double log1p(double x) {

		// http://kevin.vanzonneveld.net
		// +   original by: Brett Zamir (http://brett-zamir.me)
		// %          note 1: Precision 'n' can be adjusted as desired
		// *     example 1: log1p(1e-15);
		// *     returns 1: 9.999999999999995e-16

		double ret = 0,
				n = 50; // degree of precision
		if (x <= -1) {
			return Double.NEGATIVE_INFINITY; // JavaScript style would be to return Number.NEGATIVE_INFINITY
		}
		if (x < 0 || x > 1) {
			return Math.log(1 + x);
		}
		for (int  i = 1; i < n; i++) {
			if ((i % 2) == 0) {
				ret -= Math.pow(x, i) / i;
			} else {
				ret += Math.pow(x, i) / i;
			}
		}
		return ret;
	}

	public static double trunc(double x) {
		return (x > 0) ? Math.floor(x) : Math.ceil(x);
	}


	public static boolean isFinite(double x) {
		return (!Double.isNaN(x) && (x != Double.POSITIVE_INFINITY) && (x != Double.NEGATIVE_INFINITY));
	}
}
