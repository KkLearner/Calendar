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
		String[] days = {"鍛ㄦ棩","鍛ㄤ竴","鍛ㄤ簩","鍛ㄤ笁","鍛ㄥ洓","鍛ㄤ簲","鍛ㄥ叚"};  
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
	
	//鑾峰彇鐢ㄦ埛鏌愪竴澶╃殑鎵�鏈夊浐瀹氭棩绋�
	//琛� gxl_fixed_task
	public List<Map<String, Object>> getAllFixedTask(Integer userid,String date) {
		String hql="from GxlFixedTask as a "
				+ "where a.user_id=? and a.if_del=0";
		List<Map<String, Object>> list=new ArrayList<>();
		String[] days = {"鍛ㄦ棩","鍛ㄤ竴","鍛ㄤ簩","鍛ㄤ笁","鍛ㄥ洓","鍛ㄤ簲","鍛ㄥ叚"};  
        Calendar cal = Calendar.getInstance();
        int dayIndex=0;
		try {
			//鏍规嵁user_id鏌ユ壘浠栫殑鎵�鏈夊浐瀹氭棩绋�
			List<GxlFixedTask> tasks=gxlFixedTaskDao.find(hql, userid);
			if(tasks==null||tasks.isEmpty())
				return null;
			cal.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(date));  
	        dayIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;//鑾峰彇date鏃ユ湡鏄懆鍑� 
			for(GxlFixedTask task:tasks){
				Integer repeat_id=task.getRepeat_id();
				switch (repeat_id) {
				case 0://0锛氬懆涓�鍒板懆浜�
					if(dayIndex==0||dayIndex==6)
						continue;
					break;
				case 1://1锛氭硶瀹氬伐浣滄棩锛堟櫤鑳借烦杩囪妭鍋囨棩锛�
					
					break;
				case 3://3锛氳嚜瀹氫箟
					if(!task.getRepeat_name().contains(days[dayIndex]))
						continue;						
					break;
				}
				Map<String, Object> teMap=new HashMap<>();
				teMap.put("type",3);//绫诲瀷涓�3锛岃〃绀哄浐瀹氭棩绋�
				teMap.put("title", task.getTitle());//鏍囬
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
