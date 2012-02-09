package com.monitor.web;

import java.util.Date;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.MultipleAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.HtmlView;
import org.zengsource.util.IDUtil;

import com.monitor.Functions;
import com.monitor.model.Authority;
import com.monitor.model.User;
import com.monitor.service.AuthorityService;
import com.monitor.service.UserService;

public class UserAction extends MultipleAction {

	/**
	 * 
	 */
	
	
	private String uid;
	private String username;
	private String password;
	private String sex;
	private String idcard;
	private String employeeno;
	private String photo;
	private String nickname;
	private String email;
	private String department;
	private String root;
	private String contact;
	private String enabled;
	private String birthday;
	
	private static final long serialVersionUID = 1L;
	private AuthorityService authorityService;
	UserService userService;
	
	public AbstractView doShowMember() throws MvcException{
		System.out.println("=here===============================");
		return null;
	}
	
	//添加员工
	public AbstractView doAdd() throws MvcException {
		User user = this.createUser();
		boolean bool = this.userService.addUser(user);
		this.authorityService.addAuthority(user, "ROLE_USER");
		if (bool) {
			// 添加成功
			return new HtmlView("{\"success\": true, \"msg\" : \"添加成员成功！\"}");
		} else {
			// 添加不成功
			return new HtmlView(
					"{\"success\": false, \"msg\" : \"添加成员失败，请稍候注册！\"}");
		}
	}
	//创建一个用户
	public User createUser(){
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = new User();
		String uid = IDUtil.generate();
		user.setUid(uid);
		user.setUsername(this.getUsername());
		user.setPassword(this.getPassword());
		user.setSex(this.getSex());
		user.setIdcard(this.getIdcard());
		user.setEmployeeno(this.getEmployeeno());
		user.setPhoto(this.getPhoto());
		user.setNickname(this.getNickname());
		user.setBirthday(Functions.strToDate(this.getBirthday(), "yyyy-mm-dd"));
		user.setEmail(this.getEmail());
		user.setDepartment(this.getDepartment());
		user.setRoot(currentUser);//新添加的员工上级为当前用户
		user.setContact(this.getContact());
		user.setEnabled(true);//默认激活
		user.setCreatedtime(new Date());
		user.setUpdatedtime(new Date());
		return user;
	}
	
	//更新用户信息
	public AbstractView doUpdateUser() throws MvcException {
		String uid = this.getUid();
		Criterion[] criterions = new Criterion[]{
				Restrictions.eq("uid", uid)
		};
		User user = (User) this.userService.queryUser(criterions);
		
		user.setUsername(this.getUsername());
		user.setPassword(this.getPassword());
		user.setSex(this.getSex());
		user.setIdcard(this.getIdcard());
		user.setEmployeeno(this.getEmployeeno());
		user.setPhoto(this.getPhoto());
		user.setNickname(this.getNickname());
		user.setEmail(this.getEmail());
		user.setDepartment(this.getDepartment());
		user.setContact(this.getContact());
		user.setBirthday(Functions.strToDate(this.getBirthday(), "yyyy-mm-dd"));
		
		if (this.getEnabled().equals("true"))
			user.setEnabled(true);
		else 
			user.setEnabled(false);
		
		user.setUpdatedtime(new Date());
		return null;
	}
	

	//用户激活
	public AbstractView doActiveUser() throws MvcException {
		String uid = this.getUid();
		Criterion[] criterions = new Criterion[]{
				Restrictions.eq("uid", uid)
		};
		User user = (User) this.userService.queryUser(criterions);
		user.setEnabled(true);
		this.userService.updateUser(user);
		return null;
	}
	
	//禁用用户 
	public AbstractView doForbidUser() throws MvcException {
		String uid = this.getUid();
		Criterion[] criterions = new Criterion[]{
				Restrictions.eq("uid", uid)
		};
		User user = (User) this.userService.queryUser(criterions);
		user.setEnabled(false);
		this.userService.updateUser(user);
		return null;
	}
	
	//查看用户是否已存在 
	public AbstractView doCheckUser() throws MvcException {
		this.getResponse().setHeader("Content-Type", "text/json");
		System.out.println("-------------->"+this.getUsername());
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("username", this.getUsername())
		};
		int count = this.userService.coutUser(criterions);
		if (count > 0){
			return new HtmlView("{\"success\":false}");
		}
		return new HtmlView("{\"success\":true}");
	}
	
	//管理员登录
	public AbstractView doAdminLogin() throws MvcException {
		String uname = this.getUsername();
		String upwd = this.getPassword();
		System.out.println(uname + "===" + upwd);
		//查询该用户信息
		Criterion[] userCri = new Criterion[]{
			Restrictions.eq("username", uname)
		};
		User u = (User) this.userService.queryUser(userCri);
		if (u == null) {
			return new HtmlView("{\"success\" : false, \"msg\" : \"用户不存在！\"}");
		}
		//查看该用户权限
		Criterion[] authCri = new Criterion[]{
			Restrictions.eq("aid", u.getUid())
		};
		Authority authObj = (Authority) this.authorityService.queryAuthority(authCri);
		//获取权限
		String auth = authObj.getAuthority();
		System.out.println(auth);
		if (auth != null){
			if (auth.equals("ROLE_ADMIN")) {
				//如果是管理员，允许登录并验证登录信息
				if (u.getPassword().equals(upwd)) {
					//密码与数据库的一致，登录成功
					return new HtmlView("{\"success\" : true, \"msg\" : \"登录成功，转入后台管理系统...\"}");
				} else {
					//密码与数据库的不一致，提示错误信息
					return new HtmlView("{\"success\" : false, \"msg\" : \"密码错误，请重新输入！\"}");
				}
			} else {
				//非管理员，不可登录，提示错误信息
				return new HtmlView("{\"success\" : false, \"msg\" : \"仅限系统管理员登录\"}");
			}
		}
		return new HtmlView("{\"success\" : false, \"msg\" : \"用户无权限进入后台系统！\"}");
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}


}
