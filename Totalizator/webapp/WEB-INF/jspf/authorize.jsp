<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="pageContent" var="rb" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>
<body>
	<fmt:message key="placeHolder.login" var="login" bundle="${rb}"/>
	<fmt:message key="placeHolder.password" var="password"  bundle="${rb}"/>
	<form action = "Controller" method="post">
		<input type = "hidden" name = 'command' value = 'login'>
	    <span>
	    	<input class = 'login' required type = 'text' id = 'login' name = 'login' placeholder = '${login}'></input>
	    	<span></span>
	    </span>
	    <span>
	    	<input required type = 'password' id = 'password' name = 'password' placeholder = '${password}'></input>
	    	<span></span>
	    </span>
	    <button type = 'submit' class = 'author'><fmt:message key="button.submit" bundle="${rb}" /></button>
	    <button type = 'reset' class = 'author'><fmt:message key="button.reset" bundle="${rb}" /></button> 
	</form>
	<a href = "${pageContext.request.contextPath}/jsp/register.jsp"><fmt:message key="authorize.registration" bundle="${rb}" /></a>
</body>
</html>