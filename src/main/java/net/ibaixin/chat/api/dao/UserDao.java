package net.ibaixin.chat.api.dao;

import java.util.List;

import net.ibaixin.chat.api.model.User;
import net.ibaixin.chat.api.model.UserDto;

public interface UserDao {
	public void addUser(User user);
	
	public void deleteUser(String id);
	
	public void updateUser(User user);
	
	public User getUser(String id);
	
	public List<User> getUsers();
	
	public List<User> getUsersPage(UserDto userDto);
}
