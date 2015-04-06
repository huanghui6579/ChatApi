package net.ibaixin.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ibaixin.ssm.dao.IUserDao;
import net.ibaixin.ssm.model.User;
import net.ibaixin.ssm.service.IUserService;

/**
 * 用户的service
 * @author huanghui1
 *
 */
@Service("userService")
public class UserService implements IUserService {
	@Autowired
	private IUserDao userMapper;

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

}
