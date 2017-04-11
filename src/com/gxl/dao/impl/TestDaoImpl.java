package com.gxl.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Transformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.gxl.dao.TestDao;
import com.gxl.entity.Test;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform;

@Repository("testDaoImpl")
public class TestDaoImpl extends BaseDaoImpl<Test> implements TestDao {

	public void check() {
		try {
			Test test=getById(1);
		Session session=sessionFactory.getCurrentSession();
			Transaction tx=session.beginTransaction();
			test.setBirthday(new Date());
			session.update(test);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
