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
@Table(name="gxl_task")
public class GxlTask implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="userid")
	private Integer userid;
	
	@Column(name="type_id")
	private Integer type_id;
	
	@Column(name="type_name")
	private String type_name;
	
	@Column(name="title")
	private String title;
	
	@Column(name="address")
	private String address;
	
	@Column(name="invited_userid")
	private String invited_userid;
	
	@Column(name="start_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date start_time;
	
	@Column(name="end_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date end_time;

	@Column(name="remind_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date remind_time;
	
	@Column(name="free_time")
	private String free_time;
	
	@Column(name="expect_time")
	private String expect_time;
	
	@Column(name="modify_reason")
	private String modify_reason;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="if_share")
	private Integer if_share;
	
	@Column(name="if_del")
	private Integer if_del;
	
	@Column(name="uDate")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date uDate;

	public GxlTask(Integer id, Integer userid, Integer type_id, String type_name,String title, String address,
			String invited_userid, Date start_time, Date end_time,Date remind_time,
			String free_time, String expect_time, String remark, Integer if_del, Date uDate,String modify_reason,
			Integer if_share) {
		super();
		this.if_share=if_share;
		this.id = id;
		this.userid = userid;
		this.type_id = type_id;
		this.type_name = type_name;
		this.title = title;
		this.address = address;
		this.invited_userid = invited_userid;
		this.start_time = start_time;
		this.end_time = end_time;
		this.remind_time=remind_time;
		this.free_time = free_time;
		this.expect_time = expect_time;
		this.remark = remark;
		this.if_del = if_del;
		this.uDate = uDate;
		this.modify_reason=modify_reason;
	}

	public GxlTask(Integer userid, Integer type_id, String type_name,String title, String address,
			String invited_userid, Date start_time, Date end_time,Date remind_time,
			String free_time, String expect_time, String remark, Integer if_del, Date uDate,String modify_reason,
			Integer if_share) {
		super();
		this.if_share=if_share;
		this.userid = userid;
		this.type_id = type_id;
		this.type_name = type_name;
		this.title = title;
		this.address = address;
		this.invited_userid = invited_userid;
		this.start_time = start_time;
		this.end_time = end_time;
		this.remind_time=remind_time;
		this.free_time = free_time;
		this.expect_time = expect_time;
		this.remark = remark;
		this.if_del = if_del;
		this.uDate = uDate;
		this.modify_reason=modify_reason;
	}

	public GxlTask() {
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

	public Integer getType_id() {
		return type_id;
	}

	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInvited_userid() {
		return invited_userid;
	}

	public void setInvited_userid(String invited_userid) {
		this.invited_userid = invited_userid;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	
	public String getFree_time() {
		return free_time;
	}

	public void setFree_time(String free_time) {
		this.free_time = free_time;
	}

	public String getExpect_time() {
		return expect_time;
	}

	public void setExpect_time(String expect_time) {
		this.expect_time = expect_time;
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

	public Date getRemind_time() {
		return remind_time;
	}

	public void setRemind_time(Date remind_time) {
		this.remind_time = remind_time;
	}

	public String getModify_reason() {
		return modify_reason;
	}

	public void setModify_reason(String modify_reason) {
		this.modify_reason = modify_reason;
	}

	public Integer getIf_share() {
		return if_share;
	}

	public void setIf_share(Integer if_share) {
		this.if_share = if_share;
	}	
	
	
}
