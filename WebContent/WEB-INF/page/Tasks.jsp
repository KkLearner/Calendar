<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Task</title>
</head>
<body>
<form action="/Calendar/Task/GetTodayAllTask" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">通过日期获取该用户该天所有待办和日程  GetTodayAllTask</th>
        <tr>
        <tr>
        	<td width="30%">user_id(用户id  必填)</td>
            <td><input type="text" name="user_id"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">date(日期（格式：2017/03/19 ） 必填)</td>
            <td><input type="text" name="date"  value="2017/03/19"/></td>
        </tr>    
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Task/DeleteTaskById" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">通过id删除待办/日程  DeleteTaskById</th>
        <tr>
        <tr>
        	<td width="30%">id(必填)</td>
            <td><input type="text" name="id"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">type(0为待办，1为日程，2为固定日程  必填)</td>
            <td><input type="text" name="type"  value="0"/></td>
        </tr>    
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Task/AddCommission" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">添加待办  AddCommission</th>
        <tr>
        <tr>
        	<td width="30%">title(标题 必填)</td>
            <td><input type="text" name="title"  value="就是测试"/></td>
        </tr>
        <tr>
        	<td width="30%">end_time(截止日期 格式：2017/03/15 09:20:00  必填)</td>
            <td><input type="text" name="end_time"  value="2017/03/15 09:20:00"/></td>
        </tr> 
        <tr>
        	<td width="30%">remind_time(提醒时间,格式：yyyy/MM/dd HH:mm:ss)</td>
            <td><input type="text" name="remind_time"  value="2017/03/15 09:00:00"/></td>
        </tr>
        <tr>
        	<td width="30%">userid(用户id 必填)</td>
            <td><input type="text" name="userid"  value="1"/></td>
        </tr>     
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Task/AddSchedule" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">添加日程  AddSchedule</th>
        <tr>
        <tr>
        	<td width="30%">userid(用户id 必填)</td>
            <td><input type="text" name="userid"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">invited_userid(邀请他人（用','分割被邀请人的用户id）)</td>
            <td><input type="text" name="invited_userid"  value=""/></td>
        </tr> 
        <tr>
        	<td width="30%">title(标题 必填)</td>
            <td><input type="text" name="title"  value="就是测试"/></td>
        </tr>
        <tr>
        	<td width="30%">address(位置)</td>
            <td><input type="text" name="address"  value=""/></td>
        </tr> 
        <tr>
        	<td width="30%">remind_time(提醒时间,格式yyyy/MM/dd HH:mm:ss)</td>
            <td><input type="text" name="remind_time"  value="2017/03/21 12:12:00"/></td>
        </tr>
        <tr>
        	<td width="30%">schedule_type(日程时间类型 ：0-确定/1-待定)</td>
            <td><input type="text" name="schedule_type"  value="0"/></td>
        </tr>  
        <tr>
        	<td width="30%">time_range(时间范围，格式：schedule_type为0时,2017/03/21 03:18:00,2017/03/12 18:10:00。schedule_type为1时,2017/03/21,07:00-08:10|09:10-10:32)</td>
            <td><input type="text" name="time_range"  value="2017/03/21 13:18:00,2017/03/12 15:10:00"/></td>
        </tr>
         <tr>
        	<td width="30%">expect_time(预计时间)</td>
            <td><input type="text" name="expect_time"  value="1小时"/></td>
        </tr> 
        <tr>
        	<td width="30%">remark(备注)</td>
            <td><input type="text" name="remark"  value=""/></td>
        </tr>       
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Task/ResponseInvite" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">回复邀请  ResponseInvite</th>
        <tr>
        <tr>
        	<td width="30%">taskid(邀请id 必填)</td>
            <td><input type="text" name="taskid"  value="33"/></td>
        </tr>
        <tr>
        	<td width="30%">invitee(被邀请人id 必填)</td>
            <td><input type="text" name="invitee"  value="4"/></td>
        </tr> 
        <tr>
        	<td width="30%">type(邀请回复（1：拒绝/2：接受并确定/3：接受并待定） 必填)</td>
            <td><input type="text" name="type"  value="2"/></td>
        </tr> 
        <tr>
        	<td width="30%">start_time(开始时间，type=2,3时填写,时间戳)</td>
            <td><input type="text" name="remind_time"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">end_time(结束时间，type=2,3时填写，时间戳)</td>
            <td><input type="text" name="remind_time"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">free_time(空余时间，type=3时填写，[{"start_time":"时间戳","end_time":"时间戳"}])</td>
            <td><input type="text" name="remind_time"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">remind_time(提醒时间，type=2,3时填写,时间戳)</td>
            <td><input type="text" name="remind_time"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">refuse(拒绝理由 type=1时填写)</td>
            <td><input type="text" name="refuse"  value=""/></td>
        </tr>       
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Task/GetAppInvitation" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">通过id获取邀请信息  GetAppInvitation</th>
        <tr>
        <tr>
        	<td width="30%">invite_id(邀请id 必填)</td>
            <td><input type="text" name="invite_id"  value="33"/></td>
        </tr>
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>
</body>
</html>