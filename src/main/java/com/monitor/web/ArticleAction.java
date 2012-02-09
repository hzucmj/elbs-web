package com.monitor.web;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.MultipleAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.HtmlView;
import org.zengsource.mvc.view.XmlView;
import org.zengsource.util.DateUtil;
import org.zengsource.util.IDUtil;

import com.monitor.iputil.IPSeeker;
import com.monitor.model.Article;
import com.monitor.model.User;
import com.monitor.service.ArticleService;
import com.monitor.service.AuthorityService;
import com.monitor.service.UserService;

public class ArticleAction extends MultipleAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String content;
	private String images;
	private String video;
	private String music;
	private String location;
	private Date posttime;	
	/*private String start;
	private String limit;*/

	private ArticleService articleService;
	private UserService userService;
	private AuthorityService authorityService;
	
	/*public String getStart() {
		return start;
	}
	
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getLimit() {
		return limit;
	}
	
	public void setLimit(String limit) {
		this.limit = limit;
	}*/
	
	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	/**
	 * 将html文档转换为文本
	 * 
	 * @param htmlStr
	 * @return string
	 */
	public static String htmlToText(String htmlStr) {
		/*String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串

*/	
		StringBuffer sb = new StringBuffer() ;
		int len = htmlStr.length() ;
		for(int i=0; i < len; i++){
			char c = htmlStr.charAt(i) ;
			switch(c){
				//case ' ' : sb.append("&sbnp;") ; break ;
				//case '\n' : sb.append("<br/>") ; break ;
				case '\r' : break ;
				case '\'' : sb.append("&#39;") ;break ;
				case '<' : sb.append("&lt;") ; break ;
				case '>' : sb.append("&gt;") ; break ;
				case '&' : sb.append("&amp;") ; break ;
				case '"' : sb.append("&#34;") ; break ;
				case '\\' : sb.append("&#92;") ;break ;
				default : sb.append(c) ;
			}
			
		}
		return sb.toString() ;}
	
	
	//获取ip地址
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public AbstractView doPostArticle() throws MvcException {
		// TODO Auto-generated method stub
		Article article=new Article();
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		//获得username对就的id
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("username", username)
		};
		User user=this.userService.findByName(criterions);
		String uid=user.getUid();
		article.setMid(IDUtil.generate());
		article.setUid(uid);
		article.setContent(htmlToText(getContent()));
		article.setPosttime(new Date());
		
		
		String ipaddress=this.getIpAddr(this.getRequest());
		//指定纯真数据库的文件名，所在文件夹    
		IPSeeker ip=new IPSeeker("QQWry.Dat","E:"); 
		//获得地区
		String address = ip.getIPLocation(ipaddress).getCountry();
		//获得类型
		String area=ip.getIPLocation(ipaddress).getArea();
		if(area.equals("local")){
			article.setLocation("局域网  "+ipaddress);
		}else{
			article.setLocation(address);
		}
		//System.out.println("你所在地为："+address+"  "+area);
		this.articleService.addArticle(article);
		return new HtmlView("{\"success\":true}");
	}
	
	//更新即时信息
	public AbstractView doShowArticle() throws MvcException {
		// TODO Auto-generated method stub
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("username", username)
		};
		User user=this.userService.findByName(criterions);
		String uid=user.getUid();
		String queryLastArticle="from Article article where article.uid='"+uid+"' order by mid desc";
		List<?> articles=this.articleService.queryArticle(queryLastArticle);
		
		// 创建xml文件
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("articles");
		for (int i = 0; i < articles.size(); i++) {
			Article article = (Article) articles.get(i);
			Element articleEle = root.addElement("article");
			articleEle.addElement("mid").addText(article.getMid());
			articleEle.addElement("uid").addText(username);
			articleEle.addElement("content").addText((article.getContent()));
			if(article.getImages()!=null){
				articleEle.addElement("images").addText(article.getImages());
			}
			if(article.getVideo()!=null){
				articleEle.addElement("video").addText(article.getVideo());
			}
			if(article.getMusic()!=null){
				articleEle.addElement("music").addText(article.getMusic());
			}
			if(article.getLocation()!=null){
				articleEle.addElement("location").addText(article.getLocation());
			}
			/*
			 * 格式化输出时间
			 * 格式为：2011-10-23 19:00
			 */
			articleEle.addElement("posttime").addText(DateUtil.format(article.getPosttime(), "yyyy-MM-dd HH:mm"));
		}
		return new XmlView(doc);
	}
	
	//显示用户发布内容
	public AbstractView showArticles() throws MvcException {
		// TODO Auto-generated method stub
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		this.getRequest().setAttribute("username", username);
		//获得username对就的id
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("username", username)
		};
		User user=this.userService.findByName(criterions);
		String uid=user.getUid();
		
		//取得总数 
		Criterion[] article_criterions = new Criterion[]{
				Restrictions.eq("uid", uid)
		};
		Long count = this.articleService.countArticle(article_criterions);
		this.getRequest().setAttribute("count", count);
		
		//分页业务逻辑
		//暂定每十条分一页
		Integer pageSize=10;
		
		//获取当前页面
		String curPage=this.getRequest().getParameter("currentPage");
		//单击是上一页还是下一页
		String pageFlag=this.getRequest().getParameter("pageFlag");
		
		//当前页
		Integer currentPage=1;
		
		if(curPage!=null){
			currentPage=new Integer(curPage);
		}
		
		//处理上一页、下一页处理
		if(pageFlag!=null){
			if(pageFlag.equals("before")){
				--currentPage;
			}
			if(pageFlag.equals("next")){
				++currentPage;
			}
		}
		
		//得到总页数
		Integer pageCount=((int) ((count+pageSize)-1)/pageSize);
		if(currentPage<1){
			currentPage=1;
		}else if(currentPage>pageCount){
			currentPage=pageCount;
		}
		this.getRequest().setAttribute("currentPage", currentPage);
		this.getRequest().setAttribute("pageCount", pageCount);
		
		//计算上一页的开始位置
		Integer firstResult=(currentPage-1)*pageSize;
		
		//权限设置
		String hql="";
		String authority="";
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Collection<GrantedAuthority> auth = userDetails.getAuthorities();  
		for(GrantedAuthority g : auth){
			authority = g.getAuthority();
		}  
		
		if(authority.equals("ROLE_USER")){
			hql=//"select article.mid as mid,article.uid as uid,article.content as content,article.video as video,article.images as images,article.music as music,substring(article.posttime,1,16) as posttime " +
					"from Article article where article.uid='"+uid+"' order by article.mid desc";
		}else if(authority.equals("ROLE_MASTER")){
			hql="from Article article where article.uid in (select uid from User user where user.root='"+username+"') or uid = '" + uid + "' order by article.posttime desc";
			System.out.println(hql);
		}
		@SuppressWarnings("unchecked")
		List<Article> articles=(List<Article>) this.articleService.queryArtile(hql, firstResult, pageSize);
		//List<Article> articles=(List<Article>) this.articleService.queryArticle(article_criterions, firstResult, pageSize);
		if(articles.size()>0){
			this.getRequest().setAttribute("articles", articles);
		}else{
			this.getRequest().setAttribute("articles", "");
		}
		return null;
	}
	
	
	public AbstractView doService() throws MvcException {
		// TODO Auto-generated method stub
		String actionName=this.getRequest().getParameter("action");
		//System.out.println(actionName);
		if(actionName==null){
			showArticles();
		}
		return super.doService();
	}
}
