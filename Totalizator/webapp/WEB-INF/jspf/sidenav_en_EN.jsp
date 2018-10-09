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
 	<a href="${pageContext.request.contextPath}/jsp/main_en_EN.jsp">Competitions schedule<i class="fa fa-calendar"></i></a>
  	<a href="${pageContext.request.contextPath}/jsp/statistic_en_EN.jsp">Matches statistic<i class="fa fa-line-chart"></i></a>
	<a href="${pageContext.request.contextPath}/jsp/teams_en_EN.jsp">Teams information<i class="fa fa-info-circle"></i></a>
	<a href="#">Players bets<i class="fa fa-users"></i></a>
	<c:if test="${sessionScope.user.role != 'User'}">
		<a href="${pageContext.request.contextPath}/jsp/pool_en_EN.jsp">Prize pool<i class="fa fa-money"></i></a>
	</c:if>
	<c:if test="${sessionScope.user.role == 'Administrator'}">
		<a href="${pageContext.request.contextPath}/jsp/user_en_EN.jsp">Managment of users<i class="fa fa-group"></i></a>
	</c:if>
  </div>
</body>
</html>