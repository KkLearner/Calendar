package com.gxl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gxl.entity.GxlTask;

public interface GxlTaskService extends BaseService<GxlTask> {

	public List<Map<String, Object>> getTodayAllTask(String what,String isshare,Integer userid,String date);
	public  Map<String, Object> getInviteInfo(Integer taskid);
	public  Map<String, Object> getModifyInfo(Integer taskid);
	void copyTask(Integer userid, Integer taskid,Date start_time,
			Date end_time,String free_time,Date remind_time);
	public Map<String, Object> setIsShare(String share,String nShare);
}
