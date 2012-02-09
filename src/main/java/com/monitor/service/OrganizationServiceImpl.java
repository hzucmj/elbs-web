package com.monitor.service;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.monitor.dao.OrganizationDao;
import com.monitor.model.Organization;

public class OrganizationServiceImpl implements OrganizationService {

	OrganizationDao organizationDao;
	
	public void addOrganization(Organization org) {
		this.organizationDao.addOrganization(org);
	}

	public void delOrganization(Criterion[] criterions) {
		Organization org = (Organization) this.organizationDao.queryUniqueOrgation(criterions);
		this.organizationDao.delOrganization(org);
	}

	public void updateOrganization(Organization org) {
		this.organizationDao.updateOrganization(org);
	}

	public List<?> queryOrganization(Criterion[] criterions, int start,
			int limit) {
		return this.organizationDao.queryOrganization(criterions, start, limit);
	}

	public Object queryUniqueOrganization(Criterion[] criterions) {
		return this.organizationDao.queryUniqueOrgation(criterions);
	}

	public void update(Organization org) {
		this.organizationDao.updateOrg(org);
	}
	
	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}


}
