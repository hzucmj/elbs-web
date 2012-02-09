package com.monitor.service;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zengsource.util.IDUtil;

import com.monitor.dao.AuthorityDao;
import com.monitor.model.Authority;
import com.monitor.model.User;

public class AuthorityServiceImpl implements AuthorityService {

	AuthorityDao authorityDao;
	
	public void addAuthority(User user, String authority) {
		Authority auth = new Authority();
		auth.setAid(IDUtil.generate());
		auth.setUid(user.getUid());
		auth.setAuthority(authority);
		this.authorityDao.addAuthority(auth);
	}

	public void delAuthority(User user) {
		Criterion[] criterions = new Criterion[]{
				Restrictions.eq("uid", user.getUid())
		};
		//Authority auth = (Authority) this.authorityDao.queryUserAuth(criterions);
		Authority auth = this.authorityDao.queryAuth(criterions);
		this.authorityDao.delAuthority(auth);
	}

	public Object queryAuthority(Criterion[] criterions) {
		return this.authorityDao.queryAuth(criterions);
	}
	
	public AuthorityDao getAuthorityDao() {
		return authorityDao;
	}

	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}


}
