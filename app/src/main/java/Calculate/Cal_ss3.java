package Calculate;

import entity.Patient;

/**
 * Created by Administrator on 2016/6/17.
 */
public class Cal_ss3 {

	public static double Gender = 0.2196434;
	public static double Extracardiac_arteriopathy = 0.5360268;
	public static double Poor_mobility = 0.2407181;
	public static double Previous_cardiac_surgery = 1.118599 ;
	public static double Chronic_lung_disease = 0.1886564;
	public static double Active_endocarditis = 0.6194522;
	public static double Critical_preoperative_state = 1.086517;
	public static double Diabetes_on_insulin = 0.3542749;
	public static double CCS_class_4_angina = 0.2226147;
	public static double Recent_MI = 0.1528943;
	public static double Surgery_on_thoracic_aorta = 0.6527205;

	public static double[] Renal_impairment = {0,0.303553,0.8592256,0.6421508};
	public static double[] NYHA ={0,0.1070545,0.2958358,0.5597929};

	public static double[] LV_function = {0,0.3150652,0.8084096,0.9346919};
	public static double[] Pulmonary_hypertension ={0,0.1788899,0.3491475};
	public static double[] Urgency = {0,0.3174673,0.7039121,1.362947};
	public static double[] Weight_of_the_intervention = {0,0.0062118,0.5521478,0.9724533};

	Patient patient;

	public String CalcMort(int [] data, boolean[]data2, boolean[] data3, double sex, Patient patient){

		// 添加

		this.patient = patient;
		int age = patient.getAge();

		double ri = Renal_impairment[data[0]];
		double ny = NYHA[data[1]];
		double lv = LV_function[data[2]];
		double ph = Pulmonary_hypertension[data[3]];
		double ur = Urgency[data[4]];
		double wo = Weight_of_the_intervention[data[5]];

		CCS_class_4_angina = data2[0] ? CCS_class_4_angina:0;
		Recent_MI = data2[1] ? Recent_MI:0;
		Surgery_on_thoracic_aorta = data2[2] ? Surgery_on_thoracic_aorta:0;

		Extracardiac_arteriopathy = data3[0] ? Extracardiac_arteriopathy:0;
		Poor_mobility = data3[1] ? Poor_mobility:0;
		Previous_cardiac_surgery = data3[2] ? Previous_cardiac_surgery:0;
		Chronic_lung_disease = data3[3] ? Chronic_lung_disease:0;
		Active_endocarditis = data3[4] ? Active_endocarditis:0;
		Critical_preoperative_state = data3[5] ? Critical_preoperative_state:0;
		Diabetes_on_insulin = data3[6] ? Diabetes_on_insulin:0;

		// 17个数据相加
		double z = ri + ny + lv + ph + ur + wo + CCS_class_4_angina+ Recent_MI +Surgery_on_thoracic_aorta
		+Extracardiac_arteriopathy +Poor_mobility +Previous_cardiac_surgery +Chronic_lung_disease
		+Active_endocarditis + Critical_preoperative_state + Diabetes_on_insulin+sex;

		if(age>90){

		}
		double t = age+1;
		t = t*0.0285181;
		if (t<=1.711086) {t=0.0285181;}
		else {t = t-60*0.0285181;}
		//age = Fmt(t);
		z = t + z;
		z = z-5.324537;
		z = Math.exp(z) / (1 + Math.exp(z));
		String s_z = Fmt(100 * z) + " %";
		return s_z;
	}
	public static String Fmt(double x) {
		String v;
		if(x>=0) { v=""+(x+0.005) ;}
		else { v="" +(x-0.005); }
		return v.substring(0,v.indexOf('.')+3);
	}


}
