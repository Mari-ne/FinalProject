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
	<title>${login}</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\upper_en_EN.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.personalData"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_en_EN.jsp" />
	<div class = "content">
		<table class = "personal">
			<tr>
				<td>Login:</td>
				<td>${sessionScope.user.login}</td>
			</tr>
			<tr>
				<td>E-mail:</td>
				<td>${sessionScope.user.email}</td>
			</tr>
			<c:if test="${sessionScope.user.role == 'User'}">
			<tr>
				<td>Cards:</td>
				<c:choose>
					<c:when test="${sessionScope.user.cards == null}">
						<td>You havn't added any cards yet</td>
					</c:when>
					<c:otherwise>
						<td></td>
						<c:forEach var="elem" items="${sessionScope.user.cards}" varStatus="status">
							<tr>
								<td></td>
								<td><c:out value="${elem}" /></td>
							</tr>
						</c:forEach>					
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td>Your last bet:</td>
				<td>		
					<c:choose>
						<c:when test="${perRes.lastBet == null}">
							You havn't made any bets
						</c:when>
						<c:otherwise>
							${perRes.lastBet}
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>Your last gain:</td>
				<td>		
					<c:choose>
						<c:when test="${perRes.lastGain == null}">
							<c:choose>
								<c:when test="${perRes.lastBet == null}">
									You havn't made any bets
								</c:when>
								<c:otherwise>
									Your last bet hasn't processed yet
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							${perRes.lastGain}
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>All your bets amount:</td>
				<td>	
					<c:choose>
						<c:when test="${perRes.allBet == null}">
							You havn't made any bets
						</c:when>
						<c:otherwise>
							${perRes.allBet}
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>All your gain:</td>	
				<td>	
					<c:choose>
						<c:when test="${perRes.allGain == null}">
							<c:choose>
								<c:when test="${perRes.lastBet == null}">
									You havn't made any bets
								</c:when>
								<c:otherwise>
									Your last bet hasn't processed yet
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							${perRes.allGain}
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</c:if>
		</table>
		<a href="${pageContext.request.contextPath}/jsp/personalUpdate_en_EN.jsp" >Edit personal data<i class="material-icons">mode_edit</i></a>
		<c:if test="${sessionScope.user.role == 'User' && forecast.size() != 0}">
			<table>
				<caption>Your forecast</caption>
				<tr>
					<th>№</th>
					<th>Forecast</th>
				</tr>
				<c:forEach var="elem" items="${forecast}" varStatus="status">
					<tr>
						<td><c:out value="${elem.competitionId}" /></td>
						<td><c:out value="${elem.resultFull}"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
</body>
</html>