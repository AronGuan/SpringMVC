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
	
	<form action="testConversionServiceConverser" method="POST">
		<!-- lastname-email-gender-department.id 例如:GG-gg@atuguigu.com-0-105-->
		Employee：<input type="text" name="employee"/>
		<input type="submit" value="Submit"/>
	</form>
    <br><br>
	<!-- 
		1.Why使用form标签呢?
		可以更快的开发出表单页面，而且可以更方便的进行表单值得回显
		2.模型
		可以通过modelAttribute属性指定绑定的属性指定绑定的模型属性,
		若没有指定该属性，则默认从Request域对象中读取command的表单bean
		如果该属性值也不存在，则会发生错误
	 -->
	 <!-- 推荐使用绝对路径 -->
	 <form:form action="${pageContext.request.contextPath}/emp" method="post" modelAttribute="employee">
	 <!-- path属性对应html表单标签的name属性值 -->
	 
	 	<form:errors path="*"></form:errors>
	 	<br>
	 	<c:if test="${employee.id == null }">
	 	LastName：<form:input path="lastName"/>
	 	<form:errors path="lastName"></form:errors>
	 	<br/>
	 	</c:if>
	 	<c:if test="${employee.id != null }">
	 		<form:hidden path="id"/>
	 		<!-- 对于_method 不能使用form:hidden标签, 因为modelAttribute对应的bean中没有_method这个属性
	 		用input标签-->
	 		<input type="hidden" name="_method" value="PUT"/>
	 	</c:if>
	 	
	 	Email：<form:input path="email"/>
	 	<form:errors path="email"></form:errors>
		<br/>
		<%
			Map<String,String> genders = new HashMap<String,String>();
			genders.put("1","Male");
			genders.put("0","Female");
			
			request.setAttribute("genders",genders);
		%>
		Gender：<form:radiobuttons path="gender" items="${genders}" delimiter="<br>"/>
		<br/>
			Department: <form:select path="department.id" 
			items="${departments}" itemLabel="departmentName" itemValue="id"></form:select>
		<!-- 1.数据类型转换
			 2.数据类型格式化
			 3.数据校验 
			 1).如何校验 注解
			 
			 1.使用JSR 303验证标准
			 2.加入hibernate Validator验证框架 jar包
			 3.在SpringMVC配置文件中添加<mvc:annotation-driven/>
			 4.需要在bean的属性上添加对应的注解
			 5.在目标方法bean类型的前面添加@Valid注解
			 
			 
			 2).验证出错转向到哪一个页面
			 错误消息放在BindingResult对象上
			 注意:需校验的 Bean 对象和其绑定结果对象或错误对象时成对出现的，它们
				之间不允许声明其他的入参
			 3).错误消息?如何显示,如何把错误消息进行国际化-->
		<br>
		Birth:<form:input path="birth"/>
		<form:errors path="birth"></form:errors>
		<br>
		
		<br>
		Salary:<form:input path="salary"/>
		<br>
		
		<input type="submit" value="Submit"/>
	 </form:form>
	 
</body>
</html>