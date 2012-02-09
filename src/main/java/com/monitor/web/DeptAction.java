package com.monitor.web;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.MultipleAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.HtmlView;

import com.monitor.Functions;
import com.monitor.model.Organization;
import com.monitor.model.User;
import com.monitor.service.OrganizationService;
import com.monitor.service.UserService;

public class DeptAction extends MultipleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UserService userService;
	OrganizationService organizationService;

	private String uid;
	private String oid;

	private String delDept;
	private String newDept;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDelDept() {
		return delDept;
	}

	public void setDelDept(String delDept) {
		this.delDept = delDept;
	}

	public String getNewDept() {
		return newDept;
	}

	public void setNewDept(String newDept) {
		this.newDept = newDept;
	}

	/**
	 * 显示当前用户所在组织的所有部门
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doShowDept() throws MvcException {
		String role = Functions.getLocalAuth();
		if (role.equals("ROLE_MASTER")) {
			StringBuffer sb = new StringBuffer(
					"{\"success\": true, \"depts\": \"" + this.getDept() + "\"}");
			return new HtmlView(sb.toString());
		} else {
			return new HtmlView(
					"{\"success\": false, \"msg\": \"你当前权限无法浏览该页面！\"}");
		}
	}
	
	
	/**
	 * 获取当前用户所在组织
	 * @return
	 */
	public Organization getLocalOrganization() {
		Criterion[] uCriterion = new Criterion[] { Restrictions.eq(
				"username", Functions.getLocalUser()) };
		User u = (User) this.userService.queryUser(uCriterion);
		Criterion[] oCriterion = new Criterion[] { Restrictions.eq(
				"organizer", u.getUid()) };
		Organization org = (Organization) organizationService
				.queryUniqueOrganization(oCriterion);
		return org;
	}
	
	/**
	 * 获取当前用户所在组织的所有部门，以字符串返回
	 * @return
	 */
	public String getDept() {
		String deptString = this.getLocalOrganization().getDepartment();
		return deptString;
	}

	/**
	 * 添加部门
	 * 
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doAdd() throws MvcException {
		String newDept = this.getNewDept();
		String deptString = this.getDept();
		//判断要增加的部门是否已存在
		if (deptString.indexOf(newDept) >= 0){
			return new HtmlView("{\"success\": false, \"msg\": \"该部门已经存在！\"}");
		}
		deptString = deptString + "," + newDept;
		Organization org = this.getLocalOrganization();
		org.setDepartment(deptString);
		this.organizationService.updateOrganization(org);
		return new HtmlView("{\"success\": true, \"msg\": \"添加成功\"}");
	}

	/**
	 * 判断要删除的部门是否有人使用,false有人使用,true没人使用
	 * @return
	 */
	public boolean checkExistDept(String dept){
		Organization org = this.getLocalOrganization();
		Criterion[] uCri = new Criterion[]{
			Restrictions.eq("uid", org.getOrganizer())
		};
		User u = (User) this.userService.queryUser(uCri);
		if (u.getDepartment().trim().equals(delDept)) return false;
		uCri = new Criterion[]{
			Restrictions.eq("root", u.getUsername()),
			Restrictions.eq("department", delDept)
		};
		List<User> userList = this.userService.queryUserList(uCri, 0, 100000);
		if (userList.size() > 0){
			return  false;
		}
		return true;
	}
	
	/**
	 * 删除某组织的相关部门
	 * @return
	 * @throws MvcException
	 */
	public AbstractView doDel() throws MvcException {
		//获取要删除的部门
		String delDept = this.getDelDept();
		//判断要删除的部门内是否有成员
		if (this.checkExistDept(delDept)) {
			String deptString = this.getDept();
			int deptIdx = deptString.indexOf(delDept);
			//判断要删除的部门是否存在
			if (deptIdx >= 0) {
				String[] depts = deptString.split(",");
				Vector<String> vector = new Vector<String>();
				for (int i = 0; i < depts.length; i++) {
					if (!depts[i].trim().equals(delDept)){
						vector.add(depts[i]);
					}
				}
				deptString = "";
				Iterator<String> it = vector.iterator();
				while (it.hasNext()) {
					deptString += it.next() + ",";
				}
				deptString = deptString.substring(0, deptString.length() - 1);
				Organization org = this.getLocalOrganization();
				org.setDepartment(deptString);
				this.organizationService.update(org);
				return new HtmlView("{\"success\": true, \"msg\": \"删除部门成功！\"}");
			} else {
				return new HtmlView("{\"success\": false, \"msg\": \"该部门不存在！\"}");
			}
		} else {
			return new HtmlView("{\"success\": false, \"msg\": \"该部门内有成员，请将成员移出部门再执行该操作！\"}");
		}
	}
}
