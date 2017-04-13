package com.gxl.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gxl.entity.GxlTask;

public interface GxlTaskService extends BaseService<GxlTask> {

	public List<Map<String, Object>> getTodayAllTask(String what,Integer userid,String date);
	public  Map<String, Object> getInviteInfo(Integer taskid);
	public  Map<String, Object> getModifyInfo(Integer taskid);
	void copyTask(Integer userid, Integer taskid);
	
}
