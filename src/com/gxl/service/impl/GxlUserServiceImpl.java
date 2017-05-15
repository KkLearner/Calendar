package com.gxl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.dao.GxlUserDao;
import com.gxl.entity.GxlUser;
import com.gxl.service.GxlUserService;
import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONObject;

@Transactional
@Service("gxlUserService")
public class GxlUserServiceImpl extends BaseServiceImpl<GxlUser> implements GxlUserService {

	@Autowired
	private GxlUserDao gxlUserDao;
	//学生模板个人信息
	private static Map<String, String> student=new HashMap<String,String>(){{
		put("nickname", "姓名");
		put("partnership_name","院校");
		put("partnership_detail", "社团");
		put("job", "职位");
	}};
	//职工模板个人信息
	private static Map<String, String> staff=new HashMap<String,String>(){{
		put("nickname", "姓名");
		put("partnership_name","行业");
		put("partnership_detail", "公司");
		put("job", "职位");
	}};
	//自定义模板个人信息
	private static Map<String, String> custom=new HashMap<String,String>(){{
		put("nickname", "姓名");
	}};
	//联系信息
	private static Map<String, String> connectInfo=new HashMap<String,String>(){{
		put("phone", "电话");
		put("email","邮箱");
		put("weixin", "微信");
		put("qq", "QQ");
		put("weibo", "微博");
	}};
	//其他信息
	private static Map<String, String> otherInfo=new HashMap<String,String>(){{
		put("birthday", "生日");
		put("blood","血型");
		put("address", "详细地址");
		put("homepage", "个人主页");
	}};
	
	public Map<String, Object> updateMap(Map<String, Object>map){
		Map<String, Object> result=new HashMap<>();
		Set<String> keys=map.keySet();
		for(String key:keys){
			Object value=map.get(key);
			if(value!=null&&!((String)value).trim().equals(""))
				result.put(key, value);
		}
		return result;		
	}
	
	
	@Override
	public Map<String, Object> shareCard(Integer userid){
		return gxlUserDao.shareCard(userid);
	}
	
	@Override
	public Map<String, Object> shareExtendInfo(Integer userid){
		return gxlUserDao.shareExtendInfo(userid);
	}
	
	@Override
	public Map<String, Object> shareCode(Integer userid){
		return gxlUserDao.shareCode(userid);
	}
	
	private List<Map<String, Object>> getInfo(Map<String, String> oril,Map<String, Object> entity,String info) {
		List<Map<String, Object>> list=new ArrayList<>();
		try {
			Set<String> keys=oril.keySet();
			for(String key:keys){
				Map<String, Object> tMap=new HashMap<>();
				tMap.put("key", oril.get(key));
				tMap.put("value", entity.get(key));
				list.add(tMap);
			}			
			if(info!=null&&!info.equals("")){//扩展部分 json格式字符串
				Map<String, String> extend=(Map<String, String>)JSONObject.fromObject(info);
				keys=extend.keySet();
				for(String key:keys){
					Map<String, Object> tMap=new HashMap<>();
					tMap.put("key", key);
					tMap.put("value", extend.get(key));
					list.add(tMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			list=null;
		}		
		return list;
	}

	@Override
	public Map<String, Object> shareAllInfo(Integer type,Integer userid){
		Map<String, Object> result=new HashMap<>();
		try {
			String sql="select * from gxl_user where gxlid="+userid;
			List<Map<String, Object>> list=gxlUserDao.getBySQL(sql);
			if(list==null||list.isEmpty())
				return null;
			Map<String, Object> entity=list.get(0);
			List<Map<String, Object>> person=new ArrayList<>();
			switch (type) {
			case 1://学生
				person=getInfo(student, entity,null);
				break;
			case 2://职工
				person=getInfo(staff, entity,null);			
				break;
			case 0://自定义
				person=getInfo(custom, entity,(String)entity.get("person_extend"));				
				break;
			default:
				return null;
			}
			List<Map<String, Object>> connect=getInfo(connectInfo, entity,(String)entity.get("connect_extend"));
			List<Map<String, Object>> other=getInfo(otherInfo, entity, (String)entity.get("other_extend"));
			if(person==null||connect==null||other==null)
				return null;
			Map<String, Object> tMap=new HashMap<>();
			tMap.put("key", "所在地");
			tMap.put("value", (String)entity.get("province")+(String)entity.get("city"));
			person.add(tMap);
			result.put("id", userid);//用户id
			result.put("avatar", entity.get("head_img"));//头像地址
			result.put("captcha", entity.get("code"));//验证码
			result.put("person", person);//个人信息
			result.put("connect", connect);//联系信息
			result.put("other", other);//其他信息
		} catch (Exception e) {
			result=null;
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public List<Map<String, Object>> getAllUser(String account) {
		return gxlUserDao.getAllUser(account);
	}
}
