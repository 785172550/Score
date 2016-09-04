package entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/11.
 */
public class DiseasePosition implements Serializable{

	int num;
	boolean isleft;
	private String name;
	private String level;
	private String fenshu;
	private String cname;

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean isleft() {
		return isleft;
	}

	public void setIsleft(boolean isleft) {
		this.isleft = isleft;
	}

	public DiseasePosition(int num, boolean isleft, String name, String cname,String level, String fenshu) {
		this.num = num;
		this.isleft = isleft;
		this.cname = cname;
		this.name = name;
		this.level = level;
		this.fenshu = fenshu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFenshu() {
		return fenshu;
	}

	public void setFenshu(String fenshu) {
		this.fenshu = fenshu;
	}
}
