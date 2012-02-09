
//common setting
	$("input:button, input:submit").button();
	$("#birthday").datepicker({altFormat: 'yy-mm-dd', dateFormat: 'yy-mm-dd'});
	$("#alertDialog").dialog({
		autoOpen: false,
		title : '提示',
		//modal : true,
		resizable : false,
		buttons : {
			"ok" : function(){
				$(this).dialog("close");
			}
		}
	});
	//registe.jsp
	$("#username").blur(function(){
		bu = validate_userid("#username", "#usernameError", "用户名只能由字母、数字和下划线组成");
		$.ajax({
			//url : './user.jxp',
			type : 'post',
			dataType : 'json',
			data : {
				username : $(this).attr("value")
			},
			success : function(data){
				/*alert("aa");
				if (data.success == true) {
					alert("good");
				} else {
					alert("bad");
				}*/
			},
			failure : function(data){
				//alert("aaa");
			}
		});
	});
	$("#password").blur(function(){
		bp = isBlank("#password", "#pwdError", "密码不能为空");
	});
	$("#repassword").blur(function(){
		brp = isBlank("#repassword", "#repwdError", "确认密码不能为空");
	});
	$("#birthday").change(function(){
		bd = validate_date("#birthday", "#dateError", "日期格式不正确");
	});
	$("#birthday").blur(function(){
		bd = isBlank("#birthday", "#dateError", "日期格式不正确");
	});
	$("#email").blur(function(){
		be = validate_email("#email", "#emailError", "邮箱格式不正确");
	});
	$("#organization").blur(function(){
		bo = isBlank("#organization", "#orgError", "组织名称不能为空");
	});
	$("#department").blur(function(){
		bd = isBlank("#department", "#deptError", "部门名称不能为空");
	});
	$("#contact").blur(function(){
		bc = isBlank("#contact", "#contactError", "联系方式 不能为空");
	});
	$("#idcard").blur(function(){
		bi = isBlank("#idcard", "#idError", "身份证号码不能为空");
	});
	$("#registeUser").click(function(e){
		var url = "./registe.jxp?action=registe";
		saveUser(url);
	});
	
	function saveUser(url){
		$.ajax({
			url : url,
			method : "post",
			dataType : 'json',
			data : {
				username : $("#username").attr("value"),
				nickname : $("#nickname").attr("value"),
				sex : $("#m").attr("value") != null ? $("#m").attr("value") : $("#f").attr("value"),
				password : $("#password").attr("value"),
				repassword : $("#repassword").attr("value"),
				//employeeno : $("#employeeno").attr("value"),
				photo : $("#avatar").attr("value"),
				birthday : $("#birthday").attr("value"),
				email : $("#email").attr("value"),
				organization : $("#organization").attr("value"),
				department : $("#department").attr("value"),
				contact : $("#contact").attr("value"),
				idcard : $("#idcard").attr("value")
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
	};