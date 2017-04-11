package com.gxl.dao;

import java.util.List;
import java.util.Map;

import com.gxl.entity.GxlTask;

public interface GxlTaskDao extends BaseDao<GxlTask> {

	public List<Map<String, Object>> getTodayAllTask(String what,Integer userid,String date);
	
}
