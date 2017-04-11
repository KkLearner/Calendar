<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/Calendar/Test/Add" method="POST" >
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">test add</th>
        <tr>
        <tr>
        	<td width="30%">name(必填)</td>
            <td><input type="text" name="name" value="" /></td>
        </tr>
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Test/Delete" method="POST" >
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">test delete</th>
        <tr>
        <tr>
        	<td width="30%">name(必填)</td>
            <td><input type="text" name="name" value="" /></td>
        </tr>
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Test/Find" method="POST" >
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">test find</th>
        <tr>
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>

<form action="/Calendar/Test/Update" method="POST" >
	<table width="500px" border="1px #FFFFFF soild">
    	<tr>
        	<th colspan="2" align="left">test Update</th>
        <tr>
        <tr>
        	<td colspan="2" align="left"><input type="submit" value="go"/> </td>
        <tr>
    </table>
</form>
</body>