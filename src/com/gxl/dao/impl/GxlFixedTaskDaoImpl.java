package com.gxl.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gxl.dao.GxlFixedTaskDao;
import com.gxl.entity.GxlFixedTask;

@Repository("gxlFixedTaskDaoImpl")
public class GxlFixedTaskDaoImpl extends BaseDaoImpl<GxlFixedTask> implements GxlFixedTaskDao {

}
