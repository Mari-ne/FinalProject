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
	<title>Pool</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper_en_EN.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.pool"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_en_EN.jsp" />
	<div class = "content">
		<c:if test="${sessionScope.user.role == 'Bookmaker'}">
			<button id="manage" type="button" onclick="manage()">Prize pool managment<i class="fa fa-money"></i></button>
		</c:if>
		<form action="Controller" method="get" onsubmit="return check();">
			<input type="hidden" name="commnad" value="set_pool">
			<table>
				<tr>
					<th>Number of correct</th>
					<th>Pool part (%)</th>
					<th>Pool size</th>
					<th>Bets quantity</th>
					<th>Bets amount</th>
					<th>Coefficient</th>
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
			<button type="submit" id="submit" style="display:none">Save</button>
			<button type="reset" id="reset" style="display:none">Reset</button>
		</form>
    </div>
</body>
	<script>
		<%@include file="../assets/js/pool.js"%>
	</script>
</html>