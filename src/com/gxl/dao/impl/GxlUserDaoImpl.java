package com.gxl.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import com.gxl.dao.GxlUserDao;
import com.gxl.entity.GxlUser;

@Repository("gxlUserDaoImpl")
public class GxlUserDaoImpl extends BaseDaoImpl<GxlUser> implements GxlUserDao {

	private Map<String, Object> share(String what,Integer userid) {
		Map<String, Object> tMap=null;
		Session session=sessionFactory.getCurrentSession();
		Transaction tx;
	    if (session.getTransaction() != null
	            && session.getTransaction().isActive()) {
	        tx = session.getTransaction();
	    } else {
	        tx = session.beginTransaction();
	    }
		try {
			String sql="select "+what
					+ " from gxl_user as a "
					+ " where a.if_del=0 and a.gxlid="+userid;
			tMap=(Map<String, Object>)session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tMap;
	}
	
	@Override
	public Map<String, Object> shareCard(Integer userid) {
		return share("a.gxlid as id,a.nickname as username,a.head_img as avatar,a.partnership_name as job,"
					+ "a.job as major,a.partnership_detail as company,a.province,a.city ", userid);
	}
	
	@Override
	public Map<String, Object> shareExtendInfo(Integer userid){
		return share("a.email as mail,a.phone as phoneNumber,a.weixin as wechat,a.qq,a.weibo,"
				+ "a.birthday,a.blood as bloodType ", userid);
	}
	
	@Override
	public Map<String, Object> shareCode(Integer userid){
		return share("a.code as invitationCode ", userid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getAllUser(String account) {
		List<Map<String, Object>> userList = null;
		Session session=sessionFactory.getCurrentSession();
		Transaction tx;
	    if (session.getTransaction() != null
	            && session.getTransaction().isActive()) {
	        tx = session.getTransaction();
	    } else {
	        tx = session.beginTransaction();
	    }
	    try {
			String sql="select nickname, gxlid"
					+ " from gxl_user "
					+ " where if_del=0 and gxl_account like '%"+account+"%'";
			userList = session.createSQLQuery(sql)					
						.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();						
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
}
