<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="pageContent" var="rb" />
<!DOCTYPE html>
<html>
<head>
	<style>
	    <c:choose>
			<c:when test="${sessionScope.lang == 'en'}">
				<%@include file="../assets/css/enLang.css"%>
			</c:when>
			<c:when test="${sessionScope.lang == 'jp'}">
				<%@include file="../assets/css/jpLang.css"%>
			</c:when>
			<c:otherwise>
				<%@include file="../assets/css/ruLang.css"%>		
			</c:otherwise>
		</c:choose>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
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
		<fmt:message key="placeHolder.login" var="login" bundle="${rb}"/>
		<fmt:message key="placeHolder.password" var="password"  bundle="${rb}"/>
		<fmt:message key="placeHolder.email" var="email"  bundle="${rb}"/>
		<fmt:message key="registration.login" var="loginT" bundle="${rb}"/>
		<fmt:message key="registration.password" var="passwordT"  bundle="${rb}"/>
		<form action="Controller" method = "post">
			<input type = "hidden" name = "command" value = "register">
		    <div>
		      <input class = 'login' required type = 'text' id = 'login' name = 'login' pattern = '[\a\A\d-_]' placeholder = '${login}' minlength = '4' maxlength = '20' title = "${loginT }"></input>
		      <span>${message}</span>
		    </div>
		    <div>
		      <input required type = 'password' id = 'password' name = 'password' pattern = '(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\S{8,20}' placeholder = '${password}' minlength = '8', maxlength = '20' title = '${passwordT }'></input>
		      <span></span>
		    </div>
		    <div>
		      <input required type = "email" id = 'email' name = 'email' placeholder = '${email}'></input>
		      <span></span>
		    </div>
		    <input type = "hidden" name = "role" value = "User">
		    <button type = 'submit' class = 'author'><fmt:message key="button.submit" bundle="${rb}" /></button>
		    <button type = 'reset' class = 'author'><fmt:message key="button.reset" bundle="${rb}" /></button> 
	    </form>
    </div>
</body>
</html>