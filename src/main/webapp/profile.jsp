<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="./jquery_ui/css/redmond/jquery-ui-1.8.16.custom.css" />
<script type="text/javascript" src="./jquery_ui/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="./jquery_ui/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="./js/validate.js"></script>
<script type="text/javascript" src="./libs/uploadify-v3.0.0/jquery.uploadify.min.js"></script>
<link type="text/css" rel="stylesheet" href="css/global.css" />
<title>个人设置</title>
<script>

$(document).ready(function(){
	$("#r_p").tabs();

	var id = $.request.queryString["id"];
	var edit = $.request.queryString["e"];

	if (edit == "t") {
		$("#deptTD").html("<select class='inputText' id='deptList' name='deptlist' style='float:left;'></select>");
	}
	function ErrorInGetData(){ elbs.alert("数据加载失败！"); elbs.closeProgressbar();}

	function getDepts(depts){
		var deptList = depts.split(",");
		return deptList;
	}

	function getDeptValue(){
		var value;
		if ($("#deptList").length > 0) {
			value = $("#deptList option:selected").text();
		} else {
			value = $("#department").attr("value");
		}	
		console.log(value);
		return value;
	}

	function loadProfile(){
		elbs.progressbar({value:100}, "正在加载数据，请稍候...", false);
		$.ajax({
			url: './profile.jxp?action=view&id=' + id,
			dataType: 'json',
			type: 'post',
			success: function(data){
				$("#username").html(data.username);
				$("#nickname").attr("value", data.nickname);
				$("#avatar-small").attr("src", "./images/avatar/" + data.photo);
				$("#avatar-large").attr("src", "./images/avatar/" + data.photo);
				if (data.sex == "m") {
					$("#m").attr("checked", true);
				} else {
					$("#f").attr("checked", true);
				}
				var birthday = data.birthday.substring(0, data.birthday.length - 11);
				$("#birthday").attr("value", birthday);
				$("#email").attr("value", data.email);
				$("#organization").html("　" + data.organization);
				//$("#department").attr("value", data.department);
				
				if ($("#deptList").length > 0) {
					var depts = getDepts(data.allDepts);
					for (var i = 0; i < depts.length; i++) {
						$("#deptList").append("<option value=" + depts[i] + ">" + depts[i] + "</option>");
					}
					$("#deptList").val(data.department);
				} else {
					$("#department").html("　" + data.department);
				}
				
				$("#contact").attr("value", data.contact);
				//$("#idcard").attr("value", data.idcard);
				elbs.closeProgressbar();
			},
			failure: ErrorInGetData,
			error: ErrorInGetData
		});
	}
	function saveProfile(){
		elbs.progressbar({value: 100}, "正在保存设置，请稍候...", false);
		$.ajax({
			url: './profile.jxp?action=updateMember',
			dataType: 'json',
			type: 'post',
			data: {
				uid: id,
				nickname: $("#nickname").attr("value"),
				sex: $("#m").attr("value") != null ? $("#m").attr("value") : $("#f").attr("value"),
				birthday: $("#birthday").attr("value"),
				photo: $("#avatar").attr("value"),
				department: getDeptValue(),
				email: $("#email").attr("value"),
				contact: $("#contact").attr("value")
			},
			success: function(data){
				elbs.closeProgressbar();
				elbs.alert(data.msg);
			},
			failure: ErrorInGetData,
			error: ErrorInGetData
		});
	}
	function savePwd(){
		var pwd = $("#password").attr("value").trim();
		var opwd = $("#oldpassword").attr("value").trim();
		var repwd = $("#repassword").attr("value").trim();
		console.log(pwd == repwd);
		if (pwd == repwd) {
			elbs.progressbar({value: 100}, "正在执行操作，请稍候...");
			$.ajax({
				url: "./profile.jxp",
				dataType: "json",
				type: "post",
				data: {
					action: "setPwd",
					uid: id,
					oldpassword: opwd,
					password: pwd
				},
				success: function(data){
					elbs.closeProgressbar();
					elbs.alert(data.msg);
					$("#oldpassword, #password, #repassword").attr("value", "");
				}, 
				failure: function(data){
					elbs.closeProgressbar();
					elbs.alert(data.msg);
				},
				error: function(){
					elbs.closeProgressbar();
					elbs.alert("操作出错！");
				}
			});
		} else {
			elbs.alert("两次密码输入不一致，请重新输入！");
		}
	}
	
	loadProfile();
	
	$("#saveProfile").click(function(){
		saveProfile();
	});
	$("#savePwd").click(function(){
		savePwd();
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
				<li><a href="#r_profile">基本信息维护</a></li>
				<li><a href="#r_setPwd">更改密码</a></li>
				<li><a href="#r_uploadAvatar">上传头像</a></li>
			</ul>
			<div id="r_profile">
				<form>
	        		<span style="float:right;padding-right: 28px;">带<span class="redFlag">*</span>项为必填项</span>
	        		<hr />
	                <table>
	                	<tr>
	                		<td width="100"><span class="redFlag">*</span>用户名：</td><td><span id="username" style="float:left;"></span><!-- <input class="inputText" type="text" id="username" name="username" disabled="disabled" /> --><span class="redFlag" id="usernameError"></span></td>
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
	                	<!--<tr>
	                		<td>密码：</td><td align="left"><a class="btnCls" href="#r_setPwd">修改</a></td>
	                	</tr>
	                	
	                	--><!-- <tr>
	                		<td><span class="redFlag">*</span>员工号：</td><td><input class="inputText" type="text" id="employeeno" name="employeeno" /></td>
	                	</tr> -->
	                	<tr>
	                		<td>头像：</td><td><span style="float:left;">　<img id="avatar-small" src="" height="50" /></span><!--<input class="inputText" type="text" id="avatar" name="avatar" />--></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>出生日期：</td><td><input class="inputText" type="text" id="birthday" name="birthday" /><span class="redFlag" id="dateError"></span></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>电子邮箱：</td><td><input class="inputText" type="text" id="email" name="email" /><span class="redFlag" id="emailError"></span></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>组织名称：</td><td><span id="organization" style="float:left;"></span><span class="redFlag" id="orgError"></span></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>所在部门：</td><td id="deptTD"><span id="department" style="float:left;"></span><span class="redFlag" id="deptError"></span></td>
	                	</tr>
	                	<tr>
	                		<td><span class="redFlag">*</span>联系电话：</td><td><input class="inputText" type="text" id="contact" name="contact" /><span class="redFlag" id="contactError"></span></td>
	                	</tr>
	                	<!--<tr>
	                		<td><span class="redFlag">*</span>身份证号：</td><td><input class="inputText" type="text" id="idcard" name="idcard" /><span class="redFlag" id="idError"></span></td>
	                	</tr>
	                    --><tr>
	                        <td></td><td align="left"><input class="inputBtn" id="saveProfile" type="button" value="保存" /><input class="inputBtn" id="backToIndex" type="button" value="取消" onclick="javascript:window.location.href='./member.jxp'" /></td>
	                    </tr>
	                </table>
	            </form>
			</div>
			<div id="r_setPwd">
				<table>
					<tr>
	               		<td><span class="redFlag">*</span>旧密码：</td><td><input class="inputText" type="password" id="oldpassword" name="oldpassword" /><span class="redFlag" id="oldpwdError"></span></td>
	               	</tr>
	               	<tr>
	               		<td><span class="redFlag">*</span>密码：</td><td><input class="inputText" type="password" id="password" name="password" /><span class="redFlag" id="pwdError"></span></td>
	               	</tr>
	               	<tr>
	               		<td><span class="redFlag">*</span>确认密码：</td><td><input class="inputText" type="password" id="repassword" name="repassword" /><span class="redFlag" id="repwdError"></span></td>
	               	</tr>
	               	<tr>
	               		<td></td><td><input type="button" class="inputBtn" name="setPwd" id="savePwd" value="确认" style="float:left;" /></td><td></td>
	               	</tr>
				</table>
			</div>
			<div id="r_uploadAvatar">
				<form action="./upload.jxp?type=image" method="post" enctype="multipart/form-data" >
					<table>
						<tr>
							<td>
								<span>上传头像:</span>
								<span><input class="btnCls" id="upload-file" type="file" name="avatar" id="uploadAvatar" /></span>
								<span><input id="upload-submit" type="submit" value="上传" /></span>
							</td>
						</tr>
						<tr>
							<td><span><img id="avatar-large" height="200"></span></td>
						</tr>
						<tr>
							<td></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div><jsp:include page="./footer.jsp" /></div>
</div>
<div id="alertDialog"></div>
</body>
</html>