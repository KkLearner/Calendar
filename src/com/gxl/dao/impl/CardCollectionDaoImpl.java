package com.gxl.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.dao.CardCollectionDao;
import com.gxl.entity.CardCollection;

@Repository("cardCollectionDaoImpl")
public class CardCollectionDaoImpl extends BaseDaoImpl<CardCollection> implements CardCollectionDao {

	@Override
	public List<Map<String, Object>> shareCardHolders(Integer userid) {
		List<Map<String, Object>> list=null;
		Session session=sessionFactory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		try {
			String sql="select a.nickname as name,a.partnership_name as category,a.partnership_detail as company "
					+ "from gxl_user as a,card_collection as b "
					+ "where b.friend_id=a.gxlid and b.if_del=0 and a.if_del=0 and b.user_id="+userid;
			list=session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
