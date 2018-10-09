<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<style>
		<%@include file="../assets/css/enLang.css"%>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/addCompetition.js"></script>
	<title>Add</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper_en_EN.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.addCompetition"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_en_EN.jsp" />
	<div class = "content">
		<form action = "Controller" method = "get" onsubmit="return check();">
			<input type = "hidden" name = "command" value = "add_competition">
			<table class = "personal">
				<tr>
					<td>Sport type</td>
					<td>
						<select name="sport" id="sport" oninput="teamsOptions()">
							<c:forEach var="elem" items="${sport}" varStatus="status">
								<option value="${elem.id}"><c:out value="${elem.name}" /></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Team 1</td>
					<td>
						<select name="team1" id="team1">
						</select>
					</td>
				</tr>
				<tr>
					<td>Team 2</td>
					<td>
						<select name="team2" id="team2">
						</select>
					</td>
				</tr>
				<tr>
					<td>Date and time of start</td>
					<td>
						<input type="datetime-local" name="start" id="start">
					</td>
				</tr>
				<tr>
					<td>Date and time of finish</td>
					<td>
						<input type="datetime-local" name="finish" id="finish">
					</td>
				</tr>
			</table>
			<div id = "error"></div>
			<button type="submit">Save</button>
			<button type="reset">Reset</button>
		</form>
	</div>
	<div>
		<c:forEach var="elem" items="${teams}" varStatus="status">
				<input type="hidden" name ="${elem.name}" value="${elem.sportId}" class="data" id="${elem.id}">
		</c:forEach>
	</div>
</body>
	<script>
		<%@include file="../assets/js/addCompetition.js"%>
	</script>
</html>