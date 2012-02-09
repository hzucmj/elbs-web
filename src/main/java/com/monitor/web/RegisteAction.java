package com.monitor.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.MultipleAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.HtmlView;
import org.zengsource.util.IDUtil;

import com.monitor.model.Organization;
import com.monitor.model.User;
import com.monitor.service.AuthorityService;
import com.monitor.service.OrganizationService;
import com.monitor.service.UserService;

public class RegisteAction extends MultipleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService userService;
	private AuthorityService authorityService;
	private OrganizationService organizationService;

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
	private String birthday;
	private String organization;

	// 注册新用户
	public AbstractView doRegiste() throws MvcException {
		User user = this.createUser();
		boolean bool = this.userService.addUser(user);
		this.authorityService.addAuthority(user, "ROLE_MASTER");
		
		Organization org = this.createOrganization(user);
		this.organizationService.addOrganization(org);
		
		if (bool) {
			// 添加成功
			return new HtmlView("{\"success\": true, \"msg\" : \"恭喜你，注册成功！\"}");
		} else {
			// 添加不成功
			return new HtmlView(
					"{\"success\": false, \"msg\" : \"注册失败，请稍候注册！\"}");
		}
	}

	public User createUser() {
		User user = new User();
		String uid = IDUtil.generate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date birthday = null;
		System.out.println("birthday from fre" + this.getBirthday());
		try {
			birthday = sdf.parse(this.getBirthday());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setUid(uid);
		user.setUsername(this.getUsername());
		user.setPassword(this.getPassword());
		user.setSex(this.getSex());
		user.setIdcard(this.getIdcard());
		user.setEmployeeno(this.getEmployeeno());
		user.setPhoto(this.getPhoto());
		user.setNickname(this.getNickname());
		user.setBirthday(birthday);
		user.setEmail(this.getEmail());
		user.setDepartment(this.getDepartment());
		
		System.out.println("Department: " + this.getDepartment());
		
		user.setRoot("root");// 注册的用户默认上级为管理员
		user.setContact(this.getContact());
		user.setEnabled(false);// 默认不激活
		user.setCreatedtime(new Date());
		user.setUpdatedtime(new Date());
		return user;
	}
	
	//查看用户是否已存在 
	public AbstractView doCheckUser() throws MvcException {
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
	
	
	public AbstractView doCheckEmail() throws MvcException {
		System.out.println("-------------->"+this.getEmail());
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("email", this.getEmail())
		};
		int count = this.userService.coutUser(criterions);
		if (count > 0){
			return new HtmlView("{\"success\":false}");
		}
		return new HtmlView("{\"success\":true}");
	}
	
	public Organization createOrganization(User user) {
		Organization org = new Organization();
		org.setOid(IDUtil.generate());
		org.setOrganizer(user.getUid());
		org.setDepartment(this.getDepartment());
		org.setOrganization(this.getOrganization());
		org.setEnabled(false);
		org.setCreatedtime(new Date());
		org.setUpdatedtime(new Date());
		return org;
	}
	//激活组织者账号
	public AbstractView doActiveOrganizer() throws MvcException{
		String uid = this.getUid();
		//
		Criterion[] cu= new Criterion[]{
				Restrictions.eq("uid", uid)
		};
		User user = (User) this.userService.queryUser(cu);
		user.setEnabled(true);
		this.userService.updateUser(user);
		//
		Criterion[] co = new Criterion[]{
				Restrictions.eq("uid", uid)
		};
		Organization org = (Organization) this.organizationService.queryUniqueOrganization(co);
		org.setEnabled(true);
		this.organizationService.updateOrganization(org);
		return null;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

}
