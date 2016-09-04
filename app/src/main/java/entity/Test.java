package entity;

import com.orm.SugarRecord;

/**
 * Created by Administrator on 2016/6/22.
 */
public class Test extends SugarRecord {

	// You must provide an empty constructor
	public Test() {
	}

	private String name;
	private String pass;
	private String score;
	private String nn;

	public Test(String name, String pass, String score) {
		this.name = name;
		this.pass = pass;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getNn() {
		return nn;
	}

	public void setNn(String nn) {
		this.nn = nn;
	}
}
