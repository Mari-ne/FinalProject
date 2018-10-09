<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<style>
		<%@include file="../assets/css/jpLang.css"%>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<title>ユーザーの管理</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper_jp_JP.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.user"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_jp_JP.jsp" />
	<div class = "content">
		<table style="display:inline">
			<caption>ユーザー</caption>
			<tr>
				<th>ユーザー名</th>
				<th>Eメル</th>
			</tr>
			<c:forEach var="elem" items="${users}" varStatus="status">
				<tr>
					<td><c:out value="${elem.login}"/></td>
					<td><c:out value="${elem.email}"/></td>
				</tr>
			</c:forEach>	
		</table>
		<table style="display:inline">
			<caption>ブックメーカー</caption>
			<tr>
				<th>ユーザー名</th>
				<th>Eメル</th>
			</tr>
			<c:forEach var="elem" items="${bookmakers}" varStatus="status">
				<tr>
					<td><c:out value="${elem.login}"/></td>
					<td><c:out value="${elem.email}"/></td>
				</tr>
			</c:forEach>	
		</table>
    </div>
</body>
</html>