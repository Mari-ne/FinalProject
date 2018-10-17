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
	<title><fmt:message key="makeBet.title" bundle="${rb}" /></title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.makeBet"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<form action = "Controller" method = "get">
			<input type = "hidden" name = "command" value = "add_forecast">
			<table class = "personal">
				<tr>
					<th><fmt:message key="table.number" bundle="${rb}" /></th>
					<th><fmt:message key="table.sport" bundle="${rb}" /></th>
					<th><fmt:message key="table.forecast" bundle="${rb}" /></th>
				</tr>
				<c:forEach var="elem" items="${list}" varStatus="status">
					<tr>
						<td><c:out value="${elem.id}"/></td>
						<td>
							<input type="hidden" name="id" value="${elem.id}">
							<c:out value="${elem.sport}"/></td>
						<td>
							<select name = "forecast">
								<option value="1"><c:out value="${elem.team1}" /></option>
								<option value="x"><fmt:message key="makeBet.draw"  bundle="${rb}"/></option>
								<option value="2"><c:out value="${elem.team2}"/></option>
							</select>
						</td>
					</tr>
				</c:forEach>
			</table>
			<table class = "personal">
				<tr>
					<td><fmt:message key="makeBet.bet" bundle="${rb}" /></td>
					<td><input type="text" name="bet" required></td>
				</tr>
				<tr>
					<td><fmt:message key="makeBet.card" bundle="${rb}" /></td>
					<td>
						<select name="card">
							<c:forEach var="elem" items="${sessionScope.user.cards}" varStatus="status">
								<option value="${elem}"><c:out value="${elem}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>			
			<div></div>
			<button type="submit"><fmt:message key="button.save" bundle="${rb}" /></button>
			<button type = 'reset'><fmt:message key="button.reset" bundle="${rb}" /></button> 
		</form>
	</div>
</body>
</html>