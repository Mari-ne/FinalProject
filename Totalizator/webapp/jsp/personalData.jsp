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
	    <%@include file="../assets/css/enLang.css"%>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<title>${login}</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.personalData"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<table class = "personal">
			<tr>
				<td><fmt:message key="personal.login" bundle="${rb}" /></td>
				<td>${sessionScope.user.login}</td>
			</tr>
			<tr>
				<td><fmt:message key="personal.email" bundle="${rb}" /></td>
				<td>${sessionScope.user.email}</td>
			</tr>
			<c:if test="${sessionScope.user.role == 'User'}">
			<tr>
				<td><fmt:message key="personal.card" bundle="${rb}" /></td>
				<c:choose>
					<c:when test="${sessionScope.user.cards == null}">
						<td><fmt:message key="personal.cards.none" bundle="${rb}" /></td>
					</c:when>
					<c:otherwise>
						<td></td>
						<c:forEach var="elem" items="${sessionScope.user.cards}" varStatus="status">
							<tr>
								<td></td>
								<td><c:out value="${elem}" /></td>
							</tr>
						</c:forEach>					
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td><fmt:message key="personal.lastBet" bundle="${rb}" /></td>
				<td>${perRes.lastBet}</td>
			</tr>
			<tr>
				<td><fmt:message key="personal.lastGain" bundle="${rb}" /></td>
				<td>${perRes.lastGain}</td>
			</tr>
			<tr>
				<td><fmt:message key="personal.allBet" bundle="${rb}" /></td>
				<td>${perRes.allBet}</td>
			</tr>
			<tr>
				<td><fmt:message key="personal.allGain" bundle="${rb}" /></td>	
				<td>${perRes.allGain}</td>
			</tr>
			</c:if>
		</table>
		<a href="${pageContext.request.contextPath}/jsp/personalUpdate.jsp" ><fmt:message key="personal.update" bundle="${rb}"/><i class="material-icons">mode_edit</i></a>
		<c:if test="${sessionScope.user.role == 'User' && forecast.size() != 0}">
			<table>
				<caption><fmt:message key="personal.forecast" bundle="${rb}" /></caption>
				<tr>
					<th><fmt:message key="table.number" bundle="${rb}" /></th>
					<th><fmt:message key="table.forecast" bundle="${rb}" /></th>
				</tr>
				<c:forEach var="elem" items="${forecast}" varStatus="status">
					<tr>
						<td><c:out value="${elem.competitionId}" /></td>
						<td><c:out value="${elem.resultFull}"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
</body>
</html>