package com.gxl.dao;

import java.util.List;
import java.util.Map;

import com.gxl.entity.GxlTask;
import com.gxl.response.InvitedInfoResponse;
import com.gxl.response.ModifyInfoResponse;

public interface GxlTaskDao extends BaseDao<GxlTask> {

	public List<Map<String, Object>> getTodayAllTask(String what,Integer userid,String date);
	public InvitedInfoResponse getInviteInfo(Integer taskid);
	public ModifyInfoResponse getModifyInfo(Integer taskid);
	public void copyTask(Integer userid, Integer taskid);	
}
