package com.gxl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gxl_friends")
public class GxlFriends implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer user_id;
	
	@Column(name="friend_id")
	private Integer friend_id;
	
	@Column(name="type")
	private Integer type;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="source_id")
	private Integer source_id;
	
	@Column(name="source_name")
	private String source_name;

	@Column(name="if_del")
	private Integer if_del;
	
	@Column(name="uDate")
	private Date uDate;

	public GxlFriends(Integer id, Integer user_id, Integer friend_id, Integer type, String remark, Integer source_id,
			String source_name, Integer if_del, Date uDate) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.friend_id = friend_id;
		this.type = type;
		this.remark = remark;
		this.source_id = source_id;
		this.source_name = source_name;
		this.if_del = if_del;
		this.uDate = uDate;
	}

	public GxlFriends(Integer user_id, Integer friend_id, Integer type, String remark, Integer source_id,
			String source_name, Integer if_del, Date uDate) {
		super();
		this.user_id = user_id;
		this.friend_id = friend_id;
		this.type = type;
		this.remark = remark;
		this.source_id = source_id;
		this.source_name = source_name;
		this.if_del = if_del;
		this.uDate = uDate;
	}

	public GxlFriends() {
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

	public Integer getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(Integer friend_id) {
		this.friend_id = friend_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSource_id() {
		return source_id;
	}

	public void setSource_id(Integer source_id) {
		this.source_id = source_id;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
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
