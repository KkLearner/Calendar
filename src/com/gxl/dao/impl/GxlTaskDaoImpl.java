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

import com.gxl.common.utils.ResultReturn;
import com.gxl.dao.GxlTaskDao;
import com.gxl.entity.GxlTask;

@Repository("gxlTaskDaoImpl")
public class GxlTaskDaoImpl extends BaseDaoImpl<GxlTask> implements GxlTaskDao {

	//获取用户某一天的待办和日程
	//表gxl_task
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTodayAllTask(String what,String isshare,Integer userid,String date){
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
					+ " and DATE_FORMAT(end_time,'%Y/%m/%d')) and if_del=0 "+isshare)
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
			Date uDate=new Date();
			GxlTask task=(GxlTask)session.get(GxlTask.class, taskid);
			if(task.getIf_del()==1){
				task.setIf_del(0);				
				task.setuDate(uDate);
				session.update(task);
			}
			GxlTask newTask=new GxlTask(userid, task.getType_id(), task.getType_name(),
					task.getTitle(), task.getAddress(), "", 
					start_time, end_time, remind_time, free_time,
					task.getExpect_time(), task.getRemark(), 0,uDate,"",0);
			session.save(newTask);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();		
		}		
	}
	
	@Override
	public Map<String, Object> setIsShare(String share,String nShare){
		Session session=sessionFactory.getCurrentSession();
		Transaction tx=null;
		 if (session.getTransaction() != null && session.getTransaction().isActive()) 
		        tx = session.getTransaction();
		 else 
		        tx = session.beginTransaction();
		Map<String, Object> result=new HashMap<>();
		try {
			String hql="update GxlTask task set task.if_share=:if_share where task.id=:taskid";				
			String []task={share,nShare};
			for(int i=0;i<2;++i){
				String tString=task[i];
				if(tString==null||tString.equals(""))
					continue;
				String []shareid=tString.split(",");
				for(String taskid:shareid)
					session.createQuery(hql).setInteger("if_share", i).setInteger("taskid", Integer.valueOf(taskid)).executeUpdate();
				
			}
			tx.commit();
			return  ResultReturn.setMap(result, 0, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 2, e.getMessage(), null);
		}
		
	}
}
