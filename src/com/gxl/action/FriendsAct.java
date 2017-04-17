package com.gxl.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gxl.common.utils.ResultReturn;
import com.gxl.entity.GxlFriends;
import com.gxl.entity.GxlUser;
import com.gxl.im.utils.IMApiUtils;
import com.gxl.im.wrapper.ResponseWrapper;
import com.gxl.service.GxlFriendsService;
import com.gxl.service.GxlUserService;

@Controller
@RequestMapping("/Friend")
public class FriendsAct {

	private static IMApiUtils imApiUtils=IMApiUtils.getInstance();
	@Autowired
	private GxlUserService gxlUserService;
	@Autowired
	private GxlFriendsService gxlFriendsService;
	private Map<Integer, String> sourceList=new HashMap<Integer,String>(){{
		put(0,"微信");
		put(1,"QQ");
		put(2,"微博");
		put(3,"其他");
	}};
	
	//添加好友
	//表gxl_friends gxl_user  
	@ResponseBody
	@RequestMapping(value="/AddFriend",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> addFriend(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			Integer user_id=Integer.valueOf((String)map.get("user_id"));
			GxlUser user=gxlUserService.getById(user_id);
			if(user==null)
				return ResultReturn.setMap(result, 1, "no this userid", null);
			Integer friend_id=Integer.valueOf((String)map.get("friend_id"));
			GxlUser friend=gxlUserService.getById(friend_id);
			if(friend==null)
				return ResultReturn.setMap(result, 2, "no this friend_id", null);
			List<GxlFriends> friends=gxlFriendsService.getByCriterion(Restrictions.eq("user_id", user_id),Restrictions.eq("friend_id", friend_id));
			if(friends==null||friends.isEmpty()){//从来没有添加过好友
				Integer sourceid=Integer.valueOf((String)map.get("sourceid"));
				if(!sourceList.containsKey(sourceid))
					return ResultReturn.setMap(result, 3, "no this sourceid", null);
				GxlFriends one=new GxlFriends(user_id, friend_id, 0, friend.getNickname(), 
						sourceid, sourceList.get(sourceid), 0, new Date());
				GxlFriends two=new GxlFriends(friend_id,user_id , 0, user.getNickname(), 
						sourceid, sourceList.get(sourceid), 0, new Date());
				gxlFriendsService.add(one);
				gxlFriendsService.add(two);
			}else{//之前添加过
				GxlFriends one=friends.get(0);
				if(one.getIf_del()==1){//将其删除了好友，现在恢复
					one.setIf_del(0);
					one.setuDate(new Date());
					gxlFriendsService.update(one);
				}
			}
			IMApiUtils.getInstance().addIMUserFriend(user.getIm_name(), friend.getIm_name());
			return ResultReturn.setMap(result, 0, "success", null);
		}catch(Exception e){
			e.printStackTrace();
			return ResultReturn.setMap(result, 4, "false", null);
		}
	}
	
	//删除好友
	//表gxl_friends gxl_user  
	@ResponseBody
	@RequestMapping(value="/DeleteFriend",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> deleteFriend(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			return gxlFriendsService.changeFriendType(Integer.valueOf((String)map.get("user_id")), Integer.valueOf((String)map.get("friend_id")), null,1);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 4, "false", null);
		}
	}
	
	//发送信息
	//表gxl_user   
	@ResponseBody
	@RequestMapping(value="/SendMeg",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> sendMeg(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			GxlUser user=gxlUserService.getById(Integer.valueOf((String)map.get("user_id")));
			if(user==null)
				return ResultReturn.setMap(result, 1, "no this userid", null);
			GxlUser friend=gxlUserService.getById(Integer.valueOf((String)map.get("friend_id")));
			if(friend==null)
				return ResultReturn.setMap(result, 2, "no this friend_id", null);
			List<String> to=new ArrayList<>();
			to.add(friend.getIm_name());
			ResponseWrapper wrapper=imApiUtils.sendTxt(user.getIm_name(), to,(String)map.get("msg"), "users");
			if(wrapper.hasError())
				return ResultReturn.setMap(result, 3, wrapper.toString(), null);
			return ResultReturn.setMap(result, 0, "success", null);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 4, "false", null);
		}
	}
	
	//添加黑名单
	//表gxl_friends  
	@ResponseBody
	@RequestMapping(value="/AddBlackList",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> addBlackList(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			return gxlFriendsService.changeFriendType(Integer.valueOf((String)map.get("user_id")),Integer.valueOf((String)map.get("friend_id")) , 1,null);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 4, "false", null);
		}
	}
	
	//恢复好友
	//表gxl_friends  
	@ResponseBody
	@RequestMapping(value="/ResetFriend",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> resetFriend(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			return gxlFriendsService.changeFriendType(Integer.valueOf((String)map.get("user_id")), Integer.valueOf((String)map.get("friend_id")), 0,null);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 4, "false", null);
		}
	}
}
