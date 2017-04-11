package com.gxl.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.common.utils.ResultReturn;
import com.gxl.dao.GxlFriendsDao;
import com.gxl.dao.ResponseInviteDao;
import com.gxl.entity.GxlFriends;
import com.gxl.entity.ResponseInvite;
import com.gxl.service.ResponseInviteService;

@Transactional
@Service("responseInviteService")
public class ResponseInviteServiceImpl extends BaseServiceImpl<ResponseInvite> implements ResponseInviteService {

	@Autowired
	private ResponseInviteDao responseInviteDao;
	@Autowired
	private GxlFriendsDao gxlFriendsDao;
	
	@Override
	public Map<String, Object> sendInvitations(Integer taskid,Integer userid,String invitees,Map<String, Object> result){
		try{
			String []invited=invitees.split(",");
			for(String use:invited){//给每个被邀请者发送邀请
				List<GxlFriends> friends=gxlFriendsDao.getByCriterion(Restrictions.eq("user_id", userid),
						Restrictions.eq("friend_id", Integer.valueOf(use)));
				if(friends==null||friends.isEmpty())
					return ResultReturn.setMap(result, 3, "no this "+use+" id", null);
				else if(friends.get(0).getIf_del()==1)
					return ResultReturn.setMap(result, 4, "you have't this friend's "+use, null);
				Map<String, Object> temp=new HashMap<>();
				temp.put("taskid", taskid);
				temp.put("invitee", use);
				temp.put("type", 0);
				temp.put("uDate", new java.util.Date());
				responseInviteDao.add(temp);
			}
			result.put("invite_id", taskid);
			return ResultReturn.setMap(result, 0, "success", null);
		}catch (Exception e) {
			e.printStackTrace();
			result.clear();
			return ResultReturn.setMap(result, 5, "false", null);
		}
	}

	public List<Map<String, Object>> responseInvitations(Integer taskid,String invitees,Map<String, Object> result){
		List<Map<String, Object>> invitee=new ArrayList<>();
		try {
			String []users=invitees.split(",");
			SimpleDateFormat sf=new SimpleDateFormat();
			for(String invited:users){
				Map<String, Object> teMap=new HashMap<>();
				List<ResponseInvite> responseInvites=responseInviteDao.getByCriterion(Restrictions.eq("taskid", taskid),Restrictions.eq("invitee", invited));
				if(responseInvites==null||responseInvites.isEmpty())
					continue;
				teMap.put("Invitee_id", invited);
				ResponseInvite responseInvite=responseInvites.get(0);
				Integer t=responseInvite.getType();
				teMap.put("type", t);
				String refuse=null;
				String time_horizon=null;
				java.util.Date inform_time=null;
				switch (t) {
				case 0://默认，未回复
					break;
				case 1://拒绝
					refuse=responseInvite.getRefuse();
					break;
				case 2://接受并确定
					sf.applyPattern("yyyy/MM/dd HH:mm:ss");
					inform_time=responseInvite.getRemind_time();
					time_horizon=sf.format(responseInvite.getStart_time())+","+sf.format(responseInvite.getEnd_time());
					break;
				case 3://接受并待定
					sf.applyPattern("yyyy/MM/dd");
					inform_time=responseInvite.getRemind_time();
					time_horizon=sf.format(responseInvite.getStart_time())+","+responseInvite.getFree_time();
					break;
				default:
					break;
				}
				teMap.put("content", refuse);
				teMap.put("time_horizon", time_horizon);
				teMap.put("inform_time", inform_time);
				invitee.add(teMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			invitee=null;
		}
		return invitee;
	}
}
