<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Share</title>
</head>
<body>
<form action="/Calendar/Share/CheckCodeCorrect" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">验证验证码是否正确  CheckCodeCorrect</th>
        <tr>
        <tr>
        	<td width="30%">id(用户id  必填)</td>
            <td><input type="text" name="id"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">code(验证码 必填)</td>
            <td><input type="text" name="code"  value="123456"/></td>
        </tr>    
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Share/SetAllInfo" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">设置所有信息  SetAllInfo</th>
        <tr>
        <tr>
        	<td width="30%">id(用户id 必填)</td>
            <td><input type="text" name="id"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">card_type(名片夹类型（0：自定义/1：学生/2：公司）)</td>
            <td><input type="text" name="card_type"  value="123456"/></td>
        </tr>
        <tr>
        	<td width="30%">code(验证码)</td>
            <td><input type="text" name="code"  value="123456"/></td>
        </tr>
        <tr>
        	<td width="30%">nickname(姓名 必填)</td>
            <td><input type="text" name="nickname"  value="kk"/></td>
        </tr>
                <tr>
        	<td width="30%">partnership_name(card_type=1时，表示院校名。当card_type=2时，表示行业名  必填)</td>
            <td><input type="text" name="partnership_name"  value="华南师范大学 计算机学院"/></td>
        </tr>
        <tr>
        	<td width="30%">partnership_detail(当card_type=1时，表示社团名；当card_type=2时，表示公司名  必填)</td>
            <td><input type="text" name="partnership_detail"  value="小清新"/></td>
        </tr>
        <tr>
        	<td width="30%">job(职位 必填)</td>
            <td><input type="text" name="job"  value="干事"/></td>
        </tr>
        <tr>
        	<td width="30%">province(所在地省份)</td>
            <td><input type="text" name="province"  value="广东"/></td>
        </tr>
        <tr>
        	<td width="30%">city(所在地城市)</td>
            <td><input type="text" name="city"  value="肇庆"/></td>
        </tr>
        <tr>
        	<td width="30%">phone(联系电话)</td>
            <td><input type="text" name="phone"  value="14112"/></td>
        </tr>
                <tr>
        	<td width="30%">email(邮箱)</td>
            <td><input type="text" name="email"  value="342121"/></td>
        </tr>
        <tr>
        	<td width="30%">weixin(微信)</td>
            <td><input type="text" name="weixin"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">qq</td>
            <td><input type="text" name="qq"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">weibo(微博)</td>
            <td><input type="text" name="weibo"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">birthday(生日)</td>
            <td><input type="text" name="birthday"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">blood(血型)</td>
            <td><input type="text" name="blood"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">address(详细地址)</td>
            <td><input type="text" name="address"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">homepage(个人主页)</td>
            <td><input type="text" name="homepage"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">person_extend(个人信息扩展部分   json格式字符串)</td>
            <td><input type="text" name="person_extend"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">connect_extend(联系信息扩展部分  json格式字符串)</td>
            <td><input type="text" name="connect_extend"  value=""/></td>
        </tr>
         <tr>
        	<td width="30%">other_extend(其他信息扩展部分  json格式字符串)</td>
            <td><input type="text" name="other_extend"  value=""/></td>
        </tr>    
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Share/Feedback" method="POST" enctype="multipart/form-data">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">反馈  Feedback</th>
        <tr>
        <tr>
        	<td width="30%">userid(用户id  必填)</td>
            <td><input type="text" name="userid"  value="1"/></td>
        </tr>
        <tr>
        	<td width="30%">content(反馈内容)</td>
            <td><input type="text" name="content"  value="有问题"/></td>
        </tr>
        <tr>
        	<td width="30%">images(图片文件组)</td>
            <td><input type="file" name="images"  value="" multiple /></td>
        </tr>  
        <tr>
        	<td width="30%">fault_time(故障时间)</td>
            <td><input type="text" name="fault_time"  value=""/></td>
        </tr> 
         <tr>
        	<td width="30%">phone(联系电话)</td>
            <td><input type="text" name="phone"  value=""/></td>
        </tr>     
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>
</body>
</html>