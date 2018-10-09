<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>
	<form action = "Controller" method="post">
		<input type = "hidden" name = 'command' value = 'login'>
	   	<span>
	   		<input class = 'login' required type = 'text' id = 'login' name = 'login' placeholder = 'ユーザー名' oninvalid="this.setCustomValidity('このフィールドは入力する必要があります')"></input>
	   		<span></span>
	   	</span>
	   	<span>
	   		<input required type = 'password' id = 'password' name = 'password' placeholder = 'パスワード'  oninvalid="this.setCustomValidity('このフィールドは入力する必要があります')"></input>
	   		<span></span>
	   	</span>
	   	<button type = 'submit' class = 'author'>ログイン</button>
	   	<button type = 'reset' class = 'author'>レセット</button> 
	</form>
	<a href = "${pageContext.request.contextPath}/jsp/register_jp_JP.jsp">登録</a>
</body>
</html>