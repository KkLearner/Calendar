package com.gxl.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gxl.entity.GxlTask;


public interface GxlTaskDao extends BaseDao<GxlTask> {

	public List<Map<String, Object>> getTodayAllTask(String what,Integer userid,String date);
	public Map<String, Object> getInviteInfo(Integer taskid);
	public  Map<String, Object> getModifyInfo(Integer taskid);
	public void copyTask(Integer userid, Integer taskid,Date start_time,
			Date end_time,String free_time,Date remind_time);	
}
