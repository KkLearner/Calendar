package com.gxl.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gxl.entity.GxlUser;

public interface GxlUserService extends BaseService<GxlUser> {

	public Map<String, Object> updateMap(Map<String, Object>map);
	
	public Map<String, Object> shareCard(Integer userid);
	
	public Map<String, Object> shareExtendInfo(Integer userid);
	
	public Map<String, Object> shareCode(Integer userid);
	
	public Map<String, Object> shareAllInfo(Integer type,Integer userid);
	
	public List<Map<String, Object>> getAllUser(String account);
}
