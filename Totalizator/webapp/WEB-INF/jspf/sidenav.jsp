<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="pageContent" var="rb" />
<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
		<%@include file="../../assets/css/sideNav.css"%>
	</style>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
 <div class = "sidenav">
 	<a href="${pageContext.request.contextPath}/jsp/main.jsp"><fmt:message key="sidenav.competition" bundle="${rb}" /><i class="fa fa-calendar"></i></a>
  	<a href="${pageContext.request.contextPath}/jsp/statistic.jsp"><fmt:message key="sidenav.statistic" bundle="${rb}" /><i class="fa fa-line-chart"></i></a>
	<a href="${pageContext.request.contextPath}/jsp/teams.jsp"><fmt:message key="sidenav.team" bundle="${rb}" /><i class="fa fa-info-circle"></i></a>
	<a href="#"><fmt:message key="sidenav.bet" bundle="${rb}" /><i class="fa fa-users"></i></a>
	<a href="${pageContext.request.contextPath}/jsp/pool.jsp"><fmt:message key="sidenav.pool" bundle="${rb}" /><i class="fa fa-money"></i></a>
	<c:if test="${sessionScope.user.role == 'Administrator'}">
		<a href="${pageContext.request.contextPath}/jsp/user.jsp"><fmt:message key="sidenav.user" bundle="${rb}" /><i class="fa fa-group"></i></a>
	</c:if>
  </div>
</body>
</html>