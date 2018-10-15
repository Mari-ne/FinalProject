<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="pageContent" var="rb" />
<!DOCTYPE html>
<html>
<head>
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
	<title>${sessionScope.user.login}</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.personalUpdate"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<c:if test="${sessionScope.user.role != 'Administrator'}">
			<form method="post" action="Controller" style="float:left">
				<input type="hidden" name="command" value="user_update">
				<table class = "personal">
					<caption><fmt:message key="update.data" bundle="${rb}" /></caption>
					<tr>
						<td><fmt:message key="personal.login" bundle="${rb}" /></td>
						<td><input type="text" value="${sessionScope.user.login}" disabled></td>
					</tr>
					<tr>
						<td><fmt:message key="personal.password" bundle="${rb}" /></td>
						<td><input type="email" value="${sessionScope.user.email}" name="email" required></td>
					</tr>
					<c:if test="${sessionScope.user.role == 'User'}">
					<tr>
						<td><fmt:message key="personal.card" bundle="${rb}" /></td>
						<c:if test="${sessionScope.user.cards != null}">
							<td></td>
							<c:forEach var="elem" items="${sessionScope.user.cards}" varStatus="status">
								<tr>
									<td></td>
									<td>
										<input type="text" value="${elem}" name = "card" oninput="input(this)" onchange="change(this)" pattern="[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}"/>
										<i class="material-icons" onclick="remove(this)">close</i>
									</td>
								</tr>
							</c:forEach>					
						</c:if>
						<tr>
						    <td></td>
						    <td><input type="text" name = "card" oninput="input(this)" onchange="change(this)" pattern="[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}"/></td>
						 </tr>
					</tr>
					</c:if>
				</table>
				<div></div>
				<button type="submit"><fmt:message key="button.save" bundle="${rb}" /></button>
			</form>
		</c:if>
		<form action="Controller" method="post" onsubmit="return check();">	
			<input type="hidden" name="command" value="password_change">
			<table class = "personal">
				<caption><fmt:message key="update.password" bundle="${rb}" /></caption>
				<tr>
					<td><fmt:message key="update.old" bundle="${rb}" /></td>
					<td><input type="password" name="old" required></td>
				</tr>
				<tr>
					<td><fmt:message key="update.new" bundle="${rb}" /></td>
					<td><input type="password" name="new" required pattern='(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}'></td>
				</tr>
			</table>
			<div>${message}</div>
			<button type="submit"><fmt:message key="button.save" bundle="${rb}" /></button>
			<button type="reset"><fmt:message key="button.reset" bundle="${rb}" /></button>
		</form>
	</div>
</body>
	<script type="text/javascript" src="/assets/js/personalUpdate.js">
	</script>
</html>