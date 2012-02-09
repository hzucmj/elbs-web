package com.monitor.dao.orm;

import org.hibernate.criterion.Criterion;
import org.zengsource.util.spring.dao.orm.HibernateDaoTemplate;

import com.monitor.dao.AuthorityDao;
import com.monitor.model.Authority;

public class HibernateAuthorityDao extends HibernateDaoTemplate implements AuthorityDao {

	@Override
	public Class<?> getPrototypeClass() {
		// TODO Auto-generated method stub
		return Authority.class;
	}

	public void addAuthority(Authority auth) {
		this.hibernateTemplate.save(auth);
	}

	public void delAuthority(Authority auth) {
		this.hibernateTemplate.delete(auth);
	}

	/*public Object queryUserAuth(Criterion[] criterions) {
		return this.queryUnique("username", "aa");
	}*/
	
	public Authority queryAuth(Criterion[] criterions){
		return (Authority) this.queryUnique(criterions);
	}

}
