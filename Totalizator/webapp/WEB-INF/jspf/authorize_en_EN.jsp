<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Authorization</title>
</head>
<body>
	<form action = "Controller" method="post">
		<input type = "hidden" name = 'command' value = 'login'>
	   	<span>
	   		<input class = 'login' required type = 'text' id = 'login' name = 'login' placeholder = 'Login' oninvalid="this.setCustomValidity('This field must be filled')"></input>
	   		<span></span>
	   	</span>
	   	<span>
	   		<input required type = 'password' id = 'password' name = 'password' placeholder = 'Password' oninvalid="this.setCustomValidity('This field must be filled')"></input>
	   		<span></span>
	   	</span>
	   	<button type = 'submit' class = 'author'>Submit</button>
	   	<button type = 'reset' class = 'author'>Reset</button> 
	</form>
	<a href = "${pageContext.request.contextPath}/jsp/register_en_EN.jsp">Registration</a>
</body>
</html>