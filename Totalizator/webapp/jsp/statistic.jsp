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
	<title><fmt:message key="statistic.title" bundle="${rb}" /></title>
</head>
<body>
	<div id = "upperline">
		<c:choose>
			<c:when test="${sessionScope.user == null}">
				<c:import url="..\WEB-INF\jspf\authorize.jsp" />
			</c:when>
			<c:otherwise>
				<c:import url="..\WEB-INF\jspf\upper.jsp" />
			</c:otherwise>
		</c:choose>
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.statistic"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<div class = "scroll">
			<table cellspacing="0">
			  	<tr>
					<th><fmt:message key="table.team1" bundle="${rb}" /></th>
					<th><fmt:message key="table.team2" bundle="${rb}" /></th>
					<th><fmt:message key="table.team1" bundle="${rb}" /> <fmt:message key="table.wins" bundle="${rb}" /></th>
					<th><fmt:message key="table.team2" bundle="${rb}" /> <fmt:message key="table.wins" bundle="${rb}" /></th>
					<th><fmt:message key="table.quantity" bundle="${rb}" /></th>
				</tr>
			  	<c:forEach var="elem" items="${list}" varStatus="status">
					<tr>
						<td><c:out value="${ elem.team1 }" /></td>
						<td><c:out value="${ elem.team2 }" /></td>
						<td><c:out value="${ elem.team1Wins}"/></td>
						<td><c:out value="${ elem.team2Wins}"/></td>
						<td><c:out value="${ elem.quantity }" /></td>
					</tr>
				</c:forEach>
		  </table>
		</div>
    </div>
</body>
</html>