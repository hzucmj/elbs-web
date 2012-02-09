package com.monitor.web;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.MultipleAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.HtmlView;

import com.monitor.Functions;
import com.monitor.model.Organization;
import com.monitor.model.User;
import com.monitor.service.AuthorityService;
import com.monitor.service.OrganizationService;
import com.monitor.service.UserService;

public class MemberAction extends MultipleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UserService userService;
	AuthorityService authorityService;
	OrganizationService organizationService;

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
	private String oldpassword;
	
	private final static int ML = 10;
	
	public AbstractView doService() throws MvcException {
		String action = (String) this.getRequest().getParameter("action");
		if (action == null){
			String page = this.getRequest().getParameter("page");
			int pageNum = (page == null) ? 1 : Integer.parseInt(page);
			System.out.println("pageNum  :" + pageNum);
			List<User> memberList = this.queryMember(pageNum, ML);
			this.getRequest().setAttribute("memberList", memberList);
		}
		return super.doService();
	}
	
	//获取当前用户id
	public AbstractView doGetLocalUserId() throws MvcException{
		String currentUser = Functions.getLocalUser();
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("username", currentUser)
		};
		User user = (User) this.userService.queryUser(criterions);
		return new HtmlView("{\"success\" : true, \"uid\" : \"" + user.getUid() + "\"}");
	}
	
	//搜索用户
	public List<User> queryMember(int pageNum, int limit){
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Criterion[] criterions = new Criterion[]{
				Restrictions.eq("root", currentUser)
		};
		//组织成员个数
		double userCount = this.userService.coutUser(criterions);
		System.out.println(userCount / limit);
		//显示页面数
		int pageCount = (int) Math.ceil(userCount / limit);
		//接收前台页码,如果参数小于0，则查看第一页；如果参数大于页面数，则查看最后一页
		if (pageNum <= 0){pageNum = 1;}
		if (pageNum > pageCount){pageNum = pageCount;}
		//设置当前页
		this.getRequest().setAttribute("currentPage", pageNum);
		//设置总页数
		this.getRequest().setAttribute("pageCount", pageCount);
		//获取列表信息
		System.out.println(pageNum + "====================");
		List<User> memberList = this.userService.queryUserList(criterions, (pageNum - 1) * limit, limit);
		Criterion[] uCri = new Criterion[]{
				Restrictions.eq("username", currentUser)
		};
		User u = (User) this.userService.queryUser(uCri);
		memberList.add(0, u);
		return memberList;
	}
	
	//激活账号
	public AbstractView doActiveMember() throws MvcException {
		String username = this.getUsername();
		String enabled = this.getEnabled(); 
		Criterion[] criterions = new Criterion[]{
				Restrictions.eq("username", username)
		};
		User user = (User) this.userService.queryUser(criterions);
		if (enabled.equals("1")){
			user.setEnabled(true);
		} else {
			user.setEnabled(false);
		}
		this.userService.updateUser(user);
		return null;
	}
	
	//删除用户 
	public AbstractView doDelMember() throws MvcException {
		//从前台获取成员账号
		String username = this.getUsername();
		//搜索条件
		Criterion[] criterions = new Criterion[]{
				Restrictions.eq("username", username)
		};
		//是否存在该成员记录
		int count = this.userService.coutUser(criterions);
		if (count <= 0) {
			return new HtmlView("{\"failure\": true, \"msg\" : \"不存在该成员！\"}");
		}
		//搜索成员
		User user = (User) this.userService.queryUser(criterions);
		//先删除权限
		this.authorityService.delAuthority(user);
		//再删除成员
		boolean bool = this.userService.delUser(user);
		if (bool){
			//删除成功
			return new HtmlView("{\"success\": true, \"msg\" : \"删除成员成功！\"}");
		} else {
			//删除失败
			return new HtmlView("{\"failure\": true, \"msg\" : \"删除成员失败，请稍候再试！\"}");
		}
	}
	
	//更新成员信息
	public AbstractView doUpdateMember() throws MvcException {
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("uid", this.getUid())
		};
		User user = (User) this.userService.queryUser(criterions);
		user.setNickname(this.getNickname());
		user.setPhoto(this.getPhoto());
		user.setBirthday(Functions.strToDate(this.getBirthday(), "yyyy-mm-dd"));
		user.setContact(this.getContact());
		user.setEmail(this.getEmail());
		user.setDepartment(this.getDepartment());
		user.setIdcard(this.getIdcard());
		user.setSex(this.getSex());
		user.setUpdatedtime(new Date());
		this.userService.updateUser(user);
		return new HtmlView("{\"success:\" : true, \"msg\" : \"更新成功！\"}");
	}
	
	//重置密码
	public AbstractView doSetPwd() throws MvcException {
		String uid = this.getUid();
		String pwd = this.getPassword();
		String opwd = this.getOldpassword();
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("uid", uid)	
		};
		User user = (User) this.userService.queryUser(criterions);
		if (opwd.equals(user.getPassword())){
			user.setPassword(pwd);
			this.userService.updateUser(user);
			return new HtmlView("{\"success\": true, \"msg\": \"密码修改成功！\"}");
		} else {
			return new HtmlView("{\"success\": false, \"msg\": \"旧密码错误！\"}");
		} 
	}
	
	//获得当前用户权限并返回前台
	public AbstractView doGetAuthority() throws MvcException {
		
		String auth = Functions.getLocalAuth();
		System.out.println(this.getRequest().getParameter("action"));
		
		return new HtmlView("{\"success\" : true, \"auth\" : \"" + auth + "\"}");
	}
	
	//获得成员信息,通过id获取成员的详细信息
	public AbstractView doView() throws MvcException {
		String uid = this.getRequest().getParameter("id");
		
		Criterion[] criterions = new Criterion[]{
				Restrictions.eq("uid", uid)
		};
		User user = (User) this.userService.queryUser(criterions);

		System.out.println(user.getBirthday());
		Organization org = new Organization();
		if (user.getRoot().equals("root")) {
			System.out.println(user.getUid());
			Criterion[] orgCriterions = new Criterion[]{
				Restrictions.eq("organizer", user.getUid())
			};
			//获取成员所在组织名称
			org = (Organization) this.organizationService.queryUniqueOrganization(orgCriterions);
		} else {
			System.out.println(user.getRoot());
			Criterion[] rootCriterions = new Criterion[]{
				Restrictions.eq("username", user.getRoot())	
			};
			User u = (User) this.userService.queryUser(rootCriterions);
			System.out.println(u.getUid());
			Criterion[] orgCriterions = new Criterion[]{
				Restrictions.eq("organizer", u.getUid())	
			};
			//获取成员所在组织名称
			org = (Organization) this.organizationService.queryUniqueOrganization(orgCriterions);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"user_role\" : \"" + Functions.getLocalAuth() + "\",");
		sb.append("\"uid\" : \"" + user.getUid() + "\",");
		sb.append("\"username\" : \"" + user.getUsername() + "\",");
		sb.append("\"nickname\" : \"" + user.getNickname() + "\",");
		sb.append("\"sex\" : \"" + user.getSex() + "\",");
		sb.append("\"photo\" : \"" + user.getPhoto() + "\",");
		sb.append("\"birthday\" : \"" + user.getBirthday() + "\",");
		sb.append("\"email\" : \"" + user.getEmail() + "\",");
		sb.append("\"organization\" : \"" + org.getOrganization() + "\",");
		sb.append("\"allDepts\" : \"" + org.getDepartment() + "\",");
		sb.append("\"department\" : \"" + user.getDepartment() + "\",");
		sb.append("\"contact\" : \"" + user.getContact() + "\",");
		sb.append("\"idcard\" : \"" + user.getIdcard() + "\",");
		sb.setLength(sb.length() - 1);
		sb.append("}");
		return new HtmlView(sb.toString());
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

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	
	
	
}
