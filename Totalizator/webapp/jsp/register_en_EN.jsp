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
	<title>Registration</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\authorize_en_EN.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.register"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_en_EN.jsp" />
	<div class = "content">
		<form action="Controller" method = "post">
			<input type = "hidden" name = "command" value = "register">
		    <div>
		      <input class = 'login' required type = 'text' id = 'login' name = 'login' pattern = '[\a\A\d-_]' placeholder = 'Login' minlength = '4' maxlength = '20' title = "Use only english letters"></input>
		      <span>${message}</span>
		    </div>
		    <div>
		      <input required type = 'password' id = 'password' name = 'password' pattern = '(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\S{8,20}' placeholder = 'Password' minlength = '8', maxlength = '20' title = 'Password must have a least 1 big, 1 little letter and 1 number.'></input>
		      <span></span>
		    </div>
		    <div>
		      <input required type = "email" id = 'email' name = 'email' placeholder = 'E-mail'></input>
		      <span></span>
		    </div>
		    <input type = "hidden" name = "role" value = "user">
		    <button type = 'submit' class = 'author'>Submit</button>
		    <button type = 'reset' class = 'author'>Reset</button> 
	    </form>
    </div>
</body>
</html>