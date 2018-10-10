<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<style>
	    <%@include file="../assets/css/enLang.css"%>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<title>${sessionScope.user.login}</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper_en_EN.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.personalUpdate"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_en_EN.jsp" />
	<div class = "content">
		<c:if test="${sessionScope.user.role != 'Administrator'}">
			<form method="post" action="Controller" style="float:left">
				<input type="hidden" name="command" value="user_update">
				<table class = "personal">
					<caption>Data change</caption>
					<tr>
						<td>Login:</td>
						<td><input type="text" value="${sessionScope.user.login}" disabled></td>
					</tr>
					<tr>
						<td>E-mail:</td>
						<td><input type="email" value="${sessionScope.user.email}" name="email" required></td>
					</tr>
					<c:if test="${sessionScope.user.role == 'User'}">
					<tr>
						<td>Cards:</td>
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
				<button type="submit">Save</button>
			</form>
		</c:if>
		<form action="Controller" method="post" onsubmit="return check();">	
			<input type="hidden" name="command" value="password_change">
			<table class = "personal">
				<caption>Password change</caption>
				<tr>
					<td>Old password</td>
					<td><input type="password" name="old" required></td>
				</tr>
				<tr>
					<td>New password</td>
					<td><input type="password" name="new" required pattern='(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}'></td>
				</tr>
			</table>
			<div>${message}</div>
			<button type="submit">Save</button>
			<button type="reset">Reset</button>
		</form>
	</div>
</body>
	<script type="text/javascript">
		<%@include file="../assets/js/personalUpdate.js"%>
	</script>
</html>
