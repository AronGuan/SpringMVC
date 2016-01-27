<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<h4>Success Page</h4>
	
	time:${requestScope.time}
	<br/>
	names:${requestScope.names}
	<br/>
	request user:${requestScope.user}
	
	<br/>
	Session user:${requestScope.user}
	<br/>
	request school:${requestScope.school}
	
	<br/>
	Session school:${sessionScope.school}
	<br/>
	abc user:${requestScope.abc}
	<br/>
	user user: ${requestScope.user}
	<br/>
	<fmt:message key="i18n.username"></fmt:message>
	<br/>
	<fmt:message key="i18n.password"></fmt:message>
	
</body>
</html>