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

<script type="text/javascript">
	$(function(){
		$(".delete").click(function(){
			var href=$(this).attr("href");
			$("form").attr("action",href).submit();
			return false;
		})
	 });
</script>
</head>
<body>
	
	<form action="" method="POST">
		<input type="hidden" name="_method" value="DELETE"/>
	</form>
	
	<c:if test="${empty requestScope.employees}">
		没有任何员工信息。
	</c:if>
	
	<c:if test="${!empty requestScope.employees}">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>ID</th>
				<th>LastName</th>
				<th>Email</th>
				<th>Gender</th>
				<th>Department</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			
			<c:forEach items="${requestScope.employees}" var="emp">
				<tr>
					<td>${emp.id}</td>
					<td>${emp.lastName}</td>
					<td>${emp.email}</td>
					<td>${emp.gender == 0?'Female':'Male'}</td>
					<td>${emp.department.departmentName}</td>
					<td><a href="emp/${emp.id}">Edit</a></td>
					<td><a class="delete" href="emp/${emp.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<br/>
	<a href="emp">Add New Employ</a>
</body>
</html>