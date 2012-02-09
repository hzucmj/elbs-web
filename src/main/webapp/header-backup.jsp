<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="./jquery_ui/css/redmond/jquery-ui-1.8.16.custom.css" />
<script type="text/javascript" src="./jquery_ui/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="./jquery_ui/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="./js/validate.js"></script>
<link type="text/css" rel="stylesheet" href="css/global.css" />
<style>
.tf1{
    position:fixed;
    width:100%;
    height:32px;
    background : rgba(0, 0, 0, 0.7);
    top:0px;
    left:0px;
    z-index:1000;
}
html{overflow:auto;height:100%;}
body{overflow:auto;height:100%;margin:0 0 0 0;}
</style>
<script>
$(document).ready(function(){
	$.ajax({
		url: "./profile.jxp",
		dataType: "json",
		data: {
			action: "getLocalUserId"
		},
		success: function(data){
			$("#pLink").click(function(){
				window.location.href='./profile.jxp?id=' + data.uid;
			});
		}
	});	
});
</script>
</head>
<body>
<div>
	<span class="tf1">
		<div style="width: 800px;margin: 0 auto;">
			<div id="m_logo">
				
			</div>
			<header>
				<ul>
					<li><a id="iLink" href="javascript:void(0)" onclick="window.location.href='./index.jxp'">首页</a></li>
					<li><a id="pLink" href="javascript:void(0)">个人设置</a></li>
					<li><a id="qLink" href="javascript:void(0)" onclick="window.location.href='./j_spring_security_logout'">退出</a></li>
				</ul>
			</header>
		</div>
	</span>
</div>
</body>
</html>