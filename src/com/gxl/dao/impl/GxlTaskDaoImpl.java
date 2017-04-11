package com.gxl.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
}
