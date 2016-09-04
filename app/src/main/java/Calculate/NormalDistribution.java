package Calculate;

/**
 * Created by Administrator on 2016/6/16.
 */
public class NormalDistribution {

	double _mean, _sigma;

	public NormalDistribution(int mean, int sigma) {
		this._mean = mean;
		this._sigma = sigma;
	}

//	public double _pdf(x, give_log) {
//		if (give_log == null) {
//			give_log = false;
//		}  // default is false;
//		var sigma = this._sigma;
//		var mu = this._mean;
//		if (!jstat.isFinite(sigma)) {
//			return (give_log) ? Number.NEGATIVE_INFINITY : 0.0
//		}
//		if (!jstat.isFinite(x) && mu == x) {
//			return Number.NaN;
//		}
//		if (sigma <= 0) {
//			if (sigma < 0) {
//				throw "invalid sigma in _pdf";
//			}
//			return (x == mu) ? Number.POSITIVE_INFINITY : (give_log) ? Number.NEGATIVE_INFINITY : 0.0;
//		}
//		x = (x - mu) / sigma;
//		if (!jstat.isFinite(x)) {
//			return (give_log) ? Number.NEGATIVE_INFINITY : 0.0;
//		}
//		return (give_log ? -(jstat.LN_SQRT_2PI + 0.5 * x * x + Math.log(sigma)) :
//				jstat.ONE_SQRT_2PI * Math.exp(-0.5 * x * x) / sigma);
//	}


	/*
	*    -CDF 函数
	* */

	public double _cdf(double x, boolean lower_tail, boolean log_p) {
//		if (lower_tail == null)
//				lower_tail = true;
//		if (log_p == null) log_p = false;

		/**/
		double p, cp=0;
		double mu = this._mean;
		double sigma = this._sigma;
		double R_DT_0, R_DT_1;

		if (lower_tail) {
			if (log_p) {
				R_DT_0 = Double.NEGATIVE_INFINITY;
				R_DT_1 = 0.0;
			} else {
				R_DT_0 = 0.0;
				R_DT_1 = 1.0;
			}
		} else {
			if (log_p) {
				R_DT_0 = 0.0;
				R_DT_1 = Double.NEGATIVE_INFINITY;
			} else {
				R_DT_0 = 1.0;
				R_DT_1 = 0.0;
			}
		}

//		if (!jstat.isFinite(x) && mu == x) return Number.NaN;
		if (!jstat.isFinite(x) && mu == x) return Double.NaN;
		if (sigma <= 0) {
			if (sigma < 0) {
				//console.warn("Sigma is less than 0");
				return  Double.NaN;
			}
			return (x < mu) ? R_DT_0 : R_DT_1;
		}

		p = (x - mu) / sigma;

		if (!jstat.isFinite(p)) {
			return (x < mu) ? R_DT_0 : R_DT_1;
		}

		x = p;

		// pnorm_both(x, &p, &cp, (lower_tail ? 0 : 1), log_p);
		// result[0] == &p
		// result[1] == &cp

		//-----------
		double[] result = pnorm_both(x, p, cp, (lower_tail ? false : true), log_p);

		return (lower_tail ? result[0] : result[1]);
	}

	/*
	*  pnorm_both 函数
	* */

	public double[] pnorm_both (double x,double cum,double ccum,boolean i_tail,boolean log_p){
			/*  i_tail in {0,1,2} means: "lower", "upper", or "both" :
				if(lower) return  *cum := P[X <= x]
                if(upper) return *ccum := P[X >  x] = 1 - P[X <= x]
             */

		double[] a = {
				2.2352520354606839287,
				161.02823106855587881,
				1067.6894854603709582,
				18154.981253343561249,
				0.065682337918207449113
		};
		double[] b = {
				47.20258190468824187,
				976.09855173777669322,
				10260.932208618978205,
				45507.789335026729956
		};
		double[] c = {
				0.39894151208813466764,
				8.8831497943883759412,
				93.506656132177855979,
				597.27027639480026226,
				2494.5375852903726711,
				6848.1904505362823326,
				11602.651437647350124,
				9842.7148383839780218,
				1.0765576773720192317e-8
		};
		double[] d = {
				22.266688044328115691,
				235.38790178262499861,
				1519.377599407554805,
				6485.558298266760755,
				18615.571640885098091,
				34900.952721145977266,
				38912.003286093271411,
				19685.429676859990727
		};
		double[] p = {
				0.21589853405795699,
				0.1274011611602473639,
				0.022235277870649807,
				0.001421619193227893466,
				2.9112874951168792e-5,
				0.02307344176494017303
		};
		double[] q = {
				1.28426009614491121,
				0.468238212480865118,
				0.0659881378689285515,
				0.00378239633202758244,
				7.29751555083966205e-5
		};

		//
		double xden, xnum, temp, del, eps, xsq, y, i;
		boolean lower, upper;

            /* Consider changing these : */
		// jstat.DBL_EPSILON
		eps =  2.220446049250313e-16 * 0.5;

            /* i_tail in {0,1,2} =^= {lower, upper, both} */
		lower = i_tail != true;
		upper = i_tail != false;

		y = Math.abs(x);

		if (y <= 0.67448975) { /* qnorm(3/4) = .6744.... -- earlier had 0.66291 */
			if (y > eps) {
				xsq = x * x;
				xnum = a[4] * xsq;
				xden = xsq;
				for (int ii = 0; ii < 3; ++ii) {
					xnum = (xnum + a[ii]) * xsq;
					xden = (xden + b[ii]) * xsq;
				}
			} else {
				xnum = xden = 0.0;
			}
			temp = x * (xnum + a[3]) / (xden + b[3]);
			if (lower) cum = 0.5 + temp;
			if (upper) ccum = 0.5 - temp;
			if (log_p) {
				if (lower) cum = Math.log(cum);
				if (upper) ccum = Math.log(ccum);
			}

		} else if (y <= jstat.SQRT_32) {
                /* Evaluate pnorm for 0.674.. = qnorm(3/4) < |x| <= sqrt(32) ~= 5.657 */

			xnum = c[8] * y;
			xden = y;
			for (int ii = 0; ii < 7; ++ii) {
				xnum = (xnum + c[ii]) * y;
				xden = (xden + d[ii]) * y;
			}
			temp = (xnum + c[7]) / (xden + d[7]);

                /* do_del */
			xsq = jstat.trunc(x * 16) / 16;
			del = (x - xsq) * (x + xsq);
			if (log_p) {
				cum = (-xsq * xsq * 0.5) + (-del * 0.5) + Math.log(temp);
				if ((lower && x > 0.) || (upper && x <= 0.))
					ccum = jstat.log1p(-Math.exp(-xsq * xsq * 0.5) *
							Math.exp(-del * 0.5) * temp);
			} else {
				cum = Math.exp(-xsq * xsq * 0.5) * Math.exp(-del * 0.5) * temp;
				ccum = 1.0 - cum;
			}
                /* end do_del */

                /* swap_tail */
			if (x > 0.0) {/* swap  ccum <--> cum */
				temp = cum;
				if (lower) {
					cum = ccum;
				}
				ccum = temp;
			}
            /* end swap_tail */

		}
            /* else	  |x| > sqrt(32) = 5.657 :
             * the next two case differentiations were really for lower=T, log=F
             * Particularly	 *not*	for  log_p !

             * Cody had (-37.5193 < x  &&  x < 8.2924) ; R originally had y < 50
             *
             * Note that we do want symmetry(0), lower/upper -> hence use y
             */

		else if ((log_p && y < 1e170) || (lower && -37.5193 < x && x < 8.2924)
				|| (upper && -8.2924 < x && x < 37.5193)) {
                /* Evaluate pnorm for x in (-37.5, -5.657) union (5.657, 37.5) */
			xsq = 1.0 / (x * x); /* (1./x)*(1./x) might be better */
			xnum = p[5] * xsq;
			xden = xsq;
			for (int ii = 0; ii < 4; ++ii) {
				xnum = (xnum + p[ii]) * xsq;
				xden = (xden + q[ii]) * xsq;
			}
			temp = xsq * (xnum + p[4]) / (xden + q[4]);
			temp = (jstat.ONE_SQRT_2PI - temp) / y;

                /* do_del */
			xsq = jstat.trunc(x * 16) / 16;
			del = (x - xsq) * (x + xsq);
			if (log_p) {
				cum = (-xsq * xsq * 0.5) + (-del * 0.5) + Math.log(temp);
				if ((lower && x > 0.) || (upper && x <= 0.))
					ccum = jstat.log1p(-Math.exp(-xsq * xsq * 0.5) *
							Math.exp(-del * 0.5) * temp);
			} else {
				cum = Math.exp(-xsq * xsq * 0.5) * Math.exp(-del * 0.5) * temp;
				ccum = 1.0 - cum;
			}
                /* end do_del */

                /* swap_tail */
			if (x > 0.0) {/* swap  ccum <--> cum */
				temp = cum;
				if (lower) {
					cum = ccum;

				}
				ccum = temp;
			}
            /* end swap_tail */

		} else { /* large x such that probs are 0 or 1 */
			if (x > 0) {
				cum = (log_p) ? 0.0 : 1.0;  // R_D__1
				ccum = (log_p) ? Double.NEGATIVE_INFINITY : 0.0;  //R_D__0;
			} else {
				cum = (log_p) ? Double.NEGATIVE_INFINITY : 0.0;  //R_D__0;
				ccum = (log_p) ? 0.0 : 1.0;  // R_D__1
			}
		}

		return new double[]{cum, ccum};
	}

}
