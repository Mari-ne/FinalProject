<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="../assets/css/ruLang.css"%>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<title>Статистика</title>
</head>
<body>
	<div id = "upperline">
		<c:choose>
			<c:when test="${sessionScope.user == null}">
				<c:import url="..\WEB-INF\jspf\authorize_ru_RU.jsp" />
			</c:when>
			<c:otherwise>
				<c:import url="..\WEB-INF\jspf\upper_ru_RU.jsp" />
			</c:otherwise>
		</c:choose>
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.statistic"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_ru_RU.jsp" />
	<div class = "content">
		<div class = "scroll">
			<table cellspacing="0">
		  	<tr>
				<th>Команда 1</th>
				<th>Команда 2</th>
				<th>Количесво побед команды 1</th>
				<th>Количесво побед команды 2</th>
				<th>Количество матчей</th>
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