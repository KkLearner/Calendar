package com.gxl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="card_collection")
public class CardCollection implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer user_id;
	
	@Column(name="friend_id")
	private Integer friend_id;
	
	@Column(name="group_name")
	private String group_name;
	
	@Column(name="if_del")
	private Integer if_del;
	
	@Column(name="uDate")
	private Date uDate;

	public CardCollection(Integer id, Integer user_id, Integer friend_id, String group_name,Integer if_del, Date uDate) {
		super();
		this.id = id;
		this.group_name=group_name;
		this.user_id = user_id;
		this.friend_id = friend_id;
		this.if_del = if_del;
		this.uDate = uDate;
	}

	public CardCollection(Integer user_id, Integer friend_id,String group_name, Integer if_del, Date uDate) {
		super();
		this.group_name=group_name;
		this.user_id = user_id;
		this.friend_id = friend_id;
		this.if_del = if_del;
		this.uDate = uDate;
	}

	public CardCollection() {
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

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	

}
