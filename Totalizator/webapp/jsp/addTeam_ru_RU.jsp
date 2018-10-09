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
	<title>Добавление</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper_ru_RU.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.addTeam"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_ru_RU.jsp" />
	<div class = "content">
		<form action = "Controller" method = "post">
			<input type = "hidden" name = "command" value = "add_team">
			<table class = "personal">
				<tr>
					<td>Вид спорта</td>
					<td>
						<select name="sport">
							<c:forEach var="elem" items="${sport}" varStatus="status">
								<option value="${elem.id}"><c:out value="${elem.name}" /></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Название команды (EN)</td>
					<td>
						<input type="text" name="nameEN" required>
					</td>
				</tr>
				<tr>
					<td>Название команды (JP)</td>
					<td>
						<input type="text" name="nameJP" required>
					</td>
				</tr>
				<tr>
					<td>Название команды (RU)</td>
					<td>
						<input type="text" name="nameRU" required>
					</td>
				</tr>
			</table>
			<div></div>
			<button type="submit">Сохранить</button>
			<button type="reset">Сбросить</button>
		</form>
	</div>
</body>
</html>