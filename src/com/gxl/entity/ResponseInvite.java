package com.gxl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="response_invite")
public class ResponseInvite implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="taskid")
	private Integer taskid;
	
	@Column(name="invitee")
	private Integer invitee;
	
	@Column(name="type")
	private Integer type;
	
	@Column(name="start_time")
	private Date start_time;
	
	@Column(name="end_time")
	private Date end_time;
	
	@Column(name="free_time")
	private String free_time;
	
	@Column(name="remind_time")
	private Date remind_time;
	
	@Column(name="refuse")
	private String refuse;
	
	@Column(name="if_del")
	private Integer if_del;
	
	@Column(name="uDate")
	private Date uDate;

	public ResponseInvite(Integer id, Integer taskid, Integer invitee, Integer type, 
			Date start_time, Date end_time,String free_time, Date remind_time, String refuse, Integer if_del, Date uDate) {
		super();
		this.id = id;
		this.taskid = taskid;
		this.invitee = invitee;
		this.type = type;
		this.start_time = start_time;
		this.end_time = end_time;
		this.free_time = free_time;
		this.remind_time = remind_time;
		this.refuse = refuse;
		this.if_del = if_del;
		this.uDate = uDate;
	}

	public ResponseInvite(Integer taskid, Integer invitee, Integer type, Date start_time, Date end_time,
			String free_time, Date remind_time, String refuse, Integer if_del, Date uDate) {
		super();
		this.taskid = taskid;
		this.invitee = invitee;
		this.type = type;
		this.start_time = start_time;
		this.end_time = end_time;
		this.free_time = free_time;
		this.remind_time = remind_time;
		this.refuse = refuse;
		this.if_del = if_del;
		this.uDate = uDate;
	}

	public ResponseInvite() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	public Integer getInvitee() {
		return invitee;
	}

	public void setInvitee(Integer invitee) {
		this.invitee = invitee;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Date getRemind_time() {
		return remind_time;
	}

	public void setRemind_time(Date remind_time) {
		this.remind_time = remind_time;
	}

	public String getRefuse() {
		return refuse;
	}

	public void setRefuse(String refuse) {
		this.refuse = refuse;
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
	
}
