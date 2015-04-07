package net.ibaixin.chat.api.service;

import java.util.List;

import net.ibaixin.chat.api.model.User;
import net.ibaixin.chat.api.model.UserDto;

public interface IUserService {
	public void add(User user);
	
	public void delete(String id);
	
	public void update(User user);
	
	public User getUser(String id);
	
	public List<User> getUsers();
	
	public List<User> getUsers(UserDto userDto);
}
