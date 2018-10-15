<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="pageContent" var="rb" />
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
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
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<title><fmt:message key="main.title" bundle="${rb}" /></title>
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
		  	<input type = "hidden" name = 'page' value = "path.main"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
 	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
  	<div class = "content">
  		<c:if test="${sessionScope.user.role == 'Administrator'}">
	 	<form action="Controller" method="get">
		  	<input type="hidden" name="command" value="add">
		  	<input type="hidden" name="type" value="Competition">
		  	<button type="submit"><fmt:message key="main.addCompetition" bundle="${rb}" /><i class="material-icons">add</i></button>
		</form>
		</c:if>
  		<div class = "scroll">
			<table cellspacing="0">
			  	<tr>
					<th><fmt:message key="table.number" bundle="${rb}" /></th>
					<th><fmt:message key="table.sport" bundle="${rb}" /></th>
					<th><fmt:message key="table.team1" bundle="${rb}" /></th>
					<th><fmt:message key="table.team2" bundle="${rb}" /></th>
					<th><fmt:message key="table.start" bundle="${rb}" /></th>
					<th><fmt:message key="table.finish" bundle="${rb}" /></th>
					<th><fmt:message key="table.state" bundle="${rb}" /></th>
					<th><fmt:message key="table.result" bundle="${rb}" /></th>
				</tr>
			  	<c:forEach var="elem" items="${list}" varStatus="status">
					<tr>
						<td><c:out value="${ elem.id }" /></td>
						<td><c:out value="${ elem.sport }" /></td>
						<td><c:out value="${ elem.team1 }" /></td>
						<td><c:out value="${ elem.team2 }" /></td>
						<td><ctg:date-time timePattern="HH:mm" datePattern="dd.MM.yyyy" value="${ elem.start}"/></td>
						<td><ctg:date-time timePattern="HH:mm" datePattern="dd.MM.yyyy" value="${ elem.finish}"/></td>
						<td><c:out value="${ elem.state }" /></td>
						<td><c:out value="${ elem.result }" /></td>
					</tr>
				</c:forEach>
			</table>
	  </div>
  	</div>
  	<span>${sessionScope.lang }</span>
</body>
</html>