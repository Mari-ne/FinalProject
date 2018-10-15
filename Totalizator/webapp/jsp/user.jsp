<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="pageContent" var="rb" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
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
	<title><fmt:message key="user.title" bundle="${rb}" /></title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.user"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<table style="display:inline">
			<caption><fmt:message key="user.user" bundle="${rb}" /></caption>
			<tr>
				<th><fmt:message key="personal.login" bundle="${rb}" /></th>
				<th><fmt:message key="personal.email" bundle="${rb}" /></th>
			</tr>
			<c:forEach var="elem" items="${users}" varStatus="status">
				<tr>
					<td><c:out value="${elem.login}"/></td>
					<td><c:out value="${elem.email}"/></td>
				</tr>
			</c:forEach>	
		</table>
		<table style="display:inline">
			<caption><fmt:message key="user.bookmaker" bundle="${rb}" /></caption>
			<tr>
				<th><fmt:message key="personal.login" bundle="${rb}" /></th>
				<th><fmt:message key="personal.email" bundle="${rb}" /></th>
			</tr>
			<c:forEach var="elem" items="${bookmakers}" varStatus="status">
				<tr>
					<td><c:out value="${elem.login}"/></td>
					<td><c:out value="${elem.email}"/></td>
				</tr>
			</c:forEach>	
		</table>
		<button type="button"><fmt:message key="user.addBookmaker" bundle="${rb}" /></button>
    </div>
</body>
</html>