package net.ibaixin.chat.api.dao;

import java.util.List;

import net.ibaixin.chat.api.model.User;
import net.ibaixin.chat.api.model.UserDto;

public interface UserDao {
	public int addUser(User user) throws Exception;
	
	public int deleteUser(String id) throws Exception;
	
	/**
	 * 批量删除用户
	 * @param ids 用户账号的集合
	 * @return
	 * @throws Exception
	 * @update 2015年7月23日 上午10:31:19
	 */
	public int deleteUsers(List<String> ids) throws Exception;
	
	public int updateUser(User user) throws Exception;
	
	public User getUser(String id) throws Exception;
	
	public List<User> getUsers() throws Exception;
	
	public List<User> getUsersPage(UserDto userDto) throws Exception;
}
