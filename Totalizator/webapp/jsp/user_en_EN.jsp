<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<style>
		<%@include file="../assets/css/enLang.css"%>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<title>Mangnent of users</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper_en_EN.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.user"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_en_EN.jsp" />
	<div class = "content">
		<table style="display:inline">
			<caption>Users</caption>
			<tr>
				<th>Login</th>
				<th>E-mail</th>
			</tr>
			<c:forEach var="elem" items="${users}" varStatus="status">
				<tr>
					<td><c:out value="${elem.login}"/></td>
					<td><c:out value="${elem.email}"/></td>
				</tr>
			</c:forEach>	
		</table>
		<table style="display:inline">
			<caption>Bookmakers</caption>
			<tr>
				<th>Login</th>
				<th>E-mail</th>
			</tr>
			<c:forEach var="elem" items="${bookmakers}" varStatus="status">
				<tr>
					<td><c:out value="${elem.login}"/></td>
					<td><c:out value="${elem.email}"/></td>
				</tr>
			</c:forEach>	
		</table>
		<button type="button">Add new bookmaker</button>
    </div>
</body>
</html>