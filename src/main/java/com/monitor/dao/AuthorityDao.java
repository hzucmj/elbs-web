package com.monitor.dao;

import org.hibernate.criterion.Criterion;

import com.monitor.model.Authority;

public interface AuthorityDao {

	//增加权限
	public void addAuthority(Authority auth);
	
	//删除权限
	public void delAuthority(Authority auth);
	
	//查找用户权限
	//public Object queryUserAuth(Criterion[] criterions);
	
	public Authority queryAuth(Criterion[] criterions);
}
