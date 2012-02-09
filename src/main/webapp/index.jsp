<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome</title>
<!--<link type="text/css" rel="stylesheet" href="./jquery_ui/css/ui-lightness/jquery-ui-1.8.16.custom.css" />
--><script type="text/javascript" src="./jquery_ui/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="./jquery_ui/js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" rel="stylesheet" href="css/global.css" />

<script type="text/javascript">

	function nullornot(val){
		if(val==undefined || val==""){
			return false;
		}else{
			return true;
		}
	}

</script>

<script>
	$(function(){
		$("input:button").button();
		$("#logout, #profile, #mgmt").button();
		$("#profile").click(function(){
			window.location.href = "./profile.jsp";
		});
		$("#mgmt").click(function(){
			window.location.href = "./mgmt.jsp";
		});
		$("#postArticle").click(function(){
			$.ajax({
				url : './index.jxp?action=postArticle',
				type : 'post',
				dataType : 'json',
				data : {
					//暂时为content
					content: $("#articleArea").val()
				},
				success : function(data){
					if (data.success == true) {
						$.ajax({
							url:'./index.jxp?action=showArticle',
							type:'post',
							dataType:'xml',
							success:function(data){
								$(data).find('article').each(function(index){

									var mid=$(this).children("mid").text();
									var uid=$(this).children("uid").text();
									var content=$(this).children("content").text();
									var images=$(this).children("images").text();
									var video=$(this).children("video").text();
									var music=$(this).children("music").text();
									var posttime=$(this).children("posttime").text();
									var location=$(this).children("location").text();
									 
									var html="<div class='article' id='a_article_"+ mid +"'>"
										+"<div class='a_avatar'>"
											+"<img src='http://tp1.sinaimg.cn/1857435872/50/5598785305/0' width='48' height='48' />"
										+"</div>"
										+"<div class='a_body'>"
											+"<div class='a_content'>"
												+"<span>"
													+"<a href='' class='redFlag'>"+uid+"</a>："
												+"</span>"
												+content
											+"</div>"
											+"<div class='a_pic'>";
												if(images==undefined || images==''){
													html+="<img src='' height='0'/>";
												}else{
													html+="<img src='"+images+"' height='200'/>";
												}
											html+="</div>"
											+"<div class='a_video'>";
												if(video==undefined || video==''){
													html+="<embed width='0' height='0'></embed>";
												}else{
													html+="<embed src='"+video+"' type='application/x-shockwave-flash' allowscriptaccess='always' allowfullscreen='true' wmode='opaque' width='200' height='150'></embed>";
												}
											html+="</div>"
											+"<div class='a_music'>"+music+"</div>"
											+"<div class='a_posttime'>"+posttime+"</div>"
											+"<div class='a_location'>";
												if(location==undefined || location==''){
													html+="";
												}else{
													html+="来自："+location;
												}
											html+="</div>"
											+"</div>"
											+"<hr />"
											+"</div>";
									
									$("#articles").prepend(html);
								
								});
							},
							failure:function(){
								
							}
						});
					} else {
						
					}
				},
				failure : function(data){
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
		<div id="l_p">
			<jsp:include page="./left-menu.jsp"></jsp:include>
		</div>
		<div id="r_p">
			<div id="r_articles">
				<h2>发布信息</h2>
				<div>
					<div><textarea id="articleArea" name="articleArea"></textarea></div>
					<div id="postBtn">
						<a class="btnCls" id="postArticle">
							<span>发布</span>
						</a>
					</div>
				</div>
				<div id="hr"></div>
				<div id="articles">
					<c:forEach var="articles" items="${articles}">
						<!-- begin -->
						<div class="article" id="a_article_${articles.mid}">
							<div class="a_avatar"><img src="http://tp1.sinaimg.cn/1857435872/50/5598785305/0" width="48" height="48" /></div>
							<div class="a_body" >
								<div class="a_content">
									<span><a href="" class="redFlag">${username}</a>：</span>
									${articles.content}
								</div>
								<div class="a_pic">
									<c:choose>
										<c:when test="${articles.images==null }">
											<img src="" height="0"/>
										</c:when>
										<c:otherwise>
											<img src="${articles.images}" height="200"/>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="a_video">
									<c:choose>
										<c:when test="${articles.video==null}">
											<embed width="0" height="0"></embed>
										</c:when>
										<c:otherwise>
											<embed src="${articles.video}" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" wmode="opaque" width="200" height="150"></embed>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="a_music">${articles.music}</div>
								<div class="a_posttime">${articles.posttime}</div>
								<div class="a_location">
									<c:choose>
										<c:when test="${articles.location==null}">
										</c:when>
										<c:otherwise>
											来自：${articles.location}
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<hr />					
						</div>
						<!-- end -->
					</c:forEach>
				</div>
				<div class="pageBar">
					<span>
					<span><a class="btnCls" href='./index.jxp?currentPage=${currentPage}&pageFlag=before'>上一页</a></span>
					<span><a class="btnCls" href='./index.jxp?currentPage=${currentPage}&pageFlag=next'>下一页</a></span>
						 <%-- <c:choose>
							<c:when test="${currentPage>1}">
								<span><a href='./index.jxp?currentPage=${currentPage}&pageFlag=before'>上一页</a></span>
							</c:when>
							<c:otherwise>
								<span><a>上一页</a></span>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${currentPage} < ${pageCount} ">
								<span><a href='./index.jxp?currentPage=${currentPage }&pageFlag=next'>下一页</a></span>
							</c:when>
							<c:otherwise>
								<span><a>下一页</a></span>
							</c:otherwise>
						</c:choose> --%>
					</span>
				</div>
				<div style="margin-left:10px;">
					
				</div>
			</div>
			<div></div>
		</div>
	</div>
	<div><jsp:include page="./footer.jsp"></jsp:include></div>
</div>
</body>
</html>
