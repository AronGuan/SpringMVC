<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

	<!-- 
		1.Why使用form标签呢?
		可以更快的开发出表单页面，而且可以更方便的进行表单值得回显
		2.模型
		可以通过modelAttribute属性指定绑定的属性指定绑定的模型属性,
		若没有指定该属性，则默认从request从Request域对象中读取command的表单bean
		如果该属性值也不存在，则会发生错误
	 -->
	 
	 <form:form action="emp" method="post" modelAttribute="employee">
	 <!-- path属性对应html表单标签的name属性值 -->
	 	LastName：<form:input path="LastName"/>
	 	<br/>
	 	Email：<form:input path="email"/>
		<br/>
		<%
			Map<String,String> genders = new HashMap<String,String>();
			genders.put("1","Male");
			genders.put("0","Female");
			
			request.setAttribute("genders",genders);
		%>
		Gender：<form:radiobuttons path="gender" items="${genders}"/>
		<br/>
			Department: <form:select path="department.id" 
			items="${departments}" itemLabel="departmentName" itemValue="id"></form:select>
		<br>
		<br>
		
		<input type="submit" value="Submit"/>
	 </form:form>
	 
</body>
</html>