package entity;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by Administrator on 2016/6/21.
 */
@Table
public class User extends SugarRecord{

	private long id;

	// 手机号就是用户名
	private String phone;

	private String passwd;
	private String name;
	private String hospital;
	private String room;

	public User() {
	}

	public User(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
}
