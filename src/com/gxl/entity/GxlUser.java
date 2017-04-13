package com.gxl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="gxl_user")
public class GxlUser implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="gxlid")
	private Integer gxlid;
	
	@Column(name="gxl_account")
	private String gxl_account;
	
	@Column(name="IM_name")
	private String im_name;
	
	@Column(name="nickname")
	private String nickname;
	
	@Column(name="card_type")
	private Integer card_type;
	
	@Column(name="country")
	private String country;
	
	@Column(name="province")
	private String province;
	
	@Column(name="city")
	private String city;
	
	@Column(name="code")
	private String code;
	
	@Column(name="birthday")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	
	@Column(name="address")
	private String address;
	
	@Column(name="signature")
	private String signature;
	
	@Column(name="head_img")
	private String head_img;
	
	@Column(name="sex")
	private Integer sex;
	
	@Column(name="password")
	private String password;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="age")
	private Integer age;
	
	@Column(name="qr_code")
	private String qr_code;
	
	@Column(name="id_card")
	private String id_card;
	
	@Column(name="partnership_name")
	private String partnership_name;
	
	@Column(name="partnership_detail")
	private String partnership_detail;
	
	@Column(name="job")
	private String job;
	
	@Column(name="blood")
	private String blood;
	
	@Column(name="weibo")
	private String weibo;
	
	@Column(name="qq")
	private String qq;
	
	@Column(name="weixin")
	private String weixin;
	
	@Column(name="homepage")
	private String homepage;
	
	@Column(name="person_extend")
	private String person_extend;
	
	@Column(name="connect_extend")
	private String connect_extend;
	
	@Column(name="other_extend")
	private String other_extend;
	
	@Column(name="if_del")
	private Integer if_del;
	
	@Column(name="uDate")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date uDate;

	

	public GxlUser(Integer gxlid, String gxl_account, String im_name, String nickname, Integer card_type, String country,
			String province,String city,String code, Date birthday, String address, String signature, String head_img, Integer sex, String password,
			String phone, String email, Integer age, String qr_code, String id_card, String partnership_name,
			String partnership_detail, String job, String blood, String weibo, String qq, String weixin,
			String homepage, String person_extend,String connect_extend,String other_extend, Integer if_del, Date uDate) {
		super();
		this.gxlid = gxlid;
		this.gxl_account = gxl_account;
		this.im_name = im_name;
		this.nickname = nickname;
		this.card_type = card_type;
		this.country=country;
		this.province=province;
		this.city=city;
		this.code = code;
		this.birthday = birthday;
		this.address = address;
		this.signature = signature;
		this.head_img = head_img;
		this.sex = sex;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.qr_code = qr_code;
		this.id_card = id_card;
		this.partnership_name = partnership_name;
		this.partnership_detail = partnership_detail;
		this.job = job;
		this.blood = blood;
		this.weibo = weibo;
		this.qq = qq;
		this.weixin = weixin;
		this.homepage = homepage;
		this.person_extend = person_extend;
		this.connect_extend=connect_extend;
		this.other_extend=other_extend;
		this.if_del = if_del;
		this.uDate = uDate;
	}

	
	public GxlUser(String gxl_account, String im_name, String nickname, Integer card_type, String country,String province,
			String city,String code,Date birthday, String address, String signature, String head_img, Integer sex, String password,
			String phone, String email, Integer age, String qr_code, String id_card, String partnership_name,
			String partnership_detail, String job, String blood, String weibo, String qq, String weixin,
			String homepage, String person_extend,String connect_extend,String other_extend, Integer if_del, Date uDate) {
		super();
		this.gxl_account = gxl_account;
		this.im_name = im_name;
		this.nickname = nickname;
		this.card_type = card_type;
		this.country=country;
		this.province=province;
		this.city=city;
		this.code = code;
		this.birthday = birthday;
		this.address = address;
		this.signature = signature;
		this.head_img = head_img;
		this.sex = sex;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.qr_code = qr_code;
		this.id_card = id_card;
		this.partnership_name = partnership_name;
		this.partnership_detail = partnership_detail;
		this.job = job;
		this.blood = blood;
		this.weibo = weibo;
		this.qq = qq;
		this.weixin = weixin;
		this.homepage = homepage;
		this.person_extend = person_extend;
		this.connect_extend=connect_extend;
		this.other_extend=other_extend;
		this.if_del = if_del;
		this.uDate = uDate;
	}


	public GxlUser() {
		super();
	}

	public Integer getGxlid() {
		return gxlid;
	}

	public void setGxlid(Integer gxlid) {
		this.gxlid = gxlid;
	}

	public String getGxl_account() {
		return gxl_account;
	}

	public void setGxl_account(String gxl_account) {
		this.gxl_account = gxl_account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getIf_del() {
		return if_del;
	}

	public void setIf_del(Integer if_del) {
		this.if_del = if_del;
	}

	public Date getuDate() {
		return uDate;
	}

	public void setuDate(Date uDate) {
		this.uDate = uDate;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getIm_name() {
		return im_name;
	}

	public void setIm_name(String im_name) {
		this.im_name = im_name;
	}


	public Integer getCard_type() {
		return card_type;
	}


	public void setCard_type(Integer card_type) {
		this.card_type = card_type;
	}

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public String getPartnership_name() {
		return partnership_name;
	}


	public void setPartnership_name(String partnership_name) {
		this.partnership_name = partnership_name;
	}


	public String getPartnership_detail() {
		return partnership_detail;
	}


	public void setPartnership_detail(String partnership_detail) {
		this.partnership_detail = partnership_detail;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public String getBlood() {
		return blood;
	}


	public void setBlood(String blood) {
		this.blood = blood;
	}


	public String getWeibo() {
		return weibo;
	}


	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}


	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}


	public String getWeixin() {
		return weixin;
	}


	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}


	public String getHomepage() {
		return homepage;
	}


	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}


	

	public String getPerson_extend() {
		return person_extend;
	}


	public void setPerson_extend(String person_extend) {
		this.person_extend = person_extend;
	}


	public String getConnect_extend() {
		return connect_extend;
	}


	public void setConnect_extend(String connect_extend) {
		this.connect_extend = connect_extend;
	}


	public String getOther_extend() {
		return other_extend;
	}


	public void setOther_extend(String other_extend) {
		this.other_extend = other_extend;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}
	
}
