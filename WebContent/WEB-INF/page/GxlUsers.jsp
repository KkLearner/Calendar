<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>GxlUser</title>
</head>
<body>
<form action="/Calendar/GxlUser/Register" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">用户注册  Register</th>
        <tr>
        <tr>
        	<td width="30%">gxl_account(账号  必填)</td>
            <td><input type="text" name="gxl_account"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">password(密码 必填)</td>
            <td><input type="text" name="password"  value=""/></td>
        </tr>
        <tr>
        	<td width="30%">nickname(昵称)</td>
            <td><input type="text" name="nickname"  value=""/></td>
        </tr> 
        <tr>
        	<td width="30%">address(联系地址)</td>
            <td><input type="text" name="address"  value=""/></td>
        </tr> 
        <tr>
        	<td width="30%">sex(男：1  /  女：0 )</td>
            <td><input type="text" name="sex"  value=""/></td>
        </tr>         
        <tr>
        	<td width="30%">email(邮箱)</td>
            <td><input type="text" name="email"  value=""/></td>
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

<form action="/Calendar/GxlUser/Login" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">验证账号密码是否通过  Login</th>
        <tr>
        <tr>
        	<td width="30%">account(账号  必填)</td>
            <td><input type="text" name="account"  value="121024"/></td>
        </tr>
        <tr>
        	<td width="30%">password(密码 必填)</td>
            <td><input type="text" name="password"  value="123456"/></td>
        </tr>    
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/GxlUser/GetUserInfoByAccount" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">验证账号获取用户基本信息  GetUserInfoByAccount</th>
        <tr>
        <tr>
        	<td width="30%">account(账号  必填)</td>
            <td><input type="text" name="account"  value="121024"/></td>
        </tr>  
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/GxlUser/EditPsw" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">修改密码  EditPsw</th>
        <tr>
        <tr>
        	<td width="30%">account(账号  必填)</td>
            <td><input type="text" name="account"  value="121024"/></td>
        </tr>
        <tr>
        	<td width="30%">old_pass(旧密码 必填)</td>
            <td><input type="text" name="old_pass"  value="123456"/></td>
        </tr>  
         <tr>
        	<td width="30%">new_pass(新密码 必填)</td>
            <td><input type="text" name="new_pass"  value="456"/></td>
        </tr>    
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/GxlUser/EditInfo" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">修改个人信息  EditInfo</th>
        <tr>
        <tr>
        	<td width="30%">account(账号  必填)</td>
            <td><input type="text" name="account"  value="121024"/></td>
        </tr>
        <tr>
        	<td width="30%">nickname(昵称)</td>
            <td><input type="text" name="nickname"  value=""/></td>
        </tr>
         <tr>
        	<td width="30%">address(地址)</td>
            <td><input type="text" name="address"  value=""/></td>
        </tr>
         <tr>
        	<td width="30%">signature(个人签名)</td>
            <td><input type="text" name="signature"  value=""/></td>
        </tr>
         <tr>
        	<td width="30%">head_img(头像)</td>
            <td><input type="text" name="head_img"  value=""/></td>
        </tr>
         <tr>
        	<td width="30%">phone(手机)</td>
            <td><input type="text" name="phone"  value=""/></td>
        </tr>
         <tr>
        	<td width="30%">email(邮箱)</td>
            <td><input type="text" name="email"  value=""/></td>
        </tr>
         <tr>
        	<td width="30%">age(年龄)</td>
            <td><input type="text" name="age"  value=""/></td>
        </tr>
         <tr>
        	<td width="30%">sex(性别 （男：1  /  女：0  /  未知：2）)</td>
            <td><input type="text" name="sex"  value=""/></td>
        </tr>
         <tr>
        	<td width="30%">qr_code(二维码)</td>
            <td><input type="text" name="qr_code"  value=""/></td>
        </tr>  
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/GxlUser/GetAllUser" method="POST">
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">获取所有以注册账号（用于添加好友时的账号搜索）  GetAllUser</th>
        <tr>
        <tr>
        	<td width="30%">account(账号  必填)</td>
            <td><input type="text" name="account"  value="121024"/></td>
        </tr>   
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>
</body>
</html>