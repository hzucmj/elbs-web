package com.monitor.service;

import org.hibernate.criterion.Criterion;

import com.monitor.model.User;

public interface AuthorityService {
	
	public void addAuthority(User user, String authority);
	
	public void delAuthority(User user);
	
	public Object queryAuthority(Criterion[] criterions);
	
}
