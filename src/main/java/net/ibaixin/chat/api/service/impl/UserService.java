package net.ibaixin.chat.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.ibaixin.chat.api.dao.UserDao;
import net.ibaixin.chat.api.model.User;
import net.ibaixin.chat.api.model.UserDto;
import net.ibaixin.chat.api.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户的service
 * @author huanghui1
 *
 */
@Service("userService")
public class UserService implements IUserService {
	private UserDao userMapper;
	
	@Autowired
	public void setUserMapper(UserDao userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public void add(User user) {
		userMapper.addUser(user);
	}

	@Override
	public void delete(String id) {
		userMapper.deleteUser(id);
	}

	@Override
	public void update(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public User getUser(String id) {
		return userMapper.getUser(id);
	}

	@Override
	public List<User> getUsers() {
		return userMapper.getUsers();
	}

	@Override
	public List<User> getUsers(UserDto userDto) {
		return userMapper.getUsersPage(userDto);
	}

}
