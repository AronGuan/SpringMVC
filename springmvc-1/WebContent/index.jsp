<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- 模拟修改操作
		1.原始数据为:1,Tom,123456,tom@atguigu.com,12
		2.密码不能被修改
		3.表单回显，模拟操作直接在表单填写对应的属性值 -->
	<form action="springmvc/testModelAttribute" method="post">
		<input type="hidden" name="id" value="1">
		username:<input type="text" name="username" value="Tom">
		<br>
		email:<input type="text" name="email" value="tom@atguigu.com">
		<br>
		age:<input type="text" name="age" value="12">
		<br>
		<input type="submit" value="Submit"/> 
	</form>
	<br/>
	
	<a href="springmvc/testSessionAttributes">testSessionAttributes</a>
	<br/>

	<a href="springmvc/testMap">testMap</a>
	<br/>
	<a href="springmvc/testModelAndView">testModelAndView</a>
	<br/>
	<a href="springmvc/testServletAPI">testServletAPI</a>
	<br/>

	<form action="springmvc/testPojo" method="post">
		username:<input type="text" name="username"/>
		<br>
		password:<input type="password" name="password"/>
		<br>
		email:<input type="text" name="email"/>
		<br>
		age:<input type="text" name="age"/>
		<br>
		city:<input type="text" name="address.city"/>
		<br>
		province:<input type="text" name="address.province"/>
		<br>
		<input type="submit" value="submit"/>
	</form>
	<br/>
	<a href="springmvc/tesCookieValue">tesCookieValue</a>
	<br/>

	<a href="springmvc/testReuestHeader">testReuestHeader</a>
	<br/>

	<a href="springmvc/testReuestHeader">testReuestHeader</a>
	<br/>

	<a href="springmvc/testRequestParam?username=atguigu&age=11">testRequestParam</a>
	<br/>

	<form action="springmvc/testRest/1" method="post">
		<input type="hidden" name="_method" value="PUT">
		<input type="submit" value="TestRest PUT">
	</form>
	<br/>
	
	<form action="springmvc/testRest/1" method="post">
		<input type="hidden" name="_method" value="DELETE">
		<input type="submit" value="TestRest DELETE">
	</form>
	<br/>
	
	<form action="springmvc/testRest" method="post">
		<input type="submit" value="TestRest POST">
	</form>
	<br/>
	<a href="springmvc/testRest/1">Test Rest Get</a>
	<br/>



	<a href="springmvc/testPathVariable/1">testPathVariable</a>
	<br/>
	<a href="springmvc/testAntPath/dssdds/abc">testAntPath</a>
	<br/>
	<a href="springmvc/testParamsAndHeaders?username=atguigu&age=11">testParamsAndHeaders</a>
	<br/>
	<form action="springmvc/testmethod" method="POST">
		<input type="submit" value="submit">
	</form>
	<br/>
	<a href="springmvc/testRequestMapping">testRequestMapping</a>

	<br/>
	<a href="helloworld">Hello World</a>
</body>
</html>