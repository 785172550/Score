package entity;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 *
 */

@Table
public class Patient extends SugarRecord implements Serializable{


	//private Long id;
	 //基本信息
	private String name;
	//编号
	@Unique
	private String num;
	//造影日期
	private String date;
	//性别
	private String gender;
	//年龄
	private int age;

	//测评信息  I 得分
	private double syntax1;

	// II 二 得分
	private double PCI;
	private double PCI_Death;
	private double CABG;
	private double CABG_Death;
	//推荐治疗
	private String rec_ope;

	// III 三 得分
	private String syntax3;

	// IV 四 得分
	private double syntax4;

	private String choice = "无";

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	// You must provide an empty constructor
	public Patient() {
	}

//	public Long getId() {
//		return id;
//	}

	public Patient(String name, String num, int age, String gender, String date) {
		this.name = name;
		this.num = num;
		this.date = date;
		this.gender = gender;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSyntax1() {
		return syntax1;
	}

	public void setSyntax1(double syntax1) {
		this.syntax1 = syntax1;
	}

	public double getPCI() {
		return PCI;
	}

	public void setPCI(double PCI) {
		this.PCI = PCI;
	}

	public double getPCI_Death() {
		return PCI_Death;
	}

	public void setPCI_Death(double PCI_Death) {
		this.PCI_Death = PCI_Death;
	}

	public double getCABG() {
		return CABG;
	}

	public void setCABG(double CABG) {
		this.CABG = CABG;
	}

	public double getCABG_Death() {
		return CABG_Death;
	}

	public void setCABG_Death(double CABG_Death) {
		this.CABG_Death = CABG_Death;
	}

	public String getRec_ope() {
		return rec_ope;
	}

	public void setRec_ope(String rec_ope) {
		this.rec_ope = rec_ope;
	}

	public String getSyntax3() {
		return syntax3;
	}

	public void setSyntax3(String syntax3) {
		this.syntax3 = syntax3;
	}

	public double getSyntax4() {
		return syntax4;
	}

	public void setSyntax4(double syntax4) {
		this.syntax4 = syntax4;
	}


	public List<ImagePath> getImg(){
		return ImagePath.find(ImagePath.class,"num = ?",getNum());
	}

	@Override
	public String toString() {
		return "Patient{" +
				"name='" + name + '\'' +
				", num='" + num + '\'' +
				", date='" + date + '\'' +
				", gender='" + gender + '\'' +
				", age=" + age +
				", syntax1=" + syntax1 +
				", PCI=" + PCI +
				", PCI_Death=" + PCI_Death +
				", CABG=" + CABG +
				", CABG_Death=" + CABG_Death +
				", rec_ope='" + rec_ope + '\'' +
				", syntax3='" + syntax3 + '\'' +
				", syntax4=" + syntax4 +
				", choice='" + choice + '\'' +
				'}';
	}
}
