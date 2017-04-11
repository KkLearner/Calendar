package com.gxl.dao.impl;

import org.springframework.stereotype.Repository;

import com.gxl.dao.FeedbackDao;
import com.gxl.entity.Feedback;

@Repository("feedbackDaoImpl")
public class FeedbackDaoImpl extends BaseDaoImpl<Feedback> implements FeedbackDao {

}
