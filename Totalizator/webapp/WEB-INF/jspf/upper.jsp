<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="pageContent" var="rb" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<a href = "${pageContext.request.contextPath}/jsp/personalData.jsp"><fmt:message key="upper.personal" bundle="${rb}" /></a>
	<c:if test="${sessionScope.user.role == 'User'}">
		<a href = "${pageContext.request.contextPath}/jsp/makeBet.jsp"><fmt:message key="upper.makeBet" bundle="${rb}" /></a>
	</c:if>
	<form action="Controller" method="post">
		<button type="submit" name = "command" value = "logout"><fmt:message key="upper.logOut" bundle="${rb}" /></button>
	</form>
</body>
</html>