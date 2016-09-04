package Calculate;


import android.util.Log;

import Jama.Matrix;

/**
 * Created by Administrator on 2016/6/15.
 */
public class Cal_ss2 {

	double matrix_T[][] = {
			{4.7974789000000, -0.0022691742000, -0.0412830750000, -0.0142098030000, -0.0157607790000, 0.0164216910000, -0.1322081600000, -0.0297472900000, -0.0724848320000},
			{-0.0022691742000, 0.0001755837300, -0.0000398184210, -0.0000038640218, 0.0000102745950, -0.0004595223300, -0.0004221792200, -0.0001866830600, -0.0002317636300},
			{-0.0412830750000, -0.0000398184210, 0.0005058653700, 0.0001318949600, -0.0000421320290, -0.0004911125000, 0.0003503229800, -0.0000404428970, 0.0001878566500},
			{-0.0142098030000, -0.0000038640218, 0.0001318949600, 0.0001125569900, -0.0000437744760, -0.0000962177520, -0.0006560432700, 0.0000695365610, 0.0004395860700},
			{-0.0157607790000, 0.0000102745950, -0.0000421320290, -0.0000437744760, 0.0004418016100, -0.0002319316200, 0.0010977041000, 0.0001489606400, 0.0002052775800},
			{0.0164216910000, -0.0004595223300, -0.0004911125000, -0.0000962177520, -0.0002319316200, 0.0993969360000, 0.0074237030000, 0.0057716330000, -0.0039146463000},
			{-0.1322081600000, -0.0004221792200, 0.0003503229800, -0.0006560432700, 0.0010977041000, 0.0074237030000, 0.1462399900000, -0.0021780574000, -0.0018524115000},
			{-0.0297472900000, -0.0001866830600, -0.0000404428970, 0.0000695365610, 0.0001489606400, 0.0057716330000, -0.0021780574000, 0.1720792500000, -0.0184643010000},
			{-0.0724848320000, -0.0002317636300, 0.0001878566500, 0.0004395860700, 0.0002052775800, -0.0039146463000, -0.0018524115000, -0.0184643010000, 0.1318248300000}
	};

	double[] gamma_PCI = {
			5.9363531000000,
			0.0239369855000,
			0.0255030450000,
			-0.0203536122000,
			-0.0578258800000,
			-0.2015715800000,
			-0.5314724800000,
			0.3023548900000,
			1.0269338356630
	};

	//used for step 3
	double[] gamma_CABG =
		{
			5.936353100000,
			-0.003465218500,
			0.063047153000,
			-0.009266941200,
			-0.017106187000,
			0.386125340000,
			0.521747420000,
			1.042834200000,
			1.026892800000
		};

	//used for step 4
	double nomogram_intercept = -2.5885388;
	double nomogram_slope = 0.084062871;
	double cum_haz = 0.075349171;

	// step 2 - declaration
//	double[] z_PCI;
//	double[] z_CABG;
//	double[] z_delta;
	double se_delta;

	// step 3 - declaration
	double lp_CABG;
	double lp_center = 3.4210909;
	double lp_delta;
	double lp_PCI;

	// step 4 declaration (result)
	double ss2_PCI;
	double ss2_CABG;
	double M_CABG;
	double M_PCI;
	double p_value;

	// calculator switch double
	boolean calcVisible = false;

	public double[] calcSyntaxScore2(double ss2Syntaxscore1, int age, int crcl, int lvef, int lms, int gender, int copd, int pvd) {
//		// fetch input
//		var ss2Syntaxscore1 = getElementInForm('right', 'syntaxScore2Form', 'ss2SyntaxScore1').value;
//		var age = getElementInForm('right', 'syntaxScore2Form', 'age').value;
//		var gender = getCheckedValue(getElementInForm('right', 'syntaxScore2Form', 'gender'));
//		var crclUnity = getCheckedValue(getElementInForm('right', 'syntaxScore2Form', 'crclUnity'));
//		var crcl = getCrcl(age, gender, crclUnity);
//		var lvef = getElementInForm('right', 'syntaxScore2Form', 'lvef').value;
//		var lms = getCheckedValue(getElementInForm('right', 'syntaxScore2Form', 'leftMain'));
//		var copd = getCheckedValue(getElementInForm('right', 'syntaxScore2Form', 'copd'));
//		var pvd = getCheckedValue(getElementInForm('right', 'syntaxScore2Form', 'pvd'));

		// calc step 2
		double[] z_PCI = {1, ss2Syntaxscore1, age, Math.min(crcl, 90), Math.min(lvef, 50),
				lms, gender, copd, pvd};
		double[] z_CABG = {0, ss2Syntaxscore1, age, Math.min(crcl, 90), Math.min(lvef, 50),
				lms, gender, copd, pvd};
		double[] z_delta = {1, ss2Syntaxscore1, age, Math.min(crcl, 90), Math.min(lvef, 50),
				lms, gender, copd, pvd};

		// calc step 3
//		lp_PCI = z_PCI.dot(gamma_PCI);
//		lp_CABG = z_CABG.dot(gamma_CABG);
		lp_PCI = dot(z_PCI, gamma_PCI);
		lp_CABG = dot(z_CABG, gamma_CABG);

		Matrix matrix_t = new Matrix(matrix_T);
		double[][] z_delta_2 = {{1, ss2Syntaxscore1, age, Math.min(crcl, 90), Math.min(lvef, 50),
				lms, gender, copd, pvd}};

		Matrix z_delta_t = new Matrix(z_delta_2);

		lp_delta = lp_PCI - lp_CABG;
		se_delta = Math.sqrt(dot(z_delta, z_delta_t.times(matrix_t.transpose()).getArray()[0]));
		//se_delta = Math.sqrt(z_delta.dot(matrix_T.transpose().multiply(z_delta)));

		// calc step 4 (results)
		ss2_PCI = ((lp_PCI - lp_center - nomogram_intercept) / nomogram_slope);
		ss2_CABG = ((lp_CABG - lp_center - nomogram_intercept) / nomogram_slope);
		M_PCI = 100 * (1 - Math.exp(-cum_haz * Math.exp(lp_PCI - lp_center)));
		M_CABG = 100 * (1 - Math.exp(-cum_haz * Math.exp(lp_CABG - lp_center)));

		// p_value
		NormalDistribution n = new NormalDistribution(0, 1);
		p_value = n._cdf(-Math.abs(lp_delta) / se_delta, true, false);

		Log.d("-------", ""+ss2_PCI+ss2_CABG+M_PCI+M_CABG+p_value);

		return new double[]{ss2_PCI,ss2_CABG,M_PCI,M_CABG,p_value};
		/*parent.meScore2PValue = p_value;
		parent.meScore2;*/
	}

	public double dot(double[] v1, double[] v2) {
		double sum = 0;
		for (int i = 0; i < v1.length; i++) {
			sum += v1[i] * v2[i];
		}
		return sum;
	}


}
