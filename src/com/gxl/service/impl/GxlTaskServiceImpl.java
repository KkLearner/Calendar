package com.gxl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.dao.GxlTaskDao;
import com.gxl.entity.GxlTask;
import com.gxl.response.InvitedInfoResponse;
import com.gxl.response.ModifyInfoResponse;
import com.gxl.service.GxlTaskService;

@Transactional
@Service("gxlTaskService")
public class GxlTaskServiceImpl extends BaseServiceImpl<GxlTask> implements GxlTaskService {
	
	@Autowired
	private GxlTaskDao gxlTaskDao;
	
	public List<Map<String, Object>> getTodayAllTask(String what,Integer userid,String date){
		return gxlTaskDao.getTodayAllTask(what,userid, date);
	}

	@Override
	public InvitedInfoResponse getInviteInfo(Integer taskid) {
		return gxlTaskDao.getInviteInfo(taskid);
	}

	@Override
	public ModifyInfoResponse getModifyInfo(Integer taskid) {
		return gxlTaskDao.getModifyInfo(taskid);
	}
	
	@Override
	public void copyTask(Integer userid, Integer taskid) {
		gxlTaskDao.copyTask(userid, taskid);
	}
}
