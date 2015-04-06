package net.ibaixin.ssm.dao;

import net.ibaixin.ssm.model.User;

public interface IUserDao {
	public void addUser(User user);
	
	public void deleteUser(String id);
	
	public void updateUser(User user);
	
	public User getUser(int id);
}
