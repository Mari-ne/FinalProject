<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="../assets/css/ruLang.css"%>
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
		<c:import url="..\WEB-INF\jspf\upper_ru_RU.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.personalData"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_ru_RU.jsp" />
	<div class = "content">
		<table class = "personal">
			<tr>
				<td>Логин:</td>
				<td>${sessionScope.user.login}</td>
			</tr>
			<tr>
				<td>Электронная почта:</td>
				<td>${sessionScope.user.email}</td>
			</tr>
			<c:if test="${sessionScope.user.role == 'User'}">
				<tr>
					<td>Платежные карты:</td>
					<c:choose>
						<c:when test="${sessionScope.user.cards == null}">
							<td>Вы еще не добавили ни одной карты</td>
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
					<td>Размер последней ставки:</td>
					<td>		
						<c:choose>
							<c:when test="${perRes.lastBet == null}">
								Вы еще не сделали ни одной ставки
							</c:when>
							<c:otherwise>
								${perRes.lastBet}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>Размер последнего выйгрыша:</td>
					<td>		
						<c:choose>
							<c:when test="${perRes.lastGain == null}">
								<c:choose>
									<c:when test="${perRes.lastBet == null}">
										Вы еще не сделали ни одной ставки
									</c:when>
									<c:otherwise>
										Ваша последняя ставки еще не была обработана
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
					<td>Размер всех ставок:</td>
					<td>	
						<c:choose>
							<c:when test="${perRes.allBet == null}">
								Вы еще не сделали ни одной ставки
							</c:when>
							<c:otherwise>
								${perRes.allBet}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>Размер всех выйгрышей:</td>	
					<td>	
						<c:choose>
							<c:when test="${perRes.allGain == null}">
								<c:choose>
									<c:when test="${perRes.lastBet == null}">
										Вы еще не сделали ни одной ставки
									</c:when>
									<c:otherwise>
										Ваша последняя ставка еще не была обработана
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
		<a href="${pageContext.request.contextPath}/jsp/personalUpdate_ru_RU.jsp" >Редактировать учетную запись<i class="material-icons">mode_edit</i></a>
		<c:if test="${sessionScope.user.role == 'User' && forecast.size() != 0}">
			<table>
				<caption>Ваши прогнозы</caption>
				<tr>
					<th>№</th>
					<th>Прогноз</th>
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
