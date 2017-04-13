package com.gxl.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.gxl.dao.GxlTaskDao;
import com.gxl.entity.GxlTask;

@Repository("gxlTaskDaoImpl")
public class GxlTaskDaoImpl extends BaseDaoImpl<GxlTask> implements GxlTaskDao {

	//获取用户某一天的待办和日程
	//表gxl_task
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTodayAllTask(String what,Integer userid,String date){
		List<Map<String, Object>> list=null;
		Session session=sessionFactory.getCurrentSession();
		Transaction tx;
	    if (session.getTransaction() != null
	            && session.getTransaction().isActive()) {
	        tx = session.getTransaction();
	    } else {
	        tx = session.beginTransaction();
	    }	
		try {
			//根据用户id和日期再表gxl_task查找当天的日程和待办
			list=session.createSQLQuery("select "+what
					+ " from gxl_task "
					+ " where userid=:userid and ('"+date+"' BETWEEN DATE_FORMAT(start_time,'%Y/%m/%d') "
					+ " and DATE_FORMAT(end_time,'%Y/%m/%d')) and if_del=0")
					.setInteger("userid", userid).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
					.list();
			tx.commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			list=null;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getInviteInfo(Integer taskid) {		
		Map<String, Object> map=null;	   
		Map<String, Object> data = new HashMap<String, Object>();
		List<HashMap<String, Object>> invitees = new ArrayList<HashMap<String, Object>>();
		Session session=sessionFactory.getCurrentSession();
		Transaction tx;
	    if (session.getTransaction() != null
	            && session.getTransaction().isActive()) {
	        tx = session.getTransaction();
	    } else {
	        tx = session.beginTransaction();
	    }					
		try {
			
			map = (Map<String, Object>) session.createSQLQuery("select *"
					+ " from gxl_task "
					+ " where id=:taskid and if_del=0 ")
					.setInteger("taskid", taskid)
					.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
					.list().get(0);
			String invitedUserId = (String) map.get("invited_userid");
			data.put("id", (int) map.get("userid"));			
			data.put("topic", (String)map.get("title"));
			data.put("place", (String)map.get("address"));
			data.put("remarks", (String)map.get("remark"));
			data.put("duration", (String)map.get("expect_time"));
			data.put("startTime", (Timestamp)map.get("start_time"));
			data.put("endTime", (Timestamp)map.get("end_time"));
			
			map = (Map<String, Object>) session.createSQLQuery("SELECT head_img,nickname from gxl_user "
					+ "where gxlid=:gxlid")						
					.setInteger("gxlid", (int) map.get("userid"))
					.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
					.list().get(0);
			data.put("name", (String)map.get("nickname"));	
			data.put("avatar", (String)map.get("head_img"));	
			
			
			
			if(!invitedUserId.equals("")) {
				String[] invitedUserIds = invitedUserId.split(",");		
				data.put("total", invitedUserIds.length);
				HashMap<String, Object> invitee = new HashMap<String, Object>();
				for (int i =0; i<invitedUserIds.length; ++i) {
				   int inviteeId = Integer.parseInt(invitedUserIds[i]);				   
				   map = (Map<String, Object>) session.createSQLQuery("SELECT type from response_invite "
							+ "where taskid=:taskid and invitee=:invitee "
							+ "and type!=0 and if_del=0")						
							.setInteger("taskid", taskid)
							.setInteger("invitee", inviteeId)
							.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
							.list().get(0);	
				   invitee.put("status", (int) map.get("type"));				   
				   map = (Map<String, Object>) session.createSQLQuery("SELECT head_img,nickname from gxl_user "
							+ "where gxlid=:gxlid")						
							.setInteger("gxlid", inviteeId)
							.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
							.list().get(0);
				   
				   invitee.put("id", inviteeId);
				   invitee.put("avatar", (String) map.get("head_img"));
				   invitee.put("name", (String) map.get("nickname"));
				   invitees.add(invitee);
				}
				
			}
			data.put("invitees", invitees);			
			tx.commit();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			data = null;
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getModifyInfo(Integer taskid) {
		Map<String, Object> map=null;	
		Map<String, Object> data = new HashMap<String, Object>();		
		Session session=sessionFactory.getCurrentSession();
		Transaction tx;
	    if (session.getTransaction() != null
	            && session.getTransaction().isActive()) {
	        tx = session.getTransaction();
	    } else {
	        tx = session.beginTransaction();
	    }		
		try {
			
			map = (Map<String, Object>) session.createSQLQuery("select *"
					+ " from gxl_task "
					+ " where id=:taskid and if_del=0 ")
					.setInteger("taskid", taskid)
					.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
					.list().get(0);			
			data.put("id", (int) map.get("userid"));			
			data.put("topic", (String)map.get("title"));
			data.put("place", (String)map.get("address"));
			data.put("remarks", (String)map.get("remark"));
			data.put("duration", (String)map.get("expect_time"));
			data.put("startTime", (Timestamp)map.get("start_time"));
			data.put("endTime", (Timestamp)map.get("end_time"));
			data.put("modifyReason", (String)map.get("modify_reason"));
			
			map = (Map<String, Object>) session.createSQLQuery("SELECT head_img from gxl_user "
					+ "where gxlid=:gxlid")						
					.setInteger("gxlid", (int) map.get("userid"))
					.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
					.list().get(0);
			
			data.put("name", (String)map.get("nickname"));	
			data.put("avatar", (String)map.get("head_img"));	
			tx.commit();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			data = null;
		}
		return data;
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void copyTask(Integer userid, Integer taskid,Date start_time,
			Date end_time,String free_time,Date remind_time) {		
		Session session=sessionFactory.getCurrentSession();
		Transaction tx;
	    if (session.getTransaction() != null
	            && session.getTransaction().isActive()) {
	        tx = session.getTransaction();
	    } else {
	        tx = session.beginTransaction();
	    }		
		try {
			
			session.createSQLQuery("INSERT into gxl_task"
					+ "(userid,type_id,type_name,title,address,start_time,end_time,free_time,expect_time,remark,remind_time,uDate)"
					+ " select :userid,type_id,type_name,title,address,:start_time,:end_time,:free_time,expect_time,remark,:remind_time,now() "
					+ " from gxl_task "
					+ " where id=:taskid and if_del=0 ")
					.setInteger("userid", userid)
					.setInteger("taskid", taskid)
					.setDate("start_time", start_time)
					.setDate("end_time", end_time)
					.setDate("remind_time", remind_time)
					.setString("free_time", free_time)
					.executeUpdate();			
								
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();		
		}		
	}
	
}
