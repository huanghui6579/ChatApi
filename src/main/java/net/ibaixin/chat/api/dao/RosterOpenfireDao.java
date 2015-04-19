package net.ibaixin.chat.api.dao;

import java.util.List;

import net.ibaixin.chat.api.model.User;

/**
 * 访问openfire的好友信息列表持久层
 * @author tiger
 * @version 2015年4月18日 上午11:18:03
 */
public interface RosterOpenfireDao {
	/**
	 * 根据用户名获得该用户的所有好友列表
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月18日 上午11:19:32
	 * @param username
	 * @return
	 */
	public List<String> getRosters(String username);
}
