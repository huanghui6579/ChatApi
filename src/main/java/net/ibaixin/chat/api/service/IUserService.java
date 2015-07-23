package net.ibaixin.chat.api.service;

import java.util.List;

import net.ibaixin.chat.api.model.User;
import net.ibaixin.chat.api.model.UserDto;

public interface IUserService {
	public boolean add(User user) throws Exception;
	
	public boolean delete(String id) throws Exception;
	
	public boolean update(User user) throws Exception;
	
	public User getUser(String id) throws Exception;
	
	public List<User> getUsers() throws Exception;
	
	public List<User> getUsers(UserDto userDto) throws Exception;
}
