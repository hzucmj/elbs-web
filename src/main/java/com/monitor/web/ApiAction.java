package com.monitor.web;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.MultipleAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.HtmlView;
import org.zengsource.mvc.view.XmlView;
import org.zengsource.util.DateUtil;
import org.zengsource.util.IDUtil;

import com.monitor.Functions;
import com.monitor.model.Article;
import com.monitor.model.User;
import com.monitor.service.ArticleService;
import com.monitor.service.AuthorityService;
import com.monitor.service.OrganizationService;
import com.monitor.service.UserService;

public class ApiAction extends MultipleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	OrganizationService organizationService;
	AuthorityService authorityService;
	UserService userService;
	ArticleService articleService;
	
	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

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

	// Message Entity
	private String mid;
	private String content;
	private String images;
	private String video;
	private String music;
	private String location;
	private String posttime;
	// Orgnization Entity
	private String oid;
	private String organizer;
	private String organization;
	// private String department;
	// private int enabled;
	// private String createdtime;
	// private String updatedtime;
	// User Entity
	private String uid;
	private String username;
	private String nickname;
	private String sex;
	private String password;
	private String repassword;
	private String employeeno;
	private String photo;
	private String birthday;
	private String email;
	private String department;
	private String idcard;
	private String root;
	private String contact;
	private int enabled;
	private String createdtime;
	private String updatedtime;
	// Authority Entity
	private String aid;
	private String authority;

	//
	private int s;//start
	private int l;//limit
	private String start;
	private String limit;
	public String getStart() {
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
	}

	private int p;//page
	private String t;//数据类型
	
	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
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

	public String getPosttime() {
		return posttime;
	}

	public void setPosttime(String posttime) {
		this.posttime = posttime;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getEmployeeno() {
		return employeeno;
	}

	public void setEmployeeno(String employeeno) {
		this.employeeno = employeeno;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	public String getUpdatedtime() {
		return updatedtime;
	}

	public void setUpdatedtime(String updatedtime) {
		this.updatedtime = updatedtime;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * 登錄系統
	 * 
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doLogin() throws MvcException {
		String username = this.getUsername();
		String password = this.getPassword();
		Criterion[] loginCri = new Criterion[]{
			Restrictions.eq("username", username)
		};
		User u = (User)this.userService.queryUser(loginCri);
		if (password.equals(u.getPassword())){
			return new HtmlView("{\"success\": true, \"msg\": \"登录成功\", \"uid\": \"" + u.getUid() + "\"}");
		} else {
			return new HtmlView("{\"success\": false, \"msg\": \"用户名或密码错误！\"}");
		}
	}
	
	/**
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doProfile() throws MvcException {
		String uid = this.getUid();
		Criterion[] profileCri = new Criterion[]{
			Restrictions.eq("uid", uid)
		};
		User u = (User) this.userService.queryUser(profileCri);
		String sex = (u.getSex().equals("m"))? "男": "女";
		String birthday = DateUtil.format(u.getBirthday(), "yyyy-mm-dd");
		return new HtmlView("{\"success\": true, \"info\":[{\"username\": \"" + u.getUsername() + "\", \"email\":\"" + u.getEmail() + "\", \"sex\": \"" + sex + "\", \"nickname\": \"" + u.getNickname() + "\", \"department\": \"" + u.getDepartment() + "\", \"birthday\": \"" + birthday + "\", \"contact\": \"" + u.getContact() + "\"}]}");
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doAdduser() throws MvcException {
		String uid = IDUtil.generate();
		String username = this.getUsername();
		String password = this.getPassword();
		Criterion[] checkCri = new Criterion[]{
				Restrictions.eq("username", username)
		};
		User user = (User) this.userService.queryUser(checkCri);
		if (user == null){
			User u = new User();
			u.setBirthday(null);
			u.setContact(null);
			u.setCreatedtime(new Date());
			u.setUpdatedtime(new Date());
			u.setEmail(null);
			u.setDepartment(null);
			u.setNickname(null);
			u.setPhoto(null);
			u.setPassword(password);
			u.setUsername(username);
			u.setUid(uid);
			u.setEmployeeno(null);
			u.setEnabled(true);
			u.setIdcard(null);
			u.setRoot("root");
			u.setSex("m");
			
			if (this.userService.addUser(u)){
				return new HtmlView("{\"success\": true, \"msg\": \"恭喜你，注册成功！\"}");
			}
			else {
				return new HtmlView("{\"success\": false, \"msg\": \"注册失败，请稍后再试！\"}");	
			}
		} else {
			return new HtmlView("{\"success\": false, \"msg\": \"用户已存在，请更换用户名！\"}");
		}
		
	}

	/**
	 * 更新用户资料
	 * 
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doUpdateuser() throws MvcException {
		String username = this.getUsername();
		String password = this.getPassword();
		String sex = this.getSex();
		String idcard = this.getIdcard();
		String employeeno = this.getEmployeeno();
		String photo = this.getPhoto();
		String nickname = this.getNickname();
		Date birthday = Functions.strToDate(this.getBirthday(), "yyyy-mm-dd");
		String email = this.getEmail();
		String department = this.getDepartment();
		String contact = this.getContact();
		Date updatedtime = DateUtil.now();
		Criterion[] updateCri = new Criterion[]{
				Restrictions.eq("username", username)
		};
		User u = (User) this.userService.queryUser(updateCri);
		if (password != null){
			u.setPassword(password);
		}
		u.setSex(sex);
		u.setIdcard(idcard);
		u.setEmployeeno(employeeno);
		u.setPhoto(photo);
		u.setNickname(nickname);
		u.setBirthday(birthday);
		u.setEmail(email);
		u.setDepartment(department);
		u.setContact(contact);
		u.setUpdatedtime(updatedtime);
		this.userService.updateUser(u);
		return new HtmlView("{\"success\": true, \"msg\": \"修改成功！\"}");
	}

	/**
	 * 添加组织
	 * 
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doAddorg() throws MvcException {
		return null;
	}

	/**
	 * 更新组织信息
	 * 
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doUpdateorg() throws MvcException {
		return null;
	}

	/**
	 * 发布新信息
	 * 
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doAddmsg() throws MvcException {
		String mid = IDUtil.generate();
		String uid = this.getUid();
		String content = this.getContent();
		String images = this.getImages();
		String location = this.getLocation();
		Date posttime = DateUtil.now();
		
		Article article = new Article();
		article.setMid(mid);
		article.setUid(uid);
		article.setImages(images);
		article.setLocation(location);
		article.setContent(content);
		article.setPosttime(posttime);
		
		this.articleService.addArticle(article);
	
		return new HtmlView("{success: true, msg: '发布成功！'}");
	}

	/**
	 * 删除信息
	 * 
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doDelmsg() throws MvcException {
		String mid = this.getMid();
		Criterion[] delCri = new Criterion[]{
				Restrictions.eq("aid", mid)
		};
		Article article = (Article) this.articleService.queryArticle(delCri);
		if (this.articleService.deleteArticle(article)){
			return new HtmlView("{\"success\": true, \"msg\":\"删除成功！\"}");
		} else {
			return new HtmlView("{\"success\": false, \"msg\":\"删除失败！\"}");
		}
	}

	/**
	 * 信息列表
	 * 
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doShowmsg() throws MvcException {
		String uid = this.getUid();
		String msgSql = "select m.mid,m.uid,m.content, m.images, m.video, m.music, m.location, m.posttime, u.username, u.photo "
				+ "from Article m, User u "
				+ "where m.uid = u.uid and m.uid = '" 
				+ uid + "'";
		//String msgSql = "select m.mid,m.uid,m.content,m.images,m.video,m.music,m.location,m.posttime,u.username from Article m, User u where m.uid = '20111027001719408' and m.uid = u.uid";
		int s = Integer.parseInt(this.getStart());
		int l = Integer.parseInt(this.getLimit());
		System.out.println("======" + s + "======" + l + "======" + this.getUid());
		String type = this.getT();
		List<?> msgList = this.articleService.queryArtile(msgSql, s, l);
		Iterator<?> it = msgList.iterator();
		if(type != null){
			
			if (type.equals("xml")){
				Document doc = DocumentHelper.createDocument();
				Element root = doc.addElement("msglist");
				while (it.hasNext()){
					Object[] obj = (Object[])it.next();
					Element sub = root.addElement("msg");
					sub.addElement("mid").setText(obj[0].toString());
					sub.addElement("uid").setText(obj[1].toString());
					sub.addElement("username").setText(obj[8].toString());
					sub.addElement("content").setText(obj[2].toString());
					if (obj[3] != null)
						sub.addElement("images").setText(obj[3].toString());
					else 
						sub.addElement("images").setText("");
					if (obj[4] != null)
						sub.addElement("video").setText(obj[4].toString());
					else 
						sub.addElement("video").setText("");
					if (obj[5] != null)
						sub.addElement("music").setText(obj[5].toString());
					else 
						sub.addElement("music").setText("");
					if (obj[6] != null)
						sub.addElement("location").setText(obj[6].toString());
					else 
						sub.addElement("location").setText("");
					if (obj[7] != null)
						sub.addElement("posttime").setText(obj[7].toString());
					else 
						sub.addElement("posttime").setText("");
					if (obj[9] != null) 
						sub.addElement("photo").setText(obj[9].toString());
					else
						sub.addElement("photo").setText("no-image.gif");
				}
				return new XmlView(doc);
			} else if (type.equals("json")){
				StringBuffer sb = new StringBuffer();
				sb.append("{\"success\": true, \"data\": [");
				while(it.hasNext()){
					Object[] obj = (Object[])it.next();
					sb.append("{\"mid\":\"" + obj[0].toString() + "\",");
					sb.append("\"uid\":\"" + obj[1].toString() + "\",");
					sb.append("\"username\":\"" + obj[8].toString() + "\",");
					sb.append("\"content\":\"" + obj[2].toString() + "\",");
					if (obj[3] != null)
						sb.append("\"images\":\"" + obj[3].toString() + "\",");
					if (obj[4] != null)
						sb.append("\"video\":\"" + obj[4].toString() + "\",");
					if (obj[5] != null)
						sb.append("\"music\":\"" + obj[5].toString() + "\",");
					if (obj[6] != null)
						sb.append("\"location\":\"" + obj[6].toString() + "\",");
					if (obj[7] != null)
						sb.append("\"posttime\":\"" + obj[7].toString() + "\",");
					if (obj[9] != null)
						sb.append("\"photo\":\"" + obj[9].toString() + "\"},");
					else 
						sb.append("\"photo\":\"no-image.gif\"},");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("]}");
				return new HtmlView(sb.toString());
			}
		}
		return new HtmlView("{\"success\": true, \"msg\": \"数据加载失败！\"}");
	}
}
