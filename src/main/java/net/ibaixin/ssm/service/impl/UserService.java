package net.ibaixin.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.ibaixin.ssm.dao.UserMapper;
import net.ibaixin.ssm.model.User;
import net.ibaixin.ssm.model.UserDto;
import net.ibaixin.ssm.service.IUserService;

import org.springframework.stereotype.Service;

/**
 * 用户的service
 * @author huanghui1
 *
 */
@Service("userService")
public class UserService implements IUserService {
	private UserMapper userMapper;
	
	@Resource
	public void setUserMapper(UserMapper userMapper) {
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
