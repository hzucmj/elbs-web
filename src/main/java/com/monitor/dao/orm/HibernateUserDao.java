package com.monitor.dao.orm;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.zengsource.util.spring.dao.orm.HibernateDaoTemplate;

import com.monitor.dao.UserDao;
import com.monitor.model.User;

public class HibernateUserDao extends HibernateDaoTemplate implements UserDao {

	@Override
	public Class<?> getPrototypeClass() {
		return User.class;
	}

	public void addUser(User user) {
		this.hibernateTemplate.save(user);
	}

	public void delUser(User user) {
		this.hibernateTemplate.delete(user);
	}

	public Object queryUser(Criterion[] criterions) {
		return this.queryUnique(criterions);
	}

	public Object queryUserList(Criterion[] criterions) {
		return this.queryUnique(criterions);
	}
	
	public void updateUser(User user) {
		this.hibernateTemplate.update(user);
	}

	public Integer countUser(Criterion[] criterions) {
		return this.queryCount(criterions);
	}

	public List<?> queryUserList(Criterion[] criterions, int start, int limit) {
		return this.query(criterions, start, limit);
	}

	public User findByName(Criterion[] criterions) {
		// TODO Auto-generated method stub
		return (User)this.queryUnique(criterions);
	}
}
