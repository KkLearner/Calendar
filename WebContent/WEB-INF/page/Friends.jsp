<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Friend</title>
</head>
<body>
<form action="/Calendar/Friend/AddFriend" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">添加好友  AddFriend</th>
        <tr>
        <tr>
        	<td width="30%">user_id(用户id 必填)</td>
            <td><input type="text" name="user_id"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">friend_id(好友id 必填)</td>
            <td><input type="text" name="friend_id"  value="4"/></td>
        </tr>  
        <tr>
        	<td width="30%">sourceid(0：微信，1：QQ，2：微博，3:其他 必填)</td>
            <td><input type="text" name="sourceid"  value="0"/></td>
        </tr>
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Friend/DeleteFriend" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">删除好友  DeleteFriend</th>
        <tr>
        <tr>
        	<td width="30%">user_id(用户id 必填)</td>
            <td><input type="text" name="user_id"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">friend_id(好友id 必填)</td>
            <td><input type="text" name="friend_id"  value="4"/></td>
        </tr>  
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Friend/SendMeg" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">发送信息 SendMeg</th>
        <tr>
        <tr>
        	<td width="30%">user_id(用户id 必填)</td>
            <td><input type="text" name="user_id"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">friend_id(好友id 必填)</td>
            <td><input type="text" name="friend_id"  value="4"/></td>
        </tr> 
        <tr>
        	<td width="30%">msg(信息 必填)</td>
            <td><input type="text" name="msg"  value=""/></td>
        </tr> 
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Friend/AddBlackList" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">添加黑名单 AddBlackList</th>
        <tr>
        <tr>
        	<td width="30%">user_id(用户id 必填)</td>
            <td><input type="text" name="user_id"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">friend_id(好友id 必填)</td>
            <td><input type="text" name="friend_id"  value="4"/></td>
        </tr>  
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Friend/ResetFriend" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">恢复好友 ResetFriend</th>
        <tr>
        <tr>
        	<td width="30%">user_id(用户id 必填)</td>
            <td><input type="text" name="user_id"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">friend_id(好友id 必填)</td>
            <td><input type="text" name="friend_id"  value="4"/></td>
        </tr>  
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Friend/SendMsgFroce" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">强制发信息 SendMsgFroce</th>
        <tr>
        <tr>
        	<td width="30%">userid(环信用户id 必填)</td>
            <td><input type="text" name="userid"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">friendid(环信好友id 必填)</td>
            <td><input type="text" name="friendid"  value="4"/></td>
        </tr>  
        <tr>
        	<td width="30%">msg(信息 必填)</td>
            <td><input type="text" name="msg"  value="4"/></td>
        </tr>
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>
</body>
</html>