package com.monitor.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.monitor.dao.AuthorityDao;
import com.monitor.dao.UserDao;
import com.monitor.model.User;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	private AuthorityDao authorityDao;


	public boolean addUser(User user) {
		this.userDao.addUser(user);
		String uid = user.getUid();
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("uid", uid)
		};
		Integer count = this.userDao.countUser(criterions);
		if (count > 0) {
			return true;
		} 
		return false;
	}

	public boolean delUser(User user) {
		this.userDao.delUser(user);
		String uid = user.getUid();
		Criterion[] criterions = new Criterion[]{
			Restrictions.eq("uid", uid)
		};
		Integer count = this.userDao.countUser(criterions);
		if (count == 0) {
			return true;
		} 
		return false;
	}

	public Object queryUser(Criterion[] criterions) {
		return this.userDao.queryUser(criterions);
	}

	@SuppressWarnings("unchecked")
	public List<User> queryUserList(Criterion[] criterions, int start, int limit) {
		return (List<User>) this.userDao.queryUserList(criterions, start, limit);
	}
	
	public Integer coutUser(Criterion[] criterions) {
		return this.userDao.countUser(criterions);
	}

	public void updateUser(User user) {
		this.userDao.updateUser(user);
	}

	public AuthorityDao getAuthorityDao() {
		return authorityDao;
	}
	
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User findByName(Criterion[] criterions) {
		// TODO Auto-generated method stub
		return this.userDao.findByName(criterions);
	}
	
}
