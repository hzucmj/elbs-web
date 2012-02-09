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
<link type="text/css" rel="stylesheet" href="css/global.css" />
<title>部门管理</title>
<script>
	function loadDept(){
		elbs.progressbar({value: 100}, "正在加载数据，请稍候...", false);
		$.ajax({
			url: './dept.jxp',
			type: 'post',
			dataType: 'json',
			data: {
				action: 'showDept'
			},
			success: function(data){
				var deptString = data.depts;
				var deptArr = deptString.split(',');
				var deptHtml = '<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">';
				deptHtml += '<tr><th width=10%>序号</th><th width=65%>部门名称</th><th width=25%>是否删除?</th></tr>';
				
				for (var i = 0; i < deptArr.length; i++) {
					deptHtml += "<tr><td>" + (i + 1) + "</td><td>" + deptArr[i] + "</td><td><input class='inputBtn' id='" + deptArr[i] + "' type='button' value='删除' onclick='delDept(this)' /></td></tr>";
					$("#" + deptArr[i]).click(delDept);
				}
				deptHtml += '</table>';
				$("#deptTbl").html(deptHtml);
				$(".inputBtn").button();
				elbs.closeProgressbar();
			},
			failure: function(data){
				
			},
			error: function(data){
				elbs.alert("数据加载失败！");
			}
		});
	}
	function delDept(obj){
		//$("#alertDialog").html(obj.id);
		//$("#alertDialog").dialog("open");
		var deptName = obj.id;
		if (deptName != null) {
			var msg = "确认删除部门<span style='color: #f00;font-weight:bold;'>　" + deptName + "　</span>吗？";
			var title = "警告";
			var buttons = {
					"确定" : function(){
						$(this).dialog("close");
						elbs.progressbar({});
						$.ajax({
							url: "./dept.jxp",
							dataType: 'json',
							type: 'post',
							data: {
								action: 'del',
								delDept: deptName
							},
							success: function(data){
								elbs.closeProgressbar();
								elbs.alert(data.msg);
								//window.location.reload();
								loadDept();
							}, 
							failure: function(data){
								elbs.closeProgressbar();
								elbs.alert(data.msg);
							},
							error: function(data){
								elbs.closeProgressbar();
								elbs.alert("操作失败");
							}
						});
					}, 
					"取消" : function(){
						$(this).dialog("close");
					}
			};
			elbs.confirm(msg, title, buttons);
		}
	}
	function addDept(){
		var deptName = $("#deptName").attr("value");
		if (deptName != null) {
			elbs.progressbar({value:100}, null, false);
			$.ajax({
				url: './dept.jxp',
				type: 'post',
				dataType: 'json',
				data: {
					newDept: deptName,
					action: 'add'
				},
				success: function(data){
					elbs.closeProgressbar();
					elbs.alert(data.msg);
					$("#deptName").attr("value", "");
					loadDept();
				},
				failure: function(data){
					elbs.alert(data.msg);
				}
			});
		}
	}

$(document).ready(function(){
	$("#r_p").tabs();
	
	loadDept();
	
	$("#addDeptBtn").click(function(){
		addDept();
	});
	
	$("#reloadPage").click(function(){
		//window.location.reload();
		loadDept();
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
				<li><a href="#r_dept">部门管理</a></li>
				<li><a href="#r_addDept">添加部门</a></li>
			</ul>
			<div id="r_dept">
				<div id="deptOp">
					<input type="button" value="添加" />
					<input type="button" value="刷新" id="reloadPage" />
				</div>
				<div id="deptTbl"></div>
				<!--<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						 <th>员工号</th> 
						<th>序号</th>
						<th>部门名称</th>
					</tr>
					<c:forEach var="member" items="${deptList}">
					<tr bgcolor="#FFFFFF">
						 <td>0001</td> 
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
						<td><a href="./profile.jxp?id=${member.uid}">查看</a>　<a href="javascript:void(0)" id="del-${member.username}" onclick="javascript:delMember(this);">删除</a></td>
					</tr>
					</c:forEach>
				</table>
				-->
				<!--<div class="pageBar">
					<span><a href="./member.jxp?page=1" id="firstPage">首页</a>　</span>
					<span><a href="./member.jxp?page=${currentPage-1}" id="previousPage">上一页</a>　</span>
					<span><a href="./member.jxp?page=${currentPage+1}" id="nextPage">下一页</a>　</span>
					<span><a href="./member.jxp?page=${pageCount}" id="lastPage">最后一页</a></span>
				</div>-->
			</div>
			<div id="r_addDept">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td>部门名称</td><td><input class="inputText" type="text" id="deptName" name="deptName" /></td>
					</tr>
					<tr>
						<td></td><td><input class="inputBtn" id="addDeptBtn" type="button" value="添加" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div><jsp:include page="./footer.jsp" /></div>
</div>
<div id="alertDialog"></div>
<div id="alertConfirm"></div>
</body>
</html>