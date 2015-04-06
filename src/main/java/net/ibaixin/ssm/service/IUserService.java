package net.ibaixin.ssm.service;

import java.util.List;

import net.ibaixin.ssm.model.User;
import net.ibaixin.ssm.model.UserDto;

public interface IUserService {
	public void add(User user);
	
	public void delete(String id);
	
	public void update(User user);
	
	public User getUser(String id);
	
	public List<User> getUsers();
	
	public List<User> getUsers(UserDto userDto);
}
