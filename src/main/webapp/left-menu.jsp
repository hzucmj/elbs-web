<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
$(document).ready(function(){
	$.ajax({
		url: "./profile.jxp",
		dataType: "json",
		data: {
			action: "getLocalUserId"
		},
		success: function(data){
			$("#profile-link").attr("href", "./profile.jxp?id=" + data.uid);
		}
	});	
});
</script>
</head>
<body>
<div id="left_menu">
	<ul>
		<li><a href="./">首页</a></li>
		<li><a id="profile-link" href="./profile.jxp?id=">个人信息维护</a></li>
		<li><a href="./member.jxp">成员信息维护</a></li>
		<li><a href="./adduser.jxp">添加成员</a></li>
		<li><a href="./dept.jxp">部门管理</a></li>
		<li><a href="#">xx功能</a></li>
	</ul>
</div>
<!-- 
 <ul id="navMenu">
	<li>
		<a href="./" class="nav-home">
			<span>首　　页</span>
		</a>	
	</li>
	<li>
		<a id="profile-link" href="./profile.jxp?id=" class="nav-about">
			<span>个人信息</span>
		</a>	
	</li>
	<li>
		<a href="./member.jxp" class="nav-member">
			<span>成员信息</span>
		</a>	
	</li>
	<li>
		<a href="./dept.jxp" class="nav-dept">
			<span>部门管理</span>
		</a>	
	</li>
</ul>
 -->
</body>
</html>