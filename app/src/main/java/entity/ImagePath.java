package entity;

import com.orm.SugarRecord;

/**
 * Created by Administrator on 2016/6/20.
 */
public class ImagePath extends SugarRecord{

	String path;
	private Patient patient;
	private String num;

	public String getnum() {
		return num;
	}

	public void setnum(String num) {
		this.num = num;
	}

	// You must provide an empty constructor
	public ImagePath() {
	}

	public ImagePath(String path) {
		this.path = path;
	}

	public ImagePath(String path, Patient patient) {

		this.patient = patient;
		this.path = path;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
