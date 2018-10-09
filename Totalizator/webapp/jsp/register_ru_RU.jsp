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
	<title>Регистрация</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\authorize_ru_RU.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.register"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_ru_RU.jsp" />
	<div class = "content">
		<form action="Controller" method = "post">
			<input type = "hidden" name = "command" value = "register">
		    <div>
		      <input class = 'login' required type = 'text' id = 'login' name = 'login' pattern = '[A-Za-z0-9-]{4,20}' placeholder = 'Логин' title = "Используйте только английские буквы"></input>
		      <span>${message}</span>
		    </div>
		    <div>
		      <input required type = 'password' id = 'password' name = 'password' pattern='(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}' placeholder = 'Пароль' title = 'Пароль должен содержать не меньше 1 прописной, 1 строчной букв и 1 цифры.'></input>
		      <span></span>
		    </div>
		    <div>
		      <input required type = "email" id = 'email' name = 'email' placeholder = 'Эл. почта'></input>
		      <span></span>
		    </div>
		    <input type = "hidden" name = "role" value = "user">
		    <button type = 'submit' class = 'author'>Подтвердить</button>
		    <button type = 'reset' class = 'author'>Сбросить</button> 
	    </form>
    </div>
</body>
</html>