package com.gxl.action;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gxl.common.utils.ResultReturn;
import com.gxl.entity.GxlFixedTask;
import com.gxl.entity.GxlTask;
import com.gxl.entity.GxlUser;
import com.gxl.entity.ResponseInvite;
import com.gxl.im.utils.IMApiUtils;
import com.gxl.im.wrapper.ResponseWrapper;
import com.gxl.service.GxlFixedTaskService;
import com.gxl.service.GxlTaskService;
import com.gxl.service.GxlUserService;
import com.gxl.service.ResponseInviteService;
import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/Task")
public class TaskAct {

	@Autowired
	private GxlTaskService gxlTaskService;
	@Autowired
	private GxlFixedTaskService gxlFixedTaskService;
	@Autowired
	private GxlUserService gxlUserService;
	@Autowired
	private ResponseInviteService responseInviteService;
	private static IMApiUtils imApiUtils=IMApiUtils.getInstance();
	
	private Map<Integer, String> repeatList=new HashMap<Integer,String>(){{
		put(0,"周一到周五");
		put(1,"法定工作日(智能跳过节假日)");
		put(2,"每天");
		put(3,"自定义");
	}};
	
	private Map<Integer, String> typeList=new HashMap<Integer,String>(){{
		put(0,"待办");
		put(1,"确定日程");
		put(2,"待定日程");
	}};
	
	//通过日期获取该用户该天所有待办和日程
	//表gxl_task  gxl_fixed_task
	@ResponseBody
	@RequestMapping(value="/GetTodayAllTask",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> getTodayAllTask(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			String date=(String)map.get("date");//日期
			Integer userid=Integer.valueOf((String)map.get("user_id"));//用户id
			List<Map<String, Object>> list=new ArrayList<>();
			List<Map<String, Object>> teMaps=gxlTaskService.getTodayAllTask("id,type_id as type,title ",userid, date);
			if(teMaps!=null)
				list.addAll(teMaps);//获取日程和待办
			teMaps=gxlFixedTaskService.getAllFixedTask(userid, date);
			if(teMaps!=null)
				list.addAll(teMaps);//获取固定日程
			if (list==null||list.isEmpty()) 
				return ResultReturn.setMap(result, 1, "no info", null);
			return ResultReturn.setMap(result, 0, "success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 2, "false", null);
		}
	}
	
	//通过id删除待办/日程
	//表gxl_task  gxl_fixed_task
	@ResponseBody
	@RequestMapping(value="/DeleteTaskById",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> deleteTaskById(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			Integer type=Integer.valueOf((String)map.remove("type"));//0为待办，1为日程，2为固定日程
			Map<String, Object> value=new HashMap<>();
			value.put("if_del", 1);
			if(type==2)
				gxlFixedTaskService.update(value, map);
			else
				gxlTaskService.update(value, map);
			return ResultReturn.setMap(result, 0, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 1, "false", null);
		}
	}
	
	//添加待办
	//表gxl_task  
	@ResponseBody
	@RequestMapping(value="/AddCommission",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> addCommission(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			Integer userid=Integer.valueOf((String)map.get("userid"));//用户id
			List<GxlUser> users=gxlUserService.getByCriterion(Restrictions.eq("gxlid", userid),
					Restrictions.eq("if_del", 0));//查找是否存在该用户
			if(users==null||users.isEmpty())
				return ResultReturn.setMap(result, 1, "no this user", null);	
			java.util.Date uDate=new java.util.Date();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");			
			GxlTask task=new GxlTask(userid, 0, "待办", 
					(String)map.get("title"), (String)map.get("address"), "", uDate,
					sf.parse((String)map.get("end_time")), sf.parse((String)map.get("remind_time")), "", "", "", 
					0, uDate,"");
			gxlTaskService.add(task);
			result.put("taskid", task.getId());
			return ResultReturn.setMap(result, 0, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 3, "false", null);
		}
	}
	
	//添加日程
	//表gxl_task  
	@ResponseBody
	@RequestMapping(value="/AddSchedule",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> addSchedule(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			Integer id=Integer.valueOf((String)map.get("userid"));
			GxlUser user=gxlUserService.getByIdWithoutDel(id);//查找是否存在该用户
			if(user==null)
				return ResultReturn.setMap(result, 1, "no this user", null);
			Integer schedule_type=Integer.valueOf((String)map.remove("schedule_type"));
			SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	
			java.util.Date start_time=sf.parse((String)map.get("start_time"));
			java.util.Date end_time=sf.parse((String)map.get("end_time"));
			String free_time="";		
			switch (schedule_type) {
			case 0://确定的日程	
				break;
			case 1://待定的日程
				free_time=(String)map.get("free_time");
				break;
			default:
				return ResultReturn.setMap(result, 2, "no this schedule_type", null);
			}
			String invites=(String)map.get("invited_userid");
			Integer if_del=0;
			if(!invites.trim().equals(""))//当有邀请别人时，要对方接受了才可以设置if_del=0
				if_del=1;
			String rtime=(String)map.get("remind_time");
			java.util.Date remind_time=null;
			if(rtime!=null&&!rtime.equals(""))
				remind_time=sf.parse(rtime);		
			GxlTask task=new GxlTask(id, schedule_type+1, typeList.get(schedule_type+1), (String)map.get("title"), 
					(String)map.get("address"), invites, start_time, end_time,remind_time , free_time,
					(String)map.get("expect_time"), (String)map.get("remark"), if_del,new java.util.Date(),"");
			gxlTaskService.add(task);
			result.put("taskid", task.getId());
			if(!invites.trim().equals(""))
				return responseInviteService.sendInvitations(task.getId(), id, invites, result);
			return ResultReturn.setMap(result, 0, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			result.clear();
			return ResultReturn.setMap(result, 3, e.getMessage(), null);
		}
	}
	
	//回复邀请
	//表response_invite gxl_user
	@CrossOrigin(origins="*",maxAge=3600)
	@ResponseBody
	@RequestMapping(value="/ResponseInvite",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> responseInvite(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			Integer invitee=Integer.valueOf((String)map.get("invitee"));
			List<ResponseInvite> invite=responseInviteService.getByCriterion(Restrictions.eq("taskid", Integer.valueOf((String)map.get("taskid"))),
					Restrictions.eq("invitee", invitee));
			if(invite==null||invite.isEmpty())
				return ResultReturn.setMap(result, 1, "no this intitation", null);
			ResponseInvite responseInvite=invite.get(0);
			if(responseInvite.getType()!=0)
				return ResultReturn.setMap(result, 2, "you have response this invitation", null);
			Integer type=Integer.valueOf((String)map.get("type"));	
			java.util.Date start_time=null;
			java.util.Date end_time=null;
			switch (type) {
			case 1://拒绝
				responseInvite.setRefuse((String)map.get("refuse"));
				break;
			case 2:case 3://接受			
				start_time=new java.util.Date(Long.valueOf((String)map.get("start_time")));
				end_time=new java.util.Date(Long.valueOf((String)map.get("end_time")));
				String free_time="";
				if(type==3){
					SimpleDateFormat sf=new SimpleDateFormat("HH:mm");
					List<Map<String, Long>> array=(List<Map<String, Long>>)JSONArray.fromObject(map.get("free_time"));
					StringBuffer buffer=new StringBuffer("");
					for(Map<String, Long> temp:array)
						buffer.append(sf.format(new java.util.Date(temp.get("start_time")))+"-"+sf.format(new java.util.Date(temp.get("end_time")))+"|");
					free_time=buffer.substring(0, buffer.length()-1);
					responseInvite.setFree_time(free_time);
				}
				java.util.Date remind_time=new java.util.Date(Long.valueOf((String)map.get("remind_time")));
				responseInvite.setRemind_time(remind_time);
				gxlTaskService.copyTask(Integer.valueOf((String)map.get("invitee")), Integer.valueOf((String)map.get("taskid")),
						start_time,end_time,free_time,remind_time);
				break;
			default:
				return ResultReturn.setMap(result, 3, "no this type", null);
			}
			responseInvite.setStart_time(start_time);
			responseInvite.setEnd_time(end_time);
			responseInvite.setType(type);
			responseInvite.setuDate(new java.util.Date());
			responseInviteService.update(responseInvite);
			return ResultReturn.setMap(result, 0, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 4, e.getMessage(), null);
		}
	}
	
	//设置邀请信息
	private Map<String, Object> setInvitationInfo(Map<String, Object> map,Integer type) {
		Map<String,Object> result=new HashMap<String,Object>();
		GxlTask task=gxlTaskService.getById(Integer.valueOf((String)map.get("invite_id")));
		if(task==null||task.getType_id()==0)
			return ResultReturn.setMap(result, 1, "no this invite_id", null);
		GxlUser user=gxlUserService.getByIdWithoutDel(task.getUserid());
		if(user==null)
			return ResultReturn.setMap(result, 2, "no this invite_id", null);	
		result.put("invite_id", task.getId());//邀请id
		result.put("invite_name", user.getNickname());//昵称
		result.put("avatar", user.getHead_img());//头像地址
		result.put("invitor_id", task.getUserid());//邀请者id
		result.put("title", task.getTitle());//标题
		result.put("type", task.getType_id());//1：确定日程，2：待定日程
		result.put("location", task.getAddress());//地点
		result.put("content", task.getRemark());//备注
		result.put("scheduled_time", task.getExpect_time());//预计时间
		result.put("free_time", task.getFree_time());//空余时间
		result.put("inform_time", task.getRemind_time());//提前通知时间
		result.put("start_time", task.getStart_time());//开始时间
		result.put("end_time", task.getEnd_time());//结束时间
		String invited_userid=task.getInvited_userid();
		if(type==0&&!invited_userid.equals(""))
			result.put("invitees", responseInviteService.responseInvitations(task.getId(), invited_userid, result));			
		return ResultReturn.setMap(result, 0, "success", null);
	}
	
	//通过id获取邀请信息
	//表response_invite  
	@CrossOrigin(origins="*",maxAge=3600)
	@ResponseBody
	@RequestMapping(value="/GetAppInvitation",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> getAppInvitation(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			return setInvitationInfo(map, 0);
		}catch (Exception e) {
			result.clear();
			e.printStackTrace();
			return ResultReturn.setMap(result, 3, e.getMessage(), null);
		}
		
	}
	
	//通过id获取邀请信息
	//表response_invite  
	@CrossOrigin(origins="*",maxAge=3600)
	@ResponseBody
	@RequestMapping(value="/GetWebInvitation",method=RequestMethod.GET,headers="Accept=application/json")
	public Map<String,Object> getWebInvitation(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			return setInvitationInfo(map, 1);
		}catch(Exception exception){
			result.clear();
			exception.printStackTrace();
			return ResultReturn.setMap(result, 3, exception.getMessage(), null);
		}
	}
	
	//获取邀请信息（web端）
	@CrossOrigin(origins="*",maxAge=3600)
	@ResponseBody
	@RequestMapping(value="/GetInvitationInfo",method=RequestMethod.GET, headers="Accept=application/json")
	public Map<String,Object> getInvitationInfo(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {		
		Map<String, Object> result = new HashMap<String, Object>();
		try {							
			return  ResultReturn.setJson(result, 0, "success", gxlTaskService.getInviteInfo(Integer.valueOf((String)map.get("taskid"))));
		}catch(Exception exception){			
			return ResultReturn.setJson(result, 3, exception.getMessage(), null);
		}
		
	}
	
	//获取日程修改信息（web端）
	@CrossOrigin(origins="*",maxAge=3600)
	@ResponseBody
	@RequestMapping(value="/GetModifyInfo",method=RequestMethod.GET, headers="Accept=application/json")
	public Map<String, Object> GetModifyInfo(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {		
		Map<String, Object> result = new HashMap<String, Object>();
		try {							
			return  ResultReturn.setJson(result, 0, "success", gxlTaskService.getModifyInfo(Integer.valueOf((String)map.get("taskid"))));
		}catch(Exception exception){			
			return ResultReturn.setJson(result, 3, exception.getMessage(), null);
		}
	}
	
	//修改日程
	@ResponseBody
	@RequestMapping(value="/ModifyTaskInfo",method=RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> modifyTaskInfo(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {		
		Map<String, Object> result = new HashMap<String, Object>();
		try {			
			Integer taskid=Integer.valueOf((String)map.remove("taskid"));
			GxlTask task=gxlTaskService.getByIdWithoutDel(taskid);
			if(task==null)
				return ResultReturn.setMap(result, 1, "no this taskid", null);
			String rtime=(String)map.get("remind_time");
			if(rtime==null||rtime.equals(""))
				map.replace("remind_time",null);
			Map<String, Object> condition=new HashMap<>();
			map.put("type_name", typeList.get(Integer.valueOf((String)map.get("type_id"))));
			map.put("uDate", new java.util.Date());
			condition.put("id", taskid);
			if(gxlTaskService.update(map, condition))
				return  ResultReturn.setMap(result, 0, "success", null);
			return  ResultReturn.setMap(result, 3, "false", null);
		}catch(Exception exception){			
			return ResultReturn.setMap(result, 2, exception.getMessage(), null);
		}
	}
		
}
