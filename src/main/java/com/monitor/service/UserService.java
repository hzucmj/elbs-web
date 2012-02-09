package com.monitor.service;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.monitor.model.User;

public interface UserService {

	/* 添加用户 */
	public boolean addUser(User user);

	/* 删除用户 */
	public boolean delUser(User user);

	/* 查找用户 */
	public Object queryUser(Criterion[] criterions);
	public List<User> queryUserList(Criterion[] criterions, int start, int limit);

	/* 更新用户 */
	public void updateUser(User user);
	
	/* 统计用户*/
	public Integer coutUser(Criterion[] criterions);
	
	/*  查询得到id   */
	public User findByName(Criterion[] criterions);

}
