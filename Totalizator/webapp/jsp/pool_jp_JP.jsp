<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="jp">
<head>
	<meta charset="utf-8">
	<style>
		<%@include file="../assets/css/jpLang.css"%>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<title>プール</title>
</head>
<body>
	<div id = "upperline">
		<c:choose>
			<c:when test="${sessionScope.user == null}">
				<c:import url="..\WEB-INF\jspf\authorize_jp_JP.jsp" />
			</c:when>
			<c:otherwise>
				<c:import url="..\WEB-INF\jspf\upper_jp_JP.jsp" />
			</c:otherwise>
		</c:choose>
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.pool"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_jp_JP.jsp" />
	<div class = "content">
		<c:if test="${sessionScope.user.role == 'Bookmaker'}">
			<button id="manage" type="button" onclick="manage()">賞金プールの管理<i class="fa fa-money"></i></button>
		</c:if>
		<form action="Controller" method="get" onsubmit="return check();">
			<input type="hidden" name="commnad" value="set_pool">
			<table>
				<tr>
					<th>政界の数</th>
					<th>プールの一部 (%)</th>
					<th>全てのプール</th>
					<th>ベットの数</th>
					<th>ベットの量</th>
					<th>係数</th>
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
			<button type="submit" id="submit" style="display:none">セーブ</button>
			<button type="reset" id="reset" style="display:none">レセット</button>
		</form>
    </div>
</body>
	<script>
		<%@include file="../assets/js/pool.js"%>
	</script>
</html>