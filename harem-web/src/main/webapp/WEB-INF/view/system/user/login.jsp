<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>登陆</title>
	<script src="/resources/js/md5.min.js" type="text/javascript"></script>
	<link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="/resources/css/login.css" rel="stylesheet" type="text/css">
	<%--<script type="text/javascript">
		function subForm(obj) {
			var username=document.getElementById("username");
			var message=document.getElementById("message");
			if(!username){
				message.value="用户名不能为空！";
				return false;
			}
			var password=document.getElementById("password");
			if(!password){
				message.value="密码不能为空！";
				return false;
			}
			password.value = md5(password.value);
			obj.value="登录...";
			obj.disabled=true;
			test.submit;
			return true;
		}
	</script>--%>
</head>
<body>
<div class="container">
	<form class="form-signin" id="loginForm" action="/user/loginTest" method="post" role="form" enctype="application/x-www-form-urlencoded">
		<div class="form-group">
			<h3 class="form-signin-heading">登录</h3>
			<label class="sr-only" for="userName"></label>
			<input class="form-control" id="userName" name="userName" type="text" required autofocus placeholder="用户名"/>
			<label class="sr-only" for="password">Email address</label>
			<input class="form-control" id="password" name="password" type="password" required autofocus placeholder="密码"/>
			<%--<label class="checkbox" for="remmberme">
				<input type="checkbox" id="remmberme" name="remmberme"/>
				记住我
			</label>--%>
			<c:if test="${!empty message}">
				<li style="color:red" id="message">${message}</li>
			</c:if>
			<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
		</div>
	</form>
</div>
</body>
</html>
