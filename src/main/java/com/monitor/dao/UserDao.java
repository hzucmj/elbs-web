package com.monitor.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.monitor.model.User;

public interface UserDao {

	/* 添加用户 */
	public void addUser(User user);

	/* 删除用户 */
	public void delUser(User user);

	/* 查找用户 */
	public Object queryUser(Criterion[] criterions);
	public List<?> queryUserList(Criterion[] criterions, int start, int limit);

	/* 更新用户 */
	public void updateUser(User user);
	
	/*统计用户*/
	public Integer countUser(Criterion[] criterions);

	/*  查询得到id   */
	public User findByName(Criterion[] criterions);

}
