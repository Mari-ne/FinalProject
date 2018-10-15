<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="pageContent" var="rb" />
<fmt:setBundle basename="message" var="mes" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
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
	<title><fmt:message key="pool.title" bundle="${rb}" /></title>
</head>
<body>
	<div id = "upperline">
		<c:choose>
			<c:when test="${sessionScope.user == null}">
				<c:import url="..\WEB-INF\jspf\authorize.jsp" />
			</c:when>
			<c:otherwise>
				<c:import url="..\WEB-INF\jspf\upper.jsp" />
			</c:otherwise>
		</c:choose>
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.pool"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav.jsp" />
	<div class = "content">
		<c:if test="${sessionScope.user.role == 'Bookmaker'}">
			<button id="manage" type="button" onclick="manage()"><fmt:message key="pool.managePool" bundle="${rb}" /><i class="fa fa-money"></i></button>
		</c:if>
		<form action="Controller" method="get" onsubmit="return check();">
			<input type="hidden" name="commnad" value="set_pool">
			<table>
				<tr>
					<th><fmt:message key="table.correct" bundle="${rb}" /></th>
					<th><fmt:message key="table.poolPart" bundle="${rb}" /> (%)</th>
					<th><fmt:message key="table.pool" bundle="${rb}" /></th>
					<th><fmt:message key="table.betQ" bundle="${rb}" /></th>
					<th><fmt:message key="table.betA" bundle="${rb}" /></th>
					<th><fmt:message key="table.coeff" bundle="${rb}" /></th>
				</tr>
				<c:forEach var="elem" items="${list}" varStatus="status">
					<tr>
						<td><c:out value="${ elem.correct }" /></td>
						<td class="pool-info"><c:out value="${ elem.poolPart }" /></td>
						<td><c:out value="${ elem.pool}"/></td>
						<td><c:out value="${ elem.betters}"/></td>
						<td><c:out value="${ elem.bets}"/></td>
						<td><c:out value="${ elem.coefficient}"/></td>
					</tr>
				</c:forEach>
			</table>
			<div id = "error"></div>
			<button type="submit" id="submit" style="display:none"><fmt:message key="button.save" bundle="${rb}" /></button>
			<button type="reset" id="reset" style="display:none"><fmt:message key="button.reset" bundle="${rb}" /></button>
		</form>
    </div>
</body>
	<script src="/assets/js/pool.js">
	</script>
</html>