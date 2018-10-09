<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Index</title>
</head>
<body>
	<c:choose>
		<c:when test="${sessionScope.lang == 'en_EN'}">
			<jsp:forward page="/jsp/main_en_EN.jsp"/>
		</c:when>
		<c:when test="sessionScope.lang == 'jp_JP'">
			<jsp:forward page="/jsp/main_jp_JP.jsp"/>
		</c:when>
		<c:otherwise>
			<jsp:forward page="/jsp/main_ru_RU.jsp"/>			
		</c:otherwise>
	</c:choose>
</body>
</html>
