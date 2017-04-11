package com.gxl.service;

import java.util.List;
import java.util.Map;

import com.gxl.entity.ResponseInvite;

public interface ResponseInviteService extends BaseService<ResponseInvite> {

	public Map<String, Object> sendInvitations(Integer taskid,Integer userid,String invitees,Map<String, Object> result);
	
	public List<Map<String, Object>> responseInvitations(Integer taskid,String invitees,Map<String, Object> result);
}
