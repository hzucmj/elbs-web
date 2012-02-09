<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
<link type="text/css" rel="stylesheet" href="./jquery_ui/css/redmond/jquery-ui-1.8.16.custom.css" />
<script type="text/javascript" src="./jquery_ui/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="./jquery_ui/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="./js/validate.js"></script>
<link type="text/css" rel="stylesheet" href="css/global.css" />
<script type="text/javascript">
$(function(){
	$("#username").blur(function(){
		validate_userid("#username", "#usernameError", "用户名只能由字母、数字和下划线组成");
		if (isBlank("#username", "#usernameError", "用户名只能由字母、数字和下划线组成")) {
			$.ajax({
				url : './registe.jxp',
				type : 'post',
				dataType : 'json',
				contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
				data : {
					username : $(this).attr("value"),
					action: 'checkUser'
				},
				success : function(data){
					if (data.success == true) {
						$("#usernameError").html("该用户名可以注册！");
						$("#usernameError").show();
					} else {
						$("#alertDialog").html("该用户名已存在！");
						$("#alertDialog").dialog("open");
					}
				},
				failure : function(data){
					$("#alertDialog").html("数据加载失败，请重试！");
					$("#alertDialog").dialog("open");
				}
			});
		}
	});
	$("#password").blur(function(){
		isBlank("#password", "#pwdError", "密码不能为空");
	});
	$("#repassword").blur(function(){
		if (isBlank("#repassword", "#repwdError", "确认密码不能为空")) {
			if ($("#password").attr("value") != $("#repassword").attr("value")) {
				$("#alertDialog").dialog("open");
				$("#alertDialog").html("两次密码输入不一致");
			}
		};
	});
	$("#birthday").change(function(){
		validate_date("#birthday", "#dateError", "日期格式不正确");
	});
	$("#birthday").blur(function(){
		isBlank("#birthday", "#dateError", "日期格式不正确");
	});
	$("#email").blur(function(){
		validate_email("#email", "#emailError", "邮箱格式不正确");
		$.ajax({
			url : './registe.jxp',
			type : 'post',
			dataType : 'json',
			data : {
				email : $(this).attr("value"),
				action: 'checkEmail'
			},
			success : function(data){
				if (data.success == true) {
					$("#emailError").html("该邮箱可以注册！");
					$("#emailError").show();
				} else {
					$("#alertDialog").html("该邮箱已存在！");
					$("#alertDialog").dialog("open");
				}
			},
			failure : function(data){
				$("#alertDialog").html("数据加载失败，请重试！");
				$("#alertDialog").dialog("open");
			}
		});
		/*if (isBlank("#email", "#emailError", "邮箱格式不正确")) {
			be = true;
		} else {
			be = false;
		}*/
	});
	$("#organization").blur(function(){
		isBlank("#organization", "#orgError", "组织名称不能为空");
	});
	$("#department").blur(function(){
		isBlank("#department", "#deptError", "部门名称不能为空");
	/*	if (isBlank("#department", "#deptError", "部门名称不能为空")) {
			bd = true;
		} else {
			bd = false;
		}*/
	});
	$("#contact").blur(function(){
		isBlank("#contact", "#contactError", "联系方式 不能为空");
	});
	/*$("#idcard").blur(function(){
		isBlank("#idcard", "#idError", "身份证号码不能为空");
	});*/
	
	$("input:button").button();
	$("#r_p").tabs();
	$("#backToIndex").click(function(){
		window.location.href="./member.jxp";
	});
	$("#addUser").click(function(e){
		$.ajax({
			url : "./user.jxp",
			method : "post",
			dataType : 'json',
			contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
			data : {
				action: "add",
				type: "member",
				username : $("#username").attr("value"),
				nickname : $("#nickname").attr("value"),
				sex : $("#m").attr("value") != null ? $("#m").attr("value") : $("#f").attr("value"),
				password : $("#password").attr("value"),
				repassword : $("#repassword").attr("value"),
				//employeeno : $("#employeeno").attr("value"),
				photo : $("#avatar").attr("value"),
				birthday : $("#birthday").attr("value"),
				email : $("#email").attr("value"),
				//organization : $("#organization").attr("value"),
				department : $("#department").attr("value"),
				contact : $("#contact").attr("value")
				//idcard : $("#idcard").attr("value")
			},
			success : function(data){
				$(":text, :password").attr("value", "");
				$("#alertDialog").html(data.msg);
				$("#alertDialog").dialog("open");
			},
			failure : function(data){
				$("#alertDialog").html(data.msg);
				$("#alertDialog").dialog("open");
			},
			error : function(data){
				//alert("注册失败，请稍候注册！");
				$("#alertDialog").html("操作失败，请稍候重新操作！");
				$("#alertDialog").dialog("open");
			}
		});
	});
});
</script>
</head>
<body>
<div class="m_body">
	<div>
		<jsp:include page="header-backup.jsp"></jsp:include>
	</div>
	<div class="m_p">
		<div>
			<jsp:include page="./left-menu.jsp"></jsp:include>
		</div>
		<div id="r_p">
			<ul>
				<li><a href="#adduser">添加成员</a></li>
			</ul>
        	<div id="adduser">
        		<form>
	        		<span style="float:right;padding-right: 28px;">带<span class="redFlag">*</span>项为必填项</span>
	        		<hr />
	                <table>
	                	<tr>
	                		<td width="100"><span class="redFlag">*</span>用户名：</td><td><input class="inputText" type="text" id="username" name="username" /><span class="redFlag" id="usernameError"></span></td>
	                	</tr>
	                	<tr>
	                		<td>昵称：</td><td><input class="inputText" type="text" id="nickname" name="nickname" /></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>性别：</td><td align="left">
	                			<input style="margin-left: 15px;" type="radio" name="sex" id="m" value="m" checked="checked"/>男
	                			<input style="margin-left: 15px;" type="radio" name="sex" id="f" value="f" />女
	                		<!-- <input class="inputText" type="text" id="sex" name="sex" /></td> -->
	                		</td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>密码：</td><td><input class="inputText" type="password" id="password" name="password" /><span class="redFlag" id="pwdError"></span></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>确认密码：</td><td><input class="inputText" type="password" id="repassword" name="repassword" /><span class="redFlag" id="repwdError"></span></td>
	                	</tr>
	                	<!-- <tr>
	                		<td><span class="redFlag">*</span>员工号：</td><td><input class="inputText" type="text" id="employeeno" name="employeeno" /></td>
	                	</tr> -->
	                	<tr>
	                		<td>头像：</td><td><input class="inputText" type="text" id="avatar" name="avatar" /></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>出生日期：</td><td><input class="inputText" type="text" id="birthday" name="birthday" /><span class="redFlag" id="dateError"></span></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>电子邮箱：</td><td><input class="inputText" type="text" id="email" name="email" /><span class="redFlag" id="emailError"></span></td>
	                	</tr>
	                	<!--<tr>
	                		<td><span class="redFlag">*</span>组织名称：</td><td><input class="inputText" type="text" id="organization" name="organization" /><span class="redFlag" id="orgError"></span></td>
	                	</tr>
	                	--><tr>
	                		<td><span class="redFlag">*</span>所在部门：</td><td><input class="inputText" type="text" id="department" name="department" /><span class="redFlag" id="deptError"></span></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>联系电话：</td><td><input class="inputText" type="text" id="contact" name="contact" /><span class="redFlag" id="contactError"></span></td>
	                	</tr>
	                	<!-- <tr>
	                		<td><span class="redFlag">*</span>身份证号：</td><td><input class="inputText" type="text" id="idcard" name="idcard" /><span class="redFlag" id="idError"></span></td>
	                	</tr>
	                	-->
	                    <tr>
	                        <td></td><td align="left"><input class="inputBtn" id="addUser" type="button" value="保存" /><input class="inputBtn" id="backToIndex" type="button" value="取消" /></td>
	                    </tr>
	                </table>
	            </form>
        	</div>
        </div>
	</div>
	<div><jsp:include page="./footer.jsp"></jsp:include></div>
	<div id="alertDialog"></div>
</div>
</body>
</html>