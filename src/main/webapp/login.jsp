<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<link type="text/css" rel="stylesheet" href="./jquery_ui/css/redmond/jquery-ui-1.8.16.custom.css" />
<script type="text/javascript" src="./jquery_ui/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="./jquery_ui/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="./js/validate.js"></script>
<link type="text/css" rel="stylesheet" href="css/global.css" />
<script>
	$(function() {
		//$("input:submit, #registe").button();
		/*$("#registeBtn").click(function(){
			window.location.href="./registe.jxp";
		});*/
	});
</script>
<style>
html {
	background: none;
}
</style>
</head>
<body style="height:auto;" id="loginpage">
	<div id="login-registe">
		<a href="./registe.jxp" id="registeBtn">注册</a>
	</div>
	<div id="loginform">
		<h1>ELBS</h1>
		<form method="post" action="./j_spring_security_check" >
			<span id="login-inputs">
				<input id="login-username" type="text" name="j_username" placeholder="请输入用户名" />
				<input id="login-password" type="password" name="j_password" placeholder="请输入密码" />
			</span>
			<span id="login-btns">
				<input id="login-submit" type="submit" value="登录" name="login" onclick="" />
				<!-- <input id="login-registe" type="button" value="注册" name="registe" onclick="" />-->
				<!--<a href="" onclick="" id="login-registe">注册</a>-->
			</span>
		</form>
	</div>
<!--<div class="m_body">
	<div class="m_login">
    	<div></div>
        <div id="loginForm">
        	<form method="post" action="./j_spring_security_check" >
                <table>
                    <tr>
                        <td width="100">用户名:</td><td><input class="inputText" type="text" id="j_username" name="j_username" /></td>
                    </tr>
                    <tr>
                        <td>密码:</td><td><input class="inputText" type="password" id="j_userpwd" name="j_password" /></td>
                    </tr>
                    <tr>
                        <td></td><td><input type="checkbox" id="rememberMe"name="_spring_security_remember_me" />下次自动登录</td>
                    </tr>
                    <tr>
                        <td></td><td><input id="login" type="submit" value="登录" /><input id="registe" type="button" value="注册" /></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>

--></body>
</html>