<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="./jquery_ui/css/redmond/jquery-ui-1.8.16.custom.css" />
<script type="text/javascript" src="./jquery_ui/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="./jquery_ui/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="./js/validate.js"></script> 
<link type="text/css" rel="stylesheet" href="css/global.css" />
<title>员工信息管理</title>
<script>
$(function(){
	$("#r_p").tabs();
});

function activeMember(obj){
	var f, un;
	if (obj.value == "已启用") {
		f = 0;
	} else {
		f = 1;
	}
	un = obj.id;
	if (un != null){
		elbs.progressbar({
			value: 100
		}, "正在执行操作，请稍候...");
		$.ajax({
			url : "./member.jxp?action=activeMember",
			type : 'post',
			dataType : 'json',
			data : {
				enabled : f,
				username : un
			},
			success : function(data){
				if (f == 1) {
					elbs.alert("启用成员成功！");
					obj.value="已启用";				
				} else {
					elbs.alert("禁用成员成功！");
					obj.value="已禁用";
				}
				elbs.closeProgressbar();
			},
			failure : function(data){
				f == 1 ? elbs.alert("启用成员失败！") : elbs.alert("禁用成员失败！");
			},
			error : function(data){
				elbs.alert("操作出错！");
			}
		});
	}
}

function delMember(obj){
	un = obj.id.substring(4, obj.id.length);
	var msg = "<p>确认删除成员<span style='color: #f00;font-weight:bold;'>　" + un + "　</span>吗？</p>";
	var title = "警告";
	var buttons = {
			"确定": function(){
				$(this).dialog("close");
				elbs.progressbar({value: 100});
				$.ajax({
					url : "./member.jxp?action=delMember",
					type: "post",
					data : {
						username : un
					},
					dataType : 'json',
					success : function(data){
						elbs.closeProgressbar();
						elbs.alert(data.msg);
						window.location.reload();
					},
					failure : function(data){
						elbs.closeProgressbar();
						elbs.alert(data.msg);
					},
					error : function(){
						elbs.closeProgressbar();
						elbs.alert("删除用户失败!");
					}
				}); 
			},
			"取消": function(){
				$(this).dialog("close");
			}
	};
	elbs.confirm(msg, title, buttons);
}
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
				<li><a href="#r_employee">员工信息管理</a></li>
			</ul>
			<div id="r_employee">
				<div><a class="btnCls" href="javascript:void(0)" onclick="window.location.href='./adduser.jxp'">新增</a><a class="btnCls" href="javascript:void(0)" onclick="window.location.href='./member.jxp'">刷新</a></div>
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" >
					<tr>
						<!-- <th>员工号</th> -->
						<th>昵称</th>
						<th>账号</th>
						<th>状态</th>
						<th>邮箱</th>
						<th>操作</th>
					</tr>
					<c:forEach var="member" items="${memberList}">
					<tr bgcolor="#FFFFFF">
						<!-- <td>0001</td> -->
						<td>${member.nickname}</td>
						<td><a href="#">${member.username}</a></td>
						<c:choose>
							<c:when test="${member.enabled}">
								<td>
									<input class="inputBtn" style="margin:0 auto;" type="button" value="已启用" id="${member.username}"  onclick="javascript:activeMember(this)" />
								</td>
							</c:when>
							<c:otherwise>
								<td>
									<input class="inputBtn" style="margin:0 auto;" type="button" value="已禁用"  id="${member.username}"  onclick="javascript:activeMember(this)" />
								</td>
							</c:otherwise>						
						</c:choose>
						<td><a href="mailto:${member.email}">${member.email}</a></td>
						<td><a class="btnCls"  href="javascript:void(0)" onclick="window.location.href='./profile.jxp?e=t&id=${member.uid}'">查看</a>　<a class="btnCls" href="javascript:void(0)" id="del-${member.username}" onclick="javascript:delMember(this);">删除</a></td>
					</tr>
					</c:forEach>
				</table>
				<div class="pageBar">
					<span><a href="./member.jxp?page=1" id="firstPage">首页</a>　</span>
					<span><a href="./member.jxp?page=${currentPage-1}" id="previousPage">上一页</a>　</span>
					<span><a href="./member.jxp?page=${currentPage+1}" id="nextPage">下一页</a>　</span>
					<span><a href="./member.jxp?page=${pageCount}" id="lastPage">最后一页</a></span>
				</div>
			</div>
			<div id="tab1"></div>
		</div>
	</div>
	<div><jsp:include page="./footer.jsp"></jsp:include></div>
</div>
<div id="dialog-confirm"></div>
</body>
</html>