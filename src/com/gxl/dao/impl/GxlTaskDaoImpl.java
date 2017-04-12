package com.gxl.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.gxl.response.InvitedInfoResponse;
import com.gxl.response.ModifyInfoResponse;
import com.gxl.response.model.Invitee;

@Repository("gxlTaskDaoImpl")
public class GxlTaskDaoImpl extends BaseDaoImpl<GxlTask> implements GxlTaskDao {

	//获取用户某一天的待办和日程
	//表gxl_task
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTodayAllTask(String what,Integer userid,String date){
		List<Map<String, Object>> list=null;
		Session session=sessionFactory.getCurrentSession();
		Transaction tx=session.beginTransaction();
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
	public InvitedInfoResponse getInviteInfo(Integer taskid) {		
		Map<String, Object> map=null;
	    Vector<Invitee> invitees = new Vector<Invitee>();
		Session session=sessionFactory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		InvitedInfoResponse invitedInfoResponse = new InvitedInfoResponse();				
		try {
			
			map = (Map<String, Object>) session.createSQLQuery("select *"
					+ " from gxl_task "
					+ " where id=:taskid and if_del=0 ")
					.setInteger("taskid", taskid)
					.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
					.list().get(0);
			String invitedUserId = (String) map.get("invited_userid");	
			System.out.println((String)map.get("title"));
			invitedInfoResponse.setId((int) map.get("userid"));
			invitedInfoResponse.setTopic((String)map.get("title"));
			invitedInfoResponse.setPlace((String)map.get("address"));
			invitedInfoResponse.setRemarks((String)map.get("remark"));
			invitedInfoResponse.setDuration((String)map.get("expect_time"));
			invitedInfoResponse.setStartTime((Timestamp)map.get("start_time"));
			invitedInfoResponse.setEndTime((Timestamp)map.get("end_time"));
			
			map = (Map<String, Object>) session.createSQLQuery("SELECT head_img from gxl_user "
					+ "where gxlid=:gxlid")						
					.setInteger("gxlid", (int) map.get("userid"))
					.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
					.list().get(0);
			invitedInfoResponse.setName((String)map.get("nickname"));	
			invitedInfoResponse.setAvatar((String)map.get("head_img"));			
			if(!invitedUserId.equals("")) {
				String[] invitedUserIds = invitedUserId.split(",");		
				invitedInfoResponse.setTotal(invitedUserIds.length);
				Invitee invitee = new Invitee(); 
				for (int i =0; i<invitedUserIds.length; ++i) {
				   int inviteeId = Integer.parseInt(invitedUserIds[i]);				   
				   map = (Map<String, Object>) session.createSQLQuery("SELECT type from response_invite "
							+ "where taskid=:taskid and invitee=:invitee "
							+ "and type!=0 and if_del=0")						
							.setInteger("taskid", taskid)
							.setInteger("invitee", inviteeId)
							.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
							.list().get(0);	
				   invitee.setStatus((int) map.get("type"));				   
				   map = (Map<String, Object>) session.createSQLQuery("SELECT head_img,nickname from gxl_user "
							+ "where gxlid=:gxlid")						
							.setInteger("gxlid", inviteeId)
							.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
							.list().get(0);
				   
				   invitee.setId(inviteeId);
				   invitee.setAvatar((String) map.get("head_img"));
				   invitee.setName((String) map.get("nickname"));
				   invitees.add(invitee);
				}
				
			}
			invitedInfoResponse.setInvitees(invitees);
			tx.commit();
			return invitedInfoResponse;
		} catch (Exception e) {
			e.printStackTrace();
			invitedInfoResponse = null;
		}
		return invitedInfoResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModifyInfoResponse getModifyInfo(Integer taskid) {
		Map<String, Object> map=null;	    
		Session session=sessionFactory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		ModifyInfoResponse modifyInfoResponse = new ModifyInfoResponse();
		try {
			
			map = (Map<String, Object>) session.createSQLQuery("select *"
					+ " from gxl_task "
					+ " where id=:taskid and if_del=0 ")
					.setInteger("taskid", taskid)
					.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
					.list().get(0);			
			System.out.println((String)map.get("title"));
			modifyInfoResponse.setId((int) map.get("userid"));
			modifyInfoResponse.setTopic((String)map.get("title"));
			modifyInfoResponse.setPlace((String)map.get("address"));
			modifyInfoResponse.setRemarks((String)map.get("remark"));
			modifyInfoResponse.setDuration((String)map.get("expect_time"));
			modifyInfoResponse.setModifyReason((String)map.get("modify_reason"));
			
			map = (Map<String, Object>) session.createSQLQuery("SELECT head_img from gxl_user "
					+ "where gxlid=:gxlid")						
					.setInteger("gxlid", (int) map.get("userid"))
					.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
					.list().get(0);
			
			modifyInfoResponse.setName((String)map.get("nickname"));	
			modifyInfoResponse.setAvatar((String)map.get("head_img"));						
			tx.commit();
			return modifyInfoResponse;
		} catch (Exception e) {
			e.printStackTrace();
			modifyInfoResponse = null;
		}
		return modifyInfoResponse;
		
		
	}
	
}
