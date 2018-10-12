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
	<title><fmt:message key="add.title" bundle="${rb}" /></title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.addTeam"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<form action = "Controller" method = "get">
			<input type = "hidden" name = "command" value = "add_team">
			<table class = "personal">
				<tr>
					<td><fmt:message key="add.sport" bundle="${rb}" /></td>
					<td>
						<select name="sport">
							<c:forEach var="elem" items="${sport}" varStatus="status">
								<option value="${elem.id}"><c:out value="${elem.name}" /></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="add.team.name" bundle="${rb}" /> (EN)</td>
					<td>
						<input type="text" name="name" required>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="add.team.name" bundle="${rb}" /> (JP)</td>
					<td>
						<input type="text" name="name" required>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="add.team.name" bundle="${rb}" /> (RU)</td>
					<td>
						<input type="text" name="name" required>
					</td>
				</tr>
			</table>
			<div></div>
			<button type="submit"><fmt:message key="button.save" bundle="${rb}" /></button>
			<button type="reset"><fmt:message key="button.reset" bundle="${rb}" /></button>
		</form>
	</div>
</body>
</html>