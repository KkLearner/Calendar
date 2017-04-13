package com.gxl.dao;

import java.util.List;
import java.util.Map;

import com.gxl.entity.GxlTask;


public interface GxlTaskDao extends BaseDao<GxlTask> {

	public List<Map<String, Object>> getTodayAllTask(String what,Integer userid,String date);
	public Map<String, Object> getInviteInfo(Integer taskid);
	public  Map<String, Object> getModifyInfo(Integer taskid);
	public void copyTask(Integer userid, Integer taskid);	
}
