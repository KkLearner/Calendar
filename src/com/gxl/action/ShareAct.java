package com.gxl.action;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gxl.common.utils.ResultReturn;
import com.gxl.common.utils.UploadUtils;
import com.gxl.entity.CardCollection;
import com.gxl.entity.GxlTask;
import com.gxl.entity.GxlUser;
import com.gxl.service.CardCollectionService;
import com.gxl.service.FeedbackService;
import com.gxl.service.GxlTaskService;
import com.gxl.service.GxlUserService;
import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONObject;
import sun.java2d.d3d.D3DScreenUpdateManager;
import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/Share")
@CrossOrigin(origins="*",maxAge=3600)
public class ShareAct {

	@Autowired
	private GxlTaskService gxlTaskService;
	@Autowired
	private GxlUserService gxlUserService;
	@Autowired
	private CardCollectionService cardCollectionService;
	@Autowired
	private FeedbackService feedbackService;
	
	//通过用户id获取验证
	//表gxl_user
	@ResponseBody
	@RequestMapping(value="/CheckId",method=RequestMethod.GET,headers="Accept=application/json")
	public Map<String, Object> checkId(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String, Object> result=new HashMap<>();
		try {
			Map<String, Object> data=new HashMap<>();
			Integer id=Integer.valueOf((String)map.get("id"));
			GxlUser user=gxlUserService.getByIdWithoutDel(id);
			if(user==null)
				return ResultReturn.setJson(result, 1, "no this user id", data);			
			data.put("username", user.getGxl_account());
			data.put("id", id);
			ResultReturn.setJson(result, 0, "success", data);
		}catch(Exception exception){
			exception.printStackTrace();
			ResultReturn.setJson(result, 2, "false",null);
		}
		return result;
	}
	
	//日程分享——分享用户某一天的所有日程和待办
	//表gxl_task
	@ResponseBody
	@RequestMapping(value="/ShareTask",method=RequestMethod.GET,headers="Accept=application/json")
	public Map<String, Object> shareTask(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String, Object> result=new HashMap<>();
		try {
			Map<String, Object> data=new HashMap<>();
			Integer userid=Integer.valueOf((String)map.get("id"));
			SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");
			String date=sf.format(new Date(Long.valueOf((String)map.get("time"))));
			List<Map<String, Object>> list=gxlTaskService.getTodayAllTask(" title as content,start_time as startTime,end_time as endTime ",userid, date);
			if(list==null||list.isEmpty())
				return ResultReturn.setJson(result, 1, "no info", data);
			data.put("arranges", list);
			ResultReturn.setJson(result, 0, "success", data);
		}catch(Exception exception){
			exception.printStackTrace();
			ResultReturn.setJson(result, 2, "false",null);
		}
		return result;
	}
	
	//名片夹分享
	//表card_collection
	@ResponseBody
	@RequestMapping(value="/ShareCardHolders",method=RequestMethod.GET,headers="Accept=application/json")
	public Map<String, Object> shareCardHolders(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String, Object> result=new HashMap<>();
		try {
			Map<String, Object> data=new HashMap<>();
			List<Map<String, Object>> list=cardCollectionService.shareCardHolders(Integer.valueOf((String)map.get("id")));
			if(list==null||list.isEmpty())
				return ResultReturn.setJson(result, 1, "no info", data);
			data.put("friends", list);
			ResultReturn.setJson(result, 0, "success", data);
		}catch(Exception exception){
			exception.printStackTrace();
			ResultReturn.setJson(result, 2, "false",null);
		}
		return result;
	}
	
	//分享
	//表gxl_user
	private Map<String, Object> share(Integer type,Map<String, Object> map) {
		Map<String, Object> result=new HashMap<>();
		try {
			Map<String, Object> data=new HashMap<>();
			switch (type) {
			case 0://分享名片
				data=gxlUserService.shareCard(Integer.valueOf((String)map.get("id")));
				break;
			case 1://分享扩展信息
				data=gxlUserService.shareExtendInfo(Integer.valueOf((String)map.get("id")));
				break;
			case 2://分享验证码
				data=gxlUserService.shareCode(Integer.valueOf((String)map.get("id")));
				break;
			case 3://分享所有信息
				data=gxlUserService.shareAllInfo(Integer.valueOf((String)map.get("type")),Integer.valueOf((String)map.get("id")));
				break;
			default:
				break;
			}			
			if(data==null)
				return ResultReturn.setJson(result, 1, "no info", data);
			ResultReturn.setJson(result, 0, "success", data);
		}catch(Exception exception){
			exception.printStackTrace();
			ResultReturn.setJson(result, 2, exception.getMessage(),null);
		}
		return result;
	}
	
	//名片分享
	//表gxl_user
	@ResponseBody
	@RequestMapping(value="/ShareCard",method=RequestMethod.GET,headers="Accept=application/json")
	public Map<String, Object> shareCard(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		return share(0, map);
	}
	
	//验证是否设置验证码
	//表gxl_user
	@ResponseBody
	@RequestMapping(value="/CheckSetCode",method=RequestMethod.GET,headers="Accept=application/json")
	public Map<String, Object> checkSetCode(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String, Object> result=new HashMap<>();
		try {
			Map<String, Object> data=new HashMap<>();
			GxlUser user=gxlUserService.getByIdWithoutDel(Integer.valueOf((String)map.get("id")));
			if(user==null)
				return ResultReturn.setJson(result, 1, "no this id", data);
			String code=user.getCode();
			if(code==null||code.equals(""))
				return ResultReturn.setJson(result, 2, "code is empty", data);
			ResultReturn.setJson(result, 0, "success", data);
		}catch(Exception exception){
			exception.printStackTrace();
			ResultReturn.setJson(result, 3, "false",null);
		}
		return result;
	}
	
	//验证验证码是否正确
	//表gxl_user
	@ResponseBody
	@RequestMapping(value="/CheckCodeCorrect",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String, Object> checkCodeCorrect(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String, Object> result=new HashMap<>();
		try {
			Map<String, Object> data=new HashMap<>();
			GxlUser user=gxlUserService.getByIdWithoutDel(Integer.valueOf((String)map.get("id")));
			if(user==null)
				return ResultReturn.setJson(result, 1, "no this id", data);
			String code=user.getCode();
			if(!code.equals((String)map.get("code")))
				return ResultReturn.setJson(result, 2, "code is error", data);
			ResultReturn.setJson(result, 0, "success", data);
		}catch(Exception exception){
			exception.printStackTrace();
			ResultReturn.setJson(result, 3, "false",null);
		}
		return result;
	}
	
	//分享输入正确验证码后的信息
	//表gxl_user
	@ResponseBody
	@RequestMapping(value="/ShareExtendInfo",method=RequestMethod.GET,headers="Accept=application/json")
	public Map<String, Object> shareExtendInfo(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		return share(1, map);
	}
	
	//分享验证码
	//表gxl_user
	@ResponseBody
	@RequestMapping(value="/ShareCode",method=RequestMethod.GET,headers="Accept=application/json")
	public Map<String, Object> shareCode(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		return share(2, map);
	}
	
	//分享所有信息
	//表gxl_user
	@ResponseBody
	@RequestMapping(value="/ShareAllInfo",method=RequestMethod.GET,headers="Accept=application/json")
	public Map<String, Object> shareAllInfo(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		return share(3, map);
	}
	
	private void changeJson(Map<String, Object> map) {
		String person_extend=(String)map.get("person_extend");
		if(person_extend!=null&&!person_extend.equals("")){
			map.replace("person_extend", JSONObject.fromObject(person_extend).toString());
		}
		String connect_extend=(String)map.get("connect_extend");
		if(connect_extend!=null&&!connect_extend.equals(""))
			map.replace("connect_extend", JSONObject.fromObject(connect_extend).toString());
		String other_extend=(String)map.get("other_extend");
		if(other_extend!=null&&!other_extend.equals(""))
			map.replace("other_extend", JSONObject.fromObject(other_extend).toString());
	}

	//设置所有信息
	//表gxl_user
	@ResponseBody
	@RequestMapping(value="/SetAllInfo",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String, Object> setAllInfo(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String, Object> result=new HashMap<>();
		try {
			Map<String, Object> condition=new HashMap<>();
			condition.put("gxlid", Integer.valueOf((String)map.remove("id")));
			String birthday=(String)map.get("birthday");
			if(birthday.equals(""))
				map.replace("birthday", null);
			else
				map.replace("birthday", new java.sql.Date(Long.valueOf(birthday)));
			changeJson(map);
			map.put("uDate", new Date());
			if(gxlUserService.update(map, condition))
				return ResultReturn.setMap(result, 0, "success", null);
			return ResultReturn.setMap(result, 1, "false", null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 1, e.getMessage(), null);
		}
	}
	
	//反馈
	//表feedback
	@ResponseBody
	@RequestMapping(value="/Feedback",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String, Object> feedback(@RequestParam Map<String,Object>map,
			@RequestParam(value = "images") MultipartFile[] images,
			HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String, Object> result=new HashMap<>();
		try {
			GxlUser user=gxlUserService.getByIdWithoutDel(Integer.valueOf((String)map.get("userid")));
			if(user==null)
				return ResultReturn.setMap(result, 1, "no this userid", null);
			if(images!=null&&images.length>0&&!images[0].isEmpty()){
				//上传图片并返回文件名
				String pic=UploadUtils.uploadFeedbackPic(Integer.valueOf((String)map.get("userid")), images);
				if(pic.equals(""))
					return ResultReturn.setMap(result, 2, "img fault", null);
				map.put("imgs", pic);				
				//UploadUtils.text();
			}	
			String fault_time=(String)map.get("fault_time");
			if(fault_time==null||fault_time.equals(""))
				map.replace("fault_time", null);
			else
				map.replace("fault_time", new Date(Long.valueOf(fault_time)));
			map.put("uDate", new Date());
			if(!feedbackService.add(map))
				return ResultReturn.setMap(result, 1, "info is error", null);
			return ResultReturn.setMap(result, 0, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 1, e.getMessage(), null);
		}
	}
}
