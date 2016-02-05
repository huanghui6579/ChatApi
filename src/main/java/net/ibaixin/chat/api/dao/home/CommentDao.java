package net.ibaixin.chat.api.dao.home;

import net.ibaixin.chat.api.model.home.HomeComment;

/**
 * 用户反馈的数据库持久层
 * @author huanghui1
 * @version 1.0.0
 * @update 2016年2月5日 下午2:23:01
 */
public interface CommentDao {
	/**
	 * 添加用户反馈
	 * @param comment
	 * @update 2016年2月5日 下午2:24:30
	 */
	public void addComment(HomeComment comment);
}
