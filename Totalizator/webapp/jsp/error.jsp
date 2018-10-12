<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="pageContent" var="rb" />
<!DOCTYPE html>
<html>
<head>
	<style>
	    <%@include file="../../assets/css/body.css"%>
	    <%@include file="../../assets/css/button.css"%>
	    <%@include file="../../assets/css/input.css"%>	    
	    <%@include file="../../assets/css/table.css"%>
	    <%@include file="../../assets/css/link.css"%>
	    <%@include file="../../assets/css/icon.css"%>
	</style>
	<c:choose>
		<c:when test="${sessionScope.lang == 'en'}">
			<style>
				<%@include file="../../assets/css/enLang.css"%>
			</style>
		</c:when>
		<c:when test="${sessionScope.lang == 'jp'}">
			<style>
				<%@include file="../../assets/css/jpLang.css"%>
			</style>
		</c:when>
		<c:otherwise>
			<style>
				<%@include file="../../assets/css/ruLang.css"%>
			</style>		
		</c:otherwise>
	</c:choose>
	<title>Error</title>
</head>
<body>
	<div id = "upperline">
		<c:choose>
			<c:when test="${sessionScope.user == null}">
				<c:import url="..\WEB-INF\jspf\authorize.jsp" />
			</c:when>
			<c:otherwise>
				<c:import url="..\WEB-INF\jspf\upper_en_EN.jsp" />
			</c:otherwise>
		</c:choose>
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.bets"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<img alt="" src="${pageContext.request.contextPath}/assets/image/error-image.png">
		<br>
		<div>${message}</div>
    </div>
</body>
</html>