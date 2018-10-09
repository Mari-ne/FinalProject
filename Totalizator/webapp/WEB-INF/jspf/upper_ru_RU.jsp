<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upper</title>
</head>
<body>
	<a href = "${pageContext.request.contextPath}/jsp/personalData_ru_RU.jsp">На личную страницу</a>
	<c:if test="${sessionScope.user.role == 'User'}">
		<a href = "${pageContext.request.contextPath}/jsp/makeBet_ru_RU.jsp">Сделать ставку</a>
	</c:if>
	<form action="Controller" method="post">
		<button type="submit" name = "command" value = "logout">Выйти</button>
	</form>
</body>
</html>