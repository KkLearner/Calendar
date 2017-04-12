package com.gxl.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gxl.entity.GxlTask;
import com.gxl.response.InvitedInfoResponse;
import com.gxl.response.ModifyInfoResponse;

public interface GxlTaskService extends BaseService<GxlTask> {

	public List<Map<String, Object>> getTodayAllTask(String what,Integer userid,String date);
	public InvitedInfoResponse getInviteInfo(Integer taskid);
	public ModifyInfoResponse getModifyInfo(Integer taskid);
}
