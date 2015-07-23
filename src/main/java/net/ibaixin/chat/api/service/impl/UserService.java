package net.ibaixin.chat.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ibaixin.chat.api.dao.UserDao;
import net.ibaixin.chat.api.model.User;
import net.ibaixin.chat.api.model.UserDto;
import net.ibaixin.chat.api.service.IUserService;

/**
 * 用户的service
 * @author huanghui1
 *
 */
@Service("userService")
public class UserService implements IUserService {
	private UserDao userMapper;
	
	@Autowired
	public void setUserMapper(UserDao userMapper) throws Exception {
		this.userMapper = userMapper;
	}

	@Override
	public boolean add(User user) throws Exception {
		int count = userMapper.addUser(user);
		return count > 0;
	}

	@Override
	public boolean delete(String id) throws Exception {
		int count = userMapper.deleteUser(id);
		return count > 0;
	}

	@Override
	public boolean update(User user) throws Exception {
		int count = userMapper.updateUser(user);
		return count > 0;
	}

	@Override
	public User getUser(String id) throws Exception {
		return userMapper.getUser(id);
	}

	@Override
	public List<User> getUsers() throws Exception {
		return userMapper.getUsers();
	}

	@Override
	public List<User> getUsers(UserDto userDto) throws Exception {
		return userMapper.getUsersPage(userDto);
	}

}
