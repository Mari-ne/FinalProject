<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="pageContent" var="rb" />
<fmt:setBundle basename="message" var="mes" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="/assets/css/body.css">
	<link rel="stylesheet" href="/assets/css/button.css">
	<link rel="stylesheet" href="/assets/css/input.css">	    
	<link rel="stylesheet" href="/assets/css/table.css">
	<link rel="stylesheet" href="/assets/css/link.css">
	<link rel="stylesheet" href="/assets/css/icon.css">
	<link rel="stylesheet" href="/assets/css/select.css">
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
	<title><fmt:message key="add.title" bundle="${rb}" /></title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.addCompetition"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<form action = "Controller" method = "get" onsubmit="return check();">
			<input type = "hidden" name = "command" value = "add_competition">
			<table class = "personal">
				<tr>
					<td><fmt:message key="add.sport" bundle="${rb}" /></td>
					<td>
						<select name="sport" id="sport" oninput="teamsOptions()">
							<c:forEach var="elem" items="${sport}" varStatus="status">
								<option value="${elem.id}"><c:out value="${elem.name}" /></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="table.team1" bundle="${rb}" /></td>
					<td>
						<select name="team1" id="team1">
						</select>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="table.team2" bundle="${rb}" /></td>
					<td>
						<select name="team2" id="team2">
						</select>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="add.competition.start" bundle="${rb}" /></td>
					<td>
						<input type="datetime-local" name="start" id="start">
					</td>
				</tr>
				<tr>
					<td><fmt:message key="add.competition.finish" bundle="${rb}" /></td>
					<td>
						<input type="datetime-local" name="finish" id="finish">
					</td>
				</tr>
			</table>
			<div id = "error"></div>
			<button type="submit"><fmt:message key="button.save" bundle="${rb}" /></button>
			<button type="reset"><fmt:message key="button.reset" bundle="${rb}" /></button>
		</form>
	</div>
	<div>
		<c:forEach var="elem" items="${teams}" varStatus="status">
				<input type="hidden" name ="${elem.name}" value="${elem.sportId}" class="data" id="${elem.id}">
		</c:forEach>
	</div>
</body>
	<script src="/assets/js/addCompetition.js"></script>
</html>