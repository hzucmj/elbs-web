package com.monitor.service;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.monitor.model.Organization;

public interface OrganizationService {

	public void addOrganization(Organization org);

	public void delOrganization(Criterion[] criterions);

	public void updateOrganization(Organization org);

	public List<?> queryOrganization(Criterion[] criterions, int start,
			int limit);

	public Object queryUniqueOrganization(Criterion[] criterions);
	
	//==hzucmj
	public void update(Organization org);
	//==hzucmj

}
