package com.gxl.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gxl.common.utils.ResultReturn;
import com.gxl.common.utils.UploadUtils;
import com.gxl.entity.GxlUser;
import com.gxl.im.utils.IMApiUtils;
import com.gxl.im.wrapper.ResponseWrapper;
import com.gxl.service.GxlUserService;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/GxlUser")
public class GxlUserAct {

	@Autowired
	private GxlUserService gxlUserService;
	private static IMApiUtils imApiUtils=IMApiUtils.getInstance();
	
	//注册用户
	//表 gxl_user
	@ResponseBody
	@RequestMapping(value="/Register",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> register(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			//通过账号在表gxl_user中查找该用户
			String account=(String)map.get("gxl_account");
			GxlUser user=gxlUserService.findByUniqueProperty("gxl_account", account);
			if(user!=null&&user.getIf_del()==0)//已经被注册
				return ResultReturn.setMap(result, 1, "this account has been registered", null);
			int length=account.length();
			for(int i=0;i<length;++i){
				char s=account.charAt(i);
				if(s>127)
					return ResultReturn.setMap(result, 2, "account is only ascii", null);
			}
			String imaccount=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+Math.random();
			ResponseWrapper wrapper=imApiUtils.createIMUserSingle(imaccount,(String)map.get("password") ,(String)map.get("nickname"));
			if(wrapper.hasError())
				return ResultReturn.setMap(result,3, wrapper.toString(), null);
			map.put("IM_name", imaccount);			
			map.put("uDate", new Date());			
			gxlUserService.add(map);
			user=gxlUserService.findByUniqueProperty("gxl_account", account);
			result.put("id", user.getGxlid());//id
			return ResultReturn.setMap(result, 0, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			result.clear();
			return ResultReturn.setMap(result, 4, e.getMessage(), null);
		}
	}
		
	//验证账号密码是否通过
	//表 gxl_user
	@ResponseBody
	@RequestMapping(value="/Login",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> login(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			//通过账号在表gxl_user中查找该用户
			String account=(String)map.get("account");
			GxlUser user=gxlUserService.findByUniqueProperty("gxl_account",account );
			if(user==null||user.getIf_del()==1)//没有查到或者已标记删除
				return ResultReturn.setMap(result, 1, "no this user", null);
			if(!user.getPassword().equals((String)map.get("password")))//比较密码
				return ResultReturn.setMap(result, 2, "password is not correct", null);
			ResponseWrapper wrapper=imApiUtils.checkIMUserOnline(user.getIm_name());
			if(wrapper.hasError())
				return ResultReturn.setMap(result, 3, wrapper.toString(), null);
			JsonNode data=((ObjectNode)wrapper.getResponseBody()).get("data");
			if(data.get(account).asText().equals("online"))
				return ResultReturn.setMap(result, 4, "you have logined,please don't login again" , null);
			result.put("id", user.getGxlid());//id	
			return ResultReturn.setMap(result, 0, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			result.clear();
			return ResultReturn.setMap(result, 4, e.getMessage(), null);
		}
	}
	
	//验证账号获取用户基本信息
	//表 gxl_user
	@ResponseBody
	@RequestMapping(value="/GetUserInfoByAccount",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> getUserInfoByAccount(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			//通过账号在表gxl_user中查找该用户
			String account=(String)map.get("account");
			GxlUser user=gxlUserService.findByUniqueProperty("gxl_account",account );
			if(user==null||user.getIf_del()==1)//没有查到或者已标记删除
				return ResultReturn.setMap(result, 1, "no this user", null);
			result.put("sex", user.getSex());//性别
			result.put("head_imag", user.getHead_img());//头像
			result.put("nick_name", user.getNickname());//昵称
			result.put("id", user.getGxlid());//id
			result.put("area", user.getAddress());//地址
			result.put("signature", user.getSignature());//个人签名		
			return ResultReturn.setMap(result, 0, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			result.clear();
			return ResultReturn.setMap(result, 2, e.getMessage(), null);
		}
	}
		
	//修改密码
	//表 gxl_user
	@ResponseBody
	@RequestMapping(value="/EditPsw",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> editPsw(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			String account=(String)map.get("account");
			GxlUser user=gxlUserService.findByUniqueProperty("gxl_account", account);
			if(user==null||user.getIf_del()==1)//没有查到或者已标记删除
				return ResultReturn.setMap(result, 1, "no this user", null);
			if(!user.getPassword().equals((String)map.get("old_pass")))//比较密码
				return ResultReturn.setMap(result, 2, "password is not correct", null);
			String newpassword=(String)map.get("new_pass");
			ResponseWrapper wrapper=imApiUtils.resetIMUSerPassword(account, newpassword);
			if(wrapper.hasError())
				return ResultReturn.setMap(result, 3, wrapper.toString(), null);
			user.setPassword(newpassword);//新密码
			user.setuDate(new Date());//更新时间
			gxlUserService.update(user);
			return ResultReturn.setMap(result, 0, "success", null);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 4, "false", null);
		}
	}
	
	//修改个人帐号信息
	//表 gxl_user
	@ResponseBody
	@RequestMapping(value="/EditInfo",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> editInfo(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			String gxl_account=(String)map.remove("account");
			GxlUser user=gxlUserService.findByUniqueProperty("gxl_account",gxl_account);
			if(user==null||user.getIf_del()==1)//没有查到或者已标记删除
				return ResultReturn.setMap(result, 1, "no this user", null);
			Map<String, Object> condition=new HashMap<>();
			condition.put("gxl_account", gxl_account);
			Map<String, Object> values=gxlUserService.updateMap(map);//删除没有的值
			if(values.isEmpty())
				return ResultReturn.setMap(result, 2, "please add some info", null);
			if(values.containsKey("head_img")){				
				String pic=UploadUtils.uploadHeadPic(user.getGxlid(), (String)values.get("head_img"));
				if(pic.equals(""))
					return ResultReturn.setMap(result, 3, "img error", null);
				values.replace("head_img", pic);
			}
			values.put("uDate", new Date());
			gxlUserService.update(values, condition);
			return ResultReturn.setMap(result, 0, "success", null);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 3, "false", null);
		}
	}
}
