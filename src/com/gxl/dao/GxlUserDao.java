package com.gxl.dao;

import java.util.List;
import java.util.Map;

import com.gxl.entity.GxlUser;

public interface GxlUserDao extends BaseDao<GxlUser> {

	public Map<String, Object> shareCard(Integer userid);
	
	public Map<String, Object> shareExtendInfo(Integer userid);
	
	public Map<String, Object> shareCode(Integer userid);
	
	public List<Map<String, Object>> getAllUser(String account);
	
	
}
