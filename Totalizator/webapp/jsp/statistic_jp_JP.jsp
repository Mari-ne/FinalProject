<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="../assets/css/jpLang.css"%>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<title>統計</title>
</head>
<body>
	<div id = "upperline">
		<c:choose>
			<c:when test="${sessionScope.user == null}">
				<c:import url="..\WEB-INF\jspf\authorize_jp_JP.jsp" />
			</c:when>
			<c:otherwise>
				<c:import url="..\WEB-INF\jspf\upper_jp_JP.jsp" />
			</c:otherwise>
		</c:choose>
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.statistic"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_jp_JP.jsp" />
	<div class = "content">
	 	<div class = "scroll">
			<table cellspacing="0">
			  	<tr>
					<th>チーム １</th>
					<th>チーム ２</th>
					<th>チーム １ 勝利の数</th>
					<th>チーム ２ 勝利の数</th>
					<th>試合の数</th>
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