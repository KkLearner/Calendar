package com.gxl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.media.sound.SoftInstrument;

@Entity
@Table(name="gxl_fixed_task")
public class GxlFixedTask implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="user_id")
	private Integer user_id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="start_time")
	private String start_time;
	
	@Column(name="end_time")
	private String end_time;
	
	@Column(name="address")
	private String address;
	
	@Column(name="repeat_id")
	private Integer repeat_id;
	
	@Column(name="repeat_name")
	private String repeat_name;
	
	@Column(name="remind_time")
	private String remind_time;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="if_del")
	private Integer if_del;
	
	@Column(name="uDate")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date uDate;

	public GxlFixedTask(Integer id, Integer user_id, String title, String start_time, String end_time, String address,
			Integer repeat_id, String repeat_name, String remind_time, String remark, Integer if_del,
			Date uDate) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.title = title;
		this.start_time = start_time;
		this.end_time = end_time;
		this.address = address;
		this.repeat_id = repeat_id;
		this.repeat_name = repeat_name;
		this.remind_time=remind_time;
		this.remark = remark;
		this.if_del = if_del;
		this.uDate = uDate;
	}

	public GxlFixedTask(Integer user_id, String title, String start_time, String end_time, String address,
			Integer repeat_id, String repeat_name, String remind_time, String remark, Integer if_del,
			Date uDate) {
		super();
		this.user_id = user_id;
		this.title = title;
		this.start_time = start_time;
		this.end_time = end_time;
		this.address = address;
		this.repeat_id = repeat_id;
		this.repeat_name = repeat_name;
		this.remind_time=remind_time;
		this.remark = remark;
		this.if_del = if_del;
		this.uDate = uDate;
	}

	public GxlFixedTask() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRepeat_id() {
		return repeat_id;
	}

	public void setRepeat_id(Integer repeat_id) {
		this.repeat_id = repeat_id;
	}

	public String getRepeat_name() {
		return repeat_name;
	}

	public void setRepeat_name(String repeat_name) {
		this.repeat_name = repeat_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getRemind_time() {
		return remind_time;
	}

	public void setRemind_time(String remind_time) {
		this.remind_time = remind_time;
	}
	
	
}
