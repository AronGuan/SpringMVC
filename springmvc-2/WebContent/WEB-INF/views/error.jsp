<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- SpringMVC处理静态资源
	1.为什么会有这样的问题:
	优雅的Rest风格的资源URL不希望带.html或.do等后缀
	若将DispatcherServlet请求映射配置为/.则SpringMVC将捕获WEB容器的所有请求,包括静态资源的请求，
	SpringMVC会将他们当成一个普通请求处理，因找不到对应处理器将导致出错。
	
	2.解决:在SpringMVC的配置文件中配置<mvc:default-servlet-handler/>的方式解决静态资源的问题
 -->
<script type="text/javascript" src="script/jquery.js"></script>


</head>
<body>
	
	<h4>Error Page</h4>
	
	${exception }
</body>
</html>