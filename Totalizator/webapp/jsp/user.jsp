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
	<style>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<c:choose>
		<c:when test="${sessionScope.lang == 'en'}">
			<style>
				<%@include file="../assets/css/enLang.css"%>
			</style>
		</c:when>
		<c:when test="${sessionScope.lang == 'jp'}">
			<style>
				<%@include file="../assets/css/jpLang.css"%>
			</style>
		</c:when>
		<c:otherwise>
			<style>
				<%@include file="../assets/css/ruLang.css"%>
			</style>		
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