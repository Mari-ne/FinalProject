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
	<title>Make bet</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper_jp_JP.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.makeBet"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_jp_JP.jsp" />
	<div class = "content">
		<form action = "Controller" method = "get">
			<input type = "hidden" name = "command" value = "add_forecast">
			<table class = "personal">
				<tr>
					<th>番号</th>
					<th>スポーツ</th>
					<th>予測</th>
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
								<option value="x">引き分け</option>
								<option value="2"><c:out value="${elem.team2}"/></option>
							</select>
						</td>
					</tr>
				</c:forEach>
			</table>
			<table class = "personal">
				<tr>
					<td>賭けの: </td>
					<td><input type="text" name="bet" required></td>
				</tr>
				<tr>
					<td>Choose card to pay:</td>
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
			<button type="submit">セーブ</button>
		</form>
	</div>
</body>
</html>
