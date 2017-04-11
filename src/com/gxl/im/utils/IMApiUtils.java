package com.gxl.im.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gxl.im.base.ClientContext;
import com.gxl.im.base.HttpClientRestAPIInvoker;
import com.gxl.im.wrapper.HeaderWrapper;
import com.gxl.im.wrapper.ResponseWrapper;

public class IMApiUtils {

	private ClientContext context;
	
	private HttpClientRestAPIInvoker invoker;
	
	private static IMApiUtils imApiUtils;
	
	private IMApiUtils(ClientContext context,HttpClientRestAPIInvoker invoker) {
		this.context=context;
		this.invoker=invoker;
		
	}
	
	public static IMApiUtils getInstance() {
		if(imApiUtils == null ) {
			ClientContext context = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES);
			imApiUtils = new IMApiUtils(context,new HttpClientRestAPIInvoker());
		}
		return imApiUtils;
	}
	
	//获取认证Token
	public ResponseWrapper getAuthToken(String clientId, String clientSecret) {
		String url = context.getSeriveURL() + "/token";
		ObjectNode node=JsonNodeFactory.instance.objectNode().
				put("grant_type","client_credentials" ).put("client_id", clientId).put("client_secret", clientSecret);
		HeaderWrapper header = HeaderWrapper.newInstance().addJsonContentHeader();		
		return invoker.sendRequest("POST", url, header, node, null);
	}
	
	//基本请求信息
	public ResponseWrapper baseRequest(String purl,String method,HeaderWrapper headerWrapper,ContainerNode<?> body) {
		String url = context.getSeriveURL() + purl;
		HeaderWrapper header = headerWrapper;
		if(header==null)
			header=HeaderWrapper.newInstance().addAuthorization(context.getAuthToken());
		return invoker.sendRequest(method, url, header, body, null);
	}
	
	//新建一个IMUser
	public ResponseWrapper createIMUserSingle(String username,String password,String nickname) {
		ObjectNode node=JsonNodeFactory.instance.objectNode().put("username", username)
				.put("password", password).put("nickname",nickname );
		HeaderWrapper header = HeaderWrapper.newInstance().addJsonContentHeader().addAuthorization(context.getAuthToken());
		return baseRequest("/users","POST" , header, node);
	}
	
	//新建多个IMUser
	public ResponseWrapper createIMUsers(List<String> username,List<String> password,List<String> nickname) {
		ArrayNode root = JsonNodeFactory.instance.arrayNode();		
		for(int i=0;i<username.size();++i) {
			ObjectNode node=JsonNodeFactory.instance.objectNode().put("username", username.get(i))
					.put("password", password.get(i)).put("nickname",nickname.get(i));
			root.add(node);
		}
		HeaderWrapper header = HeaderWrapper.newInstance().addJsonContentHeader().addAuthorization(context.getAuthToken());
		return baseRequest("/users","POST" , header, root);
	}

	//获取一个IMUser
	public ResponseWrapper getIMUserSingle(String username) {
		return baseRequest("/users/"+username, "GET",null,null);
	}
	
	//获取count个IMUser
	public ResponseWrapper getIMUsersByCount(Integer count) {
		return baseRequest("/users?limit="+count,"GET",null,null);
	}
	
	//删除一个IMUser
	public ResponseWrapper deleteIMUserSingle(String username) {
		return baseRequest("/users/"+username, "DELETE",null,null);
	}

	//删除count个IMUser
	public ResponseWrapper deleteIMUserByCount(Integer count) {
		return baseRequest("/users?limit="+count,"DELETE",null,null);
	}
	
	//重置IMUser密码
	public ResponseWrapper resetIMUSerPassword(String username,String newpassword) {
		ObjectNode node=JsonNodeFactory.instance.objectNode().put("newpassword", newpassword);
		return baseRequest("/users/"+username+"/password", "PUT", null, node);
	}
	
	//重置IMUser昵称()
	public ResponseWrapper resetIMUserNickname(String username,String nickname) {
		ObjectNode node=JsonNodeFactory.instance.objectNode().put("nickname", nickname);
		return baseRequest("/users/"+username, "PUT", null, node);
	}

	//增加一个IMUser好友("entities": [ {"username":"yantao", "activated":true}])
	public ResponseWrapper addIMUserFriend(String ownername,String friendname) {
		return baseRequest("/users/"+ownername+"/contacts/users/"+friendname, "POST", null, null);
	}
	
	//往IMUser黑名单增加多个用户("data" : [ "5cxhactgdj", "mh2kbjyop1" ])
	public ResponseWrapper addIMUserBlackList(String ownername,List<String> friendname) {
		ObjectNode node=JsonNodeFactory.instance.objectNode();
		ArrayNode arrayNode=node.withArray("usernames");
		for(int i=0;i<friendname.size();++i)
			arrayNode.add(friendname.get(i));
		return baseRequest("/users/"+ownername+"/blocks/users", "POST", null, node);
	}
	
	//删除一个IMUser好友("entities" : [ { "username" : "88888","activated" : true,  "nickname" : "88888",}])
	public ResponseWrapper deleteIMUserFriend(String ownername,String friendname) {
		return baseRequest("/users/"+ownername+"/contacts/users/"+friendname, "DELETE", null, null);
	}
	
	//获取IMUser好友列表("data": ["ss01","ss05","ss09"])
	public ResponseWrapper getIMUserFriend(String ownername) {
		return baseRequest("/users/"+ownername+"/contacts/users", "GET", null, null);
	}
	
	//获取IMUser黑名单列表(“data” : [ “stliu2” ] )
	public ResponseWrapper getIMUserBlackList(String ownername) {
		return baseRequest("/users/"+ownername+"/blocks/users", "GET", null, null);
	}
	
	//从黑名单中删除一个用户，恢复好友关系("entities" : [ {"username" : "5cxhactgdj","activated" : true} ])
	public ResponseWrapper deleteIMUserBlackList(String ownername,String friendname) {
		return baseRequest("/users/"+ownername+"/blocks/users/"+friendname, "DELETE", null, null);
	}
	
	//查看用户好友在线状态("data": { "stliu": "online" online 或者 offline} )
	public ResponseWrapper checkIMUserOnline(String ownername) {
		HeaderWrapper header = HeaderWrapper.newInstance().addJsonContentHeader().addAuthorization(context.getAuthToken());
		return baseRequest("/users/"+ownername+"/status","GET" , header, null);
	}
	
	//查看用户的离线信息数(“data” : {“v3y0kf9arx” : 0 })
	public ResponseWrapper getOfflineMsgCount(String ownername) {
		return baseRequest("/users/"+ownername+"/offline_msg_count","GET" , null, null);
	}
	
	//查看用户某条离线信息状态(data": {"12": "delivered" // 格式："{消息id}":"{状态}"，状态的值有两个: deliverd表示此用户的该条离线消息已经收到过了，undelivered表示此用户的该条离线消息还未收到},)
	public ResponseWrapper getOfflineMsgStatusById(String ownername,String msgId) {
		HeaderWrapper header = HeaderWrapper.newInstance().addJsonContentHeader().addAuthorization(context.getAuthToken());
		return baseRequest("/users/"+ownername+"/offline_msg_status/"+msgId,"GET" , header, null);
	}
	
	//用户账号禁用("entities": [{ "username": "zw123", "activated": false,"nickname": "zw123"}])
	public ResponseWrapper deactivateIMUser(String username) {
		HeaderWrapper header = HeaderWrapper.newInstance().addJsonContentHeader().addAuthorization(context.getAuthToken());
		return baseRequest("/users/"+username+"/deactivate","POST" , header, null);
	}
	
	//用户账号解禁
	public ResponseWrapper activateIMUser(String username) {
		HeaderWrapper header = HeaderWrapper.newInstance().addJsonContentHeader().addAuthorization(context.getAuthToken());
		return baseRequest("/users/"+username+"/activate","POST" , header, null);
	}
	
	//强制用户下线( "data" : {"result" : true// true表示强制下线成功，false表示强制用户下线失败})
	public ResponseWrapper disconnectIMUser(String username) {
		HeaderWrapper header = HeaderWrapper.newInstance().addJsonContentHeader().addAuthorization(context.getAuthToken());
		return baseRequest("/users/"+username+"/disconnect","GET" , header, null);
	}
	
	//发送文本信息("data": {"stliu1": "success","jma4": "success"})
	public ResponseWrapper sendTxt(String from,List<String> to,String message,String type) {
		HeaderWrapper header = HeaderWrapper.newInstance().addJsonContentHeader().addAuthorization(context.getAuthToken());
		ObjectNode node=JsonNodeFactory.instance.objectNode().put("target_type", type);
		ArrayNode temp=node.withArray("target");
		for(int i=0;i<to.size();++i)
			temp.add(to.get(i));
		node.put("from", from).put("msg", JsonNodeFactory.instance.objectNode().put("type", "txt").put("msg", message));
		return baseRequest("/messages","POST" , header, node);
	}
	
	//
}
