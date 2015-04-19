package net.ibaixin.chat.api.service.impl;

import java.util.List;

/**
 * 
 * @author tiger
 * @version 2015年4月18日 上午11:24:34
 */
public interface IRosterOpenfireService {
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
