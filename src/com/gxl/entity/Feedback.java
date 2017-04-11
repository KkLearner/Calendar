package com.gxl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="feedback")
public class Feedback implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="userid")
	private Integer userid;
	
	@Column(name="content")
	private String content;
	
	@Column(name="imgs")
	private String imgs;
	
	@Column(name="fault_time")
	private Date fault_time;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="uDate")
	private Date uDate;

	public Feedback(Integer id, Integer userid, String content, String imgs, Date fault_time, String phone,
			Date uDate) {
		super();
		this.id = id;
		this.userid = userid;
		this.content = content;
		this.imgs = imgs;
		this.fault_time = fault_time;
		this.phone = phone;
		this.uDate = uDate;
	}

	public Feedback(Integer userid, String content, String imgs, Date fault_time, String phone, Date uDate) {
		super();
		this.userid = userid;
		this.content = content;
		this.imgs = imgs;
		this.fault_time = fault_time;
		this.phone = phone;
		this.uDate = uDate;
	}

	public Feedback() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public Date getFault_time() {
		return fault_time;
	}

	public void setFault_time(Date fault_time) {
		this.fault_time = fault_time;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getuDate() {
		return uDate;
	}

	public void setuDate(Date uDate) {
		this.uDate = uDate;
	}
	
	

}
