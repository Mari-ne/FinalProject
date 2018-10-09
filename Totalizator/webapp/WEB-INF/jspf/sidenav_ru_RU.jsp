<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
		<%@include file="../../assets/css/sideNav.css"%>
	</style>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<title>Sidenav</title>
</head>
<body>
	<div class = "sidenav">
		<a href="${pageContext.request.contextPath}/jsp/main_ru_RU.jsp">Расписание соревнований<i class="fa fa-calendar"></i></a>
	  	<a href="${pageContext.request.contextPath}/jsp/statistic_ru_RU.jsp">Статистика матчей<i class="fa fa-line-chart"></i></a>
		<a href="${pageContext.request.contextPath}/jsp/teams_ru_RU.jsp">Информация о командах<i class="fa fa-info-circle"></i></a>
		<a href="#">Ставки игроков<i class="fa fa-users"></i></a>
		<c:if test="${sessionScope.user.role != 'User'}">
			<a href="${pageContext.request.contextPath}/jsp/pool_ru_RU.jsp">Призовой пул<i class="fa fa-money"></i></a>
		</c:if>
		<c:if test="${sessionScope.user.role == 'Administrator'}">
			<a href="${pageContext.request.contextPath}/jsp/user_ru_RU.jsp">Управление пользователями<i class="fa fa-group"></i></a>
		</c:if>
	</div>
</body>
</html>