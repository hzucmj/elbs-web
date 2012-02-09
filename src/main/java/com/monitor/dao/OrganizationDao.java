package com.monitor.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.monitor.model.Organization;

public interface OrganizationDao {

	//添加组织
	public void addOrganization(Organization org);
	
	//删除组织
	public void delOrganization(Organization org);
	
	//更新组织信息
	public void updateOrganization(Organization org);
	
	//搜索组织
	public List<?> queryOrganization(Criterion[] criterions, int start, int limit);
	public Object queryUniqueOrgation(Criterion[] criterions);

	public void updateOrg(Organization org);
}
