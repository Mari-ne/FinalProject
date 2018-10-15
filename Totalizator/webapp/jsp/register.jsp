<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="pageContent" var="rb" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/assets/css/body.css">
	<link rel="stylesheet" href="/assets/css/button.css">
	<link rel="stylesheet" href="/assets/css/input.css">	    
	<link rel="stylesheet" href="/assets/css/table.css">
	<link rel="stylesheet" href="/assets/css/link.css">
	<link rel="stylesheet" href="/assets/css/icon.css">
	<c:choose>
		<c:when test="${sessionScope.lang == 'en'}">
			<link rel="stylesheet" href="/assets/css/enLang.css">
		</c:when>
		<c:when test="${sessionScope.lang == 'jp'}">
			<link rel="stylesheet" href="/assets/css/jpLang.css">
		</c:when>
		<c:otherwise>
			<link rel="stylesheet" href="/assets/css/ruLang.css">
		</c:otherwise>
	</c:choose>
	<title><fmt:message key="registration.title" bundle="${rb}" /></title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\authorize.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.register"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<fmt:message key="placeHolder.login" var="loginP" bundle="${rb}"/>
		<fmt:message key="placeHolder.password" var="passwordP"  bundle="${rb}"/>
		<fmt:message key="placeHolder.email" var="email"  bundle="${rb}"/>
		<fmt:message key="registration.login" var="loginT" bundle="${rb}"/>
		<fmt:message key="registration.password" var="passwordT"  bundle="${rb}"/>
		<div>
			<fmt:message key="registration.info"  bundle="${rb}"/>
			<br>
			<fmt:message key="registration.note"  bundle="${rb}"/>
		</div>
		<form action="Controller" method = "post" style="position:absolute; left:50%">
			<input type = "hidden" name = "command" value = "register">
		    <div>
		      <input class = 'login' required type = 'text' name = 'login' pattern = '[\a\A\d-_]' placeholder = '${loginP}' minlength = '4' maxlength = '20' title = "${loginT}">
		    </div>
		    <div>
		      <input required type = 'password' name = 'password' pattern = '(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\S{8,20}' placeholder = '${passwordP}' minlength = '8' maxlength = '20' title = '${passwordT}'>
		      <span></span>
		    </div>
		    <div>
		      <input required type = "email" id = 'email' name = 'email' placeholder = '${email}'></input>
		      <span></span>
		    </div>
		    <div>${message}</div>
		    <input type = "hidden" name = "role" value = "User">
		    <button type = 'submit' class = 'author'><fmt:message key="button.submit" bundle="${rb}" /></button>
		    <button type = 'reset' class = 'author'><fmt:message key="button.reset" bundle="${rb}" /></button> 
	    </form>
    </div>
</body>
</html>