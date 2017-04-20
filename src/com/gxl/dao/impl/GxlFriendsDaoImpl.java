package com.gxl.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import com.gxl.dao.GxlFriendsDao;
import com.gxl.entity.GxlFriends;

@Repository("gxlFriendsDaoImpl")
public class GxlFriendsDaoImpl extends BaseDaoImpl<GxlFriends> implements GxlFriendsDao {

	public List<Map<String, Object>> getFriendsList(Integer userid) {
		Session session=sessionFactory.getCurrentSession();
		Transaction tx=session.getTransaction();
		if(tx==null||!tx.isActive())
			tx=session.beginTransaction();
		List<Map<String, Object>> result=new ArrayList<>();
		try {
			String sql="select a.gxlid,a.IM_name,a.nickname,a.head_img "
					+ "from gxl_user as a,gxl_friends as b "
					+ "where a.gxlid=b.friend_id and b.type=0 and b.if_del=0 and b.user_id="+userid;
			result=session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
