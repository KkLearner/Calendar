package com.gxl.service;

import java.util.Map;

import com.gxl.entity.GxlFriends;

public interface GxlFriendsService extends BaseService<GxlFriends> {

	public Map<String, Object> changeFriendType(Integer user_id,Integer friend_id,Integer type,Integer if_del);
}
