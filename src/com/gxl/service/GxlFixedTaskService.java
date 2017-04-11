package com.gxl.service;

import java.util.List;
import java.util.Map;

import com.gxl.entity.GxlFixedTask;

public interface GxlFixedTaskService extends BaseService<GxlFixedTask> {

	public String getRepeatName(String date);
	
	public List<Map<String, Object>> getAllFixedTask(Integer userid,String date);
}
