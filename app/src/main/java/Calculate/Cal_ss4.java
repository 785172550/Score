package Calculate;

/**
 * Created by Administrator on 2016/6/18.
 */
public class Cal_ss4 {

	public double calculate(double [] data){

		double sum = 0;
		for(int i = 0; i<data.length;i++){
			sum += data[i];
		}

		return sum;
	}
}
