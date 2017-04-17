package com.gxl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.common.utils.ResultReturn;
import com.gxl.dao.GxlFriendsDao;
import com.gxl.dao.GxlUserDao;
import com.gxl.entity.GxlFriends;
import com.gxl.entity.GxlUser;
import com.gxl.im.utils.IMApiUtils;
import com.gxl.service.GxlFriendsService;

@Transactional
@Service("gxlFriendsService")
public class GxlFriendsServiceImpl extends BaseServiceImpl<GxlFriends> implements GxlFriendsService {

	@Autowired
	private GxlFriendsDao gxlFriendsDao;
	@Autowired
	private GxlUserDao gxlUserDao;
	
	public Map<String, Object> changeFriendType(Integer user_id,Integer friend_id,Integer type,Integer if_del) {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			GxlUser user=gxlUserDao.getById(user_id);
			if(user==null)
				return ResultReturn.setMap(result, 1, "no this userid", null);
			GxlUser friend=gxlUserDao.getById(friend_id);
			if(friend==null)
				return ResultReturn.setMap(result, 2, "no this friend_id", null);
			List<GxlFriends> friends=gxlFriendsDao.getByCriterion(Restrictions.eq("user_id", user_id),Restrictions.eq("friend_id", friend_id),
					Restrictions.eq("if_del", 0));
			if(friends==null||friends.isEmpty())
				return ResultReturn.setMap(result, 3, "you have't this friend", null);
			GxlFriends one=friends.get(0);
			boolean flag=false;
			if(type!=null&&one.getType()!=type){
				if(type==1){
					List<String> blackList=new ArrayList<>();
					blackList.add(friend.getIm_name());
					IMApiUtils.getInstance().addIMUserBlackList(user.getIm_name(), blackList);
				}else if(type==0){
					IMApiUtils.getInstance().deleteIMUserBlackList(user.getIm_name(), friend.getIm_name());
				}
				one.setType(type);			
				flag=true;
			}
			if(if_del!=null&&one.getIf_del()!=if_del){
				if(if_del==1)
					IMApiUtils.getInstance().deleteIMUserFriend(user.getIm_name(), friend.getIm_name());
				one.setIf_del(if_del);
				flag=true;
			}
			if(flag){
				one.setuDate(new Date());
				gxlFriendsDao.update(one);
			}
			return ResultReturn.setMap(result, 0, "success", null);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result,4, "false", null);
		}
	}
}
