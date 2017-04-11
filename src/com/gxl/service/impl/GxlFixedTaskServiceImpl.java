package com.gxl.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.dao.GxlFixedTaskDao;
import com.gxl.entity.GxlFixedTask;
import com.gxl.service.GxlFixedTaskService;

@Transactional
@Service("gxlFixedTaskService")
public class GxlFixedTaskServiceImpl extends BaseServiceImpl<GxlFixedTask> implements GxlFixedTaskService {

	@Autowired
	private GxlFixedTaskDao gxlFixedTaskDao;
	
	public String getRepeatName(String date){
		String[] days = {"周日","周一","周二","周三","周四","周五","周六"};  
        Calendar cal = Calendar.getInstance();
        int dayIndex=0;
        try {
			cal.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(date));  
	        dayIndex = cal.get(Calendar.DAY_OF_WEEK) - 1; 
		} catch (Exception e) {
			e.printStackTrace();
		}
       return days[dayIndex];
	}
	
	//获取用户某一天的所有固定日程
	//表 gxl_fixed_task
	public List<Map<String, Object>> getAllFixedTask(Integer userid,String date) {
		String hql="from GxlFixedTask as a "
				+ "where a.user_id=? and a.if_del=0";
		List<Map<String, Object>> list=new ArrayList<>();
		String[] days = {"周日","周一","周二","周三","周四","周五","周六"};  
        Calendar cal = Calendar.getInstance();
        int dayIndex=0;
		try {
			//根据user_id查找他的所有固定日程
			List<GxlFixedTask> tasks=gxlFixedTaskDao.find(hql, userid);
			if(tasks==null||tasks.isEmpty())
				return null;
			cal.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(date));  
	        dayIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;//获取date日期是周几 
			for(GxlFixedTask task:tasks){
				Integer repeat_id=task.getRepeat_id();
				switch (repeat_id) {
				case 0://0：周一到周五
					if(dayIndex==0||dayIndex==6)
						continue;
					break;
				case 1://1：法定工作日（智能跳过节假日）
					
					break;
				case 3://3：自定义
					if(!task.getRepeat_name().contains(days[dayIndex]))
						continue;						
					break;
				}
				Map<String, Object> teMap=new HashMap<>();
				teMap.put("type",3);//类型为3，表示固定日程
				teMap.put("title", task.getTitle());//标题
				teMap.put("id", task.getId());//id
				list.add(teMap);
			}
		} catch (Exception e) {
			list=null;
			e.printStackTrace();
		}
		return list;
	}
}
