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
	<a href = "${pageContext.request.contextPath}/jsp/personalData_jp_JP.jsp">自分のページへ</a>
	<c:if test="${sessionScope.user.role == 'User'}">
		<a href = "${pageContext.request.contextPath}/jsp/makeBet_jp_JP.jsp">賭けをします</a>
	</c:if>
	<form action="Controller" method="post">
		<button type="submit" name = "command" value = "logout">ログアウト</button>
	</form>
</body>
</html>