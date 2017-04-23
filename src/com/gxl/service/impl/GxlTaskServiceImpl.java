package com.gxl.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.dao.GxlTaskDao;
import com.gxl.entity.GxlTask;
import com.gxl.service.GxlTaskService;

@Transactional
@Service("gxlTaskService")
public class GxlTaskServiceImpl extends BaseServiceImpl<GxlTask> implements GxlTaskService {
	
	@Autowired
	private GxlTaskDao gxlTaskDao;
	
	public List<Map<String, Object>> getTodayAllTask(String what,String isshare,Integer userid,String date){
		return gxlTaskDao.getTodayAllTask(what,isshare,userid, date);
	}

	@Override
	public  Map<String, Object> getInviteInfo(Integer taskid) {
		return gxlTaskDao.getInviteInfo(taskid);
	}

	@Override
	public  Map<String, Object> getModifyInfo(Integer taskid) {
		return gxlTaskDao.getModifyInfo(taskid);
	} 
	
	@Override
	public void copyTask(Integer userid, Integer taskid,Date start_time,
			Date end_time,String free_time,Date remind_time) {
		gxlTaskDao.copyTask(userid, taskid,start_time,end_time,free_time,remind_time);
	}
	
	@Override
	public Map<String, Object> setIsShare(String share,String nShare){
		return gxlTaskDao.setIsShare(share,nShare);
	}

	@Override
	public List<Map<String, Object>> getMonthAllTask(String what, Integer userid, String date) {
		return gxlTaskDao.getMonthAllTask(what, userid, date);
	}
}
