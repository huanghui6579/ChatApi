package net.ibaixin.ssm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.ibaixin.ssm.model.User;
import net.ibaixin.ssm.model.UserDto;

@Repository
public interface UserMapper {
	public void addUser(User user);
	
	public void deleteUser(String id);
	
	public void updateUser(User user);
	
	public User getUser(String id);
	
	public List<User> getUsers();
	
	public List<User> getUsersPage(UserDto userDto);
}
