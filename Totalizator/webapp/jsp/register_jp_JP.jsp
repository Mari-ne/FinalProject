<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="../assets/css/jpLang.css"%>
	    <%@include file="../assets/css/body.css"%>
	    <%@include file="../assets/css/button.css"%>
	    <%@include file="../assets/css/input.css"%>	    
	    <%@include file="../assets/css/table.css"%>
	    <%@include file="../assets/css/link.css"%>
	    <%@include file="../assets/css/icon.css"%>
	</style>
	<title>登録</title>
</head>
<body>
	<div id = "upperline">
		<c:import url="..\WEB-INF\jspf\authorize_jp_JP.jsp" />
		<form action = "Controller" method = "get">
		  	<input type = "hidden" name = 'page' value = "path.register"></input>
		  	<c:import url="..\WEB-INF\jspf\header.jsp" />
		</form>
  	</div>
	<c:import url="..\WEB-INF\jspf\sidenav_jp_JP.jsp" />
	<div class = "content">
		<form action="Controller" method = "post">
			<input type = "hidden" name = "command" value = "register">
		    <div>
		      <input class = 'login' required type = 'text' id = 'login' name = 'login' pattern = '[A-Za-z0-9-]{4,20}' placeholder = 'ユーザー名' minlength = '4' maxlength = '20' title = "英字のみを使用してください"></input>
		      <span>${message}</span>
		    </div>
		    <div>
		      <input required type = 'password' id = 'password' name = 'password' pattern = '(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\S{8,20}' placeholder = 'パスワード' title = 'パスワードには、少なくとも1つの英小文字と1つの英大文字と1つの英数字が必要です'></input>
		      <span></span>
		    </div>
		    <div>
		      <input required type = "email" id = 'email' name = 'email' placeholder = 'Eメール'></input>
		      <span></span>
		    </div>
		    <input type = "hidden" name = "role" value = "user">
		    <button type = 'submit' class = 'author'>登録</button>
		    <button type = 'reset' class = 'author'>レセット</button> 
	    </form>
    </div>
</body>
</html>