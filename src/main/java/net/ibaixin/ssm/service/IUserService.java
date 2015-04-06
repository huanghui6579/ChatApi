package net.ibaixin.ssm.service;

import net.ibaixin.ssm.model.User;

public interface IUserService {
	public void add(User user);
	
	public void delete(String id);
	
	public void update(User user);
}
