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
								<option value="x">Draw</option>
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