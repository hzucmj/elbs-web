package com.monitor.dao.orm;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.zengsource.util.spring.dao.orm.HibernateDaoTemplate;

import com.monitor.dao.OrganizationDao;
import com.monitor.model.Organization;

public class HibernateOrganizationDao extends HibernateDaoTemplate implements
		OrganizationDao {

	public void addOrganization(Organization org) {
		this.hibernateTemplate.save(org);
	}

	public void delOrganization(Organization org) {
		this.hibernateTemplate.delete(org);
	}

	public void updateOrganization(Organization org) {
		this.hibernateTemplate.update(org);
	}

	public List<?> queryOrganization(Criterion[] criterions, int start,
			int limit) {
		return this.query(criterions, start, limit);
	}

	public Object queryUniqueOrgation(Criterion[] criterions) {
//		return this.queryUnique(criterions);
		//return this.query(criterions, 0, 1).get(0);
		return this.queryUnique(criterions);
	}

	@Override
	public Class<?> getPrototypeClass() {
		// TODO Auto-generated method stub
		return Organization.class;
	}

	public void updateOrg(Organization org) {
		super.hibernateTemplate.update(org);
	}

}
