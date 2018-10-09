<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Авторизация</title>
</head>
<body>
	<form action = "Controller" method="post">
		<input type = "hidden" name = 'command' value = 'login'>
	    <span>
	    	<input class = 'login' required type = 'text' id = 'login' name = 'login' placeholder = 'Логин'></input>
	    	<span></span>
	    </span>
	    <span>
	    	<input required type = 'password' id = 'password' name = 'password' placeholder = 'Пароль'></input>
	    	<span></span>
	    </span>
	    <button type = 'submit' class = 'author'>Подтвердить</button>
	    <button type = 'reset' class = 'author'>Сбросить</button> 
	</form>
	<a href = "${pageContext.request.contextPath}/jsp/register_ru_RU.jsp">Регистрация</a>
</body>
</html>